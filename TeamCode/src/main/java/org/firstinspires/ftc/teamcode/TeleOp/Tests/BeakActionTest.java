package org.firstinspires.ftc.teamcode.TeleOp.Tests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Helper.Beak.BeakAction;
import org.firstinspires.ftc.teamcode.Helper.DeferredActions;
import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.DependencyInjector;
import org.firstinspires.ftc.teamcode.Helper.GamePad;
import org.firstinspires.ftc.teamcode.Helper.ViperSlideActions.ViperAction;
import org.firstinspires.ftc.teamcode.Helper.ViperSlideActions.ViperSlideHelper;

import java.util.List;

@Config
@TeleOp(name = "BeakAction Test", group = "Tests")
public class BeakActionTest extends LinearOpMode {
    private BeakAction beakAction;
    private GamePad gpInput;

    @Override
    public void runOpMode() {
        waitForStart();

        int initRes = this.initialize();

        if (isStopRequested() || (initRes == 1)) {
            return;
        }

        gpInput = new GamePad(gamepad1, false);

        this.beakAction.DrivePosition();


        while (opModeIsActive()) {
            GamePad.GameplayInputType inputType = gpInput.WaitForGamepadInput(100);
            switch (inputType) {
                case BUTTON_A:
                    this.beakAction.PrepForPickup();
                    break;
                case BUTTON_B:
                    this.beakAction.PickupReach();
                    break;
                case DPAD_DOWN:
                    this.beakAction.CloseBeak();
                    break;
                case DPAD_UP:
                    this.beakAction.OpenBeak();
                    break;
            }


            List<DeferredActions.DeferredActionType> action = DeferredActions.GetReadyActions();
            for(DeferredActions.DeferredActionType actionType : action){
                switch (actionType) {
                    case BEAK_OPEN:
                        this.beakAction.OpenBeak();
                        break;
                    case BEAK_CLOSE:
                        this.beakAction.CloseBeak();
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
            DependencyInjector.register("telemetry", this.telemetry);

            try {
                this.beakAction = new BeakAction();
            } catch(Exception e) {
                telemetry.clear();
                telemetry.addLine("AN ERROR OCCURED: "+e.toString());
                telemetry.update();
                throw new Exception(e);
            }

            // clean up dependencies
            DependencyInjector.unregister("hdwMap");
            DependencyInjector.unregister("telemetry");
        }
        catch(Exception e) {
            return 1;
        }
        return 0;
    }
}
