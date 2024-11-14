package org.firstinspires.ftc.teamcode.Helper.Intake;

/*
   IntakeAction is the wrapper class for all intake related mechanisms
   it handles event scheduling and bus event emitting
 */

import org.firstinspires.ftc.teamcode.Helper.ReactiveState.Reactive;
import org.firstinspires.ftc.teamcode.Helper.ReactiveState.ReactiveState;
import org.firstinspires.ftc.teamcode.Helper.ReactiveState.StateChange;

public class IntakeAction {
    public static class Params {
        public double intakeSpeed = 0.5;
    }
    public static Params PARAMS = new Params();


    @StateChange("handleRotate")
    private ReactiveState<Boolean> isRotated = new ReactiveState<>(false);

    private ReactiveState<Boolean> isPinched = new ReactiveState<>(false);

    private void handleRotate() {
        if (this.isRotated.get()) {
            // emit
        } else {
            // emit
        }
    }

    public IntakeAction() {
        Reactive.init(this);
    }
}
