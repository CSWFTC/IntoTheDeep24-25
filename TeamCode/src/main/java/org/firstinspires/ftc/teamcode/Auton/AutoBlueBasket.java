package org.firstinspires.ftc.teamcode.Auton;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helper.Beak.BeakAction;
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.Helper.ViperSlide.ViperAction;
import org.firstinspires.ftc.teamcode.Helper.ViperSlide.BucketAction;


@Autonomous(name = "Auto Blue Basket", group = "RoadRunner")
public class AutoBlueBasket extends LinearOpMode {

    public static class Params {
        public double versionNumber = 8.6;
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
    private BeakAction arm;

    private ViperAction vip;
    private BucketAction bucket;



    @Override
    public void runOpMode(){

        drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
     //   DependencyInjector.register("hdwMap", hardwareMap);
    //    staticActions = new StaticActions();

        arm = new BeakAction(hardwareMap);
        vip = new ViperAction(hardwareMap);
        bucket = new BucketAction(hardwareMap);

        //Load Introduction and Wait for Start
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.CLASSIC);
        telemetry.addLine("RoadRunner Auto Drive Basket Blue");
        telemetry.addLine();
        telemetry.addLine().addData("Version",PARAMS.versionNumber);
        telemetry.addLine();
        telemetry.update();


        waitForStart();
        telemetry.clear();


      //  if(!PARAMS.){
            toSub();
            toPosOne();
            toBasket();
            toPosTwo();
            toBasket();
            toPosThree();
            toBasket();
        /*else if(PARAMS.easy) {
            dumbBasket();
            toBasket();
        }*/


    }

    private void toSub(){
        //beginning position: ends at the sub
        Action movePos = drive.actionBuilder(drive.pose)
                .setReversed(true)
                .lineToX(-26)
                .build();
        Actions.runBlocking(new SequentialAction(movePos));

        //TODO: add code for claw

        //positioned back
        Action moveBack = drive.actionBuilder(drive.pose)
                .setReversed(true)
                .lineToX(-20)
                .build();
        Actions.runBlocking(moveBack);
    }

    private void toPosOne(){
        //pos one
        Action moveOne = drive.actionBuilder(drive.pose)
                .setReversed(false)
                .splineTo(new Vector2d(-17, -38), Math.toRadians(180))
                .build();
        Actions.runBlocking(new SequentialAction(moveOne, bucket.autonPrepForCatch(), arm.PickUpReachAuton(), vip.dumpSampleHighBasket(), bucket.autonDumpSample()));
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
                .strafeTo(new Vector2d(-10,-48))
                .build();
        Actions.runBlocking(new SequentialAction(moveBasket));

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