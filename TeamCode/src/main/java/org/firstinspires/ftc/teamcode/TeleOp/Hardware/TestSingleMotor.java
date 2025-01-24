package org.firstinspires.ftc.teamcode.TeleOp.Hardware;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helper.GamePad;

@Config
@TeleOp(name="Test Single Motor", group="Hardware")
public class TestSingleMotor extends LinearOpMode {

    public static final String version = "1.4";
    public static class Params {
        public String motorName = "viperMotor";
        public Boolean motorForward = false;
        public double motorTestPosition = 400;
    }

    public static Params PARAMS = new Params();

    @Override
    public void runOpMode() {
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);

        telemetry.addLine().addData("Single Motor Test:  ", PARAMS.motorName );
        telemetry.addLine();
        telemetry.addData("Version Number", version);
        telemetry.addLine();
        telemetry.addData(">", "Press Start to Launch");
        telemetry.update();

        DcMotor motor = hardwareMap.dcMotor.get(PARAMS.motorName);
        motor.setDirection((PARAMS.motorForward) ? DcMotorSimple.Direction.FORWARD : DcMotorSimple.Direction.REVERSE);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        GamePad gpIn1 = new GamePad(gamepad1);

        waitForStart();

        telemetry.clear();


        while (opModeIsActive()) {
            GamePad.GameplayInputType inpType1 = gpIn1.WaitForGamepadInput(30);
            switch (inpType1) {

                case JOYSTICK:
                    if (motor.getMode() != DcMotor.RunMode.RUN_USING_ENCODER)
                        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    motor.setPower(gamepad1.left_stick_y);
                    break;

                case BUTTON_A:
                    //backward
                    motor.setDirection(DcMotorSimple.Direction.REVERSE);

                    break;

                case BUTTON_Y:
                    //forward
                    motor.setDirection(DcMotorSimple.Direction.FORWARD);
                    break;

                case BUTTON_B:
                    motor.setTargetPosition(0);
                    motor.setPower(0.5);
                    motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    break;

                case BUTTON_X:
                    motor.setTargetPosition((int) PARAMS.motorTestPosition);
                    motor.setPower(0.5);
                    motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    break;

                case BUTTON_R_BUMPER:
                    motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    break;

                case BUTTON_L_BUMPER:
                    if (motor.getZeroPowerBehavior() == DcMotor.ZeroPowerBehavior.FLOAT)
                        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                    else
                        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                    break;
            }


            telemetry.addLine().addData("Motor:  ", PARAMS.motorName );
            telemetry.addData("Position", motor.getCurrentPosition());
            telemetry.addData("Power", motor.getPower());
            telemetry.addLine().addData("Direction  ", ((motor.getDirection() == DcMotorSimple.Direction.FORWARD) ? "Forward" : "Reverse") );
            telemetry.addData("Zero Power", (motor.getZeroPowerBehavior() == DcMotor.ZeroPowerBehavior.FLOAT) ? "Float" : "Brake");
            telemetry.update();
        }
    }
}

