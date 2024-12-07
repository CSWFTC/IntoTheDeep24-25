package org.firstinspires.ftc.teamcode.Auton;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.acmerobotics.roadrunner.ftc.Actions;

import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;

@Autonomous(name = "AutoRedOB", group = "RoadRunner")
public class AutoRedOB extends LinearOpMode {

    public static class Params {
        public boolean easy = true;
    }

    public static AutoBlueOB.Params PARAMS = new AutoBlueOB.Params();
    private MecanumDrive drive;

    public void runOpMode() {
        drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

        waitForStart();

        telemetry.addData("okay", "so code needs to push6");
        telemetry.update();

        if(PARAMS.easy){
            forward();
            toPark();
        }
        else{
            toLine();
            markOne();
            humanPlayer();}

    }

    public void toLine() {
        Action movePos = drive.actionBuilder(drive.pose)
                .setReversed(true)
                .lineToX(-26)
                .build();
        Actions.runBlocking(movePos);


        Action moveBack = drive.actionBuilder(drive.pose)
                .setReversed(true)
                .lineToX(-20)
                .build();
        Actions.runBlocking(moveBack);
    }


    public void markOne() {
        Action lineM1 = drive.actionBuilder(drive.pose)
                .setReversed(false)
                .splineTo(new Vector2d(-25, -38), Math.toRadians(-180))
                .build();
        Actions.runBlocking(lineM1);


    }

    public void humanPlayer() {
        Action Player = drive.actionBuilder(drive.pose)
                .setReversed(true)
                .lineToX(-12)
                .lineToY(-49)
                .lineToX(-10)
                .build();
        Actions.runBlocking(Player);
    }

    public void backToLine() {
        Action backAgain = drive.actionBuilder(drive.pose)
                .setReversed(false)
                .splineTo(new Vector2d(-26, 0), Math.toRadians(180))
                .build();
        Actions.runBlocking(backAgain);


    }

    private void toPark(){
        //basket
        Action moveBasket= drive.actionBuilder(drive.pose)
                .setReversed(true)
                // .splineTo(new Vector2d(-12, -48), Math.toRadians(-20))
                .strafeTo(new Vector2d(-14,-48))
                .build();
        Actions.runBlocking((moveBasket));

    }

    private void forward(){
        Action moveOut = drive.actionBuilder(drive.pose)
                .setReversed(true)
                .lineToX(-9)
                .build();
        Actions.runBlocking(moveOut);
    }
}



