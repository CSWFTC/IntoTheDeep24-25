package org.firstinspires.ftc.teamcode.Helper.ViperSlideActions;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.Inject;
import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.Injectable;

public class BucketAction extends Injectable {
    public static class Params {
        public double servoStartPos = 0.532;
        public double servoPresetPosX = 0.532;
        public double servoPresetPosB = 0.532;
    }

    private boolean isServoForward = false;
    private double currentServoPosition = 0.0;
    private double targetServoPosition = PARAMS.servoStartPos;

    private static final double POSITION_INCREMENT_LARGE = 0.1;
    private static final double POSITION_INCREMENT_SMALL = 0.005;

    public static Params PARAMS = new Params();

    @Inject("bucketServoName")
    public String servoName;

    public boolean initErrorStatus = false;
    public String initError = "";

    private ViperAction viperAction;

    @Inject("hdwMap")
    private HardwareMap hardwareMap;

    private Servo bucketServo;

    public BucketAction(ViperAction viperAction) {
        super();
        this.viperAction = viperAction;

        try {
            this.bucketServo = hardwareMap.servo.get(this.servoName);
        } catch(Exception e) {
            this.initErrorStatus = true;
            this.initError = e.toString();
        }
    }

    public void moveToPosition(double position) {
        targetServoPosition = clampPosition(position);
        bucketServo.setPosition(targetServoPosition);
        currentServoPosition = targetServoPosition;
    }

    public void adjustPosition(double increment) {
        targetServoPosition = clampPosition(targetServoPosition + increment);
        bucketServo.setPosition(targetServoPosition);
        currentServoPosition = targetServoPosition;
    }

    public double clampPosition(double position) {
        // Ensure the servo position stays between 0.0 and 1.0
        return Math.min(1.0, Math.max(0.0, position));
    }

    public void toggleServoDirection() {
        isServoForward = !isServoForward;
        updateServoDirection();
    }

    public void updateServoDirection() {
        bucketServo.setDirection(isServoForward ? Servo.Direction.FORWARD : Servo.Direction.REVERSE);
    }
}
