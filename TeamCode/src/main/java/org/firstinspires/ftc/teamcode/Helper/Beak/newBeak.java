package org.firstinspires.ftc.teamcode.Helper.Beak;
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
        public double sliderMaxPos = 0.405;
        public double sliderMinPos = 0.01;

        //beak
        public double beakOpenPos = 0.155;
        public double beakClosePos = 0.475;

        //elbow
        public double elbowMaxPos = 0.59;
        public double elbowMinPos = 0.485;
        public double elbowSuplexPos = 0.57;
        public int times = 0;


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
    public void MoveSlider(double newPos){
        viper.setPosition(newPos);
        targetSliderPosition = newPos;
    }

    public void JoystickMoveSlide(float position){
        double sliderPos = Range.clip((targetSliderPosition + (position * 0.005)), PARAMS.sliderMinPos, PARAMS.sliderMaxPos);
        MoveSlider(sliderPos);
    }

    //the servo for beak
    public void closedBeak(){
        beak.setPosition(PARAMS.beakClosePos);
    }
    public void openBeak(){
        beak.setPosition(PARAMS.beakOpenPos);
    }

    public void ToggleBeak(){
        PARAMS.times++;
        if(PARAMS.times %2 == 0){
            closedBeak();
        }
        else{
           openBeak();
        }
    }
    //the servo for elbow
    public void MinElbow(){
        elbow.setPosition(PARAMS.elbowMinPos);
    }

    public void startElbPos(){
        elbow.setPosition(PARAMS.elbowMaxPos);

    }

    public void suplexElbPos(){
        elbow.setPosition(PARAMS.elbowSuplexPos);
    }

    public Action autonReachSamp(){
        return packet ->{
            MinElbow();
            return false;
        };

        }

    public Action waitLong (){
        return packet ->{

         return false;
        };
    }
}
