package org.firstinspires.ftc.teamcode.Helper;

import org.firstinspires.ftc.teamcode.Helper.Beak.BeakAction;
import org.firstinspires.ftc.teamcode.Helper.Intake.IntakeAction;
import org.firstinspires.ftc.teamcode.Helper.ViperSlideActions.ViperAction;

/*
HOW TO USE INSIDE OF THE LAMBDA:

packet -> {
    StaticActions actions = StaticActions.getInstance();

    actions.getIntakeAction().whateverMethod();
    actions.getViperAction().whateverMethod();
}

 */

public class StaticActions {
    private static StaticActions instance = null;

//    private IntakeAction intakeAction;
    private ViperAction viperAction;
    private BeakAction beakAction;

//    public IntakeAction getIntakeAction() {
//        return this.intakeAction;
//    }

    public ViperAction getViperAction() {
        return this.viperAction;
    }

    public BeakAction getBeakAction() {return this.beakAction;}

    public synchronized static StaticActions getInstance() {
        if (instance == null) {
            instance = new StaticActions();
        }
        return instance;
    }

    private StaticActions() {
        // WARNING pls inject dependencies BEFORE initializing this
//        this.intakeAction = new IntakeAction();
        try {
            this.viperAction = new ViperAction();
        } catch(Exception e) {
            //
        }

        this.beakAction = new BeakAction();
    }
}
