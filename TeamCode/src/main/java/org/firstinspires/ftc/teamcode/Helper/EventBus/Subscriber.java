package org.firstinspires.ftc.teamcode.Helper.EventBus;

public interface Subscriber<T, R> {
    public T onMessage(Object ...params);

    public R onMessage();
}