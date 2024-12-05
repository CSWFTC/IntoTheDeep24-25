package org.firstinspires.ftc.teamcode.Helper.Beak;

import com.qualcomm.robotcore.hardware.Servo;

public class Beak {
    private final Servo beak;

    private BeakAction beakAction;

    public Beak(BeakAction beakAction) {
        this.beakAction = beakAction;
        beak = beakAction.hardwareMap.servo.get("beakServo");
        beak.setDirection(Servo.Direction.FORWARD);
    }

    public void moveBeak(double position) {
        beak.setPosition(position);
        this.beakAction.tlmBeakPosition = position;
    }
}