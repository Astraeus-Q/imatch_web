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

# utility.drop_collection("Image")
# print(utility.list_collections())

