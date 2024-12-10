package org.firstinspires.ftc.teamcode.Helper;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Helper.ViperSlideActions.BucketAction;
import org.firstinspires.ftc.teamcode.Helper.ViperSlideActions.ViperAction;
import org.firstinspires.ftc.teamcode.Helper.ViperSlideActions.ViperSlideHelper;

public class ViperBucketHelper  {
    private HardwareMap hardwareMap;
    private ViperSlideHelper viperSlideHelper;
    private BucketAction bucketAction;
    private ViperAction viperAction;

    public static class Params {
        public double bucketStartPos = 0.11;  // Tucked in
        public double bucketCatchPos = 0.085;  // Catch from Beak
        public double bucketDumpPos = 0.01;    // Dump to Basket

        public int viperStartPos = 0;

        public boolean viperMotorReverse = true;
        public double viperHighBasketPos = 3150;  // High Basket
        public double viperLowBasketPos = 1220;  // Low Basket (Approx 38% of High Basket)
        public double viperCatchPoint = 0;       // Catch Point for Sample
        public double viperMotorSpeed = 0.9;

        public double dumpLowBasketDelay = 750;  //ms To Wait for Dump
        public double dumpHighBasketDelay = 1250;  //ms To Wait for Dump
    }

    public static Params PARAMS = new Params();

    public ViperBucketHelper(HardwareMap hdwmap) throws Exception {
        hardwareMap= hdwmap;


        viperAction = new ViperAction();
        bucketAction = new BucketAction(viperAction);

        viperSlideHelper = new ViperSlideHelper();

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






