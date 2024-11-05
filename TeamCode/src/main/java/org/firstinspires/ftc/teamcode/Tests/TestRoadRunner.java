package org.firstinspires.ftc.teamcode.Tests;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Arclength;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Pose2dDual;
import com.acmerobotics.roadrunner.PosePath;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.VelConstraint;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;
import org.jetbrains.annotations.NotNull;

@Config
@Autonomous

public class TestRoadRunner extends LinearOpMode {
//    private MecanumDrive mecanumDrive = new MecanumDrive();
    private Pose2d beginPose = new Pose2d(-70, 0, 0);

    private static Vector2d vec(double x, double y) {
        return new Vector2d(x, y);
    }

    private static double rad(double r) {
        return Math.toRadians(r);
    }


    private static VelConstraint constr( double c ) {
        VelConstraint vel = new VelConstraint() {
            @Override
            public double maxRobotVel(@NotNull Pose2dDual<Arclength> pose2dDual, @NotNull PosePath posePath, double v) {
                return c;
            }
        };

        return vel;
    }


    @Override
    public void runOpMode() {
        MecanumDrive mecanumDrive = new MecanumDrive(hardwareMap, beginPose);


        waitForStart();

        telemetry.addLine("v0.1");
        telemetry.update();

        telemetry.addLine(mecanumDrive.pose.heading.toString());
        telemetry.update();

        Actions.runBlocking(
                mecanumDrive.actionBuilder(this.beginPose)
                        .lineToY(-30)
                        .lineToY(-40)
                        .strafeTo(vec(40, -30))
                        .turnTo(rad(0))
                        .strafeTo(vec(40,-63))
                        .strafeTo(vec(63,-63))
                        .strafeTo(vec(40,-63))
                        .strafeTo(vec(40, -30))
                        .turnTo(rad(270))
                        .strafeTo(vec(0, -40))
//                        .strafeTo(vec(0, -30), constr(40))
//                        .strafeTo(vec(0, -35))
//                        .splineTo(vec(38, -34), rad(0))
//                        .strafeTo(vec(40, -63), constr(100))
//                        .strafeTo(vec(60, -63))
//                        .strafeTo(vec(40, -63))
//                        .strafeTo(vec(40, -25))
//                        .strafeTo(vec(0, -50))
//                        .splineTo(vec(0, -40), rad(90))
//                        .turnTo(rad(270))
//                        .strafeTo(vec(0, -30), constr(40))
//                        .strafeTo(vec(0, -35))
//                        .splineTo(vec(54, -34), rad(0))
//                        .strafeTo(vec(54, -63), constr(100))
//                        .strafeTo(vec(60, -63))
//                        .strafeTo(vec(40, -63))
//                        .strafeTo(vec(40, -25))
//                        .strafeTo(vec(0, -50))
//                        .splineTo(vec(0, -40), rad(90))
//                        .turnTo(rad(270))
//                        .strafeTo(vec(0, -30), constr(40))
                        .build()
        );
    }
}
