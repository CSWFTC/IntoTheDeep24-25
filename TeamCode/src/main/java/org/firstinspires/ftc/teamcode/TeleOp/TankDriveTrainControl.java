package org.firstinspires.ftc.teamcode.TeleOp;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helper.DeferredActions;
import org.firstinspires.ftc.teamcode.Helper.GamePad;
import org.firstinspires.ftc.teamcode.Helper.TankDriveTrain;

import java.util.List;
import java.util.Locale;

    @Config
    @TeleOp(name = "Tank Drive Control", group = "Competition")
    public class TankDriveTrainControl extends LinearOpMode {

        private static final String version = "1.0";
        private boolean setReversed = false;
        private TankDriveTrain tankerdrive;


        @Override
        public void runOpMode() {
            telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);
            telemetry.addLine("Tank Drive Control");
            telemetry.addData("Version Number", version);
            telemetry.addLine();
            telemetry.addData(">", "Press Start to Launch");
            telemetry.update();

            tankerdrive = new TankDriveTrain(hardwareMap);
            GamePad gpIn1 = new GamePad(gamepad1);
            GamePad gpIn2 = new GamePad(gamepad2);

            waitForStart();
            if (isStopRequested()) return;

            telemetry.clear();
            double speedMultiplier = 1.0;
            double lastSpeed = 1.0;

            while (opModeIsActive()) {
                updateTelemetry(gpIn1, gpIn2);

                GamePad.GameplayInputType inpType1 = gpIn1.WaitForGamepadInput(30);
                switch (inpType1) {

                    case JOYSTICK:
                        tankerdrive.setDriveFromJoystick(
                                gamepad1.left_stick_y * (float) speedMultiplier,
                                gamepad1.right_stick_x * (float) speedMultiplier

                        );
                        break;


                    case BUTTON_A:
                        tankerdrive.setDriveFromJoystick(0,0);

                }



                ProcessDeferredActions();
            }
        }


        public void ProcessDeferredActions() {
            List<DeferredActions.DeferredActionType> action = DeferredActions.GetReadyActions();

            for (DeferredActions.DeferredActionType actionType : action) {
                switch (actionType) {
                    default:
                        telemetry.addLine("ERROR - Unsupported Deferred Action");
                        break;
                }
            }
        }

        private void updateTelemetry(GamePad gpi1, GamePad gpi2) {
            telemetry.addLine("Gamepad #1");
            String inpTime1 = new java.text.SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS", Locale.US).format(gpi1.getTelemetry_InputLastTimestamp());
            telemetry.addLine().addData("GP1 Time", inpTime1);
            telemetry.addLine().addData("GP1 Cnt", gpi1.getTelemetry_InputCount());
            telemetry.addLine().addData("GP1 Input", gpi1.getTelemetry_InputLastType().toString());
            telemetry.addLine().addData("L Joy  Y", "%6.3f", gamepad1.left_stick_y);
            telemetry.addLine().addData("R Joy  X", "%6.3f", gamepad1.right_stick_x);

            telemetry.update();
        }
    }


