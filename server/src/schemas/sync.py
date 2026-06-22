from typing import List

from src.schemas.activity import StudyActivity
from src.schemas.base import BaseSchema
from src.schemas.group import Group


# talvez devesse ficar em activity.py, mas como eu tava separando do mesmo jeito das rotas, deixei aqui mesmo pra num misturar as coisas
class SyncActivityRequest(BaseSchema):
    id: str
    author_id: str
    title: str
    subject: str
    description: str
    duration_minutes: int
    duration_seconds: int
    image_url: str = ""
    group_ids: List[str] = Field(default_factory=list)
    media_uris: List[str] = Field(default_factory=list)
    reactions: int = 0
    started_at_millis: int
    ended_at_millis: int
    created_at_millis: int
    is_manual: bool
    pending_sync_action: str


class SyncResponse(BaseSchema):
    synced_ids: List[str]
    failed_ids: List[str]


class SyncPullResponse(BaseSchema):
    activities: List[StudyActivity]
    groups: List[Group]
