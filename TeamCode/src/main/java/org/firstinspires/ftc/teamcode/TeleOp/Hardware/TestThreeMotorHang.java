package org.firstinspires.ftc.teamcode.TeleOp.Hardware;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helper.GamePad;
import org.firstinspires.ftc.teamcode.Helper.ViperSlide.BucketAction;
import org.firstinspires.ftc.teamcode.Helper.Beak.newBeak;
import org.firstinspires.ftc.teamcode.Helper.ViperSlide.ClawAction;


@Config
@TeleOp(name="Test 3 Motor Hang", group="Hardware")
public class TestThreeMotorHang extends LinearOpMode {

    public static final String version = "2.1 ";

    public static class Params {
        public String motor1Name = "hookLeft";
        public Boolean motor1Forward = false;
        public String motor2Name = "hookRight";
        public Boolean motor2Forward = false;
        public String motor3Name = "hookSecond";
        public Boolean motor3Forward = true;
        public String grappleServo = "grappleServo";
    }

    public static Params PARAMS = new Params();


    @Override
    public void runOpMode() {
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);
        telemetry.addLine("Three Motor Test");
        telemetry.addData("Version Number", version);
        telemetry.addLine();
        telemetry.addData(">", "Press Start to Launch");
        telemetry.update();


        DcMotor motor1 = hardwareMap.dcMotor.get(PARAMS.motor1Name);
        DcMotor motor2 = hardwareMap.dcMotor.get(PARAMS.motor2Name);
        DcMotor motor3 = hardwareMap.dcMotor.get(PARAMS.motor3Name);
        Servo grappleServo = hardwareMap.servo.get(PARAMS.grappleServo);

        motor1.setDirection((PARAMS.motor1Forward) ? DcMotorSimple.Direction.FORWARD : DcMotorSimple.Direction.REVERSE);
        motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motor2.setDirection((PARAMS.motor2Forward) ? DcMotorSimple.Direction.FORWARD : DcMotorSimple.Direction.REVERSE);
        motor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motor3.setDirection((PARAMS.motor3Forward) ? DcMotorSimple.Direction.FORWARD : DcMotorSimple.Direction.REVERSE);
        motor3.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        grappleServo.setDirection(Servo.Direction.FORWARD);

        BucketAction bucket = new BucketAction(hardwareMap);
        newBeak beak = new newBeak(hardwareMap);

        GamePad gpIn1 = new GamePad(gamepad1);
        ClawAction claw = new ClawAction(hardwareMap);

        waitForStart();
        telemetry.clear();

        // Move to Climb Configuration
        claw.CloseGrip();
        beak.ClimbInitialize();
        sleep(800);
        bucket.climbPostitions();
        sleep(1000);
        beak.ClimbPostitions();

        while (opModeIsActive()) {
            GamePad.GameplayInputType inpType1 = gpIn1.WaitForGamepadInput(30);
            switch (inpType1) {

                case JOYSTICK:
                    motor1.setPower(-gamepad1.left_stick_y);
                    motor2.setPower(-gamepad1.left_stick_y);
                    motor3.setPower(-gamepad1.right_stick_y);
                    break;

                case BUTTON_X:
                    motor3.setPower(0.03);
                    sleep(3000);
                    motor3.setPower(0.0);
                    break;

                case BUTTON_B:
                    motor1.setPower(0.02);
                    motor2.setPower(0.02);
                    sleep(3000);
                    motor1.setPower(0.00);
                    motor2.setPower(0.00);
                    break;

                case BUTTON_L_BUMPER:
                    break;

                case DPAD_UP:
                    grappleServo.setPosition(0.75); // Flips up
                    break;

                case DPAD_LEFT:
                    double posUp = Math.min((grappleServo.getPosition() + 0.05), 0.90);
                    grappleServo.setPosition(posUp);
                    break;

                case DPAD_RIGHT:
                    double posDown = Math.max((grappleServo.getPosition() - 0.05), 0.09);
                    grappleServo.setPosition(posDown);
                    break;

                case DPAD_DOWN:
                    grappleServo.setPosition(0.09); //  default position
                    break;
            }

            telemetry.addData("M1 Pos", motor1.getCurrentPosition());
            telemetry.addData("M1 Power", motor1.getPower());
            telemetry.addData("M1 Direction", (motor1.getDirection() == DcMotorSimple.Direction.FORWARD) ? "Forward" : "Reverse");
            telemetry.addData("M2 Pos", motor2.getCurrentPosition());
            telemetry.addData("M2 Power", motor2.getPower());
            telemetry.addData("M2 Direction", (motor2.getDirection() == DcMotorSimple.Direction.FORWARD) ? "Forward" : "Reverse");
            telemetry.addData("M3 Pos", motor3.getCurrentPosition());
            telemetry.addData("M3 Power", motor3.getPower());
            telemetry.addData("M3 Direction", (motor3.getDirection() == DcMotorSimple.Direction.FORWARD) ? "Forward" : "Reverse");
            telemetry.update();
        }
    }
}
