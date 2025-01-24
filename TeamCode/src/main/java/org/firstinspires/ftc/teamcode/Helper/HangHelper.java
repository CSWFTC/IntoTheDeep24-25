package org.firstinspires.ftc.teamcode.Helper;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class HangHelper {
    private DcMotor Left;
    private DcMotor Right;


    public HangHelper(HardwareMap hardwareMap) {

        Left = hardwareMap.get(DcMotor.class, "hookLeft");
        Right = hardwareMap.get(DcMotor.class, "hookRight");


        Left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        Left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        Left.setDirection(DcMotorSimple.Direction.FORWARD);
        Right.setDirection(DcMotorSimple.Direction.FORWARD);
    }


    public void moveMotors(double power) {
        Left.setPower(power);
        Right.setPower(power);
    }


    public void setMotorDirection(boolean forward) {
        if (forward) {
            Left.setDirection(DcMotorSimple.Direction.FORWARD);
            Right.setDirection(DcMotorSimple.Direction.FORWARD);
        } else {
            Left.setDirection(DcMotorSimple.Direction.REVERSE);
            Right.setDirection(DcMotorSimple.Direction.REVERSE);
        }
    }


    public void moveToPosition(int targetPosition) {
        Left.setTargetPosition(targetPosition);
        Right.setTargetPosition(targetPosition);

        Left.setPower(0.5);
        Right.setPower(0.5);

        Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }


    public void resetEncoders() {
        Left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
}



