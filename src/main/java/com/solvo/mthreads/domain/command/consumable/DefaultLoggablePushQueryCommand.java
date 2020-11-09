package com.solvo.mthreads.domain.command.consumable;

import com.solvo.mthreads.domain.log.DefaultLogger;
import com.solvo.mthreads.domain.query.IQuery;
import com.solvo.mthreads.domain.queue.QueryArrayBlockingQueue;

public class DefaultLoggablePushQueryCommand extends PushableCommand<DefaultLogger> {
    public DefaultLoggablePushQueryCommand(String pQuery) {
        super(pQuery, new DefaultLogger());
    }

    public DefaultLoggablePushQueryCommand(IQuery pQuery) {
        super(pQuery, new DefaultLogger());
    }


    @Override
    protected void pushToQueue(QueryArrayBlockingQueue pQueue) {
        pQueue.add(getQuery());
    }
}
