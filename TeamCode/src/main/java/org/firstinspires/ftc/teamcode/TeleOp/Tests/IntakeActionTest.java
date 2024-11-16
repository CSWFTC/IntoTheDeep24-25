package org.firstinspires.ftc.teamcode.TeleOp.Tests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Helper.DeferredActions;
import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.DependencyInjector;
import org.firstinspires.ftc.teamcode.Helper.GamePad;
import org.firstinspires.ftc.teamcode.Helper.Intake.IntakeAction;

import java.util.List;

@Config
@TeleOp(name = "IntakeAction Test", group = "Tests")
public class IntakeActionTest extends LinearOpMode {
    private IntakeAction intakeAction;
    private GamePad gpInput;

    // IGNORE THIS - its for local mock testing
//    public static void main(String[] args) {
//        System.out.println("hi");
//
//        IntakeActionTest iat = new IntakeActionTest();
//
//        iat.runOpMode();
//    }

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
                    telemetry.addLine("TESTING rotation");
                    this.intakeAction.TEST_rotation();
                    break;
                case BUTTON_B:
                    telemetry.addLine("TESTING derotation");
                    this.intakeAction.TEST_derotate();
                    break;
                case BUTTON_X:
                    telemetry.addLine("TESTING activate pinch");
                    this.intakeAction.TEST_activate_pinch();
                    break;
                case BUTTON_Y:
                    telemetry.addLine("TESTING deactivate pinch");
                    this.intakeAction.TEST_deactivate_pinch();
                    break;
                case DPAD_DOWN:
                    telemetry.addLine("TESTING rspb");
                    this.intakeAction.TEST_rsbp();
                    break;
                case DPAD_UP:
                    telemetry.addLine("TESTING ispb");
                    this.intakeAction.TEST_isbp();
                    break;
            }


            List<DeferredActions.DeferredActionType> action = DeferredActions.GetReadyActions();
            for(DeferredActions.DeferredActionType actionType : action){
                switch (actionType) {
                    case ROTATE_INTAKE:
                        this.intakeAction.TEST_rotation();
                        break;
                    case DEROTATE_INTAKE:
                        this.intakeAction.TEST_derotate();
                        break;
                    case UNPINCH:
                        this.intakeAction.TEST_activate_pinch();
                        break;
                }
            }

            telemetry.addData("SPB: ", this.intakeAction.TEST_GET_SPB());
            telemetry.update();
        }

    }

    private int initialize() {
        try {
            // inject the dependencies
            DependencyInjector.register("hdwMap", this.hardwareMap);
            DependencyInjector.register("intakeRotationServoName", "intakeRotationServo");
            DependencyInjector.register("pinchServoName", "pinchServo");
            DependencyInjector.register("telemetry", this.telemetry);

            this.intakeAction = new IntakeAction();
//            this.pinchAction = new Pinch();

            // clean up dependencies
            DependencyInjector.unregister("hdwMap");
            DependencyInjector.unregister("intakeRotationServoName");
            DependencyInjector.unregister("pinchServoName");
            DependencyInjector.unregister("telemetry");
        }
        catch(Exception e) {
            return 1;
        }
       return 0;
    }
}
