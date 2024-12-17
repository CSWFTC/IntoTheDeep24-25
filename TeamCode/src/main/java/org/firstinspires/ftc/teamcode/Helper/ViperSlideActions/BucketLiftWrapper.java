package org.firstinspires.ftc.teamcode.Helper.ViperSlideActions;

public class BucketLiftWrapper {
    private ViperAction viperAction;
    private ViperSlideHelper viperSlideHelper;

    public BucketLiftWrapper() throws Exception {
        this.viperAction = new ViperAction();
        this.viperSlideHelper = new ViperSlideHelper();
    }

    public void moveToPosition(int targetPosition) {
        this.viperSlideHelper.moveToPosition(targetPosition, BucketLiftParams.PARAMS.power);
    }

    public void resetEncoders() {
        this.viperSlideHelper.resetEncoders();
    }

    public float getCurrentPosition() {
        return this.viperSlideHelper.getCurrentPosition();
    }

    public void activateBucket() {
        this.viperAction.TEST_activate_bucket();
    }

    public void dumpBucket() {
        this.viperAction.TEST_rotate_bucket();
    }

    public void resetBucket() {
        this.viperAction.TEST_reset_bucket();
    }
}
