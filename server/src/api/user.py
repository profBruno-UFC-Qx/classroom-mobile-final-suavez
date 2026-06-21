from typing import List

from fastapi import APIRouter, Depends, HTTPException
from loguru import logger
from sqlalchemy.orm import Session

from src.database import get_db
from src.models.activity import DBStudyActivity
from src.models.group import DBGroup
from src.models.user import DBUser
from src.schemas.user import ActivityAuthor, User
from src.schemas.group import Group
from src.schemas.activity import StudyActivity
from src.security import get_current_user

router = APIRouter(
    prefix="/user",
    tags=["user"],
    dependencies=[Depends(get_current_user)],
)


@router.post("", response_model=User, status_code=201)
async def create_user(user: User, db: Session = Depends(get_db)):
    db_user = db.query(DBUser).filter(DBUser.id == user.id).first()

    if db_user:
        raise HTTPException(
            status_code=400, detail="Usuário já cadastrado"
        )

    new_user = DBUser(**user.model_dump())
    db.add(new_user)
    db.commit()
    db.refresh(new_user)

    return new_user


@router.get("/{user_id}", response_model=User)
async def get_user(user_id: str, db: Session = Depends(get_db)):
    db_user = db.query(DBUser).filter(DBUser.id == user_id).first()
    if not db_user:
        raise HTTPException(
            status_code=404, detail="Usuário não encontrado"
        )

    return db_user


@router.get("/{user_id}/groups", response_model=List[Group])
async def get_user_groups(
    user_id: str, db: Session = Depends(get_db)
):
    all_groups = db.query(DBGroup).all()

    return all_groups


@router.get(
    "/{user_id}/activities", response_model=List[StudyActivity]
)
async def get_user_activities(
    user_id: str, db: Session = Depends(get_db)
):
    activities = (
        db.query(DBStudyActivity)
        .filter(DBStudyActivity.author_id == user_id)
        .order_by(DBStudyActivity.created_at_millis.desc())
        .all()
    )

    result = []
    for act in activities:
        result.append(
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
                is_manual=act.is_manual,
            )
        )

    return result
