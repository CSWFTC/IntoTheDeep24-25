package org.firstinspires.ftc.teamcode.TeleOp;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helper.DeferredActions;
import org.firstinspires.ftc.teamcode.Helper.DeferredActions.DeferredActionType;
import org.firstinspires.ftc.teamcode.Helper.DrivetrainV2;
import org.firstinspires.ftc.teamcode.Helper.GamePad;

import java.util.List;
import java.util.Locale;

@Config
@TeleOp(name = "Rumble Test", group = "Test")
public class RumbleTest extends LinearOpMode {


    private static final String version = "1.0";
    private boolean setReversed = false;
    // private ClawMoves yclaw;
    private boolean frontLeft, backLeft, frontRight, backRight = false;
    @Override
    public void runOpMode() {
        // Load Introduction and Wait for Start
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);
        telemetry.addLine("Motor Test");
        telemetry.addData("Version Number", version);
        telemetry.addLine();
        telemetry.addData(">", "Press Start to Launch");
        telemetry.addLine();
        telemetry.addData(">", "A = Back Right, B = Front Right, X = Back Left, Y = Front Left");
        telemetry.update();

        GamePad gpIn1 = new GamePad(gamepad1);
        GamePad gpIn2 = new GamePad(gamepad2);
        DrivetrainV2 drvTrain = new DrivetrainV2(hardwareMap);
        BumperTest bumpOne = new BumperTest();
        // TestServo serv1 = hardwareMap.servo.get(PARAMS.);

        waitForStart();
        if (isStopRequested()) return;


        telemetry.clear();

        double speedMultiplier = 1;
        double lastSpeed = 1;

        while (opModeIsActive()) {
            update_telemetry(gpIn1, gpIn2);


            GamePad.GameplayInputType inpType1 = gpIn1.WaitForGamepadInput(30);
            switch (inpType1) {
                case BUTTON_A:
                    gamepad1.rumble(0.9, 0, 200);
                    break;

                case BUTTON_X:
                    if (!gamepad1.isRumbling())  // Check for possible overlap of rumbles.
                        gamepad1.rumbleBlips(3);
                    break;
            }
        }

        // Deferred Actions
        ProcessDeferredActions();
    }

    // Deferred Actions
    public void ProcessDeferredActions(){
        List<DeferredActionType> action = DeferredActions.GetReadyActions();

        for(DeferredActionType actionType: action){
            switch(actionType){

                default:
                    telemetry.addLine("ERROR - Unsupported Deferred Action");
                    break;
            }


        }
    }

    private void update_telemetry(GamePad gpi1, GamePad gpi2) {
        telemetry.addLine("Gamepad #1");
        String inpTime1 = new java.text.SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS", Locale.US).format(gpi1.getTelemetry_InputLastTimestamp());
        telemetry.addLine().addData("GP1 Time", inpTime1);
        telemetry.addLine().addData("GP1 Cnt", gpi1.getTelemetry_InputCount());
        telemetry.addLine().addData("GP1 Input", gpi1.getTelemetry_InputLastType().toString());
        telemetry.addLine().addData("Front Left", frontLeft).addData("Front Right", frontRight).addData("Back Left", backLeft).addData("Back Right", backRight);

        telemetry.addLine();
        telemetry.addLine("Deferred Actions");
        String actTime = new java.text.SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS", Locale.US).format(DeferredActions.tlmLastActionTimestamp);
        telemetry.addLine().addData("Time", actTime);
        telemetry.addLine().addData("Action", DeferredActions.tlmLastAction.toString());

        telemetry.update();
    }
}