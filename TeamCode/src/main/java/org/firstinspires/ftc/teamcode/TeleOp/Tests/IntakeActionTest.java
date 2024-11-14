package org.firstinspires.ftc.teamcode.TeleOp.Tests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.DependencyInjector;
import org.firstinspires.ftc.teamcode.Helper.GamePad;
import org.firstinspires.ftc.teamcode.Helper.Intake.IntakeAction;

@Config
@TeleOp(name = "IntakeAction Test", group = "Tests")
public class IntakeActionTest extends LinearOpMode {
    private IntakeAction intakeAction;
    private GamePad gpInput;

    @Override
    public void runOpMode() {
        waitForStart();

        int initRes = this.initialize();

        if (isStopRequested() || (initRes == 1)) {
            return;
        }

        while (opModeIsActive()) {
            GamePad.GameplayInputType inputType = gpInput.WaitForGamepadInput(100);
            switch (inputType) {
                case BUTTON_A:
                    telemetry.addLine("TESTING rotation");
                    this.intakeAction.TEST_rotation();
                    break;
                case BUTTON_B:
                    telemetry.addLine("TESTING derotation");
                    this.intakeAction.TEST_derotate();
                    break;
            }

            telemetry.update();
        }

    }

    private int initialize() {
        try {
            DependencyInjector.register("hdwMap", this.hardwareMap);
            DependencyInjector.register("intakeRotationServoName", "intakeRotationServo");

            this.intakeAction = new IntakeAction();

            // clean up dependencies
            DependencyInjector.unregister("hdwMap");
            DependencyInjector.unregister("intakeRotationServoName");
        }
        catch(Exception e) {
            return 1;
        }
       return 0;
    }
}
