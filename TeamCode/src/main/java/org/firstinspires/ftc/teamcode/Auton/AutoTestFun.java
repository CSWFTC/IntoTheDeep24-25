package org.firstinspires.ftc.teamcode.Auton;

import com.acmerobotics.roadrunner.SequentialAction;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.acmerobotics.roadrunner.ftc.Actions;

import org.firstinspires.ftc.teamcode.Helper.Beak.newBeak;
import org.firstinspires.ftc.teamcode.Helper.ViperSlide.BucketAction;
import org.firstinspires.ftc.teamcode.Helper.ViperSlide.ClawAction;
import org.firstinspires.ftc.teamcode.Helper.ViperSlide.ViperAction;

@Autonomous(name = "AutoTestFun", group = "RoadRunner")
public class AutoTestFun extends LinearOpMode {

    public static class Params {
        public boolean easy = false;
    }

    public static Params PARAMS = new Params();
    private ClawAction Claw;
    private ViperAction Viper;
    private newBeak Beak;
    private BucketAction Bucket;
    // paw --> beak
    // roar --> claw

    public void runOpMode(){
        Claw = new ClawAction(hardwareMap);
        Viper = new ViperAction(hardwareMap);
        Beak = new newBeak(hardwareMap);
        Bucket = new BucketAction(hardwareMap);

        Claw.CloseGrip();
        Beak.autonStartPos();

        waitForStart();

        telemetry.addData("okay", "so code needs to push 7");
        telemetry.update();

        if(PARAMS.easy){
            forward();
        }
        else{
            Grabbing1(); //
            Grabbing2();
            Grabbing3();
        }
    }

    public void Grabbing1(){

        Actions.runBlocking(new SequentialAction(
                Viper.perfBeforeDropOff(),
                Beak.autonReachOB()
        ));
    }

    public void Grabbing2(){

        Actions.runBlocking(new SequentialAction(
                Beak.grabAndDrop(),          // In-place action (grab and drop)
                Viper.clawHumanGrab()       // In-place action (grab from human)
        ));
    }

    public void Grabbing3(){

        Actions.runBlocking(new SequentialAction(
                Viper.clawHumanGrab(),
                Bucket.autonBucketDown()
        ));
    }

    private void forward(){

        Actions.runBlocking(new SequentialAction(
                Viper.perfBeforeDropOff(),
                Bucket.autonBucketDown()
        ));
    }
}
