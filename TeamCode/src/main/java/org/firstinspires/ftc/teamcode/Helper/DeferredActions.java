package org.firstinspires.ftc.teamcode.Helper;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static java.lang.System.currentTimeMillis;

public class DeferredActions {

    public enum DeferredActionType {
        NO_ACTION("None"),
        PLAY_SOUND("Plays Quack"),
        BEAK_OPEN("Open Beak"),
        BEAK_CLOSE("Close Beak"),
        SUPLEX_BEAK("Suplex Beak"),
        BEAK_DRIVE_SAFE("Beak Drive Safe"),
        ARM("hi");


        private final String description;

        DeferredActionType(String description) {
            this.description = description;
        }

        @Override
        public @NonNull String toString() {
            return description;
        }
    }

    private static class DeferredActionEvent {
        public long triggerTime;
        public DeferredActionType action;

        public DeferredActionEvent(long triggerTime, DeferredActionType action) {
            this.triggerTime = triggerTime;
            this.action = action;
        }
    }

    private static volatile List<DeferredActionEvent> deferredActions = new ArrayList<>();
    // Telemetry

    public static DeferredActionType tlmLastAction = DeferredActionType.NO_ACTION;
    public static Date tlmLastActionTimestamp = new Date();

    // Add Deferred Action
    public static void CreateDeferredAction(long deltaMS, DeferredActionType event) {
        long triggerTime = currentTimeMillis() + deltaMS;
        deferredActions.add(new DeferredActionEvent(triggerTime, event));
    }

    // Check for Deferred Actions that are Ready to be Processed;
    public static List<DeferredActionType> GetReadyActions() {
        List<DeferredActionType> readyActions = new ArrayList<>();
        List<DeferredActionEvent> removals = new ArrayList<>();

        // Build List of Actions Ready to Execute
        for (DeferredActionEvent act : deferredActions) {
            if (currentTimeMillis() >= act.triggerTime) {
                readyActions.add(act.action);
                removals.add(act);
                tlmLastAction = act.action;
                tlmLastActionTimestamp = new Date();
            }
        }

        // Remove Ready Actions from Deferred list
        for (DeferredActionEvent act : removals) { deferredActions.remove(act); }

        return (readyActions);
    }
}