package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helper.GamePad;

@Config
@TeleOp(name = "Driver Control", group = "Competition!!")
public class DriveControl extends LinearOpMode {
    private static final String version = "1.2";
    private Gamepad1Control gamepad1Control;
    private Gamepad2Control gamepad2Control;

    @Override
    public void runOpMode() {
        int initRes = initialize();

        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);
        telemetry.addLine("Driver Control");
        telemetry.addData("Version Number", version);
        telemetry.addLine();
        if (initRes != 1)
            telemetry.addData(">", "Press Start to Launch");
        telemetry.update();

        waitForStart();
        if (isStopRequested() || (initRes == 1)) {
            return;
        }

        GamePad gpIn1 = new GamePad(gamepad1, false);
        GamePad gpIn2 = new GamePad(gamepad2);

        while (opModeIsActive()) {
            gamepad1Control.update(gpIn1, gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.left_stick_y, gamepad1.right_trigger, gamepad1.left_trigger);
            gamepad2Control.update(gpIn2, gamepad2.left_stick_y, gamepad2.right_stick_y, gamepad2.left_trigger, gamepad2.right_trigger);
        }
    }

    private int initialize() {
        try {
            gamepad1Control = new Gamepad1Control(hardwareMap);
            gamepad2Control = new Gamepad2Control(hardwareMap);
        } catch (Exception e) {
            telemetry.clear();
            telemetry.addLine("AN ERROR OCCURRED: " + e.toString());
            telemetry.update();
            return 1;
        }
        return 0;
    }
}

