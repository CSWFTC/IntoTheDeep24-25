package org.firstinspires.ftc.teamcode.Tests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Helper.DriveTrain.NewDriveTrain;
import org.firstinspires.ftc.teamcode.Helper.GamePad;

@Config
@TeleOp(name = "SpeedTest V1", group = "Test")
public class SpeedTestV1 extends LinearOpMode {
    @Override
    public void runOpMode() {
        GamePad gpIn1 = new GamePad(gamepad1, false);
        NewDriveTrain driveTrain = new NewDriveTrain(hardwareMap);

        double speedMultiplier = 1;

        waitForStart();
        if (isStopRequested()) return;

        while(opModeIsActive()) {
            GamePad.GameplayInputType inpType1 = gpIn1.WaitForGamepadInput(30);
            switch (inpType1) {
                case BUTTON_A:
                    telemetry.addLine("Applied Smoothening");
                    driveTrain.applySmoothen();
                case BUTTON_B:
                    telemetry.addLine("Reset Smoothening");
                    driveTrain.resetSmoothen();
                case JOYSTICK:
                    driveTrain.setDriveVectorFromJoystick(gamepad1.left_stick_x * (float) speedMultiplier,
                            gamepad1.right_stick_x * (float) speedMultiplier,
                            gamepad1.left_stick_y * (float) speedMultiplier, false);
                    break;
            }
        }
        telemetry.addLine("Current MAX POWER: "+driveTrain.getMaxPower());
        telemetry.addLine("Current ACCELERATION RATE: "+driveTrain.getAccelerationRate());

        telemetry.update();
    }
}
