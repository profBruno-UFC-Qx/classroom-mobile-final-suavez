import time

from sqlalchemy import JSON, Boolean, Integer, String
from sqlalchemy.orm import Mapped, mapped_column

from src.database import Base


def current_time_millis() -> int:
    return int(time.time() * 1000)


class DBGroup(Base):
    __tablename__ = "groups"

    id: Mapped[str] = mapped_column(
        String, primary_key=True, index=True
    )
    name: Mapped[str] = mapped_column(String)
    description: Mapped[str] = mapped_column(String)
    banner_url: Mapped[str] = mapped_column(String)
    invite_code: Mapped[str] = mapped_column(String)
    member_count: Mapped[int] = mapped_column(Integer, default=0)
    member_ids: Mapped[list] = mapped_column(JSON, default=list)
    goal_minutes: Mapped[int] = mapped_column(Integer, default=0)
    current_minutes: Mapped[int] = mapped_column(Integer, default=0)
    user_ranking_position: Mapped[int] = mapped_column(Integer, default=0)
    user_minutes: Mapped[int] = mapped_column(Integer, default=0)
    ranking_metric: Mapped[str] = mapped_column(String)
    created_at_millis: Mapped[int] = mapped_column(Integer)
    updated_at_millis: Mapped[int] = mapped_column(Integer,default=current_time_millis)
    is_active: Mapped[bool] = mapped_column(Boolean, default=True)