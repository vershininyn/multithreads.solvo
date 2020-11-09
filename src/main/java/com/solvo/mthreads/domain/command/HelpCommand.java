package com.solvo.mthreads.domain.command;

import com.solvo.mthreads.domain.queue.QueryArrayBlockingQueue;

public class HelpCommand implements ICommand {
    @Override
    public boolean execute(QueryArrayBlockingQueue pQueueA, QueryArrayBlockingQueue pQueueB) {
        System.out.println("------ < allowed commands > ------");
        System.out.println("qstruct - structure of query queues");
        System.out.println("push type{A or B},[FEATURE_X] - create and push query of type (A or B) to queue, example - push typeA,100");
        System.out.println("exit - close console application");
        System.out.println("------------");
        return false;
    }
}
