package org.firstinspires.ftc.teamcode.Helper;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class HangAction {
    private final DcMotor left;
    private final DcMotor right;
    private final DcMotor hang2;


    public HangAction(HardwareMap hardwareMap) {
        left = hardwareMap.get(DcMotor.class, "hookLeft");
        right = hardwareMap.get(DcMotor.class, "hookRight");
        hang2 = hardwareMap.get(DcMotor.class, "hookSecond");

        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        hang2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        left.setDirection(DcMotorSimple.Direction.REVERSE);
        right.setDirection(DcMotorSimple.Direction.REVERSE);
        hang2.setDirection(DcMotorSimple.Direction.REVERSE);


        left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        hang2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


    }


    public void moveMotors(double power) {
        left.setPower(power);
        right.setPower(power);
    }

    public void moveHang2(double power){
        hang2.setPower(power);
    }


}



