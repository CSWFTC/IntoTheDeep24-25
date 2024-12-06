package org.firstinspires.ftc.teamcode.Helper.ViperSlideActions;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.Inject;
import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.Injectable;

public class ViperSlideHelper extends Injectable {

    private DcMotor viperMotor;

    @Inject("hdwMap")
    private HardwareMap hardwareMap;

    public ViperSlideHelper() {
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

    public Action moveVip(int pos1, double power){
        return packet -> {
            resetEncoders();
            moveToPosition(pos1, power);
            return false;
        };

    }

    public Action resetVip(){
        return packet ->{
            getCurrentPosition();
            moveToPosition((getCurrentPosition()-5)*-1, 0.8);
        return false;
        };

    }
}


