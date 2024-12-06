package org.firstinspires.ftc.teamcode.TeleOp.Tests;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Helper.DeferredActions;
import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.DependencyInjector;
import org.firstinspires.ftc.teamcode.Helper.GamePad;
import org.firstinspires.ftc.teamcode.Helper.Intake.IntakeAction;
import org.firstinspires.ftc.teamcode.Helper.ViperSlideActions.ViperAction;
import org.firstinspires.ftc.teamcode.Helper.ViperSlideActions.ViperSlideHelper;

import java.util.List;

@Config
@TeleOp(name = "VIPERandBUCKETAction Test", group = "Tests")
public class ViperslideActionTest extends LinearOpMode {

    private ViperAction viperAction;
    private GamePad gpInput;

    private ViperSlideHelper viperSlideHelper;

    @Override
    public void runOpMode() {
        waitForStart();

        int initRes = this.initialize();

        if (isStopRequested() || (initRes == 1)) {
            return;
        }

        gpInput = new GamePad(gamepad1, false);

        this.viperSlideHelper.resetEncoders();


        while (opModeIsActive()) {
            GamePad.GameplayInputType inputType = gpInput.WaitForGamepadInput(100);
            switch (inputType) {
                case BUTTON_A:
                    this.viperAction.TEST_activate_bucket();
                    telemetry.addLine("ACTIVATING BUCKET");
                    telemetry.addData("pos", this.viperAction.pos);
                    break;
//                case DPAD_UP:
//                    this.viperSlideHelper.resetEncoders();
//                    this.viperSlideHelper.moveToPosition(16000, 0.8);
//                    telemetry.addLine("WENT UP SLIDE");
////                    this.viperAction.TEST_increment_bucket();
////                    telemetry.addLine("INCREMENTING BUCKET");
////                    telemetry.addData("pos", this.viperAction.pos);
//                    break;
//                case DPAD_DOWN:
//                    telemetry.addData("VIPERSLIDE b4 RESET POS: ", this.viperSlideHelper.getCurrentPosition());
//                    telemetry.addData("VIPERSLIDE POS: ", this.viperSlideHelper.getCurrentPosition());
//                    this.viperSlideHelper.moveToPosition((this.viperSlideHelper.getCurrentPosition()-5)*-1, 0.8);
//                    telemetry.addLine("Reset SLIDE");
//
////                    this.viperSlideHelper.resetEncoders();
//                    telemetry.addData("VIPERSLIDE FINAL POS: ", this.viperSlideHelper.getCurrentPosition());
////                    telemetry.addData("pos", this.viperAction.pos);
////                    this.viperAction.TEST_decrement_bucket();
//                    break;
                case BUTTON_B:
                    this.viperSlideHelper.resetEncoders();
                    this.viperSlideHelper.moveToPosition(3150, 0.8);
                    telemetry.addLine("WENT UP SLIDE");
                    DeferredActions.CreateDeferredAction(2500, DeferredActions.DeferredActionType.ROTATE_BUCKET);
                    DeferredActions.CreateDeferredAction(5000, DeferredActions.DeferredActionType.RESET_SLIDER);
                    DeferredActions.CreateDeferredAction(4000, DeferredActions.DeferredActionType.RESET_BUCKET);

                    break;
                case DPAD_UP:
                    this.viperAction.TEST_increment_bucket();
                    telemetry.addLine("INCREMENTING BUCKET");
                    telemetry.addData("pos", this.viperAction.pos);
                    break;
                case DPAD_DOWN:
                    telemetry.addLine("DECREMENTING BUCKET");
                    telemetry.addData("pos", this.viperAction.pos);
                    this.viperAction.TEST_decrement_bucket();
                    break;
            }


            List<DeferredActions.DeferredActionType> action = DeferredActions.GetReadyActions();
            for(DeferredActions.DeferredActionType actionType : action){
                switch (actionType) {
                    case ROTATE_BUCKET:
                        this.viperAction.TEST_rotate_bucket();
                        break;
                    case RESET_BUCKET:
                        this.viperAction.TEST_reset_bucket();
                        break;
                    case RESET_SLIDER:
                    telemetry.addData("VIPERSLIDE b4 RESET POS: ", this.viperSlideHelper.getCurrentPosition());
                    telemetry.addData("VIPERSLIDE POS: ", this.viperSlideHelper.getCurrentPosition());
                    this.viperSlideHelper.moveToPosition((this.viperSlideHelper.getCurrentPosition()-5)*-1, 0.8);
                    telemetry.addLine("Reset SLIDE");

//                    this.viperSlideHelper.resetEncoders();
                    telemetry.addData("VIPERSLIDE FINAL POS: ", this.viperSlideHelper.getCurrentPosition());
//                    telemetry.addData("pos", this.viperAction.pos);
//                    this.viperAction.TEST_decrement_bucket();
                        break;
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
            try {
                this.viperAction = new ViperAction();
            } catch(Exception e) {
                telemetry.clear();
                telemetry.addLine("AN ERROR OCCURED: "+e.toString());
                telemetry.update();
                throw new Exception(e);
            }

                this.viperSlideHelper = new ViperSlideHelper();

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
