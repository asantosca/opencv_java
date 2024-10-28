package ca.alexsantos;

import org.opencv.core.Core;

public class ValidateEnvironment {

    static {
        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            System.out.println("OpenCV library loaded successfully!");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Failed to load OpenCV library: " + e.getMessage());
        }
    }

    public static boolean isLibraryLoaded() {
        return Core.getVersionString() != null;  // Check if a core OpenCV method returns a result
    }

    public static void main(String[] args) {
        if (isLibraryLoaded()) {
            System.out.println("OpenCV version: " + Core.getVersionString());
        } else {
            System.err.println("OpenCV library is not loaded correctly.");
        }
    }
}