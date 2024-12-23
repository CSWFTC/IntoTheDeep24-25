package org.firstinspires.ftc.teamcode.TeleOp.Tests;


import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helper.AprilTag;
import org.firstinspires.ftc.teamcode.Helper.DeferredActions;
import org.firstinspires.ftc.teamcode.Helper.DeferredActions.DeferredActionType;
import org.firstinspires.ftc.teamcode.Helper.DrivetrainV2;
import org.firstinspires.ftc.teamcode.Helper.GamePad;
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.ThreeDeadWheelLocalizer;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;
import java.util.Locale;

@Disabled
@TeleOp(name = "Test April Tag", group = "Test")
public class TestAprilTag extends LinearOpMode {
    private static final String version = "1.0";
    private boolean setReversed = false;
    // private ClawMoves yclaw;

    @Override
    public void runOpMode() {
        // Load Introduction and Wait for Start
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);
        telemetry.addLine("Driver Control");
        telemetry.addData("Version Number", version);
        telemetry.addLine();
        telemetry.addData(">", "Press Start to Launch");
        telemetry.update();

        GamePad gpIn1 = new GamePad(gamepad1, false);
        GamePad gpIn2 = new GamePad(gamepad2);
        DrivetrainV2 drvTrain = new DrivetrainV2(hardwareMap);
        AprilTag aprilTag = new AprilTag(hardwareMap);
        AprilTagDetection foundTag;
        ThreeDeadWheelLocalizer localizer = new ThreeDeadWheelLocalizer(hardwareMap, MecanumDrive.PARAMS.inPerTick);
        Pose2d rrPose = null;
        // TestServo serv1 = hardwareMap.servo.get(PARAMS.);

        waitForStart();
        if (isStopRequested()) return;


        telemetry.clear();

        double speedMultiplier = 1;
        double lastSpeed = 1;

        while (opModeIsActive()) {
            AprilTagDetection result = aprilTag.lookForMatching();
            if (result != null) {
                foundTag = result;
                if (rrPose == null) {
                    rrPose = new Pose2d(foundTag.ftcPose.x, foundTag.ftcPose.y, foundTag.ftcPose.bearing);
                } else {
                    // TODO: Roadrunner-reported translations are the opposite of that reported by AprilTag, probably because the camera is mounted on the back
                    rrPose = rrPose.plus(localizer.update().value());
                }
            } else {
                rrPose = null;
                foundTag = null;
            }
            update_telemetry(gpIn1, gpIn2, foundTag, rrPose);


            GamePad.GameplayInputType inpType1 = gpIn1.WaitForGamepadInput(30);
            switch (inpType1) {
                case LEFT_STICK_BUTTON_ON:
                    if (speedMultiplier != 1) {
                        lastSpeed = speedMultiplier;
                        speedMultiplier = 1;
                    }
                    break;

                case LEFT_STICK_BUTTON_OFF:
                    if (lastSpeed != 1) {
                        speedMultiplier = lastSpeed;
                        lastSpeed = 1;
                    }
                    break;

                case BUTTON_A:
                    speedMultiplier = 0.25;
                    break;

                case BUTTON_X:
                    speedMultiplier = 0.75;
                    break;

                case BUTTON_B:
                    speedMultiplier = 0.5;
                    break;

                case BUTTON_Y:
                    speedMultiplier = 1;
                    break;

                case BUTTON_BACK:
                    setReversed = !setReversed;
                    break;

                case JOYSTICK:
                    drvTrain.setDriveVectorFromJoystick(gamepad1.left_stick_x * (float) speedMultiplier,
                            gamepad1.right_stick_x * (float) speedMultiplier,
                            gamepad1.left_stick_y * (float) speedMultiplier, setReversed);
                    break;


            }
            GamePad.GameplayInputType inpType2 = gpIn2.WaitForGamepadInput(30);
            switch (inpType2) {

                case LEFT_STICK_BUTTON_ON:
                    if (speedMultiplier != 1) {
                        lastSpeed = speedMultiplier;
                        speedMultiplier = 1;
                    }
                    break;

                case LEFT_STICK_BUTTON_OFF:
                    if (lastSpeed != 1) {
                        speedMultiplier = lastSpeed;
                        lastSpeed = 1;
                    }
                    break;

                case JOYSTICK:
                    drvTrain.setDriveVectorFromJoystick(gamepad2.left_stick_x * (float) speedMultiplier,
                            gamepad2.right_stick_x * (float) speedMultiplier,
                            gamepad2.left_stick_y * (float) speedMultiplier, setReversed);
                    break;

                case BUTTON_Y:
                  //  DeferredActions.CreateDeferredAction(150, DeferredActionType.MOVEMENT);


            }

            // Deferred Actions
            ProcessDeferredActions();
        }
    }

    // Deferred Actions
    public void ProcessDeferredActions() {
        List<DeferredActionType> action = DeferredActions.GetReadyActions();

        for (DeferredActionType actionType : action) {
            switch (actionType) {

                default:
                    telemetry.addLine("ERROR - Unsupported Deferred Action");
                    break;
            }


        }
    }

    private void update_telemetry(GamePad gpi1, GamePad gpi2, AprilTagDetection aprilTag, Pose2d rrPose) {
        telemetry.addLine("Gamepad #1");
        String inpTime1 = new java.text.SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS", Locale.US).format(gpi1.getTelemetry_InputLastTimestamp());
        telemetry.addLine().addData("GP1 Time", inpTime1);
        telemetry.addLine().addData("GP1 Cnt", gpi1.getTelemetry_InputCount());
        telemetry.addLine().addData("GP1 Input", gpi1.getTelemetry_InputLastType().toString());
        telemetry.addLine().addData("L Joy  X", "%6.3f", gamepad1.left_stick_x).addData("Y", "%6.3f", gamepad1.left_stick_y);
        telemetry.addLine().addData("R Joy  X", "%6.3f", gamepad1.right_stick_x).addData("Y", "%6.3f", gamepad1.right_stick_y);

        telemetry.addLine();
        telemetry.addLine("Gamepad #2");
        String inpTime2 = new java.text.SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS", Locale.US).format(gpi2.getTelemetry_InputLastTimestamp());
        telemetry.addLine().addData("GP 2 Time", inpTime2);
        telemetry.addLine().addData("GP2 Cnt", gpi2.getTelemetry_InputCount());
        telemetry.addLine().addData("GP2 Input", gpi2.getTelemetry_InputLastType().toString());
        telemetry.addLine().addData("L Joy  X", "%6.3f", gamepad2.left_stick_x).addData("Y", "%6.3f", gamepad2.left_stick_y);
        telemetry.addLine().addData("R Joy  X", "%6.3f", gamepad2.right_stick_x).addData("Y", "%6.3f", gamepad2.right_stick_y);

        telemetry.addLine();
        telemetry.addLine("Deferred Actions");
        String actTime = new java.text.SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS", Locale.US).format(DeferredActions.tlmLastActionTimestamp);
        telemetry.addLine().addData("Time", actTime);
        telemetry.addLine().addData("Action", DeferredActions.tlmLastAction.toString());

        telemetry.addLine();
        telemetry.addLine("AprilTag");
        telemetry.addLine()
                .addData(
                        (aprilTag != null)
                                ?
                                "AprilTagFtcPose"
                                :
                                "No April Tag Found",
                        (aprilTag != null)
                                ?
                                "[" + aprilTag.ftcPose.x + " " + aprilTag.ftcPose.y + " " + aprilTag.ftcPose.z + " " + aprilTag.ftcPose.bearing + "degrees]"
                                :
                                ""
                );
        telemetry.addLine()
                .addData(
                        (rrPose != null)
                                ?
                                "RoadRunner Pose"
                                :
                                "No April Tag Found",
                        (rrPose != null)
                                ?
                                "[" + rrPose.position.x + " " + rrPose.position.y + " " + rrPose.heading.toDouble() + "degrees]"
                                :
                                ""
                );
        telemetry.update();
    }
}
