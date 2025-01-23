package org.firstinspires.ftc.teamcode.Helper.ViperSlideActions;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import android.os.SystemClock;

import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.Inject;
import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.Injectable;
import org.firstinspires.ftc.teamcode.Helper.ViperSlideActions.BucketAction;

public class ViperAction extends Injectable {
    public static class Params {

        public boolean viperMotorReverse = true;
        public double viperHighBasketPos = 3150;  // High Basket
        public double viperLowBasketPos = 1220;  // Low Basket (Approx 38% of High Basket)
        public double viperCatchPoint = 0;       // Catch Point for Sample
        public double viperMotorSpeed = 0.9;

        public double dumpLowBasketDelay = 750;  //ms To Wait for Dump
        public double dumpHighBasketDelay = 1250;  //ms To Wait for Dump
    }

    public static Params PARAMS = new Params();

    private final  DcMotor viperMotor;
    private BucketAction bucketAction;

    @Inject("hdwMap")
    private HardwareMap hardwareMap;

    public ViperAction() {
        viperMotor = hardwareMap.get(DcMotor.class, "viperMotor");
        viperMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        viperMotor.setDirection(DcMotor.Direction.REVERSE);

        viperMotor.setDirection((PARAMS.viperMotorReverse)? DcMotorSimple.Direction.REVERSE:DcMotorSimple.Direction.FORWARD);
        viperMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        viperMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        viperMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }




    public void moveToPosition(int targetPosition) {
        viperMotor.setTargetPosition(targetPosition);
        viperMotor.setPower(PARAMS.viperMotorSpeed);
        viperMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
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


    public void moveToHighBasket() {
        moveToPosition((int) PARAMS.viperHighBasketPos);
        waitUntilAtTarget();
    }


    public void moveToLowBasket() {
        moveToPosition((int) PARAMS.viperLowBasketPos);
        waitUntilAtTarget();
    }


    private void waitUntilAtTarget() {
        while (viperMotor.isBusy()) {

        }
    }


    public void dumpLowBasket() {
        moveToLowBasket();
        SystemClock.sleep((long) PARAMS.dumpLowBasketDelay);

    }


    public void dumpHighBasket() {
        moveToHighBasket();
        SystemClock.sleep((long) PARAMS.dumpHighBasketDelay);

    }
}


