from towhee import pipe, ops
from pymilvus import connections, Collection
import os
import shutil
# import json

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




img_vector_list = img_embedding(file_path).get() # type(img_vector) is list[numpy.array] with 1 array.
img_vector = img_vector_list[0]
print(img_vector)
print(type(img_vector))
print(img_vector.shape) # 2048 dims
img_vector = img_vector.tolist()
print(type(img_vector))


connections.connect(
  alias="default", 
  host='localhost', 
  port='19530'
) # Connect to Milvus Server.

Image = Collection("Image")
Image.load()
search_params = {"metric_type": "L2", "params": {"nprobe": 200}}

print("Searching Image...")
search_res = Image.search(
	data = [img_vector], 
	anns_field = "vector", 
	param=search_params, 
	limit=10, 
	expr=None,
	consistency_level="Strong"
)

search_ids = search_res[0].ids

query_res = Image.query(
  expr = "img_id in %s" % search_ids,
  output_fields = ["img_id", "path"],
)

# output_path = "D://CS//Data_System//Master Project//web//imatch//src//main//java//org//universe//python//result.json"
# with open(output_path, 'w+') as json_file:
#     json.dump(query_res, json_file, indent=4)

result_path = "D:/CS/Data_System/Master Project/web/imatch/src/main/resources/static/results"


file_list = os.listdir(result_path)
if len(file_list):
    # Clear the directory.
    for filename in file_list:
        file_path = os.path.join(result_path, filename)
        if os.path.isfile(file_path):
            os.remove(file_path)

# print(query_res)
# print(query_res[0])
for entity in query_res:
    p = entity["path"]
    print(p)
    shutil.copy(p, result_path)


Image.release()
