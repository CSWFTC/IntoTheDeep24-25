package org.firstinspires.ftc.teamcode.Helper;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class LEDColorHelper {

    public enum LEDColor {
        RED(0.279),
        BLUE(0.666),
        YELLOW(0.388),
        GREEN(0.500),
        ORANGE(0.333),
        VIOLET(0.722),
        AZURE(0.555),
        WHITE(1.0);

        private final double position;

        LEDColor(double pos) {
            position = pos;
        }

        public double getPosition() {
            return position;
        }
    }

    private Servo LEDservo;

    public LEDColorHelper(HardwareMap hardwareMap) {
        LEDservo = hardwareMap.get(Servo.class, "LEDServo");
    }

    public void setLEDColor(LEDColor color) {
        LEDservo.setPosition(color.getPosition());
    }
}



