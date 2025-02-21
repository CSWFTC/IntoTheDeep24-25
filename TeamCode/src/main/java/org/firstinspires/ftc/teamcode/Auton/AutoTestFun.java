package org.firstinspires.ftc.teamcode.Auton;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
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
    private ClawAction Roar;
    private ViperAction Tiger;
    private newBeak Paw;
    private BucketAction Fur;

    public void runOpMode(){
        Roar = new ClawAction(hardwareMap);
        Tiger = new ViperAction(hardwareMap);
        Paw = new newBeak(hardwareMap);
        Fur = new BucketAction(hardwareMap);

        Roar.CloseGrip();
        Paw.autonStartPos();

        waitForStart();

        telemetry.addData("okay", "so code needs to push 7");
        telemetry.update();

        if(PARAMS.easy){
            forward();
        }
        else{
            Grabbing1();
            Grabbing2();
            Grabbing3();
        }
    }

    public void Grabbing1(){

        Actions.runBlocking(new SequentialAction(
                Tiger.perfBeforeDropOff(), // In-place action
                Paw.autonReachOB()          // In-place action (grab the sample)
        ));
    }

    public void Grabbing2(){

        Actions.runBlocking(new SequentialAction(
                Paw.grabAndDrop(),          // In-place action (grab and drop)
                Tiger.clawHumanGrab()       // In-place action (grab from human)
        ));
    }

    public void Grabbing3(){

        Actions.runBlocking(new SequentialAction(
                Tiger.clawHumanGrab(),      // In-place action (grab from human)
                Fur.autonBucketDown()       // In-place action (lower the bucket)
        ));
    }

    private void forward(){

        Actions.runBlocking(new SequentialAction(
                Tiger.perfBeforeDropOff(),
                Fur.autonBucketDown()
        ));
    }
}
