package org.firstinspires.ftc.teamcode.Helper.Beak;

import android.os.SystemClock;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Helper.DeferredActions;


@Disabled
public class BeakAction {
    public static class Params {
        // Drive Position - Protect Arm
        public double armDrivePos = 0.21;
        public double elbowDrivePos = 0.66;

        // Sample Pickup Start - Clear Submersible Bar
        public double armPickStartPos = 0.27;
        public double elbowPickStartPos = 0.56;

        // Pickup Reach Position - Minimum Reach
        public double armPickReachPos = 0.36;
        public double elbowPickReachPos = 0.56;

        // Pickup Reach Position - Middle Reach
        public double armPickReachMiddlePos = 0.40;
        public double elbowPickReachMiddlePos = 0.585;

        // Suplex Sample into Bucket
        public double armBucketDropPos = 0.25;
        public double elbowBucketDropPos = 0.69;

        // Move Arm Clear of Bucket - So Bucket Can Dump
        public double armDumpPos = 0.2;
        public double elbowDumpPos = 0.64;

        // Beak Positions
        public double beakOpenGatherPos = 0.4;
        public double beakOpenDropPos = 0.5;
        public double beakClosedPos = 0.75;
        public double beakSuplexOpenDelay = 600;
        public double beakSuplexDriveDelay = 850;

        //new values
        public double armPickupMinPos = 0.36;
        public double armPickupMaxPos = 0.505;
    }

    public static Params PARAMS = new Params();

    public static double targetArmPosition = -1;
    public static double targetElbowPosition = -1;
    public static double targetBeakPosition = -1;


    private final Servo beak;
    private final Servo arm;
    private final Servo elbow;

    public BeakAction(@NonNull HardwareMap hardwareMap) {
     //   super();
        beak = hardwareMap.servo.get("beakServo");
        beak.setDirection(Servo.Direction.FORWARD);

        arm = hardwareMap.servo.get("viperServo");
        arm.setDirection(Servo.Direction.FORWARD);

        elbow = hardwareMap.servo.get("elbowServo");
        elbow.setDirection(Servo.Direction.FORWARD);
    }

    public void MoveArm(double position) {
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
        MoveBeak(PARAMS.beakOpenGatherPos);
        MoveElbow(PARAMS.elbowPickStartPos);
        MoveArm(PARAMS.armPickStartPos);
    }

    public void PrepForBucketDump() {
        if (targetElbowPosition != PARAMS.elbowDumpPos)
            MoveElbow(PARAMS.elbowDumpPos);
        if (targetArmPosition != PARAMS.armDumpPos)
            MoveArm(PARAMS.armDumpPos);
    }

    public void PickupReach() {
        MoveElbow(PARAMS.elbowPickReachPos);
        MoveArm(PARAMS.armPickReachPos);
        if (targetBeakPosition != PARAMS.beakOpenGatherPos)
            MoveBeak(PARAMS.beakOpenGatherPos);
    }

    public void PickupReachMiddle() {
        MoveBeak(PARAMS.beakOpenGatherPos);
        MoveArm(PARAMS.armPickReachMiddlePos);
        MoveElbow(PARAMS.elbowPickReachMiddlePos);
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

    public void ToggleBeak() {
        if (targetBeakPosition == PARAMS.beakClosedPos) {
            OpenBeak();
        } else {
            CloseBeak();
        }
    }

    public void SuplexSample() {
        if (targetBeakPosition != PARAMS.beakClosedPos)  {
            MoveBeak(PARAMS.beakClosedPos);
            DeferredActions.CreateDeferredAction(100, DeferredActions.DeferredActionType.SUPLEX_BEAK);
        } else {
            MoveArm(PARAMS.armBucketDropPos);
            MoveElbow(PARAMS.elbowBucketDropPos);
            DeferredActions.CreateDeferredAction( (long) PARAMS.beakSuplexOpenDelay, DeferredActions.DeferredActionType.BEAK_OPEN);
            DeferredActions.CreateDeferredAction( (long) PARAMS.beakSuplexDriveDelay, DeferredActions.DeferredActionType.BEAK_DRIVE_SAFE);
        }
    }

    public double conversion (double input){
        double elbPos = 0.774589 * Math.pow(input, 0.311196 );
        return elbPos;
    }

    public void pickUpJoystick(float power){
        boolean rightPosition = ((targetArmPosition >= PARAMS.armPickupMinPos) && (targetArmPosition <= PARAMS.armPickupMaxPos));

        if (rightPosition) {
            double armPos = Range.clip((targetArmPosition + (power * 0.004)), PARAMS.armPickupMinPos, PARAMS.armPickupMaxPos);
            double elbPos = conversion(armPos);
            MoveArm(armPos);
            MoveElbow(elbPos);
        }
    }

    public void SuplexSampleAuton(){
        MoveArm(PARAMS.armBucketDropPos);
        MoveElbow(PARAMS.elbowBucketDropPos);
        OpenBeak();
    }

   public void changingArmUp(){
        double currentArm = arm.getPosition();

        if(currentArm <= 0.505){
            double setArm = currentArm + 0.005;
            MoveArm(setArm);
        }
   }

   public void changingArmDown(){
       double currentArm = arm.getPosition();
       if(currentArm >= 0.36){
           double setArm = currentArm - 0.005;
           MoveArm(setArm);
       }

   }

   public void autonReach(){
        beakStart();
        MoveArm(0.40);
        MoveElbow(0.585);
   }

   public void autonClose(){
        MoveBeak(0.75);
   }

    public Action PickUpReachAuton() {
        return packet ->{
            beakStart();
            SystemClock.sleep(2000);
            autonReach();
            SystemClock.sleep(2000);
            return false;
        };
    }

    public Action SuplexAuton(){
        return packet ->{
           //    autonClose();
            SuplexSampleAuton();
            SystemClock.sleep(2000);
            PrepForPickup();
          return false;
        };
    }

    public Action autonCloseBeak(){
        return packet ->{
            autonClose();
          return false;
        };

    }

    public Action moveArmAuton(){
        return packet -> {
          MoveArm(0.44);
          return false;
        };

    }



    public void beakStart(){
        MoveBeak(0.40);
    }




}
