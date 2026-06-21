from fastapi import APIRouter, Depends, HTTPException
from loguru import logger
from sqlalchemy.orm import Session

from src.database import get_db
from src.models.activity import DBStudyActivity
from src.models.activity import DBStudyActivity
from src.schemas.activity import StudyActivity
from src.schemas.user import ActivityAuthor
from src.security import get_current_user

router = APIRouter(
    prefix="/activity",
    tags=["activity"],
    dependencies=[Depends(get_current_user)]
)

@router.post("", response_model=StudyActivity, status_code=201)
async def publish_activity(activity: StudyActivity, db: Session = Depends(get_db)):
    db_activity = db.query(DBStudyActivity).filter(DBStudyActivity.id == activity.id).first()
    
    if db_activity:
        raise HTTPException(status_code=400, detail="Atividade já existente")
    
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
        author_id=activity.author.id,
        author_name=activity.author.name,
        author_avatar_initials=activity.author.avatar_initials,
        author_avatar_url=activity.author.avatar_url,
        group_ids=activity.group_ids,
        media_uris=activity.media_uris,
        reactions=activity.reactions
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
            avatar_url=new_activity.author_avatar_url
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
        is_manual=new_activity.is_manual
    )
