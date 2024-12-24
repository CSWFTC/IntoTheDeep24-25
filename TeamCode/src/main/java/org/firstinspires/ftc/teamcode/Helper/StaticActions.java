package org.firstinspires.ftc.teamcode.Helper;

import android.os.SystemClock;

import com.acmerobotics.roadrunner.Action;

import org.firstinspires.ftc.teamcode.Helper.Beak.BeakAction;
import org.firstinspires.ftc.teamcode.Helper.Intake.Pinch;
import org.firstinspires.ftc.teamcode.Helper.ViperSlideActions.BucketLiftWrapper;
//import org.firstinspires.ftc.teamcode.Helper.

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

    private void resetBuc() {
        bucketLiftWrapper.resetBucket();
    }


    public Action dumpBucketLowBasket() {
        return packet -> {;
            bucketLiftWrapper.moveToPosition(1178);
            SystemClock.sleep(1200);
            dumpBucket();
            return false;
        };
    }

    public Action retractBucket() {
        return packet -> {;
            bucketLiftWrapper.resetBucket();
            SystemClock.sleep(1100);
            resetSlider();
            return false;
        };
    }

    public Action dumpBucketHighBasket() {
        return packet -> {;
            bucketLiftWrapper.moveToPosition(1178);
            SystemClock.sleep(1200);
            dumpBucket();
            return false;
        };
    }


   public Action armForward(){
        return packet -> {;
            beakAction.PickupReach();
            SystemClock.sleep(1100);
            beakAction.CloseBeak();
            SystemClock.sleep(1200);
            beakAction.SuplexSample();
            SystemClock.sleep(1200);

        return false;
        };


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
