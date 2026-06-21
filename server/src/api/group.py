from typing import List

from fastapi import APIRouter, HTTPException, status, Depends
from loguru import logger
from sqlalchemy.orm import Session

from src.database import get_db
from src.models.activity import DBStudyActivity
from src.models.group import DBGroup
from src.models.user import DBUser
from src.schemas.activity import StudyActivity
from src.schemas.group import Group, JoinGroupRequest
from src.schemas.user import ActivityAuthor
from src.security import get_current_user

router = APIRouter(
    prefix="/group",
    tags=["group"],
    dependencies=[Depends(get_current_user)],
)


@router.post(
    "", response_model=Group, status_code=status.HTTP_201_CREATED
)
async def create_group(group: Group, db: Session = Depends(get_db)):
    db_group = (
        db.query(DBGroup).filter(DBGroup.id == group.id).first()
    )
    if db_group:
        raise HTTPException(
            status_code=400, detail="Grupo já cadastrado"
        )

    new_group = DBGroup(**group.model_dump())
    db.add(new_group)
    db.commit()
    db.refresh(new_group)

    return new_group


@router.get("/{group_id}", response_model=Group)
async def get_group(group_id: str, db: Session = Depends(get_db)):
    db_group = (
        db.query(DBGroup).filter(DBGroup.id == group_id).first()
    )

    if not db_group:
        raise HTTPException(
            status_code=404, detail="Grupo não encontrado"
        )

    return db_group


@router.get(
    "/{group_id}/activities", response_model=List[StudyActivity]
)
async def get_group_activities(
    group_id: str, db: Session = Depends(get_db)
):
    all_activities = db.query(DBStudyActivity).all()
    filtered = [
        act for act in all_activities if group_id in act.group_ids
    ]

    result = []
    for act in sorted(
        filtered, key=lambda x: x.created_at_millis, reverse=True
    ):
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


@router.post("/join", response_model=Group)
async def join_group(
    payload: JoinGroupRequest,
    db: Session = Depends(get_db),
    current_user: DBUser = Depends(get_current_user),
):
    group = (
        db.query(DBGroup)
        .filter(DBGroup.invite_code == payload.invite_code)
        .first()
    )

    if not group:
        raise HTTPException(
            status_code=404,
            detail="Código de convite inválido ou grupo não encontrado.",
        )

    members = list(group.member_ids) if group.member_ids else []

    if current_user.id in members:
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail="O utilizador já pertence a este grupo.",
        )

    members.append(current_user.id)
    group.member_ids = members
    group.member_count = len(members)

    db.commit()
    db.refresh(group)

    return group
