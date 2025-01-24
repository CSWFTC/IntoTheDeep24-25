package org.firstinspires.ftc.teamcode.Helper.ViperSlide;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.Inject;
import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.Injectable;

public class BucketAction extends Injectable {
    public static class Params {
        public boolean bucketServoReverse = false;
        public double bucketStartPos = 0.12;   // Tucked in For Driving
        public double bucketCatchPos = 0.085;  // Catch from Beak
        public double bucketDumpPos = 0.01;    // Dump to Basket
    }

    public static Params PARAMS = new Params();
    public double targetBucketPosition = -1;
    private Servo bucketServo;

    @Inject("hdwMap")
    private HardwareMap hardwareMap;


    public BucketAction() {
        super();
        bucketServo = hardwareMap.servo.get("bucketServo");
        bucketServo.setDirection((PARAMS.bucketServoReverse) ?
                Servo.Direction.REVERSE : Servo.Direction.FORWARD);
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
}
