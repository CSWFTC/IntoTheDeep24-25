package org.firstinspires.ftc.teamcode.Helper.Beak;
import android.graphics.DashPathEffect;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SleepAction;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helper.DeferredActions;

public class newBeak {

    public static class Params {
        //slider
        public double sliderMaxPos = 0.445;
        public double sliderMinPos = 0.09;
       // public double sliderDropPos = 0.2675;

        //beak
        public double beakOpenPos = 0.40;
        public double beakClosePos = 0.65;
        public double beakSuplexDelay = 600;
        public double beakClosedDelay = 50;

        //elbow
        public double elbowPickPos = 0.43;
        public double elbowSuplexPos = 0.52;

        public double elbowStartPos = 0.49;


    }

    public static newBeak.Params PARAMS = new newBeak.Params();
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

    public void JoystickMoveSlide(float position) {
        double sliderPos = Range.clip((targetSliderPosition + (position * 0.008)), PARAMS.sliderMinPos, PARAMS.sliderMaxPos);
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
    }

    public Action autonReachSamp() {
        return packet -> {
            openBeak();
            PickUpElbow();

            return false;
        };
    }


    public void SuplexSample() {
        if (targetBeakPosition != PARAMS.beakClosePos)  {
            closedBeak();
            DeferredActions.CreateDeferredAction((long) PARAMS.beakClosedDelay, DeferredActions.DeferredActionType.SUPLEX_BEAK);
        } else {
            suplexElbPos();
            MoveSlider(PARAMS.sliderMinPos);
            DeferredActions.CreateDeferredAction( (long) PARAMS.beakSuplexDelay, DeferredActions.DeferredActionType.BEAK_OPEN);
            DeferredActions.CreateDeferredAction(100, DeferredActions.DeferredActionType.BEAK_DRIVE_SAFE);

        }
    }
    public Action autonSuplexSam() {
        return packet -> {
            closedBeak();
            suplexElbPos();
            return false;
        };
    }

    public void autonStartPos(){
            suplexElbPos();
            closedBeak();
            MoveSlider(PARAMS.sliderMinPos);
    }

}
