import uuid

from fastapi import APIRouter, Depends, HTTPException, status
from fastapi.security import OAuth2PasswordRequestForm
from sqlalchemy.orm import Session

from src.database import get_db
from src.models.user import DBUser
from src.schemas.user import Token, User, UserRegister
from src.security import (
    create_access_token,
    get_current_user,
    get_password_hash,
    verify_password,
)

router = APIRouter(
    prefix="/auth",
    tags=["auth", "authentication"],
)


@router.post(
    "/register",
    response_model=Token,
    status_code=status.HTTP_201_CREATED,
)
async def register_user(
    user_in: UserRegister,
    db: Session = Depends(get_db),
):
    user_exists = (
        db.query(DBUser)
        .filter(
            (DBUser.email == user_in.email)
            | (DBUser.username == user_in.username)
        )
        .first()
    )

    if user_exists:
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail="Email ou nome de usuário já registrado no sistema.",
        )

    user_id = f"usr_{uuid.uuid4().hex[:8]}"

    initials = "".join(
        [name_part[0] for name_part in user_in.name.split()[:2]]
    ).upper()

    new_user = DBUser(
        id=user_id,
        name=user_in.name,
        username=user_in.username,
        email=user_in.email,
        hashed_password=get_password_hash(user_in.password),
        institution=user_in.institution,
        course=user_in.course,
        avatar_initials=initials,
        avatar_url="",
        streak_days=0,
        total_minutes=0,
        total_activities=0,
    )

    db.add(new_user)
    db.commit()
    db.refresh(new_user)

    access_token = create_access_token(
        data={
            "sub": new_user.id,
        }
    )

    return Token(
        access_token=access_token,
        token_type="bearer",
        user=new_user,
    )


@router.post("/login", response_model=Token)
async def login(
    form_data: OAuth2PasswordRequestForm = Depends(),
    db: Session = Depends(get_db),
):
    user = (
        db.query(DBUser)
        .filter(
            (DBUser.email == form_data.username)
            | (DBUser.username == form_data.username)
        )
        .first()
    )

    if not user or not verify_password(
        form_data.password,
        user.hashed_password,
    ):
        raise HTTPException(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail="Credenciais incorretas",
            headers={"WWW-Authenticate": "Bearer"},
        )

    access_token = create_access_token(
        data={
            "sub": user.id,
        }
    )

    return Token(
        access_token=access_token,
        token_type="bearer",
        user=user,
    )


@router.get("/me", response_model=User)
async def get_me(
    current_user: DBUser = Depends(get_current_user),
):
    return current_user