package org.firstinspires.ftc.teamcode.Helper.Beak;
import androidx.annotation.NonNull;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class newBeak {

    public static class Params {

        /*
        Open 0.155
        Closed 0.475
         */
        public double versionNumber = 0.1;

        //slider
        public double sliderMaxPos = 0.46;
        public double sliderMinPos = 0.1;

        //beak
        public double beakOpenPos = 0.155;
        public double beakClosePos = 0.475;
        public int times = 0;


    }

    public static newBeak.Params PARAMS = new newBeak.Params();
    public static double targetSliderPosition = -1;
    public static double targetBeakPosition = -1;
    private final Servo viper;
    private final Servo beak;

    public newBeak(@NonNull HardwareMap hardwareMap) {
       viper = hardwareMap.servo.get("viperServo");
       viper.setDirection(Servo.Direction.FORWARD);

       beak = hardwareMap.servo.get("viperbeakServo");
       beak.setDirection(Servo.Direction.FORWARD);

    }

    //the viper slide
    public void MoveSlider(double newPos){
        viper.setPosition(newPos);
        targetSliderPosition = newPos;
    }

    public void JoystickMoveSlide(float position){
        double sliderPos = Range.clip((targetSliderPosition + (position * 0.004)), PARAMS.sliderMinPos, PARAMS.sliderMaxPos);
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
    //the servo for second hand



}
