from fastapi import APIRouter
from loguru import logger

from src.error import not_impl_route

router = APIRouter(
    prefix="/usuario",
    tags=["usuário"],
)

@router.get("/")
async def list_usuarios():
    return not_impl_route()