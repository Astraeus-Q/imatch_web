from pymilvus import connections, CollectionSchema, FieldSchema, DataType, Collection, utility

# AllRun once.

connections.connect(
  alias="default", 
  host='localhost', 
  port='19530'
) # Connect to Milvus Server.

img_id = FieldSchema(
  name ="img_id", 
  dtype = DataType.INT64, 
  is_primary = True
)

path = FieldSchema(
  name = "path", 
  dtype = DataType.VARCHAR,  
  max_length=128
)

vector = FieldSchema(
  name = "vector", 
  dtype = DataType.FLOAT_VECTOR, 
  dim = 2048
)

schema = CollectionSchema(
  fields=[img_id, path, vector], 
  description="Similar image search."
)

collection_name = "Image"


collection = Collection(
    name = collection_name, 
    schema = schema, 
    using = "default", 
    shards_num = 2,
    consistency_level = "Strong"
    )

print("OK")
# print(utility.list_collections()) # Desc all the collections.
print("Is collection Image created successfully? %s" % utility.has_collection("Image"))