package org.firstinspires.ftc.teamcode.Helper;

import android.content.Context;
import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.lynx.LynxEmbeddedIMU;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Quack {
    private LynxEmbeddedIMU imu;
    private int quackID;
    private Context appContext;

    void init(HardwareMap hwMap) {
        imu = hwMap.get(LynxEmbeddedIMU.class, "imu");
        BNO055IMU.Parameters params = new BNO055IMU.Parameters();
        imu.initialize(params);
        quackID = hwMap.appContext.getResources().getIdentifier("quack", "raw", hwMap.appContext.getPackageName());
        appContext = hwMap.appContext;
    }
    public void quack() {
        SoundPlayer.getInstance().startPlaying(appContext, quackID);
    }

   /*
    public void makeRoboQuack(){
        quack();
      //  DeferredActions.CreateDeferredAction(150, DeferredActions.DeferredActionType.ROBO_SOUND);
      //  quack();
      //  DeferredActions.CreateDeferredAction(180, DeferredActions.DeferredActionType.ROBO_SOUND);
      //  quack();


    }
*/


}