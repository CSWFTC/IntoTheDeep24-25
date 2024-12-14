package org.firstinspires.ftc.teamcode.Helper;

import android.os.SystemClock;

import org.firstinspires.ftc.teamcode.Helper.Beak.BeakAction;
import org.firstinspires.ftc.teamcode.Helper.Intake.Pinch;
import org.firstinspires.ftc.teamcode.Helper.ViperSlideActions.BucketLiftWrapper;

public class StaticActions {
    private static StaticActions instance = null;

    private BeakAction beakAction;
    private BucketLiftWrapper bucketLiftWrapper;

    // option available for fine grained control
    public BucketLiftWrapper getBucketLiftWrapper(){ return this.bucketLiftWrapper;}
    public BeakAction getBeakAction() {return this.beakAction;}

    // pre-orchestrated action controls
    private void dumpBucket() {
        bucketLiftWrapper.dumpBucket();
    }

    private void resetSlider() {
        bucketLiftWrapper.moveToPosition((int)(bucketLiftWrapper.getCurrentPosition()-5)*-1);
    }

    private void resetBucket() {
        bucketLiftWrapper.resetBucket();
    }

    public void dumpBucketSmall() {
        bucketLiftWrapper.resetEncoders();
        bucketLiftWrapper.moveToPosition(1178);
        SystemClock.sleep(1200);
        dumpBucket();
        SystemClock.sleep(1800);
        resetBucket();
        SystemClock.sleep(1100);
        resetSlider();
    }


    public synchronized static StaticActions getInstance() {
        if (instance == null) {
            instance = new StaticActions();
        }
        return instance;
    }

    private StaticActions() {
        try {
            this.beakAction = new BeakAction();
            this.bucketLiftWrapper = new BucketLiftWrapper();
        } catch(Exception e){
            // uh oh
        }
    }
}
