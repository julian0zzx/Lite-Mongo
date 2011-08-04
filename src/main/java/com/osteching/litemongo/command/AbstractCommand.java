package com.osteching.litemongo.command;

public abstract class AbstractCommand implements Command {

    protected Object[] args = null;

    public AbstractCommand(Object[] args) {
        this.args = args;
    }

}
