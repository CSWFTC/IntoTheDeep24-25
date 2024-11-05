package org.firstinspires.ftc.teamcode.Helper.DriveTrain;

import androidx.annotation.NonNull;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.DependencyInjector;
import org.firstinspires.ftc.teamcode.Helper.DrivetrainV2;
import org.firstinspires.ftc.teamcode.Helper.EventBus.EventBus;

public class NewDriveTrain extends DrivetrainV2 {
    private long joystickStartTime = 0;
    private double currentPower = 0;
    private double maxPower = 1.0;
    private double accelerationRate = 0.001;

    public double getMaxPower() {
        return this.maxPower;
    }

    public double getAccelerationRate() {
        return this.accelerationRate;
    }

    public NewDriveTrain(@NonNull HardwareMap hdwMap) {
        super(hdwMap);

        EventBus bus = EventBus.getInstance();
        DependencyInjector.register("drive_train", this);

        DriveTrainSubscriberApplySmoothen smoothen = new DriveTrainSubscriberApplySmoothen();
        DriveTrainSubscriberResetSmoothen resetSmoothen = new DriveTrainSubscriberResetSmoothen();

        bus.subscribe("apply_smoothen", smoothen.getClass());
        bus.subscribe("reset_smoothen", resetSmoothen.getClass());

        DependencyInjector.unregister("drive_train");
    }

    public void applySmoothen() {
        this.maxPower = 0.6;
        this.accelerationRate = 0.0005;
    }

    public void resetSmoothen() {
        this.maxPower = 1;
        this.accelerationRate = 0.001;
    }

    @Override
    public void setDriveVectorFromJoystick(float stickLeftX, float stickRightX, float stickLeftY, boolean setReversed) {
        if (brakingOn) return;

        // moves from neutral, start tracking time
        if (Math.abs(stickLeftX) > 0 || Math.abs(stickRightX) > 0 || Math.abs(stickLeftY) > 0) {
            if (joystickStartTime == 0) {
                joystickStartTime = System.currentTimeMillis(); // Start time
            }
        } else {
            // Reset time and power if joystick is back to neutral
            joystickStartTime = 0;
            currentPower = 0;
        }

        // Increase power, doesn't exceed max value
        if (joystickStartTime > 0) {
            long elapsedTime = System.currentTimeMillis() - joystickStartTime;
            currentPower = Math.min(maxPower, elapsedTime * accelerationRate);
        }


        double rotate = stickRightX * currentPower;
        double forward = stickLeftY * PARAMS.joystickYInputAdjustment * currentPower;
        double strafe = stickLeftX * PARAMS.strafingAdjustment * currentPower;

        if (setReversed) {
            forward *= -1;
            strafe *= -1;
        }

        setDriveVector(forward, strafe, rotate);
    }
}


