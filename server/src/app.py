from fastapi import FastAPI
from fastapi.concurrency import asynccontextmanager
from loguru import logger
from src.api.usuario import router as usuario_router
from src.api.health import router as health_router

@asynccontextmanager
async def lifespan(app: FastAPI):
    logger.info(f"Worker iniciando e carregando recursos para: {app.title}")
    
    yield
    
    logger.info("Worker finalizando. Fechando conexões de forma segura...")

app = FastAPI(
    title="API do StudyRats",
    version="0.1.0",
    lifespan=lifespan,
)

app.include_router(usuario_router)
app.include_router(health_router)