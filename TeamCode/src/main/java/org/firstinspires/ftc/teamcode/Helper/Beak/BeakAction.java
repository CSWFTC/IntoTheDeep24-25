package org.firstinspires.ftc.teamcode.Helper.Beak;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Helper.DeferredActions;
import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.Inject;
import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.Injectable;


@Config
public class BeakAction extends Injectable {
    public static class Params {
        // Drive Position - Protect Arm
        public double armDrivePos = 0.2;
        public double elbowDrivePos = 0.668;

        // Sample Pickup Start - Clear Submersible Bar
        public double armPickStartPos = 0.27;
        public double elbowPickStartPos = 0.56;

        // Pickup Reach Position - Minimum Reach
        public double armPickReachPos = 0.36;
        public double elbowPickReachPos = 0.56;

        // Suplex Sample into Bucket
        public double armBucketDropPos = 0.25;
        public double elbowBucketDropPos = 0.69;

        // Move Arm Clear of Bucket - So Bucket Can Dump
        public double armDumpPos = 0.2;
        public double elbowDumpPos = 0.64;

        // Beak Positions
        public double beakOpenGatherPos = 0.4;
        public double beakOpenDropPos = 0.45;
        public double beakClosedPos = 0.75;
        public double beakSuplexOpenDelay = 600;

        //new values
        //start sample pickup in submerisible
        public double armPickupPrepPos = 0.27;
        public double armPickupMinPos = 0.36;
        public double elbowPickupMinPos = 0.56;
        public double armPickupStartDelay = 750;

        //quarter point of reach
        public double armpickup1Qtr = 0.395;
        public double eblowpickup1Qtr = 0.569;

        // Midpoint of Reach for Sample
        public double armPickupMiddlePos = 0.395;
        public double elbowPickupMiddlePos = 0.585;

        // Three Quarter Point of Reach
        public double armPickup3QtrPos = 0.423;
        public double elbowPickup3QtrPos = 0.598;

        // Maximum Reach for Sample
        public double armPickupMaxPos = 0.505;
        public double elbowPickupMaxPos = 0.625;
        public double armPickupMaxDelay = 250;

        //speed
       // public double reachSpeed = 0.
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
        if (targetBeakPosition != PARAMS.beakClosedPos)  {
            MoveBeak(PARAMS.beakClosedPos);
            DeferredActions.CreateDeferredAction(100, DeferredActions.DeferredActionType.SUPLEX_BEAK);
        } else {
            MoveArm(PARAMS.armBucketDropPos);
            MoveElbow(PARAMS.elbowBucketDropPos);
            DeferredActions.CreateDeferredAction( (long) PARAMS.beakSuplexOpenDelay, DeferredActions.DeferredActionType.BEAK_OPEN);
        }
    }

    public double conversion (double input){
        double armAngle = 0.491924 * Math.pow(input, -4.42152 );
        double elbAngle = (90-armAngle);

        double elbPos = 0;
        if(elbAngle <= 50) {
            elbPos = 0.42194 * Math.pow(elbAngle, 0.0769263);
        }
        else if (elbAngle > 50 ) {
            elbPos = 0.4194 * Math.pow(elbAngle, 0.089263);
        }
        return elbPos;
    }

    public void pickUpJoystick(float power){
        boolean rightPosition = ((targetArmPosition >= PARAMS.armPickupMinPos) && (targetArmPosition <= PARAMS.armPickupMaxPos));

        if(rightPosition) {
            double armPos = Range.clip((targetArmPosition + (power * 0.003)), PARAMS.armPickupMinPos, PARAMS.armPickupMaxPos);
            double elbPos = conversion(armPos);
            MoveArm(armPos);
            MoveElbow(elbPos);
        }
        //0.0025
    }

}
