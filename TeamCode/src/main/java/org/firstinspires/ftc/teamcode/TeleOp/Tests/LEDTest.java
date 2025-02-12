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
    @Override
    public void runOpMode() {
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);
        telemetry.addLine("LED Test");
        telemetry.addData("Version Number", 1.0);
        telemetry.addLine();
        telemetry.addData(">", "Press Start to Launch");
        telemetry.update();

        GamePad gamePad1Helper = new GamePad(gamepad1);

        LEDColorHelper ledColorHelper = new LEDColorHelper();

        waitForStart();
        if (isStopRequested()) return;

        telemetry.clear();

        while (opModeIsActive()) {
            GamePad.GameplayInputType inpType = gamePad1Helper.WaitForGamepadInput(30);

            switch (inpType) {
                case BUTTON_X:
                    ledColorHelper.setLEDColor("Red");
                    telemetry.addData("LED Color", "Red");
                    break;

                case BUTTON_Y:
                    ledColorHelper.setLEDColor("Blue");
                    telemetry.addData("LED Color", "Blue");
                    break;

                case BUTTON_B:
                    ledColorHelper.setLEDColor("White");
                    telemetry.addData("LED Color", "White");
                    break;
            }
            telemetry.update();
        }
    }
}



