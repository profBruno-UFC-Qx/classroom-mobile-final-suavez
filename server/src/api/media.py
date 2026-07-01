import os
from pathlib import Path
from uuid import uuid4

from fastapi import APIRouter, Depends, File, HTTPException, Request, UploadFile

from src.models.user import DBUser
from src.security import get_current_user
from src.storage import upload_activity_image_to_storage

router = APIRouter(
    prefix="/media",
    tags=["media"],
)

UPLOAD_DIR = Path("uploads/activity_images")

IS_VERCEL = os.getenv("VERCEL") == "1"


@router.post("/activity-image")
async def upload_activity_image(
    request: Request,
    file: UploadFile = File(...),
    current_user: DBUser = Depends(get_current_user),
):
    if IS_VERCEL:
        image_url = await upload_activity_image_to_storage(
            file=file,
            user_id=current_user.id,
        )
    else:
        image_url = await _save_image_locally(
            request=request,
            file=file,
            user_id=current_user.id,
        )

    return {
        "imageUrl": image_url,
    }


async def _save_image_locally(
    request: Request,
    file: UploadFile,
    user_id: int,
) -> str:
    if file.content_type is None or not file.content_type.startswith("image/"):
        raise HTTPException(
            status_code=400,
            detail="O arquivo enviado não é uma imagem.",
        )

    UPLOAD_DIR.mkdir(
        parents=True,
        exist_ok=True,
    )

    extension = Path(file.filename or "").suffix.lower()

    if extension not in [".jpg", ".jpeg", ".png", ".webp"]:
        extension = ".jpg"

    filename = f"{user_id}_{uuid4()}{extension}"

    destination = UPLOAD_DIR / filename

    content = await file.read()

    destination.write_bytes(content)

    return str(
        request.base_url.replace(
            path=f"uploads/activity_images/{filename}",
            query="",
            fragment="",
        )
    )
