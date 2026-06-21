from typing import List

from fastapi import APIRouter, Depends
from loguru import logger
from sqlalchemy.orm import Session

from src.database import get_db
from src.models.activity import DBStudyActivity
from src.models.group import DBGroup
from src.models.user import DBUser
from src.schemas.activity import StudyActivity
from src.schemas.group import Group
from src.schemas.sync import SyncActivityRequest, SyncPullResponse, SyncResponse
from src.schemas.user import ActivityAuthor
from src.security import get_current_user

router = APIRouter(
    prefix="/sync",
    tags=["sync"],
    dependencies=[Depends(get_current_user)]
)

@router.post("/activity", response_model=SyncResponse)
async def sync_offline_activities(activities: List[SyncActivityRequest], db: Session = Depends(get_db)):
    synced = []
    failed = []
    
    for act in activities:
        try:
            user_author = db.query(DBUser).filter(DBUser.id == act.author_id).first()
            author_name = user_author.name if user_author else "Usuário Desconhecido"
            author_initials = user_author.avatar_initials if user_author else "UD"
            author_url = user_author.avatar_url if user_author else ""

            if act.pending_sync_action in ["CREATE", "UPDATE"]:
                existing = db.query(DBStudyActivity).filter(DBStudyActivity.id == act.id).first()
                
                if existing:
                    existing.title = act.title
                    existing.subject = act.subject
                    existing.description = act.description
                    existing.duration_minutes = act.duration_minutes
                    existing.duration_seconds = act.duration_seconds
                    existing.image_url = act.image_url
                else:
                    new_act = DBStudyActivity(
                        id=act.id,
                        title=act.title,
                        subject=act.subject,
                        description=act.description,
                        duration_minutes=act.duration_minutes,
                        duration_seconds=act.duration_seconds,
                        image_url=act.image_url,
                        started_at_millis=act.started_at_millis,
                        ended_at_millis=act.ended_at_millis,
                        created_at_millis=act.created_at_millis,
                        is_manual=act.is_manual,
                        author_id=act.author_id,
                        author_name=author_name,
                        author_avatar_initials=author_initials,
                        author_avatar_url=author_url,
                        group_ids=[],
                        media_uris=[]
                    )
                    db.add(new_act)

                synced.append(act.id)
                
            elif act.pending_sync_action == "DELETE":
                existing = db.query(DBStudyActivity).filter(DBStudyActivity.id == act.id).first()
                
                if existing:
                    db.delete(existing)

                synced.append(act.id)
                
        except Exception:
            failed.append(act.id)
            
    db.commit()
    
    return SyncResponse(synced_ids=synced, failed_ids=failed)


@router.get("/pull", response_model=SyncPullResponse)
async def pull_offline_data(
    last_sync_timestamp: int,
    db: Session = Depends(get_db),
    current_user: DBUser = Depends(get_current_user)
):
    all_groups = db.query(DBGroup).all()
    user_group_ids = [
        g.id for g in all_groups 
        if g.member_ids and current_user.id in g.member_ids
    ]
    
    recent_groups = [
        Group.model_validate(g) 
        for g in all_groups 
        if g.created_at_millis > last_sync_timestamp
    ]

    recent_activities_db = db.query(DBStudyActivity).filter(
        DBStudyActivity.created_at_millis > last_sync_timestamp
    ).all()
    
    activities_response = []
    for act in recent_activities_db:
        if any(g_id in user_group_ids for g_id in act.group_ids):
            activities_response.append(StudyActivity(
                id=act.id,
                group_ids=act.group_ids,
                author=ActivityAuthor(
                    id=act.author_id,
                    name=act.author_name,
                    avatar_initials=act.author_avatar_initials,
                    avatar_url=act.author_avatar_url
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
                is_manual=act.is_manual
            ))
            
    return SyncPullResponse(
        activities=activities_response,
        groups=recent_groups
    )