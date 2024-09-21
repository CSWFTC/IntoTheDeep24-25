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
        DcMotor motor = hardwareMap.get(DcMotor.class, "frontLeft");
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
                    //  backward
                    motor.setPower(-1);
                    telemetry.addData("Direction", "Backward");
                    break;

                case BUTTON_Y:
                    //  forward
                    motor.setPower(1);
                    telemetry.addData("Direction", "Forward");
                    break;

                case BUTTON_B:
                    motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                    motor.setPower(0); // Stops the motor >:)
                    telemetry.addData("Brake", "Engaged");
                    break;

                case BUTTON_X:
                    motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                    telemetry.addData("Brake", "Released");
                    break;


            }


            telemetry.addData("Position", motor.getCurrentPosition());
            telemetry.addData("Power", motor.getPower());
            telemetry.update();
        }
    }
}
