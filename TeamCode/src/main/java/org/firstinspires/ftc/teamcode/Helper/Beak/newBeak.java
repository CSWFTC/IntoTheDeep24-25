package org.firstinspires.ftc.teamcode.Helper.Beak;

import android.os.SystemClock;

import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.Helper.DeferredActions;
import org.firstinspires.ftc.teamcode.Helper.ViperSlide.BucketAction;


@Config
public class newBeak {

    public static class Params {
        //slider
        public double sliderMaxPos = 0.445;
        public double sliderMinPos = 0.09;
        public double sliderPosChange = 0.03;

        //beak
        public double beakOpenDropPos = 0.39; //for suplex
        public double beakOpenPickupPos = 0.42; //for pick up
        public double beakWideOpen = 0.45; // for wider opening
        public double beakClosePos = 0.255; // closed
        public double beakSuplexDelay = 1000;
        public double beakClosedDelay = 50;
        public double beakPickUpDelay = 200;

        //elbow
        public double elbowPickPos = 0.37;     // Pickup Off Mat
        public double elbowReachPos = 0.39;     // Grabber Extended Drive
        public double elbowSuplexPos = 0.465;  // Suplex in Bucket
        public double elbowStartPos = 0.443;    // Drive Position
        public double elbowClimbPos = 0.5;
        public double elbowSuplexSafeDelay = 1200;
        public double elbowPickupOpenDelay = 200;   //ms Until Open Beak Fully When At Top

        //delays

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
        MoveBeak(PARAMS.beakWideOpen);
        DeferredActions.CreateDeferredAction((long)PARAMS.beakPickUpDelay, DeferredActions.DeferredActionType.BEAK_OPEN_WIDER);
    }

    public void suplexElbPos() {
        MoveElbow(PARAMS.elbowSuplexPos);
    }

    public void ElbStart(){
        MoveElbow(PARAMS.elbowStartPos);
        openBeak();
    }

    public void SuplexSample() {
        if (targetBeakPosition != PARAMS.beakClosePos)  {
            closedBeak();
            DeferredActions.CreateDeferredAction((long) PARAMS.beakClosedDelay, DeferredActions.DeferredActionType.SUPLEX_BEAK);
        } else {
            MoveSlider(PARAMS.sliderMinPos);
            suplexElbPos();
            DeferredActions.CreateDeferredAction( (long) PARAMS.beakSuplexDelay, DeferredActions.DeferredActionType.BEAK_OPEN);
            DeferredActions.CreateDeferredAction((long) PARAMS.elbowSuplexSafeDelay, DeferredActions.DeferredActionType.BEAK_DRIVE_SAFE);
        }
    }

    public void sampleReachElbowPos() {
        if (targetElbowPosition < PARAMS.elbowStartPos)
            openBeak();
        else
            DeferredActions.CreateDeferredAction( (long) PARAMS.beakSuplexDelay, DeferredActions.DeferredActionType.BEAK_OPEN);
        MoveElbow(PARAMS.elbowReachPos);
        openBeak();
    }

    public void toggleElbowSuplex() {
        if (targetElbowPosition <= PARAMS.elbowStartPos)
            sampleReachElbowPos();
        else
            SuplexSample();
    }

    public void climbPostitions(){
        MoveSlider(PARAMS.sliderMinPos);
        MoveElbow(PARAMS.elbowClimbPos);
    }
    public void autonStartPos(){
        MoveSlider(PARAMS.sliderMinPos);
        MoveElbow(PARAMS.elbowStartPos);
        openBeak();
    }

    public Action autonBeak(){
        return packet -> {
            openBeak();
          return false;
        };
    }

    public Action autonReachSamp() {
        return packet -> {
            openBeak();
            PickUpElbow();
            SystemClock.sleep(100);

            closedBeak();
            SystemClock.sleep((long) PARAMS.beakClosedDelay);
            suplexElbPos();
            SystemClock.sleep((long)PARAMS.beakSuplexDelay);
            openBeak();
            SystemClock.sleep((long) PARAMS.beakSuplexDelay + (long) PARAMS.beakClosedDelay);
            ElbStart();
            return false;
        };
    }



}
