package org.firstinspires.ftc.teamcode.Helper;
import com.qualcomm.robotcore.hardware.Servo;



public class LEDColorHelper {

    private Servo LEDservo;


    public LEDColorHelper(Servo LEDservo) {
        LEDservo = LEDservo;

    }


    private void setLEDColor(String color) {
        switch (color) {
            case "Red":
                LEDservo.setPosition(0.279); // Set LED to Red
                break;
            case "Blue":
                LEDservo.setPosition(0.666); // Set LED to Blue
                break;
            case "White":
            default:
                LEDservo.setPosition(1.0); // Set LED to White
                break;
            case "Yellow" :
                 LEDservo.setPosition(0.388);

        }
    }
}


