package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helper.Beak.BeakAction;
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
    private BeakAction beakAction;
    private ViperAction viperAction;
    private BucketAction bucketAction;
    private ClawAction clawAction;

    private boolean isViperLocked = false;
    private static final String version = "1.1";
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

        this.beakAction.DrivePosition();

        while (opModeIsActive()) {
            update_telemetry(gpIn1, gpIn2);
            //update_telemetry(gpIn1, gpIn2);

            GamePad.GameplayInputType inpType1 = gpIn1.WaitForGamepadInput(30);
            switch (inpType1) {
                case DPAD_DOWN:

                    break;
                case DPAD_LEFT:

                    break;
                case DPAD_RIGHT:

                    break;
                case DPAD_UP:
                    this.beakAction.CloseBeak();
                    DeferredActions.CreateDeferredAction(1000, DeferredActionType.SUPLEX_BEAK);
                    DeferredActions.CreateDeferredAction(2000, DeferredActionType.BEAK_OPEN);
                    break;
                case LEFT_STICK_BUTTON_ON:
                    if (speedMultiplier < 0.5) {
                        speedMultiplier = 1;
                    } else {
                        speedMultiplier = 0.25;
                    }
                    break;
                case RIGHT_STICK_BUTTON_ON:
                    // EMERGENCY OVERRIDE DO NOT EVER USE THIS UNLESS NEEDED
                    this.isViperLocked = false;
                    break;
//                case DPAD_DOWN:
//                    if (!this.isViperLocked) {
//                        this.isViperLocked = true;
//                        this.viperSlideHelper.moveToPosition((this.viperSlideHelper.getCurrentPosition()-5)*-1, 0.8);
//                        DeferredActions.CreateDeferredAction(6500, DeferredActionType.UNLOCK_VIPER);
//                    }
//                    break;
                case BUTTON_X:
                    speedMultiplier = 0.75;
                    //this.beakAction.PrepForPickup();
                    //break;
                    break;
                case BUTTON_B:
                    //this.beakAction.PickupReach();
                    //this.beakAction.OpenBeak();
                    //break;
                    speedMultiplier = 0.25;
                    break;
                case BUTTON_A:
                    //this.beakAction.CloseBeak();
                   // DeferredActions.CreateDeferredAction(1000, DeferredActionType.SUPLEX_BEAK);
                    //DeferredActions.CreateDeferredAction(2000, DeferredActionType.BEAK_OPEN);
                    speedMultiplier = 0.5;
                    break;

                case BUTTON_Y:
                    speedMultiplier = 1;
                    break;
                case BUTTON_L_BUMPER:
                    this.beakAction.PrepForBucketDump();
//                    time++;
//                    if(time%2==0) {
//                        this.beakAction.CloseBeak();
//                    } else {
//                        this.beakAction.OpenBeak();
//                    }
                    break;
                case LEFT_TRIGGER:
                    this.beakAction.DrivePosition();
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
                    clawAction.ToggleGrip();
                    break;

                case LEFT_TRIGGER:
                    beakAction.PrepForBucketDump();  // Move Beak Clear of Bucket
                    viperAction.moveWithPower(-gamepad2.left_trigger);
                    break;

                case RIGHT_TRIGGER:
                    beakAction.PrepForBucketDump();  // Move Beak Clear of Bucket
                    viperAction.moveWithPower(gamepad2.right_trigger);
                    break;

                case BUTTON_X:
                    viperAction.moveToHighBasket();
                    break;

                case BUTTON_B:
                    viperAction.moveToLowBasket();

                case RIGHT_STICK_BUTTON_ON:
                    viperAction.resetEncoders();

                // TODO:  Add Code for Left Joystick to Drive Climb Motors
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
                case UNLOCK_VIPER:
                    this.isViperLocked = false;
                    break;
                case BUCKET_RISE_SMALL:
                    viperAction.moveToLowBasket();
                    break;
                case BUCKET_RISE_TALL:
                    viperAction.moveToHighBasket();
                    break;
                case ROTATE_BUCKET:
                    break;
                case RESET_BUCKET:
                    break;
                //case RESET_SLIDER:
                //    telemetry.addData("VIPERSLIDE b4 RESET POS: ", this.viperAction.getCurrentPosition());
                //    telemetry.addData("VIPERSLIDE POS: ", this.viperAction.getCurrentPosition());
                //    this.viperAction.moveToPosition((this.viperAction.getCurrentPosition()-5)*-1);
                //    telemetry.addLine("Reset SLIDE");
                //    telemetry.addData("VIPERSLIDE FINAL POS: ", this.viperAction.getCurrentPosition());
                //    break;
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
            DependencyInjector.register("hdwMap", hardwareMap);
            DependencyInjector.register("telemetry", this.telemetry);

            try {
                this.viperAction = new ViperAction(hardwareMap);
            } catch(Exception e) {
                telemetry.clear();
                telemetry.addLine("AN ERROR OCCURED: "+e.toString());
                telemetry.update();
                throw new Exception(e);
            }

            this.beakAction = new BeakAction(hardwareMap);
            this.viperAction = new ViperAction(hardwareMap);

            // clean up dependencies
            DependencyInjector.unregister("hdwMap");
            DependencyInjector.unregister("telemetry");
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
        telemetry.addData("Locked: ", this.isViperLocked);

        telemetry.update();
    }
}
