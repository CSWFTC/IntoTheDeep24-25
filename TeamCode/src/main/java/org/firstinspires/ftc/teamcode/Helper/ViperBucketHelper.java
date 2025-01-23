package org.firstinspires.ftc.teamcode.Helper;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Helper.ViperSlideActions.ViperAction;

public class ViperBucketHelper  {
    private HardwareMap hardwareMap;
    private ViperAction viperSlideHelper;
    private BucketAction bucketAction;
    private ViperAction viperAction;

    public static class Params {
        public double bucketStartPos = 0.11;
        public double bucketCatchPos = 0.085;
        public double bucketDumpPos = 0.01;

        public int viperStartPos = 0;

        public boolean viperMotorReverse = true;
        public double viperHighBasketPos = 3150;
        public double viperLowBasketPos = 1220;
        public double viperCatchPoint = 0;
        public double viperMotorSpeed = 0.9;

        public double dumpLowBasketDelay = 750;
        public double dumpHighBasketDelay = 1250;
    }

    public static Params PARAMS = new Params();

    public ViperBucketHelper(HardwareMap hdwmap) throws Exception {
        hardwareMap= hdwmap;


        viperAction = new ViperAction();
        bucketAction = new BucketAction(viperAction);

        viperSlideHelper = new ViperAction();

        if (this.bucketAction.initErrorStatus) {
            throw new Exception("BucketAction Init Error: " + this.bucketAction.initError);
        }
    }

    public Action moveViperAndBucket(int viperPos, double viperPower, double bucketPos) {
        return packet -> {
            viperSlideHelper.moveToPosition(viperPos, viperPower);
            bucketAction.moveToPosition(bucketPos);
            return false;
        };
    }

    public Action resetViperAndBucket() {
        return packet -> {
            viperSlideHelper.resetEncoders();
            bucketAction.moveToPosition(0.11);
            return false;
        };
    }

    public Action raiseViperAndBucket() {
        return packet -> {
            viperSlideHelper.moveToPosition(500, 0.8);
            bucketAction.moveToPosition(0.09);
            return false;
        };
    }

    public Action lowerViperAndBucket() {
        return packet -> {
            viperSlideHelper.moveToPosition(0, 0.5);
            bucketAction.moveToPosition(0.11);
            return false;
        };
    }
}

//TODO: 1. prep for catch (ready for catch) (position 0), High & low basket; buttons man, override, dump
//TODO: Integrate into one class
//refer to old code & motor test

//driver control
//





