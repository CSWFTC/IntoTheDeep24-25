package org.firstinspires.ftc.teamcode.Helper.Intake;

/*
   IntakeAction is the wrapper class for all intake related mechanisms
   it handles event scheduling and bus event emitting
 */

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helper.DeferredActions;
import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.DependencyInjector;
import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.Inject;
import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.Injectable;
import org.firstinspires.ftc.teamcode.Helper.ReactiveState.Reactive;
import org.firstinspires.ftc.teamcode.Helper.ReactiveState.ReactiveState;
import org.firstinspires.ftc.teamcode.Helper.ReactiveState.StateChange;

public class IntakeAction extends Injectable {
    public static class Params {
    }
    public static Params PARAMS = new Params();

    private IntakeRotation intakeRotation;

    private Pinch pinch;

    @Inject("telemetry")
    private Telemetry telemetry;

    @StateChange("handleRotate")
    public ReactiveState<Boolean> isRotated = new ReactiveState<>(false);

    @StateChange("handlePinch")
    public ReactiveState<Boolean> isPinched = new ReactiveState<>(false);

    private void handleRotate() {
        if (this.isRotated.get()) {
            // emit

        } else {
            // emit
        }
    }




    private void handlePinch() {
        if (this.isPinched.get()) {
            telemetry.addLine("Pinched");
            // emit a rotation event in 1 second
//            DeferredActions.CreateDeferredAction(1000, DeferredActions.DeferredActionType.ROTATE_INTAKE);
            this.intakeRotation.activateRotation();
        } else {
            telemetry.addLine("Not Pinched");
            // emit a derotation event after 1second
//            DeferredActions.CreateDeferredAction(1000, DeferredActions.DeferredActionType.DEROTATE_INTAKE);
            this.intakeRotation.deactivateRotation();
        }
    }

    public void TEST_activate_pinch() {
        this.pinch.closeGrip();
    }

    public void TEST_deactivate_pinch() {
        this.pinch.openGrip();
    }

    public void TEST_rsbp() {
        this.intakeRotation.reduceSBP();
    }

    public void TEST_isbp() {
        this.intakeRotation.increaseSBP();
    }

    public double TEST_GET_SPB() {
        return this.intakeRotation.TEST_getsbp();
    }

    public void TEST_rotation() {
        this.intakeRotation.activateRotation();
    }

    public void TEST_derotate() {
        this.intakeRotation.deactivateRotation();
    }

    public IntakeAction() {
        super();
        Reactive.init(this);

        this.intakeRotation = new IntakeRotation(this);

        this.pinch = new Pinch(this);

        if (this.intakeRotation.initErrorStatus) {
            // ERR
        }

        if (this.pinch.initErrorStatus) {
            //ERR
        }
    }
}
