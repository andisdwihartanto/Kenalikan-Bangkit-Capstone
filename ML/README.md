# ML-KENALIKAN

## Summary 
Kenalikan is the aplication can detect fish species. We built our model using CNN, and we make it from transfer learning too.

This repository mainly consist of 2 files : 
1. 'fish_classification_transfer_learning_mobilenet(3).ipynb' that the notebook files of this project 
2. gambar_ikan is a folder that consist of several fish images from teh dataset and from another source to testing the model

### How to use the dataset
Set up in your kaggle acount to connectc in colab with this tutorial https://www.kaggle.com/discussions/general/74235 . after unzipping, we create a folder for each class

### Data Preprocessing for Modelling 
use the TensorFloe Keras ImageDataGenerator to generate batches of image data for training and testing. Also preprocessing large amount of data

### Modelling Process
1. building and training a classification model using the MobleNet as a base model.
2. The weights parameter is set to 'imagenet', indicating that the pre-trained weights of the model trained on the ImageNet dataset will be used.
3. `include_top` is set to False to exclude the fully connected layers of the base model.
4. `input_shape` is set to (224, 224, 3), specifying the input shape of the images expected by the model.
5. In this scope, the model uses `Dropout and Several dense layers`. The activation function used in the model is `ReLu`. The last activation function is `softmax` activation function to produce probability predictions for the three classes.
6. The loss function is set to `categorical_crossentropy` as it is commonly used for multi-class classification tasks.
7. The training is performed for 5 epochs.
8. The history of the training is stored in the history variable.

### Evaluation 
1. Additionally, it calculates and displays the confusion matrix, which provides insights into the model's classification performance for each class.
2. demonstrates how to obtain a batch of images and labels from the test generator and make predictions on a specific image. The purpose is to demonstrate how to extract a specific image and its label from the test generator and make predictions on that image using the trained model. 
