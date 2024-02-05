from towhee import pipe, ops
import os

img_embedding = (
    pipe.input('path')
        .map('path', 'img', ops.image_decode.cv2())
        .map('img', 'embedding', ops.image_embedding.timm(model_name='resnet50'))
        .output('embedding')
)

upload_directory = "D:\\CS\\Data_System\\Master Project\\web\\imatch\\src\\main\\resources\\static\\uploaded\\"
file_path = ""
for file_name in os.listdir(upload_directory):
    if file_name.endswith((".jpg", ".jpeg", ".png")):
        # Read one file.
        file_path = os.path.join(upload_directory, file_name)
        break
# if file_name == "":
#     raise FileNotFoundError("File not found!!!")

output_directory = ""



img_vector_list = img_embedding(file_path).get() # type(img_vector) is list[numpy.array] with 1 array.
img_vector = img_vector_list[0]
print(img_vector)
print(type(img_vector))
print(img_vector.shape) # 2048 dims
