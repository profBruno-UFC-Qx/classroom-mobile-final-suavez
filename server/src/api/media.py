from pathlib import Path
from uuid import uuid4

from fastapi import APIRouter, Depends, File, HTTPException, Request, UploadFile

from src.models.user import DBUser
from src.security import get_current_user

router = APIRouter(
    prefix="/media",
    tags=["media"],
)

UPLOAD_DIR = Path("uploads/activity_images")


@router.post("/activity-image")
async def upload_activity_image(
    request: Request,
    file: UploadFile = File(...),
    current_user: DBUser = Depends(get_current_user),
):
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

    filename = f"{current_user.id}_{uuid4()}{extension}"

    destination = UPLOAD_DIR / filename

    content = await file.read()

    destination.write_bytes(content)

    image_url = str(
        request.base_url.replace(
            path=f"uploads/activity_images/{filename}",
            query="",
            fragment="",
        )
    )

    return {
        "imageUrl": image_url,
    }