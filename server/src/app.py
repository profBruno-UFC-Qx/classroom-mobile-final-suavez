from fastapi import FastAPI
from fastapi.concurrency import asynccontextmanager
from loguru import logger
from src.api.health import router as health_router
from src.api.user import router as user_router
from src.api.activity import router as activity_router
from src.api.group import router as group_router
from src.api.sync import router as sync_router
from src.api.auth import router as auth_router
from src.database import Base, engine
from pathlib import Path
from fastapi.staticfiles import StaticFiles
from src.api.media import router as media_router


@asynccontextmanager
async def lifespan(app: FastAPI):
    logger.info(
        f"Worker iniciando e carregando recursos para: {app.title}"
    )

    yield

    logger.info(
        "Worker finalizando. Fechando conexões de forma segura..."
    )


Base.metadata.create_all(bind=engine)

app = FastAPI(
    title="API do StudyRats",
    version="0.1.0",
    lifespan=lifespan,
    redirect_slashes=False,
)

uploads_dir = Path("uploads")
uploads_dir.mkdir(
    parents=True,
    exist_ok=True,
)

app.mount(
    "/uploads",
    StaticFiles(directory=uploads_dir),
    name="uploads",
)

app.include_router(media_router)
app.include_router(health_router)
app.include_router(user_router)
app.include_router(activity_router)
app.include_router(group_router)
app.include_router(sync_router)
app.include_router(auth_router)
