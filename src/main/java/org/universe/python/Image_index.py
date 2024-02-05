from pymilvus import connections, Collection, utility

connections.connect(
  alias="default", 
  host='localhost', 
  port='19530'
) # Connect to Milvus Server.

index_params = {
  "metric_type":"L2",
  "index_type":"IVF_FLAT",
  "params":{"nlist":200}
}

Image = Collection("Image")      # Get an existing collection.
Image.release()
Image.drop_index()
Image.create_index(
  field_name="vector", 
  index_params=index_params
)

print(Image.indexes)