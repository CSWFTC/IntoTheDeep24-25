package org.firstinspires.ftc.teamcode.Helper;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class DrivetrainV3 extends DrivetrainV2 {

        private long joystickStartTime = 0;
        private double currentPower = 0;
        private double maxPower = 1.0;
        private double accelerationRate = 0.001;

        public DrivetrainV3(@NonNull com.qualcomm.robotcore.hardware.HardwareMap hdwMap) {
            super(hdwMap);
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


