package org.firstinspires.ftc.teamcode.Auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Helper.Beak.newBeak;
import org.firstinspires.ftc.teamcode.Helper.ViperSlide.BucketAction;
import org.firstinspires.ftc.teamcode.Helper.ViperSlide.ClawAction;
import org.firstinspires.ftc.teamcode.Helper.ViperSlide.ViperAction;

@Autonomous(name = "AutoTestFun", group = "Test")
public class AutoTestFun extends LinearOpMode {

    private ClawAction Claw;
    private ViperAction Viper;
    private newBeak Beak;
    private BucketAction Bucket;

    public void runOpMode() {
        Claw = new ClawAction(hardwareMap);
        Viper = new ViperAction(hardwareMap);
        Beak = new newBeak(hardwareMap);
        Bucket = new BucketAction(hardwareMap);

        waitForStart();

        telemetry.addLine("Starting Test Sequence");
        telemetry.update();

        // Pick up sample
        Claw.grabFromHuman();
        sleep(500);
        Beak.autonReachOB();
        sleep(500);

        // Move sample down
        Viper.perfBeforeDropOff();
        sleep(500);
        Bucket.autonBucketDown();
        sleep(500);

        // Drop off at human player zone
        Beak.autonDropToHuman();
        sleep(500);
        Claw.placeOnSub();
        sleep(500);

        telemetry.addLine("Test Sequence Complete");
        telemetry.update();
    }
}
