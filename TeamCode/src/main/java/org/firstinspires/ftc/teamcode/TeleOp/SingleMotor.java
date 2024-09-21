package org.firstinspires.ftc.teamcode.TeleOp;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.teamcode.Helper.GamePad;

@TeleOp(name="SingleMotor", group="Hardware")
public class SingleMotor extends LinearOpMode {

    private DcMotor frontLeftMotor;

    @Override
    public void runOpMode() {
        DcMotor motor = hardwareMap.dcMotor.get("frontLeft");
        motor.setDirection(DcMotorSimple.Direction.FORWARD);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        GamePad gpIn1 = new GamePad(gamepad1);

        waitForStart();

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

