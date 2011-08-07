package com.osteching.litemongo.command;

import java.lang.reflect.Method;
import java.util.Arrays;

public abstract class AbstractCommand implements Command {

    protected Method method = null;

    protected Object[] args = null;

    public AbstractCommand(Method method, Object[] args) {
        this.method = method;
        this.args = args;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AbstractCommand)) {
            return false;
        }

        AbstractCommand that = (AbstractCommand) obj;
        return this.method.equals(that.method) && Arrays.deepEquals(this.args, that.args);
    }

    @Override
    public int hashCode() {
        return method.hashCode() * 37 + args.hashCode() * 107;
    }
}
