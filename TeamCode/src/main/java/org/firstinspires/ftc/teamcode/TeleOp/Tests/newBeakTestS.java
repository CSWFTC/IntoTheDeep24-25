package org.firstinspires.ftc.teamcode.TeleOp.Tests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helper.Beak.BeakAction;
import org.firstinspires.ftc.teamcode.Helper.Beak.newBeak;
import org.firstinspires.ftc.teamcode.Helper.GamePad;


    @Config
    @TeleOp(name = "Better Beak Test", group = "Tests")
    public class newBeakTestS extends LinearOpMode {


        public static class Params{
          public double versionNumber = 0.1;

        }
        public static BeakAction.Params PARAMS = new BeakAction.Params();
        private newBeak armActions;
        private GamePad gpInput;

        @Override
        public void runOpMode() {
            waitForStart();

            gpInput = new GamePad(gamepad1, false);
            armActions = new newBeak(hardwareMap);

            armActions.startElbPos();

            while (opModeIsActive()) {
                GamePad.GameplayInputType inputType = gpInput.WaitForGamepadInput(100);
                switch (inputType) {

                    case JOYSTICK:
                        armActions.JoystickMoveSlide(-gamepad1.right_stick_y);
                        break;
                    case BUTTON_A:
                        armActions.closedBeak();
                        break;
                    case BUTTON_B:
                        armActions.openBeak();
                        break;
                    case BUTTON_X:
                        //armActions.ToggleBeak();
                        armActions.suplexElbPos();
                        break;
                    case BUTTON_Y:
                        armActions.MinElbow();
                        break;



                }
            }

    }

    public void updateTelemtry(){


    }

}

