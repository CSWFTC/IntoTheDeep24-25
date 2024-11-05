package org.firstinspires.ftc.teamcode.Helper.EventBus.Parser;

import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.Inject;
import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.Injectable;

public class ParamsParser extends Injectable {
    @Inject("parser_params")
    Object[] params;

    public <T> T getParam(int index, Class<T> type) {
        if (params == null || params.length <= index) {
            return null;
        }
        Object param = params[index];
        if (type.isInstance(param)) {
            return type.cast(param);
        }
        return null;
    }
}