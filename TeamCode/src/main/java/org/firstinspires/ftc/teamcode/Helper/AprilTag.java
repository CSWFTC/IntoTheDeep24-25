package org.firstinspires.ftc.teamcode.Helper;

import static android.os.SystemClock.sleep;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class AprilTag {
    public static Params PARAMS = new Params();
    private final AprilTagProcessor aprilTag;
    private final VisionPortal visionPortal; // Used to manage the video source.

    public AprilTag(@NonNull HardwareMap hdwMap) {
        aprilTag = new AprilTagProcessor.Builder().build();
        aprilTag.setDecimation(2);
        visionPortal = new VisionPortal.Builder()
                .setCamera(hdwMap.get(WebcamName.class, "Webcam Back"))
                .addProcessor(aprilTag)
                .build();
        setManualExposure(6, 250);
    }

    public AprilTagDetection lookForMatching() {
        // Used to hold the data for a detected AprilTag
        AprilTagDetection foundTag = null;
        // Step through the list of detected tags and look for a matching tag
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        for (AprilTagDetection detection : currentDetections) {
            // Look to see if we have size info on this tag.
            if (detection.metadata != null) {
                foundTag = detection;
                break; // don't look any further.
            }
            // This tag is NOT in the library, so we don't have enough information to track
            // to it.
        }
        return foundTag;
    }

    public void setManualExposure(int exposureMS, int gain) {
        if (visionPortal == null) {
            return;
        }

        // Make sure camera is streaming before we try to set the exposure controls
        if (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING) {
            while ((visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING)) {
                sleep(20);
            }
        }

        ExposureControl exposureControl = visionPortal.getCameraControl(ExposureControl.class);
        if (exposureControl.getMode() != ExposureControl.Mode.Manual) {
            exposureControl.setMode(ExposureControl.Mode.Manual);
            sleep(50);
        }
        exposureControl.setExposure(exposureMS, TimeUnit.MILLISECONDS);
        sleep(20);
        GainControl gainControl = visionPortal.getCameraControl(GainControl.class);
        gainControl.setGain(gain);
        sleep(20);
    }

    public static class Params {

    }
}
