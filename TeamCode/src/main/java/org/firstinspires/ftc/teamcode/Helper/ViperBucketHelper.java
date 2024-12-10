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
        public int viperStartPos = 0;
        public double bucketStartPos = 0.11;
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






