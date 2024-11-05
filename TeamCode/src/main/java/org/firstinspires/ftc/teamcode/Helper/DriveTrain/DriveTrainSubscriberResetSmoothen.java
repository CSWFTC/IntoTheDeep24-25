package org.firstinspires.ftc.teamcode.Helper.DriveTrain;

import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.Inject;
import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.Injectable;
import org.firstinspires.ftc.teamcode.Helper.EventBus.Subscriber;

public class DriveTrainSubscriberResetSmoothen extends Injectable implements Subscriber<Void, Void> {
    @Inject("drive_train")
    NewDriveTrain driveTrain;

    @Override
    public Void onMessage(Object ... params) {
        return null;
    }

    @Override
    public Void onMessage() {
        if (this.driveTrain == null) {
            return null;
        }
        this.driveTrain.resetSmoothen();
        return null;
    }
}
