package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Helper.OpenCV.OpenCVHelper;

@Config
@TeleOp(name = "OpenCV Test", group = "Test")
public class OpenCV extends LinearOpMode {

    @Override
    public void runOpMode() {
        WebcamName webcamName = hardwareMap.get(WebcamName.class, "Webcam Back");

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        OpenCVHelper openCVHelper = new OpenCVHelper(webcamName, cameraMonitorViewId, true);

        openCVHelper.openCameraAndStart();

        waitForStart();

        while (opModeIsActive()) {
            // openCVHelper.openCameraAndStart();
//            double distance = openCVHelper.getDistanceToSample();
//
//            if (distance > 0) {
//                telemetry.addData("Distance to Sample (cm):", distance);
//            } else {
//                telemetry.addData("Distance to Sample (cm):", "Object not detected");
//            }
//
            telemetry.clear();
            double[] processedData = openCVHelper.getProcessedData();
            double width = processedData[0];
            double height = processedData[1];
            double distance = processedData[2];
            double offset = processedData[3];

            telemetry.addData("width", width);
            telemetry.addData("height", height);
            telemetry.addData("distance", distance);
            telemetry.addData("offset", offset);

            telemetry.update();
        }

        openCVHelper.stopCamera();
    }
}
