from sqlalchemy import JSON, Boolean, Column, Integer, String
from sqlalchemy.orm import Mapped, mapped_column

from src.database import Base


class DBStudyActivity(Base):
    __tablename__ = "study_activities"

    id: Mapped[str] = mapped_column(
        String, primary_key=True, index=True
    )
    title: Mapped[str] = mapped_column(String)
    subject: Mapped[str] = mapped_column(String)
    description: Mapped[str] = mapped_column(String)
    duration_minutes: Mapped[int] = mapped_column(Integer)
    duration_seconds: Mapped[int] = mapped_column(Integer)
    image_url: Mapped[str] = mapped_column(String)
    started_at_millis: Mapped[int] = mapped_column(Integer)
    ended_at_millis: Mapped[int] = mapped_column(Integer)
    created_at_millis: Mapped[int] = mapped_column(Integer)
    is_manual: Mapped[bool] = mapped_column(Boolean, default=False)
    author_id: Mapped[str] = mapped_column(String)
    author_name: Mapped[str] = mapped_column(String)
    author_avatar_initials: Mapped[str] = mapped_column(String)
    author_avatar_url: Mapped[str] = mapped_column(String, default="")
    group_ids: Mapped[list] = mapped_column(JSON, default=list)
    media_uris: Mapped[list] = mapped_column(JSON, default=list)
    reactions: Mapped[int] = mapped_column(Integer, default=0)
