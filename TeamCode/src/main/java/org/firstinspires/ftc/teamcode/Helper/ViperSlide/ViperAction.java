package org.firstinspires.ftc.teamcode.Helper.ViperSlide;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import android.os.SystemClock;

import androidx.annotation.NonNull;

public class ViperAction {
    public static class Params {
        public boolean viperMotorReverse = true;
        public double viperHighBasketPos = 3100;  // High Basket
        public double viperLowBasketPos = 1200;   // Low Basket (Approx 38% of High Basket)
        public double viperCatchPoint = 0;        // Catch Point for Sample
        public double viperMotorSpeed = 0.9;
        public double viperMaxPos = 3500;
        public double viperPowerLimitPos = 3200;
        public double clawLow = 460;
        public double clawLowHang = 70;
        public double clawHigh = 1000;
        public double clawHighHang = 700;
        public double clawWall = 0;

        public double dumpLowBasketDelay = 750;    //ms To Wait for Dump
        public double dumpHighBasketDelay = 1250;  //ms To Wait for Dump
    }

    public static Params PARAMS = new Params();

    private final DcMotor viperMotor;
    private BucketAction bucketAction;
    private ClawAction clawAction;

    public ViperAction(@NonNull HardwareMap hdwMap) {
        viperMotor = hdwMap.get(DcMotor.class, "viperMotor");
        viperMotor.setDirection((PARAMS.viperMotorReverse)? DcMotorSimple.Direction.REVERSE:DcMotorSimple.Direction.FORWARD);
        viperMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        viperMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        viperMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bucketAction = new BucketAction(hdwMap);
        clawAction = new ClawAction(hdwMap);
    }


    private void moveToPosition(int targetPosition) {
        viperMotor.setTargetPosition(targetPosition);
        viperMotor.setPower(PARAMS.viperMotorSpeed);
        viperMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void moveWithPower(double power) {
        if (viperMotor.getMode() != DcMotor.RunMode.RUN_USING_ENCODER)
            viperMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        int pos = viperMotor.getCurrentPosition();
        if (pos > PARAMS.viperMaxPos)
            viperMotor.setPower(0);   // Stop Motor at Top Limit
        else if ((pos >= PARAMS.viperPowerLimitPos) ||
                (pos <= (PARAMS.viperMaxPos - PARAMS.viperPowerLimitPos)))
            // Reduce Speed Near Physical Limits
            viperMotor.setPower(Range.clip(power, -0.3, 0.3));
        else
            // Full Joystick Power
            viperMotor.setPower(power);
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
    }


    public void moveToLowBasket() {
        moveToPosition((int) PARAMS.viperLowBasketPos);
    }

    /*
     * Autonomous Viper Movements
     */
    public Action dumpSampleLowBasket() {
        return packet -> {
            moveToLowBasket();
            SystemClock.sleep((long) PARAMS.dumpLowBasketDelay);
            bucketAction.DumpSample();
            return false;
        };
    }

    public Action dumpSampleHighBasket() {
        return packet -> {
            moveToHighBasket();
            SystemClock.sleep((long) PARAMS.dumpHighBasketDelay);
            bucketAction.DumpSample();
            return false;
        };
    }

    /*
    Claw + Viper
     */
    public void moveForSub () { moveToPosition((int) PARAMS.clawLow);}
    public void placeOnSub () {moveToPosition((int) PARAMS.clawLowHang);}
    public void clawHuman () {moveToPosition((int) PARAMS.clawWall);}
    public void perfMoveForSub () {moveToPosition((int) PARAMS.clawHigh);}
    public void perfPlaceOnSub () {moveToPosition((int) PARAMS.clawHighHang);}

    /*
    Claw + Viper Auto
     */

    public Action clawDropOnSub () {
        return packet -> {
            placeOnSub();
            SystemClock.sleep(250);
          return false;
        };
    }

    public Action clawHumanGrab () {
        return packet -> {
            clawHuman();
            SystemClock.sleep(250);
            return false;
        };
    }

    public Action beforeDropOff () {
        return packet -> {
            moveForSub();
            SystemClock.sleep(250);
            return false;
        };
    }


}


