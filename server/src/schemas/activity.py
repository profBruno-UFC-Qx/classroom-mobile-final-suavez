from typing import List

from pydantic import Field

from src.schemas.base import BaseSchema
from src.schemas.user import ActivityAuthor


class StudyActivity(BaseSchema):
    id: str
    group_ids: List[str]
    author: ActivityAuthor
    title: str
    subject: str
    description: str
    duration_minutes: int
    image_url: str
    media_uris: List[str] = Field(default_factory=list)
    reactions: int = 0
    started_at_millis: int
    ended_at_millis: int
    created_at_millis: int
    is_manual: bool = False