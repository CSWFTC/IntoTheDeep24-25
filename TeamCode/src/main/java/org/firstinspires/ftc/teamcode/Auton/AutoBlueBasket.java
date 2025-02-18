package org.firstinspires.ftc.teamcode.Auton;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helper.Beak.newBeak;
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.Helper.ViperSlide.ViperAction;
import org.firstinspires.ftc.teamcode.Helper.ViperSlide.BucketAction;
import org.firstinspires.ftc.teamcode.Helper.ViperSlide.ClawAction;


@Autonomous(name = "Auto Blue Basket", group = "RoadRunner")
public class AutoBlueBasket extends LinearOpMode {

    public static class Params {
        public double versionNumber = 13.2;

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

        claw.CloseGrip();
        arm.autonStartPos();

        waitForStart();
        telemetry.clear();
            toSub();
            toNewPosOne();
            toBasket();
            toPosTwo();
            toBasket();
            toPosThree();
            toBasket();
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
                .lineToX(-20)
                .build();
        Actions.runBlocking(new ParallelAction(moveBack,vip.autonReset()));
    }

    private void toNewPosOne(){
        Action moveOne = drive.actionBuilder(drive.pose)
                .setReversed(false)
                .splineTo(new Vector2d(-21.2, -42.9), Math.toRadians(180))
                .build();
        Actions.runBlocking(new SequentialAction(moveOne, arm.autonReachSamp()));
    }
    private void toPosTwo(){
        //pos two
        Action moveTwo = drive.actionBuilder(drive.pose)
                .setReversed(false)
                .splineTo(new Vector2d(-21.2, -52.6), Math.toRadians(180))
                .build();
        Actions.runBlocking(new SequentialAction((new ParallelAction (vip.autonReset(), moveTwo)), arm.autonReachSamp()));
    }

    private void toPosThree(){
        //pos three
        Action moveThree = drive.actionBuilder(drive.pose)
                .setReversed(false)
                .splineTo(new Vector2d(-21.2, -53.0), Math.toRadians(-2))
                .build();
        Actions.runBlocking(new SequentialAction((new ParallelAction (vip.autonReset(), moveThree)), arm.autonReachSamp()));
    }
    private void toBasket(){
        //basket
        Action moveBasket= drive.actionBuilder(drive.pose)
                .setReversed(true)
                .splineTo(new Vector2d(-7.1, -54.4), Math.toRadians(-50))
                .build();
        Actions.runBlocking(new SequentialAction(moveBasket, vip.dumpSampleHighBasket(), bucket.autonPrepForCatch()) );
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