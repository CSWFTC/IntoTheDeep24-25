package org.firstinspires.ftc.teamcode.Helper;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ClawHelper {
    public static class Params {
        public double gripOpenPos = 0.505;
        public double gripClosedPos = 0.046;
    }

    public static Params PARAMS = new Params();
    public double tlmGripPosition = -1;
    private final Servo clawServo;

    public ClawHelper(@NonNull HardwareMap hdwMap) {
        clawServo = hdwMap.servo.get("ClawServo");
        clawServo.setDirection(Servo.Direction.FORWARD);
    }

    public void MoveGrip(double position) {
        clawServo.setPosition(position);
        tlmGripPosition = position;
    }

    public void closeGrip(){
        MoveGrip(PARAMS.gripClosedPos);
    }

    public void openGrip(){
        MoveGrip(PARAMS.gripOpenPos);
    }

    /*
     * Autonomous Claw Movements
     */



}
