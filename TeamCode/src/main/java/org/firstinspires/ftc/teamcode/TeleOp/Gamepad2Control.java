package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.Helper.GamePad;
import org.firstinspires.ftc.teamcode.Helper.ViperSlide.BucketAction;
import org.firstinspires.ftc.teamcode.Helper.ViperSlide.ClawAction;
import org.firstinspires.ftc.teamcode.Helper.ViperSlide.ViperAction;
import org.firstinspires.ftc.teamcode.Helper.HangAction;

public class Gamepad2Control {
    private ViperAction viperAction;
    private BucketAction bucketAction;
    private ClawAction clawAction;
    private HangAction hangAction;

    public Gamepad2Control(HardwareMap hardwareMap) {
        viperAction = new ViperAction(hardwareMap);
        bucketAction = new BucketAction(hardwareMap);
        clawAction = new ClawAction(hardwareMap);
        hangAction = new HangAction(hardwareMap);
    }

    public void update(GamePad gp, float leftStickY, float rightStickY, float leftTrigger, float rightTrigger) {
        switch (gp.WaitForGamepadInput(30)) {
            case BUTTON_L_BUMPER: bucketAction.ToggleBucket(); break;
            case BUTTON_R_BUMPER: clawAction.OpenGrip(); break;
            case LEFT_TRIGGER: viperAction.moveWithPower(-leftTrigger); break;
            case RIGHT_TRIGGER: viperAction.moveWithPower(rightTrigger); break;
            case DPAD_UP: viperAction.perfMoveForSub(); break;
            case DPAD_DOWN: viperAction.moveForSub(); break;
            case BUTTON_Y: viperAction.moveToHighBasket(); break;
            case BUTTON_A: viperAction.moveToLowBasket(); break;
            case BUTTON_B: clawAction.OpenGrip(); break;
            case BUTTON_X: clawAction.CloseGrip(); break;
            case RIGHT_STICK_BUTTON_ON: viperAction.resetEncoders(); break;
            case JOYSTICK:
                hangAction.moveMotors(-leftStickY);
                hangAction.moveHang2(-rightStickY);
                hangAction.moveHangDown(rightStickY);
                break;
        }
    }
}
