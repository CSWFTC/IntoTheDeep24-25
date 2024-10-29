package org.firstinspires.ftc.teamcode.Auton;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.acmerobotics.roadrunner.ftc.Actions;

import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;

@Autonomous(name = "Auto Red", group = "RoadRunner")
public class AutoRedDrive extends LinearOpMode {

    public static class Params {

    }

    public static Params PARAMS = new Params();
    private MecanumDrive drive;

    public void runOpMode(){
        drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

        waitForStart();

        telemetry.addData("okay", "so code needs to push5");
        telemetry.update();

        Actions.runBlocking(
                drive.actionBuilder(drive.pose)
                        .setReversed(true)
                        .lineToX(-44.5)
                        //hang a specimen
                        .setReversed(false)
                        .lineToX(-20)
                        .turnTo(Math.toRadians(-140))
                        .setReversed(true)
                        .lineToY(15)
                        .splineTo(new Vector2d(-45, 40), Math.toRadians(0))
                        //grab a sample
                        //.turnTo(90)
                        //.lineToX(28)
                        //.strafeTo(new Vector2d(51, 15))
                        .build()
        );
    }

    private void drive(){
        Action driveRed = drive.actionBuilder(drive.pose)
                .setReversed(true)
                .lineToX(28)
                //hang a specimen
                .setReversed(false)
                .lineToX(4)
                .turnTo(Math.toRadians(-90))
                .splineTo(new Vector2d(-28, 24), Math.toRadians(0))
                //grab a sample
                .turnTo(90)
                .lineToX(28)
                //.strafeTo(new Vector2d(51, 15))
                .build();
    }

}
