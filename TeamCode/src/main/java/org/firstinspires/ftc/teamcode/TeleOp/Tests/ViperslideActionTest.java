package org.firstinspires.ftc.teamcode.TeleOp.Tests;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Helper.DeferredActions;
import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.DependencyInjector;
import org.firstinspires.ftc.teamcode.Helper.GamePad;
import org.firstinspires.ftc.teamcode.Helper.Intake.IntakeAction;
import org.firstinspires.ftc.teamcode.Helper.ViperSlideActions.ViperAction;

import java.util.List;

@Config
@TeleOp(name = "VIPERandBUCKETAction Test", group = "Tests")
public class ViperslideActionTest extends LinearOpMode {

    private ViperAction viperAction;
    private GamePad gpInput;

    @Override
    public void runOpMode() {
        waitForStart();

        int initRes = this.initialize();

        if (isStopRequested() || (initRes == 1)) {
            return;
        }

        gpInput = new GamePad(gamepad1, false);


        while (opModeIsActive()) {
            GamePad.GameplayInputType inputType = gpInput.WaitForGamepadInput(100);
            switch (inputType) {
                case BUTTON_A:
                    telemetry.addLine("ACTIVATING BUCKET");
                    this.viperAction.TEST_activate_bucket();
                    break;
                case DPAD_UP:
                    telemetry.addLine("INCREMENTING BUCKET");
                    this.viperAction.TEST_increment_bucket();
                    break;
            }


            List<DeferredActions.DeferredActionType> action = DeferredActions.GetReadyActions();
            for(DeferredActions.DeferredActionType actionType : action){
                switch (actionType) {
                    default:
                        break;
                }
            }

//            telemetry.addData("SPB: ", this.intakeAction.TEST_GET_SPB());
            telemetry.update();
        }

    }

    private int initialize() {
        try {
            // inject the dependencies
            DependencyInjector.register("hdwMap", this.hardwareMap);
            DependencyInjector.register("bucketServoName", "bucketServo");
            DependencyInjector.register("telemetry", this.telemetry);

//            his.intakeAction = new IntakeAction();
//            this.pinchAction = new Pinch();
                this.viperAction = new ViperAction();

            // clean up dependencies
            DependencyInjector.unregister("pinchServoName");
            DependencyInjector.unregister("telemetry");
            DependencyInjector.unregister("bucketServoName");
        }
        catch(Exception e) {
            return 1;
        }
        return 0;
    }
}
