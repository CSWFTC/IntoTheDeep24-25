package org.firstinspires.ftc.teamcode.Helper.Beak;
import androidx.annotation.NonNull;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class newBeak {

    public static class Params {


    }

    public static newBeak.Params PARAMS = new newBeak.Params();
    public static double targetSliderPosition = -1;
    private final Servo viper;

    public newBeak(@NonNull HardwareMap hardwareMap) {
        //   super();
       viper = hardwareMap.servo.get("viperServo");
       viper.setDirection(Servo.Direction.FORWARD);

    }

    //the viper slide
    public void MoveSlider(){
       // viper.setPosition();
    }
    //the servo for first hand
    //the servo for second hand


}
