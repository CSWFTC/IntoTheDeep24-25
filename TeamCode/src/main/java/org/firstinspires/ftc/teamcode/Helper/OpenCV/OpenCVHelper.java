package org.firstinspires.ftc.teamcode.Helper.OpenCV;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Core;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvWebcam;
import org.openftc.easyopencv.OpenCvPipeline;

public class OpenCVHelper {
    private OpenCvCamera camera;
    private VisionPipeline pipeline;

    public OpenCVHelper(WebcamName webcamName, int cameraMonitorViewId, boolean withLivePreview) {
        pipeline = new VisionPipeline();

        if (withLivePreview) {
            camera = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);
        } else {
            camera = OpenCvCameraFactory.getInstance().createWebcam(webcamName);
        }

        camera.setPipeline(pipeline);
    }

    public void openCameraAndStart() {
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
            }
        });
    }

    public Mat getProcessedFrame() {
        return pipeline.getLastFrame();
    }

    public void stopCamera() {
        camera.stopStreaming();
        camera.closeCameraDevice();
    }

    private static class VisionPipeline extends OpenCvPipeline {
        private final Mat hsvMat = new Mat();
        private final Mat thresholdMat = new Mat();

        private final Scalar lowerThreshold = new Scalar(0, 0, 100);
        private final Scalar upperThreshold = new Scalar(100, 100, 255);

        @Override
        public Mat processFrame(Mat input) {
            Mat brightenedInput = new Mat();
            input.convertTo(brightenedInput, -1, 1, 30);  // Increase brightness by adding 30

            Imgproc.cvtColor(brightenedInput, hsvMat, Imgproc.COLOR_RGB2HSV);

            Scalar lowerThreshold1 = new Scalar(0, 100, 150);  // Min saturation and value
            Scalar upperThreshold1 = new Scalar(10, 255, 255);
            Scalar lowerThreshold2 = new Scalar(170, 100, 150);
            Scalar upperThreshold2 = new Scalar(180, 255, 255);

            Core.inRange(hsvMat, lowerThreshold1, upperThreshold1, thresholdMat);
            Mat tempMat = new Mat();
            Core.inRange(hsvMat, lowerThreshold2, upperThreshold2, tempMat);
            Core.bitwise_or(thresholdMat, tempMat, thresholdMat);

            Imgproc.erode(thresholdMat, thresholdMat, new Mat());
            Imgproc.dilate(thresholdMat, thresholdMat, new Mat());

            java.util.List<org.opencv.core.MatOfPoint> contours = new java.util.ArrayList<>();
            Mat hierarchy = new Mat();
            Imgproc.findContours(thresholdMat, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

            double maxArea = 0;
            org.opencv.core.Rect largestRect = null;

            for (int i = 0; i < contours.size(); i++) {
                double area = Imgproc.contourArea(contours.get(i));

                if (area > 500) {
                    org.opencv.core.Rect boundingRect = Imgproc.boundingRect(contours.get(i));
                    if (area > maxArea) {
                        maxArea = area;
                        largestRect = boundingRect;
                    }
                }
            }

            if (largestRect != null) {
                Imgproc.rectangle(input, largestRect.tl(), largestRect.br(), new Scalar(0, 255, 0), 2);
            }

            return input;
        }


        //        @Override
//        public Mat processFrame(Mat input) {
//            Imgproc.cvtColor(input, hsvMat, Imgproc.COLOR_RGB2HSV);
//
//            Scalar lowerThreshold1 = new Scalar(0, 100, 150);
//            Scalar upperThreshold1 = new Scalar(10, 255, 255);
//            Scalar lowerThreshold2 = new Scalar(170, 100, 150);
//            Scalar upperThreshold2 = new Scalar(180, 255, 255);
//
//            Core.inRange(hsvMat, lowerThreshold1, upperThreshold1, thresholdMat);
//            Mat tempMat = new Mat();
//            Core.inRange(hsvMat, lowerThreshold2, upperThreshold2, tempMat);
//            Core.bitwise_or(thresholdMat, tempMat, thresholdMat);
//
//            Imgproc.erode(thresholdMat, thresholdMat, new Mat());
//            Imgproc.dilate(thresholdMat, thresholdMat, new Mat());
//
//            java.util.List<org.opencv.core.MatOfPoint> contours = new java.util.ArrayList<>();
//            Mat hierarchy = new Mat();
//            Imgproc.findContours(thresholdMat, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
//
//            double maxArea = 0;
//            org.opencv.core.Rect largestRect = null;
//
//            for (int i = 0; i < contours.size(); i++) {
//                double area = Imgproc.contourArea(contours.get(i));
//
//                if (area > 500) {
//                    org.opencv.core.Rect boundingRect = Imgproc.boundingRect(contours.get(i));
//                    if (area > maxArea) {
//                        maxArea = area;
//                        largestRect = boundingRect;
//                    }
//                }
//            }
//
//            if (largestRect != null) {
//                Imgproc.rectangle(input, largestRect.tl(), largestRect.br(), new Scalar(0, 255, 0), 2);
//            }
//
//            return input;
//        }
        public Mat getLastFrame() {
            return thresholdMat;
        }
    }

}

