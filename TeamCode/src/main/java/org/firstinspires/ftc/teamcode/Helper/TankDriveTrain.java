package org.firstinspires.ftc.teamcode.Helper;


import static java.lang.Thread.sleep;
import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class TankDriveTrain {

    public static TankDriveTrain.Params PARAMS = new TankDriveTrain.Params();

    private final DcMotor leftMotor;
    private final DcMotor rightMotor;
    private volatile boolean brakingOn = false;


    public static class Params {
        public double joystickYInputAdjustment  = -1;
        public double brakingStopThreshold = 0.25;
        public double brakingGain = 0.15;
        public long brakingInterval = 100;
        public double brakingMaximumTime = (long) Math.ceil(1 / brakingGain) * brakingInterval;
    }

    public TankDriveTrain(@NonNull HardwareMap hdwMap) {
        leftMotor = hdwMap.dcMotor.get("leftMotor");
        rightMotor = hdwMap.dcMotor.get("rightMotor");

        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightMotor.setDirection(DcMotorSimple.Direction.FORWARD);


        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }


    public void setDrivePower(double leftPower, double rightPower) {
        if (brakingOn) return;

        leftMotor.setPower(leftPower);
        rightMotor.setPower(rightPower);
    }


    public void setDriveFromJoystick(float stickLeftY, float stickRightX, boolean reversed) {
        if (brakingOn) return;


            double leftPower = stickLeftY + stickRightX;
            double rightPower = stickLeftY - stickRightX;


            double maxPower = Math.max(Math.abs(leftPower), Math.abs(rightPower));

            if (maxPower > 1.0) {
                leftPower /= maxPower;
                rightPower /= maxPower;
            }

            // between 1.0 and max of 2.0
            leftPower = Math.max(1.0, Math.min(2.0, leftPower));
            rightPower = Math.max(1.0, Math.min(2.0, rightPower));

            //  -1.0 or lower
            rightPower = (rightPower > 0) ? rightPower : Math.min(-1.0, rightPower);



        setDrivePower(leftPower, rightPower);
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
}

