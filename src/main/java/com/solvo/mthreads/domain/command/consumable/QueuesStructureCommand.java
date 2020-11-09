package com.solvo.mthreads.domain.command.consumable;

import com.solvo.mthreads.domain.log.ConsoleLogger;
import com.solvo.mthreads.domain.queue.QueryArrayBlockingQueue;

public class QueuesStructureCommand extends AbstractLoggableCommand<ConsoleLogger> {

    public QueuesStructureCommand() {
        super(new ConsoleLogger());
    }

    @Override
    public boolean execute(QueryArrayBlockingQueue pQueueA, QueryArrayBlockingQueue pQueueB) {
        getLogger().log(pQueueA.toString()).log(pQueueB.toString());
        return false;
    }
}
