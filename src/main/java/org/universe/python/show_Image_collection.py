from pymilvus import connections, utility, Collection

connections.connect(
  alias="default", 
  host='localhost', 
  port='19530'
) # Connect to Milvus Server.

print(utility.list_collections())
Image = Collection("Image")
print(Image.num_entities)
print(Image.description)
if not Image.loaded():
    Image.load()


ids = [1,2,3]
query_res = Image.query(
  expr = "img_id in %s" % ids,
  output_fields = ["img_id", "path"],
)

print(query_res)

# utility.drop_collection("Image")
# print(utility.list_collections())

