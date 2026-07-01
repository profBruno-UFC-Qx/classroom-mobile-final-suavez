from fastapi import APIRouter
from loguru import logger

router = APIRouter(
    prefix="/health",
    tags=["health"],
)


@router.get("")
async def check_health():
    return {"status": "ok"}
