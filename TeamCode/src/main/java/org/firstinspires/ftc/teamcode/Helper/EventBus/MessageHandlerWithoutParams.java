package org.firstinspires.ftc.teamcode.Helper.EventBus;

@FunctionalInterface
public interface MessageHandlerWithoutParams<T> {
    T handle();
}
