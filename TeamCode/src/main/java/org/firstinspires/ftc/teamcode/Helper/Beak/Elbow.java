package org.firstinspires.ftc.teamcode.Helper.Beak;

import com.qualcomm.robotcore.hardware.Servo;

public class Elbow {
    private final Servo elbow;

    private BeakAction beakAction;

    public Elbow(BeakAction beakAction) {
        this.beakAction = beakAction;
        elbow = beakAction.hardwareMap.servo.get("elbowServo");
        elbow .setDirection(Servo.Direction.FORWARD);
    }

    public void moveElbow(double position) {
        elbow.setPosition(position);
        this.beakAction.tlmElbowPosition = position;
    }
}