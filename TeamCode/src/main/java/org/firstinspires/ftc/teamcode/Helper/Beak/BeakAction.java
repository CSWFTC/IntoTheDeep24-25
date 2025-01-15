package org.firstinspires.ftc.teamcode.Helper.Beak;

import android.os.SystemClock;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Helper.DeferredActions;
import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.Inject;
import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.Injectable;


@Config
public class BeakAction extends Injectable {
    public static class Params {
        public double armDrivePos = 0.2;
        public double armPickStartPos = 0.27;
        public double armBucketDropPos = 0.25;
        public double armPickReachPos = 0.36;
        //min=0.36
        public double armDumpPos = 0.2;

        public double elbowDrivePos = 0.668;
        public double elbowPickStartPos = 0.56;
        public double elbowBucketDropPos = 0.69;
        public double elbowPickReachPos = 0.56;
        //min=0.56
        public double elbowDumpPos = 0.64;

        public double beakOpenGatherPos = 0.4;
        public double beakOpenDropPos = 0.45;
        public double beakClosedPos = 0.75;
        public double beakSuplexOpenDelay = 600;
    }

    public static Params PARAMS = new Params();

    public double targetArmPosition = -1;
    public double targetElbowPosition = -1;
    public double targetBeakPosition = -1;


    @Inject("hdwMap")
    public HardwareMap hardwareMap;

    private final Servo beak;
    private final Servo arm;
    private final Servo elbow;

    public BeakAction() {
        super();


        beak =hardwareMap.servo.get("beakServo");
        beak.setDirection(Servo.Direction.FORWARD);

        arm =hardwareMap.servo.get("armServo");
        arm.setDirection(Servo.Direction.FORWARD);

        elbow =hardwareMap.servo.get("elbowServo");
        elbow.setDirection(Servo.Direction.FORWARD);
    }

    private void MoveArm(double position) {
        arm.setPosition(position);
        targetArmPosition=position;
    }

    private void MoveElbow(double position) {
       elbow.setPosition(position);
       targetElbowPosition=position;
    }

    private void MoveBeak(double position) {
        beak.setPosition(position);
        targetBeakPosition=position;
    }



    public void DrivePosition() {
        MoveArm(PARAMS.armDrivePos);
        MoveElbow(PARAMS.elbowDrivePos);
        MoveBeak(PARAMS.beakClosedPos);
    }

    public void PrepForPickup() {
        MoveElbow(PARAMS.elbowPickStartPos);
        MoveArm(PARAMS.armPickStartPos);
        MoveBeak(PARAMS.beakOpenGatherPos);
    }

    public void PrepForBucketDump() {
        MoveElbow(PARAMS.elbowDumpPos);
        MoveArm(PARAMS.armDumpPos);
    }

    public void PickupReach() {
        MoveElbow(PARAMS.elbowPickReachPos);
        MoveArm(PARAMS.armPickReachPos);
        if (targetBeakPosition != PARAMS.beakOpenGatherPos)
            MoveBeak(PARAMS.beakOpenGatherPos);
    }

    public void CloseBeak() {
        MoveBeak(PARAMS.beakClosedPos);
    }

    public void OpenBeak() {
        if (targetElbowPosition == PARAMS.elbowBucketDropPos)
            MoveBeak(PARAMS.beakOpenDropPos);
        else
            MoveBeak(PARAMS.beakOpenGatherPos);
    }

    public void SuplexSample() {
        MoveArm(PARAMS.armBucketDropPos);
        MoveElbow(PARAMS.elbowBucketDropPos);
        DeferredActions.CreateDeferredAction( (long) PARAMS.beakSuplexOpenDelay, DeferredActions.DeferredActionType.BEAK_OPEN);

    }

    public void SuplexCloseBeak() {
        if (targetBeakPosition != PARAMS.beakClosedPos)  {
            MoveBeak(PARAMS.beakClosedPos);
            DeferredActions.CreateDeferredAction(100, DeferredActions.DeferredActionType.SUPLEX_BEAK);
        }else {
            SuplexSample();
        }
    }
}
