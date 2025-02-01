package org.firstinspires.ftc.teamcode.Helper.Beak;
import androidx.annotation.NonNull;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class newBeak {

    public static class Params {
        public double versionNumber = 0.1;
        public double sliderMaxPos = 0.46;
        public double sliderMinPos = 0.1;

    }

    public static newBeak.Params PARAMS = new newBeak.Params();
    public static double targetSliderPosition = -1;
    private final Servo viper;

    public newBeak(@NonNull HardwareMap hardwareMap) {
       viper = hardwareMap.servo.get("viperServo");
       viper.setDirection(Servo.Direction.FORWARD);

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

    //the servo for first hand
    //the servo for second hand


}
