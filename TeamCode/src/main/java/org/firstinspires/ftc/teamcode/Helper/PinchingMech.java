package org.firstinspires.ftc.teamcode.Helper;
import android.os.SystemClock;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.SequentialAction;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
public class PinchingMech{
    public static class Params{
        //public String servoNameOne = "elbowServo"; //second connector
        //public String motorNameTwo = "armServo"; //closest to the robot

        public double armStPos = 0;
        public double elStPos = 0;
        public double armPower = 0;
        public double elPower = 0;
        public double maxApos = 0;
        public double minApos = -1;
        public double maxEpos = 0;
        public double minEpos = -1;


    }
    public static Params PARAMS = new Params();


    private final Servo arm;
    private final Servo elbow;

    public PinchingMech(@NonNull HardwareMap hdwMap){
        arm = hdwMap.servo.get("armServo");
        arm.setDirection(Servo.Direction.FORWARD);

        elbow = hdwMap.servo.get("elbowServo");
        arm.setDirection(Servo.Direction.FORWARD);

    }

    public void setArm(double pos){
        arm.setPosition(pos);
    }

    public void setElbow(double pos){
        elbow.setPosition(pos);
    }
    public void startingPos(){
        setArm(PARAMS.armStPos);
        setElbow(PARAMS.elStPos);
    }

    public void oneHandMovement(double power){
        double totalAPos = arm.getPosition() + (((power) * PARAMS.armPower)); //addition of power+ the power of comp on the arm
        double totalEPos = elbow.getPosition() + (((power) * PARAMS.elPower));  // addition of power + the current power of comp on the elbow

        if(power != 0 && totalEPos < PARAMS.maxEpos && totalAPos < PARAMS.maxApos){
            setElbow((power * PARAMS.elPower) + elbow.getPosition());
            setArm((power * PARAMS.armPower)+ arm.getPosition());
        }
        else if(power != 0 && totalEPos < PARAMS.minEpos && totalAPos < PARAMS.minApos){
            setElbow(elbow.getPosition() - (power * PARAMS.elPower));
            setArm(arm.getPosition() - (power*PARAMS.armPower));
        }
        SystemClock.sleep(200);

    //power is the power of the y value from the joystick
    //all the components should move with their own power
    // each power is then multiplied by the joystick power
    //as arm goes down, elbow goes up
    //there should be a limit of how much power can be used in

    }

    public void pinching(){
        //once this is fixed

    }


}
