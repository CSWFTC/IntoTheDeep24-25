package org.firstinspires.ftc.teamcode.Auton;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.sun.tools.javac.comp.Todo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;

@Autonomous(name = "Auto Blue Basket", group = "RoadRunner")
public class AutoBlueBasket extends LinearOpMode {

    public static class Params {
        public int versionNumber = 0;

    }

    public static Params PARAMS = new Params();
    private MecanumDrive drive;


    @Override
    public void runOpMode(){

        drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

        //Load Introduction and Wait for Start
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.CLASSIC);
        telemetry.addLine("RoadRunner Auto Drive Basket Blue");
        telemetry.addLine();
        telemetry.addLine().addData("Version",PARAMS.versionNumber);
        telemetry.addLine();
        telemetry.update();

        waitForStart();
        telemetry.clear();


        toSub();
        updateTelemetry(drive.pose.position);
        toPosOne();
        updateTelemetry(drive.pose.position);
        // toBasket();
        // toPosTwo();
        // toBasket();
        // toPosTwo();
        // toBasket();



    }

    private void toSub(){
        //beginning position: ends at the sub
        Action movePos = drive.actionBuilder(drive.pose)
                .setReversed(true)
                .lineToX(-44.5)
                .build();
        Actions.runBlocking(movePos);

        //TODO: add code for claw

        //positioned back
        Action moveBack = drive.actionBuilder(drive.pose)
                .setReversed(true)
                .lineToX(-24.5)
                .build();
        Actions.runBlocking(moveBack);
    }

    private void toPosOne(){
        //pos one
        Action moveOne = drive.actionBuilder(drive.pose)
                .setReversed(false)
                .splineTo(new Vector2d(-42, -40), Math.toRadians(180))
                .build();
        Actions.runBlocking(moveOne);

        //basket
    }

    private void toPosTwo(){
        //pos two
        Action moveTwo = drive.actionBuilder(drive.pose)
                .setReversed(true)
                .splineTo(new Vector2d(-45, -48), Math.toRadians(0))
                .build();
        Actions.runBlocking(moveTwo);
        //basket
    }

    private void toPosThree(){
        //pos three
        Action moveThree = drive.actionBuilder(drive.pose)
                .setReversed(true)
                .splineTo(new Vector2d(-38, -50), Math.toRadians(320))
                .build();
        Actions.runBlocking(moveThree);
        //basket
    }

    private void toBasket(){
        //basket
        Action moveBasket= drive.actionBuilder(drive.pose)
                .setReversed(true)
                .splineTo(new Vector2d(-12, -48), Math.toRadians(-20))
                .build();
        Actions.runBlocking(moveBasket);

    }

    private void updateTelemetry(Vector2d pos) {
        telemetry.addLine("RoadRunner Auto Drive BLUE");
        telemetry.addData("Current x Position", pos.x );
        telemetry.addData("Current y Postion", pos.y);
        telemetry.update();

    }


}