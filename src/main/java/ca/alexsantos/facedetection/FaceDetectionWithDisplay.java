package ca.alexsantos.facedetection;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.opencv.highgui.HighGui;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

public class FaceDetectionWithDisplay {

    static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    public static void main(String[] args) throws IOException {

        // Load the pre-trained face detection model
        long loadingHaarCascadeStart = System.nanoTime();
        ClassPathResource resource = new ClassPathResource("haarcascade_frontalface_alt.xml");
        CascadeClassifier faceDetector = new CascadeClassifier(resource.getFile().getAbsolutePath());
        long loadingHaarCascadeEnd = System.nanoTime();
        System.out.println("Resource Loading time: " + (loadingHaarCascadeEnd - loadingHaarCascadeStart) / 1_000_000 + " ms");

        // Initialize video capture for the camera (0 is the default camera)
        long videoCaptureStart = System.nanoTime();
        VideoCapture camera = new VideoCapture(0);
        if (!camera.isOpened()) {
            System.out.println("Error: Camera not accessible!");
            return;
        }
        long videoCaptureEnd = System.nanoTime();
        System.out.println("Camera Cpaturing time: " + (videoCaptureEnd - videoCaptureStart) / 1_000_000 + " ms");

        Mat frame = new Mat(); // Store each frame

        // Detect face for 3 seconds
        long stopTime = System.currentTimeMillis() + 3000;
        while (camera.read(frame) && System.currentTimeMillis() <= stopTime) {
            long start = System.nanoTime();

            // Step 1: Detect faces
            long detectStart = System.nanoTime();
            Rect[] faces = detectFaces(frame, faceDetector);
            long detectEnd = System.nanoTime();
            System.out.println("Face detection time: " + (detectEnd - detectStart) / 1_000_000 + " ms");

            // Step 2: Draw rectangles around detected faces
            long drawStart = System.nanoTime();
            for (Rect face : faces) {
                Imgproc.rectangle(frame, face, new Scalar(0, 255, 0), 2); // Green rectangle
            }
            long drawEnd = System.nanoTime();
            System.out.println("Drawing rectangles time: " + (drawEnd - drawStart) / 1_000_000 + " ms");

            // Step 3: Display the frame with the detected faces
            long displayStart = System.nanoTime();
            HighGui.imshow("Face Detection", frame);
            long displayEnd = System.nanoTime();
            System.out.println("Displaying frame time: " + (displayEnd - displayStart) / 1_000_000 + " ms");

            // Exit the loop if the user presses 'q'
            if (HighGui.waitKey(30) == 'q') {
                break;
            }

            long end = System.nanoTime();
            System.out.println("Total frame processing time: " + (end - start) / 1_000_000 + " ms");
        }

        // Release the resources
        long cameraReleaseStart = System.nanoTime();
        camera.release();
        long cameraReleaseEnd = System.nanoTime();
        System.out.println("Camera Release time: " + (cameraReleaseEnd - cameraReleaseStart) / 1_000_000 + " ms");

        long highGuiDestroyAllWindowsStart = System.nanoTime();
        HighGui.destroyAllWindows();
        long highGuiDestroyAllWindowsEnd = System.nanoTime();
        System.out.println("High GUI destroy all windows time: " + (highGuiDestroyAllWindowsEnd - highGuiDestroyAllWindowsStart) / 1_000_000 + " ms");

        // Optional short delay to ensure resources are released
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Forcefully exit to terminate any lingering processes
        System.exit(0);
    }

    private static Rect[] detectFaces(Mat frame, CascadeClassifier faceDetector) {
        Mat grayFrame = new Mat();

        // Convert the frame to grayscale for faster processing
        Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);

        // Equalize the histogram to improve detection
        Imgproc.equalizeHist(grayFrame, grayFrame);

        // Prepare MatOfRect to hold the detected faces
        MatOfRect facesMat = new MatOfRect();

        // Detect faces and store them in facesMat
        faceDetector.detectMultiScale(grayFrame, facesMat, 1.1, 3, 0, new Size(30, 30), new Size());

        // Convert the MatOfRect to an array of Rects
        return facesMat.toArray();
    }
}
