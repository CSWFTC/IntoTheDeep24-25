package org.firstinspires.ftc.teamcode.Helper.ViperSlideActions;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ViperSlideHelper {

    private DcMotor viperMotor;

    public ViperSlideHelper(HardwareMap hardwareMap) {
        viperMotor = hardwareMap.get(DcMotor.class, "viperMotor");

        viperMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

         viperMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    public void setPower(double power) {
        viperMotor.setPower(power);
    }

    public void stopMotor() {
        viperMotor.setPower(0);
    }

    public void moveToPosition(int targetPosition, double power) {
        viperMotor.setTargetPosition(targetPosition);
        viperMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        viperMotor.setPower(power);
    }

    public boolean isAtTargetPosition() {
        return !viperMotor.isBusy();
    }

    public void resetEncoders() {
        viperMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        viperMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public int getCurrentPosition() {
        return viperMotor.getCurrentPosition();
    }
}