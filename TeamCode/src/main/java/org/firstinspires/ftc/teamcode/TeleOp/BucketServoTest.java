package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helper.GamePad;

@TeleOp(name = "BucketServoTest", group = "Hardware")
public class BucketServoTest extends LinearOpMode {

    public static class Params {
        public String servoName = "bucketServo";
        public double servoForward = 1.0;
        public double servoStartPos = 0.532;
        public double servoPresetPosX = 0.532;
        public double servoPresetPosB = 0.532;
    }

    public static Params PARAMS = new Params();

    private GamePad gpInput;
    private FtcDashboard dashboard;
    private Servo bucketServo;

    private boolean isServoForward = false;
    private double currentServoPosition = 0.0;
    private double targetServoPosition = PARAMS.servoStartPos;

    private static final double POSITION_INCREMENT_LARGE = 0.1;
    private static final double POSITION_INCREMENT_SMALL = 0.005;

    @Override
    public void runOpMode() {
        if (!initialize()) return;

        waitForStart();
        if (isStopRequested()) return;

        updateServoDirection();

        moveToPosition(PARAMS.servoStartPos);

        while (opModeIsActive()) {
            handleGamepadInputs();
            updateTelemetry();
        }
    }

    private boolean initialize() {
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);
        telemetry.addLine("Servo Test");
        telemetry.addLine();

        try {
            bucketServo = hardwareMap.servo.get(PARAMS.servoName);
            gpInput = new GamePad(gamepad1);
            dashboard = FtcDashboard.getInstance();
            dashboard.clearTelemetry();

            telemetry.addLine("All Sensors Initialized");
            telemetry.addLine("");
            telemetry.addData(">", "Press Play to Start");
            telemetry.update();

            return true;
        } catch (Exception e) {
            telemetry.addLine("*** INITIALIZATION FAILED ***");
            telemetry.update();
            return false;
        }
    }

    private void handleGamepadInputs() {
        GamePad.GameplayInputType inputType = gpInput.WaitForGamepadInput(100);

        switch (inputType) {
            case BUTTON_A:
                moveToPosition(targetServoPosition);
                break;
            case BUTTON_L_BUMPER:
                toggleServoDirection();
                break;
            case BUTTON_X:
                moveToPosition(PARAMS.servoPresetPosX);
                break;
            case BUTTON_B:
                moveToPosition(PARAMS.servoPresetPosB);
                break;
            case DPAD_UP:
                adjustPosition(POSITION_INCREMENT_LARGE);
                break;
            case DPAD_DOWN:
                adjustPosition(-POSITION_INCREMENT_LARGE);
                break;
            case DPAD_LEFT:
                adjustPosition(POSITION_INCREMENT_SMALL);
                break;
            case DPAD_RIGHT:
                adjustPosition(-POSITION_INCREMENT_SMALL);
                break;
        }
    }

    private void moveToPosition(double position) {
        targetServoPosition = clampPosition(position);
        bucketServo.setPosition(targetServoPosition);
        currentServoPosition = targetServoPosition;
    }

    private void adjustPosition(double increment) {
        targetServoPosition = clampPosition(targetServoPosition + increment);
        bucketServo.setPosition(targetServoPosition);
        currentServoPosition = targetServoPosition;
    }

    private double clampPosition(double position) {
        // Ensure the servo position stays between 0.0 and 1.0
        return Math.min(1.0, Math.max(0.0, position));
    }

    private void toggleServoDirection() {
        isServoForward = !isServoForward;
        updateServoDirection();
    }

    private void updateServoDirection() {
        bucketServo.setDirection(isServoForward ? Servo.Direction.FORWARD : Servo.Direction.REVERSE);
    }

    private void updateTelemetry() {
        telemetry.addLine("Servo Test");
        telemetry.addLine("Use Dpad to Set Position");
        telemetry.addLine("  Up/Down    +/- 0.1");
        telemetry.addLine("  Left/Right +/- 0.005");
        telemetry.addLine("Button A --> GoTo New Position");
        telemetry.addLine("Left Bumper --> Change Direction");
        telemetry.addLine().addData("Name", PARAMS.servoName);
        telemetry.addLine().addData("Direction", isServoForward ? "Forward" : "Reverse");
        telemetry.addLine().addData("Curr Pos", currentServoPosition);
        telemetry.addLine().addData("New Pos", targetServoPosition);
        telemetry.update();


        TelemetryPacket packet = new TelemetryPacket();
        packet.put("Name", PARAMS.servoName);
        packet.put("Direction", isServoForward ? 1 : 0);
        packet.put("Curr Position", currentServoPosition);
        packet.put("New Position", targetServoPosition);
        dashboard.sendTelemetryPacket(packet);
    }
}

