package org.firstinspires.ftc.teamcode.Helper;
import android.os.SystemClock;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.SequentialAction;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
public class PinchingMech {
    public static class Params{
        //public String servoNameOne = "elbowServo"; //second connector
        //public String motorNameTwo = "armServo"; //closest to the robot

        public double armStPos = 0.1;
        public double elStPos = 0.668;
        public double armPower = 0.01;
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

    public double getArm(){
        return arm.getPosition();

    }
    public double getEl(){
       return elbow.getPosition();
    }

    public void setElbow(double pos){
        elbow.setPosition(pos);
    }
    public void setPosition(){
        setArm(PARAMS.armStPos);
        setElbow(PARAMS.elStPos);
    }

    public void oneHandMovement(float stickRightY){
       /* double totalAPos = arm.getPosition() + (((power) * PARAMS.armPower)); //addition of power+ the power of comp on the arm
        double totalEPos = elbow.getPosition() + (((power) * PARAMS.elPower));  // addition of power + the current power of comp on the elbow

        if(power != 0 && totalEPos < PARAMS.maxEpos && totalAPos < PARAMS.maxApos){
            setElbow((power * PARAMS.elPower) + elbow.getPosition());
            setArm((power * PARAMS.armPower)+ arm.getPosition());
        }
        else if(power != 0 && totalEPos < PARAMS.minEpos && totalAPos < PARAMS.minApos){
            setElbow(elbow.getPosition() - (power * PARAMS.elPower));
            setArm(arm.getPosition() - (power*PARAMS.armPower));
        }
        SystemClock.sleep(200);      */
        //float forward = stickRightY;
         double currentArm = arm.getPosition();
         double currentElb = elbow.getPosition();

        // move the elbow servo from its starting position (0.668) to 0.58
        //move the arm from its starting position (0.1) to 0.29)
        // move the elbow servo to 0.45
        // move the arm servo to 0.63
        //first move
     if(stickRightY > 0) {
         if (currentElb == 0.1 && currentArm > 0.58 && currentArm != 0.58) { //current arm is less that 0.58 & elbow is at 0.1
             currentArm = Math.max(0.58, currentArm - stickRightY); // Clamp to minimum 0.58
             setArm(currentArm);
         } else if (currentArm == 0.58 && currentElb < 0.29 && currentElb != 0.29) {
             currentElb = Math.min(0.29,currentElb+stickRightY); // Clamp to maximum 0.29
             setElbow(currentElb);
         }     //if current arm is at 0.58, &
         else if (currentElb != 0.45 && currentArm != 0.63 && currentArm >= 0.58 && currentElb >= 0.29) {
             currentArm = Math.min(0.63, currentArm + stickRightY); // Clamp to maximum 0.63
             currentElb = Math.min(0.45, currentElb + stickRightY); // Clamp to maximum 0.45
             setArm(currentArm);
             setElbow(currentElb);
         }
     }
    //power is the power of the y value from the joystick
    //all the components should move with their own power
    // each power is then multiplied by the joystick power
    //as arm goes down, elbow goes up
    //there should be a limit of how much power can be used in

    }

    public void pinching(){
        //once this is fixed

    }

    public void initalizer(){
        setArm( 0.2);
        setElbow(0.668);

    }


}
