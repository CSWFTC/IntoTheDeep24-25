package org.firstinspires.ftc.teamcode.TeleOp;



import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helper.Beak.BeakAction;
import org.firstinspires.ftc.teamcode.Helper.DeferredActions;
import org.firstinspires.ftc.teamcode.Helper.DeferredActions.DeferredActionType;
import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.DependencyInjector;
import org.firstinspires.ftc.teamcode.Helper.DrivetrainV2;
import org.firstinspires.ftc.teamcode.Helper.GamePad;
import org.firstinspires.ftc.teamcode.Helper.Intake.IntakeAction;
import org.firstinspires.ftc.teamcode.Helper.Telemetry.Pinch;
import org.firstinspires.ftc.teamcode.Helper.ViperSlide;
import org.firstinspires.ftc.teamcode.Helper.ViperSlideActions.ViperAction;
import org.firstinspires.ftc.teamcode.Helper.ViperSlideActions.ViperSlideHelper;


import java.util.List;
import java.util.Locale;

@Config
@TeleOp(name = "Driver Control", group = "Competition!!")
public class DriveControl extends LinearOpMode {

    private BeakAction beakAction;
    private ViperSlideHelper viperSlideHelper;
    private ViperAction viperAction;


    private static final String version = "1.1";
    private boolean setReversed = false;
   // private ClawMoves yclaw;

    @Override
    public void runOpMode() {
        waitForStart();

        int initRes = this.initialize();

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
        BumperTest bumpOne = new BumperTest();
        ViperSlide vip = new ViperSlide(hardwareMap);

        telemetry.clear();

        double speedMultiplier = 1;
        double lastSpeed = 1;

        boolean viperOverride = false;

        this.beakAction.DrivePosition();

        while (opModeIsActive()) {
            update_telemetry(gpIn1, gpIn2);

            GamePad.GameplayInputType inpType1 = gpIn1.WaitForGamepadInput(30);
            switch (inpType1) {
                case LEFT_STICK_BUTTON_ON:
                    if (speedMultiplier != 1) {
                        lastSpeed = speedMultiplier;
                        speedMultiplier = 1;
                    }
                    break;

                case LEFT_STICK_BUTTON_OFF:
                    if (lastSpeed != 1) {
                        speedMultiplier = lastSpeed;
                        lastSpeed = 1;
                    }
                    break;

               /* case DPAD_UP:
                    this.intakeAction.TEST_derotate();
                    break;

                case DPAD_DOWN:
                    telemetry.addLine("TESTING pickup");
                    this.intakeAction.TEST_deactivate_pinch();
                    break;*/

                case BUTTON_A:
//                    Set<String> targets = new HashSet<>();
//                    targets.add("haptic");
//                    EventBus.getInstance().emit(targets, gpIn1);
                    speedMultiplier = 0.25;
                    break;

                case BUTTON_X:
                    speedMultiplier = 0.75;
                    break;

                case BUTTON_B:
                    speedMultiplier = 0.5;
                    break;

                case BUTTON_Y:
                    speedMultiplier = 1;
                    break;

                case BUTTON_BACK:
                    setReversed = !setReversed;
                    break;

                case JOYSTICK:
//                    gpIn1.HapticsController.runShortHaptic();
                    drvTrain.setDriveVectorFromJoystick(gamepad1.left_stick_x * (float) speedMultiplier,
                            gamepad1.right_stick_x * (float) speedMultiplier,
                            gamepad1.left_stick_y * (float) speedMultiplier, setReversed);
                    break;


            }
            GamePad.GameplayInputType inpType2 = gpIn2.WaitForGamepadInput(30);
            switch (inpType2) {
                case DPAD_LEFT:
                    this.beakAction.CloseBeak();
                    break;
                case DPAD_RIGHT:
                    this.beakAction.OpenBeak();
                    break;
                case DPAD_DOWN:
                    this.viperSlideHelper.moveToPosition((this.viperSlideHelper.getCurrentPosition()-5)*-1, 0.8);
                    break;
                case BUTTON_X:
                    this.beakAction.PrepForPickup();
                    break;
                case BUTTON_B:
                    this.beakAction.PickupReach();
                    this.beakAction.OpenBeak();
                    break;
                case BUTTON_A:
                    this.beakAction.CloseBeak();
                    this.viperAction.TEST_activate_bucket();
                    DeferredActions.CreateDeferredAction(1000, DeferredActionType.SUPLEX_BEAK);
                    DeferredActions.CreateDeferredAction(2000, DeferredActionType.BEAK_OPEN);
                    break;
                case BUTTON_Y:
                    this.viperSlideHelper.resetEncoders();
                    this.viperSlideHelper.moveToPosition(3100, 0.8);
                    telemetry.addLine("WENT UP SLIDE");
                    DeferredActions.CreateDeferredAction(2500, DeferredActions.DeferredActionType.ROTATE_BUCKET);
                    DeferredActions.CreateDeferredAction(5000, DeferredActions.DeferredActionType.RESET_SLIDER);
                    DeferredActions.CreateDeferredAction(4000, DeferredActions.DeferredActionType.RESET_BUCKET);

                    break;
                case LEFT_TRIGGER:
                    this.beakAction.DrivePosition();
                    break;
                default:
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
                case ROTATE_BUCKET:
                    this.viperAction.TEST_rotate_bucket();
                    break;
                case RESET_BUCKET:
                    this.viperAction.TEST_reset_bucket();
                    break;
                case RESET_SLIDER:
                    telemetry.addData("VIPERSLIDE b4 RESET POS: ", this.viperSlideHelper.getCurrentPosition());
                    telemetry.addData("VIPERSLIDE POS: ", this.viperSlideHelper.getCurrentPosition());
                    this.viperSlideHelper.moveToPosition((this.viperSlideHelper.getCurrentPosition()-5)*-1, 0.8);
                    telemetry.addLine("Reset SLIDE");
                    telemetry.addData("VIPERSLIDE FINAL POS: ", this.viperSlideHelper.getCurrentPosition());
                    break;
                case BEAK_OPEN:
                    this.beakAction.OpenBeak();
                    break;
                case BEAK_CLOSE:
                    this.beakAction.CloseBeak();
                    break;
                case SUPLEX_BEAK:
                    this.beakAction.SuplexSample();
                    break;
                default:
                    telemetry.addLine("ERROR - Unsupported Deferred Action");
                    break;
            }



        }
    }

    private int initialize() {
        try {
            // inject the dependencies
            DependencyInjector.register("hdwMap", this.hardwareMap);
            DependencyInjector.register("bucketServoName", "bucketServo");
            DependencyInjector.register("telemetry", this.telemetry);

            try {
                this.viperAction = new ViperAction();
            } catch(Exception e) {
                telemetry.clear();
                telemetry.addLine("AN ERROR OCCURED: "+e.toString());
                telemetry.update();
                throw new Exception(e);
            }

            this.beakAction = new BeakAction();
            this.viperSlideHelper = new ViperSlideHelper();

            // clean up dependencies
            DependencyInjector.unregister("hdwMap");
            DependencyInjector.unregister("telemetry");
            DependencyInjector.unregister("bucketServoName");
        }
        catch(Exception e) {
            return 1;
        }
        return 0;
    }

    private void update_telemetry(GamePad gpi1, GamePad gpi2) {
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
