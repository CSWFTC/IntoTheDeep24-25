package org.firstinspires.ftc.teamcode.Helper.ViperSlide;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.Inject;
import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.Injectable;

public class BucketAction {
    public static class Params {
        public double bucketStartPos = 0.12;   // Tucked in For Driving
        public double bucketCatchPos = 0.085;  // Catch from Beak
        public double bucketDumpPos = 0.01;    // Dump to Basket
    }

    public static Params PARAMS = new Params();
    public double targetBucketPosition = -1;
    private Servo bucketServo;


    public BucketAction(@NonNull HardwareMap hdwMap) {
        bucketServo = hdwMap.servo.get("bucketServo");
        bucketServo.setDirection(Servo.Direction.FORWARD);
    }

    private void MoveBucket(double position) {
        bucketServo.setPosition(position);
        targetBucketPosition = position;
    }

    public void StartPosition() {
        MoveBucket(PARAMS.bucketStartPos);
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
