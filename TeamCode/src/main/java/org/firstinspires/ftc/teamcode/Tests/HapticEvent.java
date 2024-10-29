/*
THIS IS SOLELY A TEST CLASS FOR EVENTBUS
 */

package org.firstinspires.ftc.teamcode.Tests;

import org.firstinspires.ftc.teamcode.Helper.GamePad;
import org.firstinspires.ftc.teamcode.Helper.EventBus.Subscriber;

public class HapticEvent implements Subscriber<Void, Void> {
    public HapticEvent() {}

    @Override
    public Void onMessage(Object... params) {
        if (params.length > 0 && params[0] instanceof GamePad) {
            GamePad gp = (GamePad) params[0];
            gp.HapticsController.runLongHaptic();
        } else {
            System.out.println("Invalid parameters or no GamePad provided");
        }
        return null;
    }

    @Override
    public Void onMessage() {
        return null;
    }
}
