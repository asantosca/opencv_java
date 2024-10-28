# opencv_java

Learning OpenCV in Java

This project is meant to be a comprehensive introduction to OpenCV using a maven structure, with a more recent java and
more steps to complete the projects. Although this was tested on a Windows x64 machine, translating it to other
platforms should not be difficult.

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

