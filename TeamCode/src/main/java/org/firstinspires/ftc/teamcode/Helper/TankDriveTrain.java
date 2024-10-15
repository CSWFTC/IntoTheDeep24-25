package org.firstinspires.ftc.teamcode.Helper;

import static java.lang.Thread.sleep;
import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
public class TankDriveTrain {

    public static TankDriveTrain.Params PARAMS = new TankDriveTrain.Params();

    private final DcMotor leftMotor;
    private final DcMotor rightMotor;
    private final DcMotor viperMotor;
    private volatile boolean brakingOn = false;

    public static class Params {
        public double joystickYInputAdjustment  = -1;
        public double brakingStopThreshold = 0.25;
        public double brakingGain = 0.15;
        public long brakingInterval = 100;
        public double brakingMaximumTime = (long) Math.ceil(1 / brakingGain) * brakingInterval;
        public double viperMotorSpeed = 0.5;
    }

    public TankDriveTrain(@NonNull HardwareMap hdwMap) {
        leftMotor = hdwMap.dcMotor.get("tankLeft");
        rightMotor = hdwMap.dcMotor.get("tankRight");
        viperMotor = hdwMap.dcMotor.get("Viper");

        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        viperMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        viperMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setDriveFromJoystick(float stickLeftY, float stickRightX) {
        if (brakingOn) return;

        double leftPower = Math.max(Math.min(1.0, (stickLeftY + stickRightX)), -1.0);
        double rightPower = Math.max(Math.min(1.0, (stickLeftY - stickRightX)), -1.0);

        leftMotor.setPower(leftPower);
        rightMotor.setPower(rightPower);
    }

    public void setBrakeStatus(boolean braking) {
        brakingOn = braking;
        if (braking) {
            boolean bothStop = false;
            boolean timerExpired = false;
            long brakeStart = System.currentTimeMillis();

            while (!bothStop && !timerExpired) {
                boolean leftStop = coasterBrakeMotor(leftMotor);
                boolean rightStop = coasterBrakeMotor(rightMotor);

                bothStop = leftStop && rightStop;
                timerExpired = (System.currentTimeMillis() >= (brakeStart + PARAMS.brakingMaximumTime));

                if (!bothStop && !timerExpired) {
                    try {
                        sleep(PARAMS.brakingInterval);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    private boolean coasterBrakeMotor(DcMotor motor) {
        double curPower = motor.getPower();
        boolean stopped = (curPower == 0);

        if (!stopped) {
            double newPower = curPower - (Math.signum(curPower) * PARAMS.brakingGain);
            if (Math.abs(newPower) < PARAMS.brakingStopThreshold) newPower = 0;
            motor.setPower(newPower);
        }

        return stopped;
    }

    // viperMotor up
    public void moveViperUp() {
        viperMotor.setPower(PARAMS.viperMotorSpeed);
    }

    //  viperMotor down
    public void moveViperDown() {
        viperMotor.setPower(-PARAMS.viperMotorSpeed);
    }

    public void stopViperMotor() {
        viperMotor.setPower(0);
    }
}


