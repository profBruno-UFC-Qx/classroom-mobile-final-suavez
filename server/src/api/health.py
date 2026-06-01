from fastapi import APIRouter
from loguru import logger

from src.error import not_impl_route

router = APIRouter(
    prefix="/health",
    tags=["health"],
)

@router.get("/")
async def check_health():
    return {"status": "ok"}