package com.solvo.mthreads.domain.command;

import com.solvo.mthreads.domain.query.StopProcessingQuery;
import com.solvo.mthreads.domain.queue.QueryArrayBlockingQueue;

public class ExitCommand implements ICommand{

    @Override
    public boolean execute(QueryArrayBlockingQueue pQueueA, QueryArrayBlockingQueue pQueueB) {
        pQueueA.add(new StopProcessingQuery());
        pQueueB.add(new StopProcessingQuery());

        return true;
    }
}
