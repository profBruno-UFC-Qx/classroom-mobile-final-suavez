from sqlalchemy import Column, Integer, String
from sqlalchemy.orm import Mapped, mapped_column

from src.database import Base


class DBUser(Base):
    __tablename__ = "users"

    id: Mapped[str] = mapped_column(
        String, primary_key=True, index=True
    )
    name: Mapped[str] = mapped_column(String)
    username: Mapped[str] = mapped_column(String)
    email: Mapped[str] = mapped_column(String, default="")
    institution: Mapped[str] = mapped_column(String, default="")
    course: Mapped[str] = mapped_column(String, default="")
    avatar_initials: Mapped[str] = mapped_column(String)
    avatar_url: Mapped[str] = mapped_column(String, default="")
    streak_days: Mapped[int] = mapped_column(Integer, default=0)
    total_minutes: Mapped[int] = mapped_column(Integer, default=0)
    total_activities: Mapped[int] = mapped_column(Integer, default=0)
    hashed_password: Mapped[str] = mapped_column(String)
