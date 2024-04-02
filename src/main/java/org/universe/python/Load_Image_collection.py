from pymilvus import connections, Collection, utility
import json

collection_name = 'Image'

connections.connect(
  alias="default", 
  host='localhost', 
  port='19530'
) # Connect to Milvus Server.

if not utility.has_collection(collection_name):
    raise KeyboardInterrupt("%s not exists!" % collection_name)

else:
    print("Collection Connected successfully.")

# Load data from JSON file.
json_file_path = "D://CS//Data_System//resourses//image_vector.json"
data = [[], [], []]
with open(json_file_path, 'r') as json_file:
    print("Loading file...")
    entity_list = json.load(json_file)
    Image = Collection(collection_name)      # Get collection.

    i = 1
    for entity in entity_list:
        img_id = entity['id']
        path = entity['path']
        vector = entity['array']

        data[0].append(img_id)
        data[1].append(path)
        data[2].append(vector)

        print("%d/17,125 have been added to data." % i)

        if i % 1000  == 0:
            print("Inserting data...")
            mr = Image.insert(data)
            print("Insert 1000 data successfully!")
            data = [[], [], []] # Reload data.
        i += 1

if data[0]:
    # Insert data left.
    print("Inserting data...")
    mr = Image.insert(data)
    print("Insert the rest of data successfully!")




