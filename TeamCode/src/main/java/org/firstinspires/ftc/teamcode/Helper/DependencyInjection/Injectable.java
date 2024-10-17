package org.firstinspires.ftc.teamcode.Helper.DependencyInjection;

import java.lang.reflect.Field;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Injectable {
    public boolean didInjectionFail = false;

    private Exception injectionFailException = null;

    public Injectable() {
        try {
            DependencyInjector.inject(this);
        } catch (Exception e) {
            this.didInjectionFail = true;
            this.injectionFailException = e;
        }
    }

    public <T> void onCatch(BiConsumer<Exception, T> handler, T obj) {
        handler.accept(this.injectionFailException, obj);
    }

    public <T> void onCatch(Consumer<Exception> handler) {
        handler.accept(this.injectionFailException);
    }

    public Exception getException() {
        return this.injectionFailException;
    }

    public boolean areDepsNull(Object ... vars) {
        for (Object _var : vars) {
            if (_var == null) {
                return true;
            }
        }
        return false;
    }

    public RemoveInjectablesReturn removeInjectables() {
        Field[] fields = this.getClass().getFields();
        boolean didError = false;
        for (Field field : fields) {
            if (field.isAnnotationPresent(Inject.class)) {
                Inject inject = field.getAnnotation(Inject.class);
                if (inject != null) {
                    try {
                        field.setAccessible(true);
                        field.set(this, null);
                    } catch(Exception e) {
                        didError = true;
                    }
                }
            }
        }
        if (didError) {
            return RemoveInjectablesReturn.ERROR;
        }
        return RemoveInjectablesReturn.SUCCESS;
    }
}