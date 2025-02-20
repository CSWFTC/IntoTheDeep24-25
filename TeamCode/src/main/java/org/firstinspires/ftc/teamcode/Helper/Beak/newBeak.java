package org.firstinspires.ftc.teamcode.Helper.Beak;

import android.os.SystemClock;

import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.Helper.DeferredActions;


@Config
public class newBeak {

    public static class Params {
        //slider
        public double sliderMaxPos = 0.440;
        public double sliderMinPos = 0.045;
        public double sliderPosChange = 0.03;


        //hover height is wrong
        //slider needs to be faster
        //fix suplex sequence
        //beak
        public double beakOpenDropPos = 0.62; //for suplex
        public double beakOpenPickupPos = 0.63; //for pick up
        public double beakWideOpen = 0.66; // for wider opening
        public double beakClosePos = 0.47; // closed

        public double beakClosedDelay = 50;
        public double beakPickUpDelay = 200;

        //elbow
        public double elbowPickPos = 0.475;     // Pickup Off Mat
        public double elbowReachPos = 0.49;    // Grabber Extended Drive
        public double elbowSuplexBucketPos = 0.56;    // Suplex in Bucket
        public double elbowStartPos = 0.541;    // Drive Position
        public double elbowSlideDumpPos = 0.575;
        public double elbowClimbInit = 0.540;
        public double elbowClimbSafePos = 0.575;
        public double elbowSuplexSlidePos = 0.55;

        //delays
        public double suplexOpenBeakDelay = 500;
        public double suplexOpenSecondBeakDelay = 550;

        public double suplexMoveToDrivePositionDelay = 1000;
        public double pickupBeakOpenDelay = 100;   //ms Until Open Beak Fully When At Top

    }

    public static Params PARAMS = new Params();

    public static double targetSliderPosition = -1;
    public static double targetBeakPosition = -1;
    public static double targetElbowPosition = -1;
    private final Servo viper;
    private final Servo beak;
    private final Servo elbow;
    public newBeak(@NonNull HardwareMap hardwareMap) {
        viper = hardwareMap.servo.get("viperServo");
        viper.setDirection(Servo.Direction.FORWARD);

        beak = hardwareMap.servo.get("beakServo");
        beak.setDirection(Servo.Direction.FORWARD);

        elbow = hardwareMap.servo.get("elbowServo");
        elbow.setDirection(Servo.Direction.FORWARD);


    }

    //the viper slide
    public void MoveSlider(double newPos) {
        viper.setPosition(newPos);
        targetSliderPosition = newPos;
    }

    public void MoveElbow(double newPos){
        elbow.setPosition(newPos);
        targetElbowPosition = newPos;
    }

    public void MoveBeak(double newPos){
        beak.setPosition(newPos);
        targetBeakPosition = newPos;
    }

    public void IncreaseElbow(){
        targetElbowPosition += 0.01;
        MoveElbow(targetElbowPosition);
    }

    public void  DecreaseElbow(){
        targetElbowPosition -= 0.01;
        MoveElbow(targetElbowPosition);
    }

    public void JoystickMoveSlide(float position) {
        double sliderPos = Range.clip((targetSliderPosition + (position * PARAMS.sliderPosChange)), PARAMS.sliderMinPos, PARAMS.sliderMaxPos);
        MoveSlider(sliderPos);
    }

    //the servo for beak
    public void closedBeak() {
       MoveBeak(PARAMS.beakClosePos);
    }

    public void openBeak() {
        if (targetElbowPosition < PARAMS.elbowStartPos)
            MoveBeak(PARAMS.beakOpenPickupPos);
        else
            MoveBeak(PARAMS.beakOpenDropPos);
    }

    public void openWideBeak(){
        MoveBeak(PARAMS.beakWideOpen);
    }

    public void ToggleBeak() {
        if (targetBeakPosition == PARAMS.beakClosePos) {
            openBeak();
        } else {
            closedBeak();
        }
    }

    //the servo for elbow
    public void PickUpElbow() {
        MoveElbow(PARAMS.elbowPickPos);
        DeferredActions.CreateDeferredAction((long)PARAMS.beakPickUpDelay, DeferredActions.DeferredActionType.BEAK_OPEN_WIDER);
    }

    public void suplexElbPos() {
        MoveElbow(PARAMS.elbowSuplexBucketPos);
    }
    public void elbowSuplexSlidePos () {MoveElbow(PARAMS.elbowSuplexBucketPos);}
//button back
    public void ElbStart(){
        MoveElbow(PARAMS.elbowStartPos);
        openBeak();
    }

    private void handleSuplexBeakOpen(double curpos) {
        if (curpos >= 0.2425) {
            DeferredActions.CreateDeferredAction((long) PARAMS.suplexOpenBeakDelay, DeferredActions.DeferredActionType.BEAK_OPEN);
        } else {
            DeferredActions.CreateDeferredAction((long) PARAMS.suplexOpenSecondBeakDelay, DeferredActions.DeferredActionType.BEAK_OPEN);
        }
    }

    private void handleSuplexActions(double curpos) {
        MoveSlider(PARAMS.sliderMinPos);
        elbowSuplexSlidePos();
        handleSuplexBeakOpen(curpos);
        DeferredActions.CreateDeferredAction((long) PARAMS.suplexMoveToDrivePositionDelay, DeferredActions.DeferredActionType.BEAK_DRIVE_SAFE);
    }

    public void SuplexSample() {
        if (targetBeakPosition != PARAMS.beakClosePos) {
            closedBeak();
            DeferredActions.CreateDeferredAction((long) PARAMS.beakClosedDelay, DeferredActions.DeferredActionType.SUPLEX_BEAK);
        } else {
            double curpos = viper.getPosition();
            handleSuplexActions(curpos);
        }
    }

    public void SuplexSlideDumpSample() {
        if (targetBeakPosition != PARAMS.beakClosePos) {
            closedBeak();
            DeferredActions.CreateDeferredAction((long) PARAMS.beakClosedDelay, DeferredActions.DeferredActionType.SUPLEX_SLIDE);
        } else {
            double curpos = viper.getPosition();
            handleSuplexActions(curpos);
        }
    }


    public void sampleReachElbowPos() {
        if (targetElbowPosition > PARAMS.elbowStartPos)
            openBeak();
        else
            DeferredActions.CreateDeferredAction( (long) PARAMS.pickupBeakOpenDelay, DeferredActions.DeferredActionType.BEAK_OPEN);
        MoveElbow(PARAMS.elbowReachPos);
        openBeak();
    }

    public void toggleElbowSuplex() {
        if (targetElbowPosition >= PARAMS.elbowStartPos)
            sampleReachElbowPos();
        else
            SuplexSample();
    }

    public void ClimbInitialize() {
        closedBeak();
        MoveElbow(PARAMS.elbowClimbInit);
    }

    public void ClimbPostitions(){
        closedBeak();
        MoveSlider(PARAMS.sliderMinPos);
        MoveElbow(PARAMS.elbowClimbSafePos);
    }

    public void autonStartPos(){
        MoveSlider(PARAMS.sliderMinPos);
        MoveElbow(PARAMS.elbowStartPos);
        openBeak();
    }


    public Action autonReachSamp() {
        return packet -> {
            openBeak();
            PickUpElbow();
            SystemClock.sleep(1000);

            closedBeak();
            SystemClock.sleep((long) PARAMS.beakClosedDelay);
            return false;
        };

    }

    public Action autonSuplex(){
        return packet -> {
            closedBeak();
            suplexElbPos();
            SystemClock.sleep((long)PARAMS.suplexOpenBeakDelay);
            openBeak();
            SystemClock.sleep((long) PARAMS.suplexOpenBeakDelay + (long) PARAMS.beakClosedDelay);
            ElbStart();
          return false;
        };

    }

    public Action autonReachOB(){
        return packet -> {
            openBeak();
            PickUpElbow();
            SystemClock.sleep(600);

            closedBeak();
            SystemClock.sleep((long) PARAMS.beakClosedDelay);
            return false;
        };
    }

    public Action dropToHuman (){
        return packet -> {
            MoveElbow(PARAMS.elbowSlideDumpPos);
            SystemClock.sleep(800);
            openBeak();
            SystemClock.sleep(500);
            ElbStart();
            return false;
        };
    }

    public Action grabAndDrop(){
        return packet -> {
            MoveSlider(PARAMS.sliderMaxPos);
            SystemClock.sleep(1000);
            PickUpElbow();
            SystemClock.sleep(600);
            closedBeak();
            SystemClock.sleep(500);
            SuplexSample();
            SystemClock.sleep(1000);
            return false;
        };
    }




}
