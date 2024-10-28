# opencv_java

Learning OpenCV in Java

This project is meant to be an introduction to face detection and face recognition using OpenCV.
Although this was tested on a Windows x64 machine, translating it to other platforms should not be difficult.

## Current Environment

Windows 11 x64
Java corretto-22.0.2
Maven 3
OpenCV java490
IntelliJ 2024.1.4

## Initial Setup for OpenCV

From https://opencv.org/releases/, download the release OpenCV – 4.9.0 from 2023-12-28

When selecting Windows, an executable is downloaded. Running the executable creates the folder
[Downloads]\opencv\build\java\x64

For your convenience, the files are already copied into this project under \libs

Since this is a DLL, it needs to be dynamically loaded, which is done via
`System.loadLibrary(Core.NATIVE_LIBRARY_NAME);`

Note that `Core.NATIVE_LIBRARY_NAME` indicates the version of OpenCV that is used. The same value
is also used to identify the name of the DLL. This means that the DLL and the source code used
for the Java code needs to be the same.

For the version 4.9.0 of OpenCV, we need the dependency

```
        <dependency>
            <groupId>org.openpnp</groupId>
            <artifactId>opencv</artifactId>
            <version>4.9.0-0</version>
        </dependency>
```

To validate our environment, let's write a simple class. Execute ValidateEnvironment to make sure all the environment
is ready.

![Screenshot ValidateEnvironment.png](/readme_images/Screenshot%20ValidateEnvironment.png)

By now, you should have gotten a
```
OpenCV library loaded successfully!
```

## Face Detection

We will be using the Haar Cascade, which is a machine learning-based approach for object detection, widely used for face detection in images and video streams. It's a type of classifier trained to detect objects (such as faces) by learning features from thousands of positive and negative images. This classifier was proposed by Paul Viola and Michael Jones in their groundbreaking 2001 paper, “Rapid Object Detection using a Boosted Cascade of Simple Features.”

Here’s how the Haar Cascade works in brief:

1. Features and Haar-like Features
   The algorithm uses rectangular Haar-like features, which are patterns of black and white rectangles. These features are similar to Haar wavelets, hence the name.
   For example, one Haar feature could detect areas where the eyes are darker than the upper cheeks or where the bridge of the nose is lighter than the eyes.
   The classifier scans different areas of an image to look for these patterns and determines if the area resembles parts of a face.
2. Training and Cascade Structure
   During training, the classifier is presented with many positive samples (images containing faces) and negative samples (images without faces). It learns the patterns of features that distinguish faces from other objects.
   Instead of evaluating every feature in every possible area of an image (which would be computationally expensive), it uses a cascade structure. The cascade consists of multiple stages; each stage is a classifier that checks whether a specific region of the image could contain the object.
   The stages are sequential: each stage quickly eliminates regions that don’t match and passes only those regions that might contain a face to the next stage for further evaluation.
3. Detection Process
   In detection, the trained cascade classifier scans the entire image in windows of different scales and detects regions that pass all stages as positive results (e.g., a face).
   For face detection, OpenCV includes several pre-trained Haar cascades, such as haarcascade_frontalface_default.xml.
   Advantages and Disadvantages
   Advantages: Fast and efficient, especially for real-time applications. Well-suited for simple detection tasks like detecting frontal faces.
   Disadvantages: Can struggle with complex scenarios, such as detecting faces in varying orientations, lighting, or partial occlusions. More advanced methods, like deep learning-based detectors, often outperform Haar Cascades in these cases.
   In short, the Haar Cascade is a powerful tool for simple and fast face detection, but may need to be combined with other techniques or models to achieve high accuracy in challenging conditions.

Advantages and Disadvantages
Advantages: Fast and efficient, especially for real-time applications. Well-suited for simple detection tasks like detecting frontal faces.
Disadvantages: Can struggle with complex scenarios, such as detecting faces in varying orientations, lighting, or partial occlusions. More advanced methods, like deep learning-based detectors, often outperform Haar Cascades in these cases.
In short, the Haar Cascade is a powerful tool for simple and fast face detection, but may need to be combined with other techniques or models to achieve high accuracy in challenging conditions.

Since OpenCV is set up in Java, let’s dive into a face detection example. We’ll use the Haar Cascade Classifier, which is also available in OpenCV's Java library.

### Detecting a face using the Haar Cascade

Here's a step-by-step breakdown and the code to detect faces in an image or from a live camera feed.

1. Import OpenCV and Load the Cascade Classifier
   We start by loading the Haar Cascade XML file for face detection, which OpenCV provides. This will let us locate faces in images.

2. Capture the camera and get frames

3. Detect Faces and Draw Bounding Boxes
   Using CascadeClassifier.detectMultiScale, we’ll detect faces and draw rectangles around them to visualize the detection.


Note the class `FaceDetectionWithDisplay`. Remember to set the `java.library.path` as we did before:

![Screenshot FaceDetectionWithDisplay.png](/readme_images/Screenshot%20FaceDetectionWithDisplay.png)

Note the interval times and how long it takes for every operation.


