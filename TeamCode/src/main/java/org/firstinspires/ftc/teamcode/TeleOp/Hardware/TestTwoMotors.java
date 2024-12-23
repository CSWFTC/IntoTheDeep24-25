package org.firstinspires.ftc.teamcode.TeleOp.Hardware;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helper.GamePad;

@Config
@TeleOp(name="Test Two Motors", group="Hardware")
public class TestTwoMotors extends LinearOpMode {

    public static final String version = "1.0 ";

    public static class Params {
        public String motor1Name = "hookLeft";
        public Boolean motor1Forward = true;
        public String motor2Name = "hookRight";
        public Boolean motor2Forward = false;
    }

    public static Params PARAMS = new Params();


    @Override
    public void runOpMode() {
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);
        telemetry.addLine("Two Motor Test");
        telemetry.addData("Version Number", version);
        telemetry.addLine();
        telemetry.addData(">", "Press Start to Launch");
        telemetry.update();


        DcMotor motor1 = hardwareMap.dcMotor.get(PARAMS.motor1Name);
        DcMotor motor2 = hardwareMap.dcMotor.get(PARAMS.motor2Name);

        motor1.setDirection((PARAMS.motor1Forward) ? DcMotorSimple.Direction.FORWARD : DcMotorSimple.Direction.REVERSE);
        motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motor2.setDirection((PARAMS.motor2Forward) ? DcMotorSimple.Direction.FORWARD : DcMotorSimple.Direction.REVERSE);
        motor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        GamePad gpIn1 = new GamePad(gamepad1);

        waitForStart();

        telemetry.clear();

        while (opModeIsActive()) {
            GamePad.GameplayInputType inpType1 = gpIn1.WaitForGamepadInput(30);
            switch (inpType1) {

                case JOYSTICK:
                    if (motor1.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
                        motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    }
                    motor1.setPower(gamepad1.left_stick_y);
                    motor2.setPower(gamepad1.left_stick_y);
                    break;

                case BUTTON_A:
                    //backward
                    motor1.setDirection(DcMotorSimple.Direction.REVERSE);
                    motor2.setDirection(DcMotorSimple.Direction.REVERSE);
                    break;

                case BUTTON_Y:
                    //forward
                    motor1.setDirection(DcMotorSimple.Direction.FORWARD);
                    motor2.setDirection(DcMotorSimple.Direction.FORWARD);
                    break;

                case BUTTON_B:
                    motor1.setTargetPosition(0);
                    motor2.setTargetPosition(0);
                    motor2.setPower(0.5);
                    motor1.setPower(0.5);
                    motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    motor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    break;


                case BUTTON_R_BUMPER:
                    motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    break;

                case BUTTON_L_BUMPER:
                    break;
            }


            telemetry.addData("M1 Pos", motor1.getCurrentPosition());
            telemetry.addData("M1 Power", motor1.getPower());
            telemetry.addData("M1 Direction", (motor1.getDirection() == DcMotorSimple.Direction.FORWARD) ? "Forward" : "Reverse");
            telemetry.addData("M2 Pos", motor2.getCurrentPosition());
            telemetry.addData("M2 Power", motor2.getPower());
            telemetry.addData("M2 Direction", (motor2.getDirection() == DcMotorSimple.Direction.FORWARD) ? "Forward" : "Reverse");
            telemetry.update();
        }
    }
}
