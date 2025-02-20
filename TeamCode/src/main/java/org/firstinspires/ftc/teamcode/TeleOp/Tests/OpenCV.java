package org.firstinspires.ftc.teamcode.TeleOp.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Helper.OpenCV.OpenCVHelper;

@Disabled
@TeleOp(name = "OpenCV Test", group = "Test")
public class OpenCV extends LinearOpMode {

    @Override
    public void runOpMode() {

        OpenCVHelper openCVHelper = new OpenCVHelper(hardwareMap, true);

        openCVHelper.openCameraAndStart();

        waitForStart();

        while (opModeIsActive()) {


        }

        openCVHelper.stopCamera();
    }
}
