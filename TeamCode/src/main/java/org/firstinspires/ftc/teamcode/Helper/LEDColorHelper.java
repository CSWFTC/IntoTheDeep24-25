package org.firstinspires.ftc.teamcode.Helper;
import com.qualcomm.robotcore.hardware.Servo;



public class LEDColorHelper {

    private Servo LEDservo;


    public LEDColorHelper() {
        LEDservo = LEDservo;

    }


    public void setLEDColor(String color) {
        switch (color) {
            case "Red":
                LEDservo.setPosition(0.279); // Red
                break;
            case "Blue":
                LEDservo.setPosition(0.666); //  Blue
                break;
            case "White":
            default:
                LEDservo.setPosition(1.0); // White
                break;


        }
    }
}


