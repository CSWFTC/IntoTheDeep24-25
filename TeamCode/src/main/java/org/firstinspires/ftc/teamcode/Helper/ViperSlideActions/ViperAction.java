package org.firstinspires.ftc.teamcode.Helper.ViperSlideActions;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.Inject;
import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.Injectable;
import org.firstinspires.ftc.teamcode.Helper.Intake.IntakeAction;
import org.firstinspires.ftc.teamcode.Helper.ReactiveState.Reactive;
import org.firstinspires.ftc.teamcode.Helper.ReactiveState.ReactiveState;
import org.firstinspires.ftc.teamcode.Helper.ReactiveState.StateChange;

public class ViperAction extends Injectable {
    public static class Params {
    }
    public static Params PARAMS = new Params();

    @StateChange("bucketChange")
    public ReactiveState<BucketState> bucketState = new ReactiveState<>(BucketState.INACTIVE);

    @Inject("telemetry")
    private Telemetry telemetry;

    private BucketAction bucketAction;

    public void bucketChange() {

    }

    public void TEST_activate_bucket() {
        this.pos = 0.09;
        this.bucketAction.moveToPosition(0.09);
        this.bucketState.set(BucketState.TRANSPORT);
    }

    public double pos = 0.1;

    public void TEST_increment_bucket() {
        pos += 0.01;
        this.bucketAction.moveToPosition(pos);
    }

    public void TEST_decrement_bucket() {
        pos -= 0.01;
        this.bucketAction.moveToPosition(pos);
    }

    public ViperAction() {
        super();
        Reactive.init(this);

        this.bucketAction = new BucketAction(this);


        if (this.bucketAction.initErrorStatus) {
            // ERR
        }

        this.bucketAction.moveToPosition(this.pos);
    }
}
