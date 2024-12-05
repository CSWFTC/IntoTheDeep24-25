package org.firstinspires.ftc.teamcode.Helper.Beak;

import com.qualcomm.robotcore.hardware.Servo;

public class Arm {
    private final Servo arm;

    private BeakAction beakAction;

    public Arm(BeakAction beakAction) {
        this.beakAction = beakAction;
        arm= beakAction.hardwareMap.servo.get("armServo");
        arm.setDirection(Servo.Direction.FORWARD);
    }

    public void moveArm(double position) {
        arm.setPosition(position);
        this.beakAction.tlmArmPosition = position;
    }
}
