package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.Helper.Beak.newBeak;
import org.firstinspires.ftc.teamcode.Helper.DrivetrainV2;
import org.firstinspires.ftc.teamcode.Helper.GamePad;

public class Gamepad1Control {
    private DrivetrainV2 drvTrain;
    private newBeak beakAction;
    private boolean setReversed = false;
    private double speedMultiplier = 1;

    public Gamepad1Control(HardwareMap hardwareMap) {
        drvTrain = new DrivetrainV2(hardwareMap);
        beakAction = new newBeak(hardwareMap);
    }

    public void update(GamePad gp, float leftStickX, float rightStickX, float leftStickY, float rightTrigger, float leftTrigger) {
        switch (gp.WaitForGamepadInput(30)) {
            case DPAD_DOWN: beakAction.DecreaseElbow(); break;
            case DPAD_UP: beakAction.IncreaseElbow(); break;
            case DPAD_LEFT: beakAction.SuplexSample(); break;
            case DPAD_RIGHT: beakAction.PickUpElbow(); break;
            case BUTTON_R_BUMPER: beakAction.ToggleBeak(); break;
            case BUTTON_L_BUMPER: beakAction.toggleElbowSuplex(); break;
            case LEFT_STICK_BUTTON_ON: speedMultiplier = (speedMultiplier < 0.5) ? 1 : 0.25; break;
            case BUTTON_X: speedMultiplier = 0.75; break;
            case BUTTON_B: speedMultiplier = 0.5; break;
            case BUTTON_A: speedMultiplier = 0.25; break;
            case BUTTON_Y: speedMultiplier = 1; break;
            case RIGHT_TRIGGER: beakAction.JoystickMoveSlide(rightTrigger); break;
            case LEFT_TRIGGER: beakAction.JoystickMoveSlide(-leftTrigger); break;
            case JOYSTICK:
                drvTrain.setDriveVectorFromJoystick(leftStickX * (float) speedMultiplier,
                        rightStickX * (float) speedMultiplier,
                        leftStickY * (float) speedMultiplier, setReversed);
                break;
        }
    }
}
