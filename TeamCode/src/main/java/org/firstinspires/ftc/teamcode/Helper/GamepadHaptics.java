package org.firstinspires.ftc.teamcode.Helper;

import androidx.annotation.NonNull;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;

public class GamepadHaptics {
    private final Gamepad gpInput;
    private ArrayList<HapticLogs> logs = new ArrayList<HapticLogs>();

    public GamepadHaptics(@NonNull Gamepad gpInput) {
        this.gpInput = gpInput;
    }

    private void addTelemetry(String log) {
        HapticLogs newLog = new HapticLogs(log);
        this.logs.add(newLog);
    }

    public void clearTelemetry() {
        // clear logs by setting empty arraylist
        this.logs = new ArrayList<>();
    }

    public ArrayList<String> getLogs() {
        ArrayList<String> res = new ArrayList<String>();
        for ( HapticLogs log : this.logs) {
            res.add(log.getLog());
        }
        return res;
    }

    public void logTelemetry(Telemetry telemetry) {
        for (String log : this.getLogs()){
            telemetry.addLine(log);
        }
        telemetry.update();
    }

    public void runShortHaptic() {
        this.addTelemetry("Controller ran short haptic");
        this.gpInput.rumble(1, 0, 150);
    }

    public void runMediumHaptic() {
        this.addTelemetry("Controller ran medium haptic");
        this.gpInput.rumble(1.5, 0, 350);
    }

    public void runLongHaptic() {
        this.addTelemetry("Controller ran long haptic");
        this.gpInput.rumble(3, 0, 650);
    }

    public void runCustomHaptic(float r1, float r2, int duration) {
        this.addTelemetry("Controller ran rumble haptic with values ["+r1+","+r2+","+duration+"]");
        this.gpInput.rumble(r1, r2, duration);
    }

    public void runCustomBlip(int count) {
        this.addTelemetry("Controller ran rumbleBlip haptic with value ["+count+"]");
        this.gpInput.rumbleBlips(count);
    }
}
