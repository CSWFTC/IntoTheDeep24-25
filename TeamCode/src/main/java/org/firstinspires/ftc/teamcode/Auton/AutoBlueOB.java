package org.firstinspires.ftc.teamcode.Auton;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.acmerobotics.roadrunner.ftc.Actions;

import org.firstinspires.ftc.teamcode.Helper.Beak.newBeak;
import org.firstinspires.ftc.teamcode.Helper.ViperSlide.BucketAction;
import org.firstinspires.ftc.teamcode.Helper.ViperSlide.ClawAction;
import org.firstinspires.ftc.teamcode.Helper.ViperSlide.ViperAction;
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;

@Autonomous(name = "AutoBlueOB", group = "RoadRunner")
public class AutoBlueOB extends LinearOpMode {

    public static class Params {
        public boolean easy = false;
        public double y = 38;
    }

    public static Params PARAMS = new Params();
    private MecanumDrive drive;
    private ClawAction Roar;
    private ViperAction Tiger;
    private newBeak Paw;
    private BucketAction Fur;
    private double x = 0;

    public void runOpMode(){

        drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

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
            toLine();
                moveBack();
                markOne();
                humanPlayer();
                Grabbing2();
                //Grabbing3();
                HumanToOB();
                GoBack();
                Reverse();
                backToLine();
                //ToHuman();
            //forward();
            //toParkLast();
            updateTelemetry(drive.pose.position);
        }
    }

    public void toLine(){
        //beginning position: ends at the sub
        Action extraMove = drive.actionBuilder(drive.pose)
                .setReversed(true)
                .lineToX(-29.5)
                .build();
        Actions.runBlocking(new SequentialAction( (new ParallelAction(Tiger.perfBeforeDropOff(), extraMove)), Tiger.perfClawDropOnSub(), Roar.placeOnSub()));

    }

    public void moveBack () {
        //move back a little bit
        Action moveBack = drive.actionBuilder(drive.pose)
                .setReversed(false)
                .lineToX(-20)
                .build();
        Actions.runBlocking(new ParallelAction(moveBack, Tiger.clawHumanGrab(), Fur.autonHuman()));
    }

    public void markOne(){
        //move to mark
        Action lineM1 = drive.actionBuilder(drive.pose)
                .setReversed(false)
                .splineTo(new Vector2d(-21, 37), Math.toRadians(180))
                .build();
        Actions.runBlocking(new SequentialAction(lineM1, Paw.autonReachOB()));
    }

    public void humanPlayer(){
        //drop off in human player zone
        Action Player = drive.actionBuilder(drive.pose)
                .setReversed(true)
                .lineToX(-1)
                .build();
        Actions.runBlocking(new ParallelAction(Player,Paw.autonDropToHuman()));
    }

    public void Grabbing2(){
        //Grab from 2 mark
        Action Grab = drive.actionBuilder(drive.pose)
                .setReversed(false)
                //.lineToX(-12)
                .strafeTo(new Vector2d(-13,48))
                .build();
        Actions.runBlocking(new SequentialAction(Grab,Paw.grabAndDrop()));

    }

    public void Grabbing3(){
        //Grab from 3 mark
        Action Grab = drive.actionBuilder(drive.pose)
                .setReversed(false)
                .turnTo(-2)
                .build();
        Actions.runBlocking(new SequentialAction(Grab,Paw.grabAndDrop()));

    }

    public void HumanToOB(){
        //pick up hooked sample from wall
        Action PlayerGrab = drive.actionBuilder(drive.pose)
                .setReversed(true)
                //.turnTo(0)
                .lineToX(-3)
                .build();
        Actions.runBlocking(new SequentialAction(PlayerGrab, Paw.autonDropToHuman(), Roar.grabFromHuman(), new ParallelAction(Tiger.perfBeforeDropOff(), Fur.autonBucketDown())));
    }

    public void GoBack(){
        //give time for human player to pick up sample
        //move out of the zone
        Action back = drive.actionBuilder(drive.pose)
                .setReversed(false)
                .lineToX(-14)
                .build();
        Actions.runBlocking(back);
    }

    public void Reverse(){
        //get ready to go to sub
        Action turnAgain = drive.actionBuilder(drive.pose)
                .setReversed(true)
                .turnTo(260)
                .build();
        Actions.runBlocking(turnAgain);
    }

    public void backToLine(){
        //drop sample on sub
        Action backAgain = drive.actionBuilder(drive.pose)
                .setReversed(true)
                .splineTo(new Vector2d(-29, -5), Math.toRadians(180))
                .build();
        Actions.runBlocking(new SequentialAction(backAgain, Tiger.perfClawDropOnSub(), Roar.placeOnSub()));
    }

    public void ToHuman(){
        Action humans = drive.actionBuilder(drive.pose)
                .setReversed(false)
                .splineTo(new Vector2d(-1, 37), Math.toRadians(-180))
                .build();
        Actions.runBlocking(new SequentialAction(humans, Roar.grabFromHuman(),Tiger.perfBeforeDropOff()));
    }

    private void toParkLast(){
        //final park position
        Action moveBasket= drive.actionBuilder(drive.pose)
                .setReversed(true)
                // .splineTo(new Vector2d(-12, -48), Math.toRadians(-20))
                .strafeTo(new Vector2d(-1.5,48))
                .build();
        Actions.runBlocking(new SequentialAction(moveBasket, Tiger.clawHumanGrab()));
    }

    private void forward(){
        //move all the way back for final
        Action moveOut = drive.actionBuilder(drive.pose)
                .setReversed(true)
                .lineToX(-4)
                .build();
        Actions.runBlocking(moveOut);
    }

    private void updateTelemetry(Vector2d pos) {
        telemetry.addLine("RoadRunner Auto Drive BLUE");
        telemetry.addData("Current x Position", pos.x );
        telemetry.addData("Current y Postion", pos.y);
        telemetry.update();

    }



}
