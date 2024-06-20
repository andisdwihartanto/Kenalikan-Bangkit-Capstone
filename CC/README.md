# KenaliKan API Documentation

## Description
This API allows users to identify types of fish from images submitted through the `/predict` endpoint. It uses a machine learning model to analyze the image and return the type of fish along with a descriptive text about the species.

## Base URL
`https://kenalikan.et.r.appspot.com`

## Endpoint

### POST /predict
- **URL**: `/predict`
- **Method**: `POST`
- **Headers**:
  - **Content-Type**: `multipart/form-data`
- **Request Body**:
  - **image** (file, required): A valid image file of the fish. The image file must be included as a form-data field with the key `image`.
- **Response**:
  ```json
  {
    "name": "Sea Bass",
    "description": "Ikan seabass umumnya memiliki bentuk tubuh yang memanjang, kepala dan mulut yang besar, sisik yang rapat, serta ekor yang lebar dan tidak bercabang. Seabass juga menghabiskan sebagian siklus hidupnya di laut. Di piringnya, seabass memiliki rasa yang ringan dan manis dengan daging yang berubah warna menjadi putih saat dimasak."
  }
