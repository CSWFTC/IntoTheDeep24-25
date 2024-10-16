package org.firstinspires.ftc.teamcode.Helper.EventBus;

@FunctionalInterface
public interface MessageHandlerWithParams<T> {
    T handle(Object... params);
}
