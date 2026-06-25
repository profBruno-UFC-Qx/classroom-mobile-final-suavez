from fastapi import APIRouter, Depends, File, UploadFile

from src.models.user import DBUser
from src.security import get_current_user
from src.storage import upload_activity_image_to_storage


router = APIRouter(
    prefix="/media",
    tags=["media"],
)


@router.post("/activity-image")
async def upload_activity_image(
    file: UploadFile = File(...),
    current_user: DBUser = Depends(get_current_user),
):
    image_url = await upload_activity_image_to_storage(
        file=file,
        user_id=current_user.id,
    )

    return {
        "imageUrl": image_url,
    }