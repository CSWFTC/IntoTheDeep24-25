package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helper.Beak.BeakAction;
import org.firstinspires.ftc.teamcode.Helper.Beak.newBeak;
import org.firstinspires.ftc.teamcode.Helper.HangAction;
import org.firstinspires.ftc.teamcode.Helper.ViperSlide.BucketAction;
import org.firstinspires.ftc.teamcode.Helper.ViperSlide.ClawAction;
import org.firstinspires.ftc.teamcode.Helper.DeferredActions;
import org.firstinspires.ftc.teamcode.Helper.DeferredActions.DeferredActionType;
import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.DependencyInjector;
import org.firstinspires.ftc.teamcode.Helper.DrivetrainV2;
import org.firstinspires.ftc.teamcode.Helper.GamePad;
import org.firstinspires.ftc.teamcode.Helper.ViperSlide.ViperAction;



import java.util.List;
import java.util.Locale;

@Config
@TeleOp(name = "Driver Control", group = "Competition!!")
public class DriveControl extends LinearOpMode {
   // private BeakAction beakAction;

  //  private BeakAction beak;
    private ViperAction viperAction;
    private BucketAction bucketAction;
    private ClawAction clawAction;
    private HangAction hangAction;
    private newBeak beakAction;

    private static final String version = "1.2";
    private boolean setReversed = false;


    @Override
    public void runOpMode() {
        int initRes = initialize();

        waitForStart();

        if (isStopRequested() || (initRes == 1)) {
            return;
            
        }

        // Load Introduction and Wait for Start
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);
        telemetry.addLine("Driver Control");
        telemetry.addData("Version Number", version);
        telemetry.addLine();
        telemetry.addData(">", "Press Start to Launch");
        telemetry.update();

        GamePad gpIn1 = new GamePad(gamepad1, false);
        GamePad gpIn2 = new GamePad(gamepad2);
        DrivetrainV2 drvTrain = new DrivetrainV2(hardwareMap);

        telemetry.clear();

        double speedMultiplier = 1;
        beakAction.suplexElbPos();

      //  beakAction.DrivePosition();
      //  bucketAction.StartPosition();

        while (opModeIsActive()) {
            update_telemetry(gpIn1, gpIn2);

            GamePad.GameplayInputType inpType1 = gpIn1.WaitForGamepadInput(30);
            switch (inpType1) {
                case DPAD_DOWN:
                    break;
                case DPAD_LEFT:
                    beakAction.suplexElbPos();
                    break;
                case DPAD_RIGHT:
                    beakAction.PickUpElbow();
                    break;
                case LEFT_STICK_BUTTON_ON:
                    if (speedMultiplier < 0.5) {
                        speedMultiplier = 1;
                    } else {
                        speedMultiplier = 0.25;
                    }
                    break;
                case BUTTON_X:
                    speedMultiplier = 0.75;
                    break;
                case BUTTON_B:
                    speedMultiplier = 0.5;
                    break;
                case BUTTON_A:
                    speedMultiplier = 0.25;
                    break;
                case BUTTON_Y:
                    speedMultiplier = 1;
                    break;
                case BUTTON_R_BUMPER:
                    beakAction.ToggleBeak();
                    break;
                case RIGHT_TRIGGER:
                    beakAction.JoystickMoveSlide(gamepad1.right_trigger);
                    break;
                case LEFT_TRIGGER:
                    beakAction.JoystickMoveSlide(-gamepad1.left_trigger);
                break;
                case JOYSTICK:
                    drvTrain.setDriveVectorFromJoystick(gamepad1.left_stick_x * (float) speedMultiplier,
                            gamepad1.right_stick_x * (float) speedMultiplier,
                            gamepad1.left_stick_y * (float) speedMultiplier, setReversed);
                    break;
            }

            GamePad.GameplayInputType inpType2 = gpIn2.WaitForGamepadInput(30);
            switch (inpType2) {
                case BUTTON_L_BUMPER:
                    bucketAction.ToggleBucket();
                    break;
                case BUTTON_R_BUMPER:
                   // clawAction.ToggleGrip();
                    beakAction.ToggleBeak();
                    break;
                case LEFT_TRIGGER:

                    break;
                case RIGHT_TRIGGER:

                    break;
                case DPAD_UP:
                    viperAction.perfMoveForSub();
                    break;
                case DPAD_DOWN:
                    viperAction.moveForSub();
                    break;
                case BUTTON_Y:
                    viperAction.moveToHighBasket();
                    break;
                case BUTTON_A:
                    viperAction.moveToLowBasket();
                    break;
                case RIGHT_STICK_BUTTON_ON:
                    viperAction.resetEncoders();
                    break;
                case JOYSTICK:
                    hangAction.moveMotors(-gamepad2.left_stick_y);
                    break;
            }

            // Deferred Actions
           ProcessDeferredActions();
        }
    }

    // Deferred Actions
    public void ProcessDeferredActions(){
        List<DeferredActionType> action = DeferredActions.GetReadyActions();

        for(DeferredActionType actionType: action){
            switch(actionType){
                case BEAK_OPEN:
                    beakAction.openBeak();
                    break;
                case BEAK_CLOSE:
                    beakAction.closedBeak();
                    break;
                case SUPLEX_BEAK:
                    beakAction.SuplexSample();
                    break;
                case BEAK_DRIVE_SAFE:
                    beakAction.suplexElbPos();
                    break;
                default:
                    telemetry.addLine("ERROR - Unsupported Deferred Action");
                    break;
            }
        }
    }

    private int initialize() {
        try {
            beakAction = new newBeak(hardwareMap);
            viperAction = new ViperAction(hardwareMap);
            hangAction = new HangAction (hardwareMap);
            bucketAction = new BucketAction(hardwareMap);
            clawAction = new ClawAction(hardwareMap);
        }
        catch(Exception e) {
            telemetry.clear();
            telemetry.addLine("AN ERROR OCCURED: "+e.toString());
            telemetry.update();
            return 1;
        }
        return 0;
    }

    private void update_telemetry(GamePad gpi1, GamePad gpi2) {
        telemetry.addLine().addData("Viper", viperAction.getCurrentPosition());

        telemetry.addLine("Gamepad #1");
        String inpTime1 = new java.text.SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS", Locale.US).format(gpi1.getTelemetry_InputLastTimestamp());
        telemetry.addLine().addData("GP1 Time", inpTime1);
        telemetry.addLine().addData("GP1 Cnt", gpi1.getTelemetry_InputCount());
        telemetry.addLine().addData("GP1 Input", gpi1.getTelemetry_InputLastType().toString());
        telemetry.addLine().addData("L Joy  X", "%6.3f", gamepad1.left_stick_x).addData("Y", "%6.3f", gamepad1.left_stick_y);
        telemetry.addLine().addData("R Joy  X", "%6.3f", gamepad1.right_stick_x).addData("Y", "%6.3f", gamepad1.right_stick_y);

        telemetry.addLine();
        telemetry.addLine("Gamepad #2");
        String inpTime2 = new java.text.SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS", Locale.US).format(gpi2.getTelemetry_InputLastTimestamp());
        telemetry.addLine().addData("GP 2 Time", inpTime2);
        telemetry.addLine().addData("GP2 Cnt", gpi2.getTelemetry_InputCount());
        telemetry.addLine().addData("GP2 Input", gpi2.getTelemetry_InputLastType().toString());
        telemetry.addLine().addData("L Joy  X", "%6.3f", gamepad2.left_stick_x).addData("Y", "%6.3f", gamepad2.left_stick_y);
        telemetry.addLine().addData("R Joy  X", "%6.3f", gamepad2.right_stick_x).addData("Y", "%6.3f", gamepad2.right_stick_y);

        telemetry.addLine();
        telemetry.addLine("Deferred Actions");
        String actTime = new java.text.SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS", Locale.US).format(DeferredActions.tlmLastActionTimestamp);
        telemetry.addLine().addData("Time", actTime);
        telemetry.addLine().addData("Action", DeferredActions.tlmLastAction.toString());

        telemetry.update();
    }
}

