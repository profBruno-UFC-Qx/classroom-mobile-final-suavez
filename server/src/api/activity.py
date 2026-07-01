from fastapi import APIRouter, Depends, HTTPException, status
from loguru import logger
from sqlalchemy.orm import Session

from src.database import get_db
from src.models.activity import DBStudyActivity
from src.models.group import DBGroup
from src.models.user import DBUser
from src.schemas.activity import StudyActivity
from src.schemas.user import ActivityAuthor
from src.security import get_current_user

router = APIRouter(
    prefix="/activity",
    tags=["activity"],
    dependencies=[Depends(get_current_user)],
)


@router.post("", response_model=StudyActivity, status_code=status.HTTP_201_CREATED)
async def publish_activity(
    activity: StudyActivity,
    db: Session = Depends(get_db),
    current_user: DBUser = Depends(get_current_user),
):
    db_activity = (
        db.query(DBStudyActivity)
        .filter(DBStudyActivity.id == activity.id)
        .first()
    )

    if db_activity:
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST, detail="Atividade já existente"
        )

    groups = (
        db.query(DBGroup)
        .filter(DBGroup.id.in_(activity.group_ids or []))
        .all()
    )

    if len(groups) != len(set(activity.group_ids or [])) or any(
        current_user.id not in (group.member_ids or [])
        for group in groups
    ):
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="Você não pertence a um dos grupos informados.",
        )

    new_activity = DBStudyActivity(
        id=activity.id,
        title=activity.title,
        subject=activity.subject,
        description=activity.description,
        duration_minutes=activity.duration_minutes,
        duration_seconds=activity.duration_minutes * 60,
        image_url=activity.image_url,
        started_at_millis=activity.started_at_millis,
        ended_at_millis=activity.ended_at_millis,
        created_at_millis=activity.created_at_millis,
        is_manual=activity.is_manual,
        author_id=current_user.id,
        author_name=current_user.name,
        author_avatar_initials=current_user.avatar_initials,
        author_avatar_url=current_user.avatar_url,
        group_ids=activity.group_ids,
        media_uris=activity.media_uris,
        reactions=activity.reactions,
    )

    db.add(new_activity)
    db.commit()
    db.refresh(new_activity)

    return StudyActivity(
        id=new_activity.id,
        group_ids=new_activity.group_ids,
        author=ActivityAuthor(
            id=new_activity.author_id,
            name=new_activity.author_name,
            avatar_initials=new_activity.author_avatar_initials,
            avatar_url=new_activity.author_avatar_url,
        ),
        title=new_activity.title,
        subject=new_activity.subject,
        description=new_activity.description,
        duration_minutes=new_activity.duration_minutes,
        image_url=new_activity.image_url,
        media_uris=new_activity.media_uris,
        reactions=new_activity.reactions,
        started_at_millis=new_activity.started_at_millis,
        ended_at_millis=new_activity.ended_at_millis,
        created_at_millis=new_activity.created_at_millis,
        is_manual=new_activity.is_manual,
    )
