package org.firstinspires.ftc.teamcode.TeleOp;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helper.GamePad;

@TeleOp(name="SingleMotor", group="Hardware")
public class SingleMotor extends LinearOpMode {

    public static final String version = "1.0";
    public static class Params {
        public String motorName = "viperMotor";
        public Boolean Forward = true;
    }

    public static Params PARAMS = new Params();
    private DcMotor motor;
    @Override
    public void runOpMode() {
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);
        telemetry.addLine("Single Motor Test");
        telemetry.addData("Version Number", version);
        telemetry.addLine();
        telemetry.addData(">", "Press Start to Launch");
        telemetry.update();


        DcMotor motor = hardwareMap.dcMotor.get(PARAMS.motorName);


        motor.setDirection(DcMotorSimple.Direction.REVERSE);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        GamePad gpIn1 = new GamePad(gamepad1);

        waitForStart();

        telemetry.clear();


        while (opModeIsActive()) {
            GamePad.GameplayInputType inpType1 = gpIn1.WaitForGamepadInput(30);
            switch (inpType1) {

                case JOYSTICK:
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
                    motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

                    break;

                case BUTTON_X:
                    motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

                    break;

                case BUTTON_BACK:
                    motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    break;

                case BUTTON_L_BUMPER:
                    motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    break;


                    //-15628
                //299

            }


            telemetry.addData("Position", motor.getCurrentPosition());
            telemetry.addData("Power", motor.getPower());
            telemetry.addData("Direction", "Backward");
            telemetry.addData("Direction", "Forward");
            telemetry.addData("Brake", "Engaged");
            telemetry.addData("Brake", "Released");
            telemetry.update();

            if (motor.getZeroPowerBehavior() == DcMotor.ZeroPowerBehavior.BRAKE)
               telemetry.addData("Brake", "Engaged");
            else
                telemetry.addData("Brake", "Released");

            if (motor.getDirection()==DcMotorSimple.Direction.FORWARD)
                motor.setDirection(DcMotorSimple.Direction.FORWARD);
            else
                motor.setDirection(DcMotorSimple.Direction.REVERSE);


            }
        }
    }

