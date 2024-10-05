package org.firstinspires.ftc.teamcode.Tests;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.acmerobotics.roadrunner.Vector2d;

import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;


@Config
@Autonomous
public class TestRoadRunner2 extends LinearOpMode {
    private Pose2d position = new Pose2d(0, 0, 0);

    void changePosition(
            double x,
            double y,
            double heading
    ) {
        this.position = new Pose2d(x,y, heading);
    }

    double getY() {
        return this.position.position.y;
    }

    double getX() {
        return this.position.position.x;
    }

    double getHeading() {
        return this.position.heading.toDouble();
    }

    @Override
    public void runOpMode() {
        MecanumDrive mecanumDrive = new MecanumDrive(hardwareMap, position);

        waitForStart();

        Actions.runBlocking(
                mecanumDrive.actionBuilder(
                                this.position
                        )
                        .lineToX(110d)
                        .build()
        );

        try {
            Thread.sleep(1000); // Wait for 1 second
        } catch (InterruptedException e) {
            // Handle the interrupted exception if necessary
            telemetry.addData("Error", "Thread interrupted!");
            telemetry.update();
        }

        changePosition(110d, getY(), getHeading());

        Actions.runBlocking(
                mecanumDrive.actionBuilder(
                                new Pose2d(30, 0, 0)
                )
                        .splineTo(new Vector2d(0d, -30d),Math.toRadians(180))
                        .build()
        );

        try {
            Thread.sleep(1000); // Wait for 1 second
        } catch (InterruptedException e) {
            // Handle the interrupted exception if necessary
            telemetry.addData("Error", "Thread interrupted!");
            telemetry.update();
        }

        changePosition(20d, 30d, Math.toRadians(180));

        Actions.runBlocking(
                mecanumDrive.actionBuilder(this.position)
                        .turnTo(270d)
                        .build()
        );

        try {
            Thread.sleep(1000); // Wait for 1 second
        } catch (InterruptedException e) {
            // Handle the interrupted exception if necessary
            telemetry.addData("Error", "Thread interrupted!");
            telemetry.update();
        }

        changePosition(getX(), getY(), Math.toRadians(270d));


        Actions.runBlocking(
                mecanumDrive.actionBuilder(this.position)
                        .lineToY(30d)
                        .build()
        );

//        Actions.runBlocking(
//                mecanumDrive.actionBuilder(this.position)
//                        .splineTo(new Vector2d(10d, 30d), Math.toRadians(270d))
//                        .build()
//        );

        try {
            Thread.sleep(1000); // Wait for 1 second
        } catch (InterruptedException e) {
            // Handle the interrupted exception if necessary
            telemetry.addData("Error", "Thread interrupted!");
            telemetry.update();
        }

        changePosition(10d, 20d, Math.toRadians(270));
//
//        Actions.runBlocking(
//                mecanumDrive.actionBuilder(this.position)
//                        .lineToY(30d)
//                        .build()
//        );

//        changePosition(getX(), 50d, getHeading());
    }
}