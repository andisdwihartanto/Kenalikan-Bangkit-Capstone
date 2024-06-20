const tf = require('@tensorflow/tfjs-node');

async function loadModel() {
  const modelPath = 'https://storage.googleapis.com/kenalikan-bucket-1/model/model.json';
  return await tf.loadLayersModel(modelPath);
}

async function predict(model, imageBuffer) {
  const tensor = tf.node.decodeImage(imageBuffer)
    .resizeNearestNeighbor([224, 224]) 
    .toFloat()
    .div(tf.scalar(255.0))
    .expandDims();

  const prediction = model.predict(tensor);
  const index = prediction.argMax(1).dataSync()[0];
  const fishTypes = ["Black Sea Sprat", "Gilt-Head Bream", "Hourse Mackerel", "Red Mullet", "Red Sea Bream", "Sea Bass", "Shrimp", "Striped Red Mullet", "Trout"];
  return fishTypes[index];
}

module.exports = { loadModel, predict };