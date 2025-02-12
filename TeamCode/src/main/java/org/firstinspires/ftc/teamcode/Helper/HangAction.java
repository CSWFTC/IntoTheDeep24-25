package org.firstinspires.ftc.teamcode.Helper;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class HangAction {
    private final DcMotor left;
    private final DcMotor right;


    public HangAction(HardwareMap hardwareMap) {
        left = hardwareMap.get(DcMotor.class, "hookLeft");
        right = hardwareMap.get(DcMotor.class, "hookRight");

        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        left.setDirection(DcMotorSimple.Direction.REVERSE);
        right.setDirection(DcMotorSimple.Direction.REVERSE);

        left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }


    public void moveMotors(double power) {
        left.setPower(power);
        right.setPower(power);
    }
}



