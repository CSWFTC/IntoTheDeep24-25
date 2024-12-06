package org.firstinspires.ftc.teamcode.Helper.Beak;

import android.os.SystemClock;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Helper.DeferredActions;
import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.Inject;
import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.Injectable;
import org.firstinspires.ftc.teamcode.Helper.Intake.BeakState;
import org.firstinspires.ftc.teamcode.Helper.ReactiveState.Reactive;
import org.firstinspires.ftc.teamcode.Helper.ReactiveState.ReactiveState;
import org.firstinspires.ftc.teamcode.Helper.ReactiveState.StateChange;
import org.firstinspires.ftc.teamcode.Helper.StaticActions;

@Config
public class BeakAction extends Injectable {
    public static class Params {
        public double armDrivePos = 0.2;
        public double armPickStartPos = 0.27;
        public double armBucketDropPos = 0.315;
        public double armPickReachPos = 0.39;
        public double armDumpPos = 0.2;

        public double elbowDrivePos = 0.668;
        public double elbowPickStartPos = 0.56;
        public double elbowBucketDropPos = 0.69;
        public double elbowPickReachPos = 0.585;
        public double elbowDumpPos = 0.64;

        public double beakOpenGatherPos = 0.4;
        public double beakOpenDropPos = 0.5;
        public double beakClosedPos = 0.75;
        public double beakSuplexOpenDelay = 1000;
    }

    public static Params PARAMS = new Params();

    public double tlmArmPosition = -1;
    public double tlmElbowPosition = -1;
    public double tlmBeakPosition = -1;


    @Inject("hdwMap")
    public HardwareMap hardwareMap;

    private Beak beak;
    private Arm arm;
    private Elbow elbow;

    private void MoveArm(double position) {
        this.arm.moveArm(position);
    }

    private void MoveElbow(double position) {
        this.elbow.moveElbow(position);
    }

    private void MoveBeak(double position) {
        this.beak.moveBeak(position);
    }

    public BeakAction() {
        super();
        Reactive.init(this);

        this.beak = new Beak(this);
        this.arm = new Arm(this);
        this.elbow = new Elbow(this);
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
        if (tlmBeakPosition != PARAMS.beakOpenGatherPos)
            MoveBeak(PARAMS.beakOpenGatherPos);
    }

    public void CloseBeak() {
        MoveBeak(PARAMS.beakClosedPos);
    }

    public void OpenBeak() {
        if (tlmElbowPosition == PARAMS.elbowBucketDropPos)
            MoveBeak(PARAMS.beakOpenDropPos);
        else
            MoveBeak(PARAMS.beakOpenGatherPos);
    }

    public void SuplexSample() {
        if (tlmBeakPosition != PARAMS.beakClosedPos)  {
            MoveBeak(PARAMS.beakClosedPos);
            SystemClock.sleep(100);
        }
        MoveArm(PARAMS.armBucketDropPos);
        MoveElbow(PARAMS.elbowBucketDropPos);
//        DeferredActions.CreateDeferredAction(1000, DeferredActions.DeferredActionType.BEAK_OPEN);
    }

    public Action autElBucket(){
        return packet ->{
        MoveElbow(PARAMS.elbowPickStartPos);
        return false;
        };

    }
    public Action autonAct(){
        return packet ->{
            StaticActions staticActions = StaticActions.getInstance();
            staticActions.getViperAction().TEST_activate_bucket();
            MoveElbow(PARAMS.elbowPickReachPos);
            MoveArm(PARAMS.armPickReachPos);
            MoveBeak(PARAMS.beakClosedPos);
            MoveElbow(PARAMS.elbowBucketDropPos);
            MoveArm(PARAMS.armBucketDropPos);
            MoveBeak(PARAMS.beakOpenDropPos);

          return false;
        };


    }

//    public void SuplexSample() {
//        if (tlmBeakPosition != PARAMS.beakClosedPos)  {
//            MoveBeak(PARAMS.beakClosedPos);
//            SystemClock.sleep(100);
//        }
//        MoveArm(PARAMS.armBucketDropPos);
//        MoveElbow(PARAMS.elbowBucketDropPos);
//        DeferredActions.CreateDeferredAction( (long) PARAMS.beakSuplexOpenDelay, DeferredActions.DeferredActionType.BEAK_OPEN);
//    }
}
