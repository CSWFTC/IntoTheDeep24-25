package org.firstinspires.ftc.teamcode.Helper;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class LEDhelper {
    private Servo servo;
    private double position;

    public LEDhelper(HardwareMap hardwareMap, String servoName, double startPos) {
        servo = hardwareMap.get(Servo.class, servoName);
        position = startPos;
        servo.setPosition(position);
    }

    public void setPosition(double newPosition) {
        position = newPosition;
        servo.setPosition(position);
    }

    public double getPosition() {
        return position;
    }
}
