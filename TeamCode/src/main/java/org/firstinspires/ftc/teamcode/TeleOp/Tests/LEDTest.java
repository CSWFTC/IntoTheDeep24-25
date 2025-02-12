package org.firstinspires.ftc.teamcode.TeleOp.Tests;



import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helper.GamePad;
import org.firstinspires.ftc.teamcode.Helper.LEDColorHelper;


@Config
@TeleOp(name="LED Test", group="Hardware")
public class LEDTest extends LinearOpMode {
    public static class Params {
        public double servoStartPos = 0.532;
        public String servoName = "LEDservo";
    }

    public static Params PARAMS = new Params();

    double newPosition = 0;
    double tlmServoPosition = 0;

    @Override
    public void runOpMode() {
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);
        telemetry.addLine("LED Test");
        telemetry.addData("Version Number", 1.0);
        telemetry.addLine();
        telemetry.addData(">", "Press Start to Launch");
        telemetry.update();

        GamePad GamePad1 = new GamePad(gamepad1);
        Servo srv;

        srv = hardwareMap.servo.get(PARAMS.servoName);

        newPosition = PARAMS.servoStartPos;
        srv.setPosition(newPosition);
        tlmServoPosition = newPosition;


        LEDColorHelper ledColorHelper = new LEDColorHelper(srv);

        waitForStart();
        if (isStopRequested()) return;

        telemetry.clear();

        while (opModeIsActive()) {
            GamePad.GameplayInputType inpType = GamePad1.WaitForGamepadInput(30);

            switch (inpType) {
                case JOYSTICK:

                    double pos = (gamepad1.left_stick_x * 0.5) + 0.5;
                    srv.setPosition(pos);
                    telemetry.addData("Current Servo Position", pos);
                    telemetry.addLine();
                    telemetry.update();
                    break;

                case BUTTON_X:
                    // Set LED to Red when X button is pressed
                    ledColorHelper.setLEDColor("Red");
                    telemetry.addData("LED Color", "Red");
                    telemetry.update();
                    break;

                case BUTTON_Y:

                    ledColorHelper.setLEDColor("Blue");
                    telemetry.addData("LED Color", "Blue");
                    telemetry.update();
                    break;

                case BUTTON_B:
                    ledColorHelper.setLEDColor("White");
                    telemetry.addData("LED Color", "White");
                    telemetry.update();
                    break;

                case BUTTON_A:

                    ledColorHelper.setLEDColor("Yellow");
                    telemetry.addData("LED Color", "Yellow");
                    telemetry.update();
                    break;

                default:
                    break;
            }
        }
    }
}


