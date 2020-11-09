package com.solvo.mthreads.domain.command;

import com.solvo.mthreads.domain.queue.QueryArrayBlockingQueue;

public interface ICommand {

    boolean execute(QueryArrayBlockingQueue pQueueA, QueryArrayBlockingQueue pQueueB);

}
