import os

from contextlib import asynccontextmanager

from fastapi import FastAPI
from loguru import logger

from src.api.health import router as health_router
from src.api.user import router as user_router
from src.api.group import router as group_router
from src.api.sync import router as sync_router
from src.api.auth import router as auth_router
from src.api.media import router as media_router

from src.database import Base, engine


@asynccontextmanager
async def lifespan(app: FastAPI):
    logger.info(
        f"Worker iniciando e carregando recursos para: {app.title}"
    )

    if os.getenv("CREATE_TABLES_ON_STARTUP") == "true":
        Base.metadata.create_all(bind=engine)
        logger.info("Tabelas verificadas/criadas com SQLAlchemy.")

    yield

    logger.info(
        "Worker finalizando. Fechando conexões de forma segura..."
    )


app = FastAPI(
    title="API do StudyRats",
    version="0.1.0",
    lifespan=lifespan,
    redirect_slashes=False,
)

app.include_router(media_router)
app.include_router(health_router)
app.include_router(user_router)
app.include_router(group_router)
app.include_router(sync_router)
app.include_router(auth_router)