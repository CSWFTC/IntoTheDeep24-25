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
        public double sliderMaxPos = 0.445;
        public double sliderMinPos = 0.09;

        //beak
        public double beakOpenPos = 0.41;
        public double beakClosePos = 0.255;
        public double beakSuplexDelay = 920;
        public double beakClosedDelay = 50;

        //elbow
        public double elbowPickPos = 0.37;
        public double elbowSuplexPos = 0.45;
        public double elbowStartPos = 0.40;
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
        double sliderPos = Range.clip((targetSliderPosition + (position * 0.009)), PARAMS.sliderMinPos, PARAMS.sliderMaxPos);
        MoveSlider(sliderPos);
    }

    //the servo for beak
    public void closedBeak() {
       MoveBeak(PARAMS.beakClosePos);
    }

    public void openBeak() {
        MoveBeak(PARAMS.beakOpenPos);
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
    }

    public void suplexElbPos() {
        MoveElbow(PARAMS.elbowSuplexPos);
    }

    public void ElbStart(){
        MoveElbow(PARAMS.elbowStartPos);
        MoveBeak(PARAMS.beakOpenPos);
    }

    public void SuplexSample() {
        if (targetBeakPosition != PARAMS.beakClosePos)  {
            closedBeak();
            DeferredActions.CreateDeferredAction((long) PARAMS.beakClosedDelay, DeferredActions.DeferredActionType.SUPLEX_BEAK);
        } else {
            MoveSlider(PARAMS.sliderMinPos);
            suplexElbPos();
            DeferredActions.CreateDeferredAction( (long) PARAMS.beakSuplexDelay, DeferredActions.DeferredActionType.BEAK_OPEN);
            DeferredActions.CreateDeferredAction((long) PARAMS.beakSuplexDelay + (long) PARAMS.beakClosedDelay, DeferredActions.DeferredActionType.BEAK_DRIVE_SAFE);
        }
    }
    public void autonStartPos(){
        MoveElbow(PARAMS.elbowStartPos);
        openBeak();
        MoveSlider(PARAMS.sliderMinPos);
    }

    public Action autonBeak(){
        return packet -> {
            openBeak();
          return false;
        };

    }


    public Action autonReachSamp() {
        return packet -> {
            PickUpElbow();
            SystemClock.sleep(100);

            closedBeak();
            SystemClock.sleep(PARAMS.beakClosedDelay);
            suplexElbPos();
            SystemClock.sleep(PARAMS.beakSuplexDelay);
            openBeak();
            SystemClock.sleep(PARAMS.beakSuplexDelay + PARAMS.beakClosedDelay);
            ElbStart();
            return false;
        };
    }



}
