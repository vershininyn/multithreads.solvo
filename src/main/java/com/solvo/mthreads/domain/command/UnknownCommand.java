package com.solvo.mthreads.domain.command;

import com.solvo.mthreads.domain.queue.QueryArrayBlockingQueue;

public class UnknownCommand implements ICommand {

    @Override
    public boolean execute(QueryArrayBlockingQueue pQueueA, QueryArrayBlockingQueue pQueueB) {
        System.out.println("Unknown command");
        return false;
    }

}
