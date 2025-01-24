package org.firstinspires.ftc.teamcode.Helper;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class HangAction {
    private DcMotor Left;
    private DcMotor Right;



    public HangAction(HardwareMap hardwareMap) {

        Left = hardwareMap.get(DcMotor.class, "hookLeft");
        Right = hardwareMap.get(DcMotor.class, "hookRight");


        Left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        Left.setDirection(DcMotorSimple.Direction.FORWARD);
        Right.setDirection(DcMotorSimple.Direction.FORWARD);
    }


    public void moveMotors(double power) {
        Left.setPower(power);
        Right.setPower(power);
    }


}



