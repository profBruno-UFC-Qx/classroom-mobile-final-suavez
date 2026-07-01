from src.schemas.base import BaseSchema


class Group(BaseSchema):
    id: str
    name: str
    description: str
    banner_url: str
    invite_code: str
    member_count: int = 0
    goal_minutes: int = 0
    current_minutes: int = 0
    user_ranking_position: int = 0
    user_minutes: int = 0
    ranking_metric: str
    created_at_millis: int
    updated_at_millis: int
    is_active: bool = True


class JoinGroupRequest(BaseSchema):
    invite_code: str
