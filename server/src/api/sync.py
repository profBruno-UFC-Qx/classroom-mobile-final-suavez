import time
from typing import List

from fastapi import APIRouter, Depends, Query
from loguru import logger
from sqlalchemy.orm import Session

from src.database import get_db
from src.models.activity import DBStudyActivity
from src.models.group import DBGroup
from src.models.user import DBUser
from src.schemas.activity import StudyActivity
from src.schemas.group import Group
from src.schemas.sync import (
    SyncActivityRequest,
    SyncPullResponse,
    SyncResponse,
)
from src.schemas.user import ActivityAuthor
from src.security import get_current_user

router = APIRouter(
    prefix="/sync",
    tags=["sync"],
    dependencies=[Depends(get_current_user)],
)


def current_time_millis() -> int:
    return int(time.time() * 1000)


@router.post("/activity", response_model=SyncResponse)
async def sync_offline_activities(
    activities: List[SyncActivityRequest],
    db: Session = Depends(get_db),
    current_user: DBUser = Depends(get_current_user),
):
    synced = []
    failed = []

    for act in activities:
        try:
            action = act.pending_sync_action.upper()
            now = current_time_millis()

            if action in ["CREATE", "UPDATE"]:
                existing = (
                    db.query(DBStudyActivity)
                    .filter(DBStudyActivity.id == act.id)
                    .first()
                )

                user_author = (
                    db.query(DBUser)
                    .filter(DBUser.id == act.author_id)
                    .first()
                )

                if user_author is None:
                    user_author = current_user

                if existing:
                    existing.title = act.title
                    existing.subject = act.subject
                    existing.description = act.description

                    existing.duration_minutes = act.duration_minutes
                    existing.duration_seconds = act.duration_seconds

                    existing.image_url = act.image_url
                    existing.group_ids = act.group_ids
                    existing.media_uris = act.media_uris
                    existing.reactions = act.reactions

                    existing.started_at_millis = act.started_at_millis
                    existing.ended_at_millis = act.ended_at_millis
                    existing.created_at_millis = act.created_at_millis
                    existing.updated_at_millis = now
                    existing.is_manual = act.is_manual

                    existing.author_id = user_author.id
                    existing.author_name = user_author.name
                    existing.author_avatar_initials = (
                        user_author.avatar_initials
                    )
                    existing.author_avatar_url = user_author.avatar_url
                else:
                    new_act = DBStudyActivity(
                        id=act.id,
                        title=act.title,
                        subject=act.subject,
                        description=act.description,
                        duration_minutes=act.duration_minutes,
                        duration_seconds=act.duration_seconds,
                        image_url=act.image_url,
                        group_ids=act.group_ids,
                        media_uris=act.media_uris,
                        reactions=act.reactions,
                        started_at_millis=act.started_at_millis,
                        ended_at_millis=act.ended_at_millis,
                        created_at_millis=act.created_at_millis,
                        updated_at_millis=now,
                        is_manual=act.is_manual,
                        author_id=user_author.id,
                        author_name=user_author.name,
                        author_avatar_initials=(
                            user_author.avatar_initials
                        ),
                        author_avatar_url=user_author.avatar_url,
                    )

                    db.add(new_act)

                db.commit()
                synced.append(act.id)

            elif action == "DELETE":
                existing = (
                    db.query(DBStudyActivity)
                    .filter(DBStudyActivity.id == act.id)
                    .first()
                )

                if existing:
                    db.delete(existing)

                db.commit()
                synced.append(act.id)

            else:
                failed.append(act.id)

        except Exception:
            db.rollback()
            logger.exception(
                f"Erro ao sincronizar atividade offline: {act.id}"
            )
            failed.append(act.id)

    return SyncResponse(
        synced_ids=synced,
        failed_ids=failed,
    )


@router.get("/pull", response_model=SyncPullResponse)
async def pull_offline_data(
    last_sync_timestamp: int | None = Query(default=None),
    last_sync_timestamp_camel: int | None = Query(
        default=None,
        alias="lastSyncTimestamp",
    ),
    db: Session = Depends(get_db),
    current_user: DBUser = Depends(get_current_user),
):
    server_timestamp = current_time_millis()

    sync_timestamp = (
        last_sync_timestamp
        if last_sync_timestamp is not None
        else last_sync_timestamp_camel or 0
    )

    all_groups = db.query(DBGroup).all()

    user_groups = [
        group
        for group in all_groups
        if group.member_ids and current_user.id in group.member_ids
    ]

    user_group_ids = [group.id for group in user_groups]

    recent_groups = [
        Group.model_validate(group)
        for group in user_groups
        if group.updated_at_millis > sync_timestamp
    ]

    recent_activities_db = (
        db.query(DBStudyActivity)
        .filter(DBStudyActivity.updated_at_millis > sync_timestamp)
        .all()
    )

    activities_response = []

    for act in recent_activities_db:
        belongs_to_user_group = any(
            group_id in user_group_ids
            for group_id in act.group_ids
        )

        if not belongs_to_user_group:
            continue

        activities_response.append(
            StudyActivity(
                id=act.id,
                group_ids=act.group_ids,
                author=ActivityAuthor(
                    id=act.author_id,
                    name=act.author_name,
                    avatar_initials=act.author_avatar_initials,
                    avatar_url=act.author_avatar_url,
                ),
                title=act.title,
                subject=act.subject,
                description=act.description,
                duration_minutes=act.duration_minutes,
                image_url=act.image_url,
                media_uris=act.media_uris,
                reactions=act.reactions,
                started_at_millis=act.started_at_millis,
                ended_at_millis=act.ended_at_millis,
                created_at_millis=act.created_at_millis,
                updated_at_millis=act.updated_at_millis,
                is_manual=act.is_manual,
            )
        )

    return SyncPullResponse(
        activities=activities_response,
        groups=recent_groups,
        server_timestamp=server_timestamp,
    )
