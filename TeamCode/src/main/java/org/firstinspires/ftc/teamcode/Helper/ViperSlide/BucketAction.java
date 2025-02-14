package org.firstinspires.ftc.teamcode.Helper.ViperSlide;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class BucketAction {
    public static class Params {
       // public double bucketStartPos = 0.32;   // Tucked in For Driving
        public double bucketCatchPos = 0.58;  // Catch from Beak
        public double bucketDumpPos = 0.495;    // Dump to Basket
        //0.08 --> straight up
    }

    public static Params PARAMS = new Params();

    public static double targetBucketPosition = -1;
    private final Servo bucketServo;


    public BucketAction(@NonNull HardwareMap hdwMap) {
        bucketServo = hdwMap.servo.get("bucketServo");
        bucketServo.setDirection(Servo.Direction.FORWARD);
    }

    private void MoveBucket(double position) {
        bucketServo.setPosition(position);
        targetBucketPosition = position;
    }

    public void StartPosition() {
        MoveBucket(PARAMS.bucketCatchPos);
    }

    public void DumpSample() {
        MoveBucket(PARAMS.bucketDumpPos);
    }

    public void PrepForCatch() {
        MoveBucket(PARAMS.bucketCatchPos);
    }

    public void ToggleBucket() {
        if (targetBucketPosition != PARAMS.bucketDumpPos)
            DumpSample();
        else
            PrepForCatch();
    }

    public Action autonDumpSample(){
        return packet ->{
            DumpSample();
          return false;
        };

    }

    public Action autonPrepForCatch(){
        return packet ->{
          PrepForCatch();
          return false;
        };
    }
}
