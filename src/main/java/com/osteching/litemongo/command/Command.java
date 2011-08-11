package com.osteching.litemongo.command;

public interface Command {

    Object execute();

    void setArgs(Object[] args);
}
