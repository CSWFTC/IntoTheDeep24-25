package org.firstinspires.ftc.teamcode.TeleOp.Tests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Helper.Beak.newBeak;
import org.firstinspires.ftc.teamcode.Helper.GamePad;


    @Config
    @TeleOp(name = "Better Beak Test", group = "Tests")
    public class newBeakTestS extends LinearOpMode {

        private newBeak armActions;
        private GamePad gpInput;

        @Override
        public void runOpMode() {
            waitForStart();

            gpInput = new GamePad(gamepad1, false);
            armActions = new newBeak(hardwareMap);

            while (opModeIsActive()) {
                GamePad.GameplayInputType inputType = gpInput.WaitForGamepadInput(100);
                switch (inputType) {

                    case JOYSTICK:
                        armActions.JoystickMoveSlide(-gamepad1.right_stick_y);
                        break;


                }
            }

    }
}

