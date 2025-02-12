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
                LEDservo.setPosition(0.279);
                break;
            case "Blue":
                LEDservo.setPosition(0.666);
                break;
            case "White":
            default:
                LEDservo.setPosition(1.0);
                break;
            case "Yellow" :
                LEDservo.setPosition(0.388);
                break;
            case "Green" :
                LEDservo.setPosition(0.500);
                break;
            case "Orange" :
                LEDservo.setPosition(0.333);
                break;
            case "Violet" :
                LEDservo.setPosition(0.722);
                break;




        }
    }
}


