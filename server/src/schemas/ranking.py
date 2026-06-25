from src.schemas.base import BaseSchema


class RankingUser(BaseSchema):
    id: str
    name: str
    username: str
    email: str = ""
    institution: str = ""
    course: str = ""
    avatar_initials: str = ""
    avatar_url: str = ""


class RankingEntry(BaseSchema):
    group_id: str
    user: RankingUser
    total_minutes: int
    active_days: int
    position: int
    is_current_user: bool
    updated_at_millis: int
