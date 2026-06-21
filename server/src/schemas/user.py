from src.schemas.base import BaseSchema


class User(BaseSchema):
    id: str
    name: str
    username: str
    email: str = ""
    institution: str = ""
    course: str = ""
    avatar_initials: str
    avatar_url: str = ""
    streak_days: int = 0
    total_minutes: int = 0
    total_activities: int = 0


class UserRegister(BaseSchema):
    name: str
    username: str
    email: str
    password: str
    institution: str = ""
    course: str = ""


class Token(BaseSchema):
    access_token: str
    token_type: str
    user: User


class ActivityAuthor(BaseSchema):
    id: str
    name: str
    avatar_initials: str
    avatar_url: str = ""

class RankingEntry(BaseSchema):
    group_id: str
    user: User
    position: int
    total_minutes: int
    active_days: int
    is_current_user: bool = False
