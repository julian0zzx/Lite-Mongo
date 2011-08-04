package com.osteching.litemongo.command;

import com.mongodb.DBObject;

public class DeleteCommand extends AbstractCommand {

    public DeleteCommand(Object[] args) {
        super(args);
    }

    @Override
    public Object execute() {
        assert null != args;

        return delete(buildDbObject(args));
    }

    private DBObject buildDbObject(Object[] args) {
        return null;
    }
    
    private boolean delete(DBObject arg) {
        // TODO collection.remove(arg);
        return false;
    }

}
