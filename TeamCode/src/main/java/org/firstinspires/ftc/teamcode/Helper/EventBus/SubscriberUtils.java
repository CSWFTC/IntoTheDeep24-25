package org.firstinspires.ftc.teamcode.Helper.EventBus;

public class SubscriberUtils {
    public static <T> Subscriber<T, Void> generateSubscriberClass(MessageHandlerWithParams<T> handler) {
        class GeneratedSubscriber implements Subscriber<T, Void> {
            @Override
            public T onMessage(Object... params) {
                return handler.handle(params);
            }

            public Void onMessage() {
                return null;
            }
        }

        return new GeneratedSubscriber();
    }

    public static <T>Subscriber<Void, T> generateSubscriberClass(MessageHandlerWithoutParams<T> handler) {
        class GeneratedSubscriber implements Subscriber<Void, T> {
            @Override
            public Void onMessage(Object... params) {
                return null;
            }

            public T onMessage() {
                return handler.handle();
            }
        }

        return new GeneratedSubscriber();
    }

    public static <T, R>Subscriber<T, R> generateSubscriberClass(MessageHandlerWithParams<T> handler1, MessageHandlerWithoutParams<R> handler2) {
        class GeneratedSubscriber implements Subscriber<T, R> {
            @Override
            public T onMessage(Object... params) {
                return handler1.handle(params);
            }

            public R onMessage() {
                return handler2.handle();
            }
        }

        return new GeneratedSubscriber();
    }

    public static Subscriber<Void, Void> generateSubscriberClass() {

        class GeneratedSubscriber implements Subscriber<Void, Void> {
            @Override
            public Void onMessage(Object... params) {
                return null;
            }

            public Void onMessage() {
                return null;
            }
        }

        return new GeneratedSubscriber();
    }
}