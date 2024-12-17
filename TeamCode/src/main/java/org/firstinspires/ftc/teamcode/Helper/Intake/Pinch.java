package org.firstinspires.ftc.teamcode.Helper.Intake;

import android.os.SystemClock;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.Inject;
import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.Injectable;

public class Pinch extends Injectable {
    public static class Params {
        public double grabOpenPos = 0.8;
        public double grabClosedPos = 0.5;
    }

    public boolean initErrorStatus = false;
    public String initError = "";

    public static Params PARAMS = new Params();

    private IntakeAction intakeAction;

    public double tlmGrabPosition = -1;

    @Inject("telemetry")
    private Telemetry telemetry;

    @Inject("hdwMap")
    private HardwareMap hdwMap;

    @Inject("pinchServoName")
    public String servoName;

    private Servo grabber;

    public Pinch(IntakeAction intakeAction){
         super();

        this.intakeAction = intakeAction;

        try {
            grabber = hdwMap.servo.get(servoName);
            grabber.setDirection(Servo.Direction.FORWARD);
        } catch(Exception e) {
            this.initErrorStatus = true;
            this.initError = e.toString();
        }
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

        this.telemetry.addLine("Pinched from PinchClass");
        this.intakeAction.isPinched.set(true);
    }

    public void openGrip(){
        MovePincher(PARAMS.grabOpenPos);

        this.telemetry.addLine("UnPinched from PinchClass");
        this.intakeAction.isPinched.set(false);
    }

    public Action movePincher(double pos) {
        return packet ->{
            openGrip();
            SystemClock.sleep(100);
            MovePincher(pos);
            SystemClock.sleep(100);
            closeGrip();

            return false;};
    }

}
