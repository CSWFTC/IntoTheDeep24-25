package org.firstinspires.ftc.teamcode.Auton;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.acmerobotics.roadrunner.ftc.Actions;

import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;

@Autonomous(name = "AutoBlueOB", group = "RoadRunner")
public class AutoBlueOB extends LinearOpMode {

    public static class Params {

    }

    public static Params PARAMS = new Params();
    private MecanumDrive drive;

    public void runOpMode(){
        drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

        waitForStart();

        telemetry.addData("okay", "so code needs to push6");
        telemetry.update();
        toLine();
        markOne();
        humanPlayer();
    }

    public void toLine(){
        //beginning position: ends at the sub
        Action movePos = drive.actionBuilder(drive.pose)
                .setReversed(true)
                .lineToX(-26)
                .build();
        Actions.runBlocking(movePos);

        //positioned back
        Action moveBack = drive.actionBuilder(drive.pose)
                .setReversed(false)
                .lineToX(-20)
                .build();
        Actions.runBlocking(moveBack);
    }

    public void markOne(){
        Action lineM1 = drive.actionBuilder(drive.pose)
                .setReversed(false)
                .splineTo(new Vector2d(-25, 38), Math.toRadians(180))
                .build();
        Actions.runBlocking(lineM1);
    }

    public void humanPlayer(){
        Action Player = drive.actionBuilder(drive.pose)
                .setReversed(false)
                .lineToX(-12)
                //dropping
                .lineToY(40)
                //.lineToX(-19)
                .build();
        Actions.runBlocking(Player);
    }

    public void backToLine(){
        Action backAgain = drive.actionBuilder(drive.pose)
                .setReversed(false)
                .splineTo(new Vector2d(-26, 0), Math.toRadians(180))
                .build();
        Actions.runBlocking(backAgain);
    }



}
