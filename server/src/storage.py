import os
from pathlib import Path
from uuid import uuid4

from dotenv import load_dotenv
from fastapi import HTTPException, UploadFile
from supabase import Client, create_client

load_dotenv()

SUPABASE_URL = os.getenv("SUPABASE_URL")
SUPABASE_SERVICE_ROLE_KEY = os.getenv("SUPABASE_SERVICE_ROLE_KEY")
SUPABASE_BUCKET = os.getenv("SUPABASE_BUCKET", "activity-images")

_supabase_client: Client | None = None


def _get_supabase_client() -> Client:
    global _supabase_client

    if _supabase_client is not None:
        return _supabase_client

    if not SUPABASE_URL:
        raise RuntimeError("SUPABASE_URL não configurada.")

    if not SUPABASE_SERVICE_ROLE_KEY:
        raise RuntimeError("SUPABASE_SERVICE_ROLE_KEY não configurada.")

    _supabase_client = create_client(
        SUPABASE_URL,
        SUPABASE_SERVICE_ROLE_KEY,
    )

    return _supabase_client


async def upload_activity_image_to_storage(
    file: UploadFile,
    user_id: int,
) -> str:
    if file.content_type is None or not file.content_type.startswith("image/"):
        raise HTTPException(
            status_code=400,
            detail="O arquivo enviado não é uma imagem.",
        )

    extension = Path(file.filename or "").suffix.lower()

    if extension not in [".jpg", ".jpeg", ".png", ".webp"]:
        extension = ".jpg"

    filename = f"{user_id}_{uuid4()}{extension}"
    storage_path = f"activity_images/{filename}"

    content = await file.read()
    client = _get_supabase_client()

    try:
        client.storage.from_(SUPABASE_BUCKET).upload(
            path=storage_path,
            file=content,
            file_options={
                "content-type": file.content_type,
            },
        )

        public_url = client.storage.from_(SUPABASE_BUCKET).get_public_url(
            storage_path
        )

        return public_url

    except Exception as error:
        raise HTTPException(
            status_code=500,
            detail=f"Erro ao enviar imagem para o Supabase Storage: {error}",
        )
