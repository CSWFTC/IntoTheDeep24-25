package org.firstinspires.ftc.teamcode.Auton;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helper.Beak.BeakAction;
import org.firstinspires.ftc.teamcode.Helper.Beak.newBeak;
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.Helper.ViperSlide.ViperAction;
import org.firstinspires.ftc.teamcode.Helper.ViperSlide.BucketAction;
import org.firstinspires.ftc.teamcode.Helper.ViperSlide.ClawAction;


@Autonomous(name = "Auto Blue Basket", group = "RoadRunner")
public class AutoBlueBasket extends LinearOpMode {

    public static class Params {
        public double versionNumber = 11.7;
        public int maxPV = 15900;
        public int minPV= 10;
        public double powerUp = 0.5;
        public double powerDown = -0.5;
        public boolean easy = false;
        public int maxVipPos = 3100;
        public double vipPower = 0.8;


    }

    public static Params PARAMS = new Params();
    private MecanumDrive drive;
    private newBeak arm;

    private ViperAction vip;
    private BucketAction bucket;
    private ClawAction claw;



    @Override
    public void runOpMode(){

        drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

        arm = new newBeak(hardwareMap);
        vip = new ViperAction(hardwareMap);
        bucket = new BucketAction(hardwareMap);
        claw = new ClawAction(hardwareMap);

        //Load Introduction and Wait for Start
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.CLASSIC);
        telemetry.addLine("RoadRunner Auto Drive Basket Blue");
        telemetry.addLine();
        telemetry.addLine().addData("Version",PARAMS.versionNumber);
        telemetry.addLine();
        telemetry.update();


        arm.autonStartPos();

        waitForStart();
        telemetry.clear();


      //  if(!PARAMS.){
            toSub();
            toNewPosOne();
                toBasket();
         //   toPosTwo();
         //   toBasket();
         //   toPosThree();
         //   toBasket();
        /*else if(PARAMS.easy) {
            dumbBasket();
            toBasket();
        }*/


    }

    private void toSub(){
        //beginning position: ends at the sub
        Action movePos = drive.actionBuilder(drive.pose)
                .setReversed(true)
                .lineToX(-27)
                .build();
        Actions.runBlocking(new ParallelAction(movePos,vip.perfBeforeDropOff()));

        Action extraMove = drive.actionBuilder(drive.pose)
                .setReversed(true)
                .lineToX(-28.5)
                .build();
        Actions.runBlocking(new SequentialAction(extraMove, vip.perfClawDropOnSub(), claw.placeOnSub()));

        //positioned back
        Action moveBack = drive.actionBuilder(drive.pose)
                .setReversed(true)
                .lineToX(-12)
                .build();
        Actions.runBlocking(new SequentialAction(moveBack,vip.autonReset() ));
    }

    private void toNewPosOne(){
        Action moveOne = drive.actionBuilder(drive.pose)
                .setReversed(false)
                .splineTo(new Vector2d(-21, -40.2), Math.toRadians(180))
                .build();
     //   Actions.runBlocking(new SequentialAction(moveOne, bucket.autonPrepForCatch(), arm.PickUpReachAuton(),arm.autonCloseBeak(),arm.SuplexAuton()));
        Actions.runBlocking(new SequentialAction(moveOne, arm.autonReachSamp()));

        /*
        Action moveTwo = drive.actionBuilder(drive.pose)
                .setReversed(false)
                .waitSeconds(3000)
                .build();
        Actions.runBlocking((moveTwo));*/
    }
    private void toPosOne(){
        //pos one
        Action moveOne = drive.actionBuilder(drive.pose)
                .setReversed(false)
                .splineTo(new Vector2d(-18.0, -39.6), Math.toRadians(180))
                .build();
       Actions.runBlocking(new SequentialAction(moveOne, bucket.autonPrepForCatch()));

    }
    private void toPosTwo(){
        //pos two
        Action moveTwo = drive.actionBuilder(drive.pose)
                .setReversed(false)
                .splineTo(new Vector2d(-19, -43), Math.toRadians(180))
                .build();
        Actions.runBlocking(new SequentialAction(moveTwo));
        //basket
    }

    private void toPosThree(){
        //pos three
        Action moveThree = drive.actionBuilder(drive.pose)
                .setReversed(false)
                .splineTo(new Vector2d(-19, -47), Math.toRadians(320))
                .build();
        Actions.runBlocking(new SequentialAction(moveThree));
        //basket
    }

    private void toBasket(){
        //basket
        Action moveBasket= drive.actionBuilder(drive.pose)
                .setReversed(true)
                // .splineTo(new Vector2d(-12, -48), Math.toRadians(-20))
                 //.strafeTo(new Vector2d(-10,-48))

                .splineTo(new Vector2d(-9, -49), Math.toRadians(-50))

                .build();
        Actions.runBlocking(moveBasket);

    }

    private void dumbBasket(){
        Action moveOut = drive.actionBuilder(drive.pose)
                .setReversed(true)
                .lineToX(-5)
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