package org.firstinspires.ftc.teamcode.Helper.Telemetry;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Pinch {
    public static class Params {
        public double grabOpenPos = 1.100;
        public double grabClosedPos = 0.200;
    }
    public static Params PARAMS = new Params();

    public double tlmGrabPosition = -1;

    private final Servo grabber;

    public Pinch(@NonNull HardwareMap hdwMap){
         grabber = hdwMap.servo.get("pinchServo");
         grabber.setDirection(Servo.Direction.FORWARD);
    }

    public void MovePincher(double position) {
        grabber.setPosition(position);
        tlmGrabPosition = position;
    }

    public void AutonomousStart () {
        MovePincher(PARAMS.grabOpenPos);
    }

    /*
     * Driver Claw Movements
     */

    public void closeGrip(){
            MovePincher(PARAMS.grabClosedPos);
    }

    public void openGrip(){
        MovePincher(PARAMS.grabOpenPos);
    }

}
