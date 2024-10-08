package org.firstinspires.ftc.teamcode.Tests;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryBuilder;
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
              mecanumDrive.actionBuilder(this.position)
                      .lineToX(100)
                      .build()
        );

        telemetry.addLine(mecanumDrive.pose.heading.toString());
        telemetry.update();

        changePosition(100, getY(), getHeading());

        Actions.runBlocking(
                mecanumDrive.actionBuilder(this.position)
                        .turnTo(180)
                        .build()
        );

        telemetry.addLine(mecanumDrive.pose.heading.toString());
        telemetry.update();
    }
}