package org.firstinspires.ftc.teamcode.Helper;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


public class HangAction {
    public static class Params {
        public double grappleStartPos = 0.11;
        public double grappleClimbPos = 0.75;
    }

    public static Params PARAMS = new Params();

    private final DcMotor left;
    private final DcMotor right;
    private final DcMotor hang2;
    private final Servo grapple;

    public HangAction(HardwareMap hardwareMap) {
        left = hardwareMap.get(DcMotor.class, "hookLeft");
        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left.setDirection(DcMotorSimple.Direction.REVERSE);
        left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        right = hardwareMap.get(DcMotor.class, "hookRight");
        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right.setDirection(DcMotorSimple.Direction.REVERSE);
        right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        hang2 = hardwareMap.get(DcMotor.class, "hookSecond");
        hang2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        hang2.setDirection(DcMotorSimple.Direction.FORWARD);
        hang2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hang2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        grapple = hardwareMap.get(Servo.class, "grappleServo");
    }


    public void moveStage1Motors(double power) {
        left.setPower(power);
        right.setPower(power);
    }

    public void holdStage2Position() {
        hang2.setTargetPosition(hang2.getCurrentPosition());
        hang2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hang2.setPower(0.5);
    }


    public void moveStage2Motor(double power) {
        hang2.setPower(power);
    }

    public void moveHangDown(double power) {
        hang2.setPower(-power);
    }

    public void grappleFlipUp() {
        grapple.setPosition(PARAMS.grappleClimbPos);
    }

    public void grappleStartPosition() {
        grapple.setPosition(PARAMS.grappleStartPos);
    }

    public void grappleForward() {
        double posUp = Math.min((grapple.getPosition() + 0.05), 0.90);
        grapple.setPosition(posUp);
    }

    public void grappleBackward() {
        double posDown = Math.max((grapple.getPosition() - 0.05), 0.09);
        grapple.setPosition(posDown);
    }
    

}





