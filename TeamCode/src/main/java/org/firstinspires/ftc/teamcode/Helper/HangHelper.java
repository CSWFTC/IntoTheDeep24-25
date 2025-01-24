 package org.firstinspires.ftc.teamcode.Helper;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class HangHelper {
    private DcMotor motor1;
    private DcMotor motor2;


    public HangHelper(DcMotor motor1, DcMotor motor2) {
        motor1 = motor1;
        motor2 = motor2;


        motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    //  forward or backward based on input
    public void moveMotors(double power) {
        motor1.setPower(power);
        motor2.setPower(power);
    }

    // direction for both motors
    public void setMotorDirection(boolean forward) {
        if (forward) {
            motor1.setDirection(DcMotorSimple.Direction.FORWARD);
            motor2.setDirection(DcMotorSimple.Direction.FORWARD);
        } else {
            motor1.setDirection(DcMotorSimple.Direction.REVERSE);
            motor2.setDirection(DcMotorSimple.Direction.REVERSE);
        }
    }

    // sets motors to a specific target position (for hanging)
    public void moveToPosition(int targetPosition) {
        motor1.setTargetPosition(targetPosition);
        motor2.setTargetPosition(targetPosition);

        motor1.setPower(0.5);
        motor2.setPower(0.5);

        motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    // Reset
    public void resetEncoders() {
        motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

}


