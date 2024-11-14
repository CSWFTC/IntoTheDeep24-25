package org.firstinspires.ftc.teamcode.Helper.Intake;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.Inject;
import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.Injectable;

public class IntakeRotation extends Injectable {
    @Inject("intakeRotationServoName")
    public String servoName;
//    public double servoForward = 1;
//    public double servoStartPos = 0.532;
//    public double servoPresetPosX = 0.532;
//    public double ServoPresetPosB = 0.532;

    public boolean initErrorStatus = false;
    public String initError = "";

    @Inject("hdwMap")
    private HardwareMap hardwareMap;

    private Servo servo;

    private IntakeAction intakeAction;

    public void activateRotation() {
        this.servo.setPosition(-0.5);

        this.intakeAction.isRotated.set(true);
    }

    public void deactivateRotation() {
        this.servo.setPosition(0.5);

        this.intakeAction.isRotated.set(false);
    }

    public IntakeRotation(IntakeAction intakeAction) {
        super();

        this.intakeAction = intakeAction;

        try {
            this.servo = hardwareMap.servo.get(this.servoName);
        } catch(Exception e) {
            this.initErrorStatus = true;
            this.initError = e.toString();
        }
    }
}
