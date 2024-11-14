package com.example.meepmeep;

import com.acmerobotics.roadrunner.Arclength;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Pose2dDual;
import com.acmerobotics.roadrunner.PosePath;
import com.acmerobotics.roadrunner.Rotation2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.VelConstraint;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import org.jetbrains.annotations.NotNull;

import java.util.Vector;

public class AnotherClass{

    private static Vector2d flip(Vector2d vec) {
        return new Vector2d(vec.y, vec.x);
    }

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


    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(0, -70, rad(270) ))
//                        .setReversed(true)
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
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
