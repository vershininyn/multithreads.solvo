package com.solvo.mthreads.domain.processing;

import com.solvo.mthreads.domain.query.IQuery;
import com.solvo.mthreads.domain.query.QueryType;
import com.solvo.mthreads.domain.queue.QueryArrayBlockingQueue;

public class QueryProcessor extends QueryCallableTask {

    private QueryType _queueType;

    public QueryProcessor(QueryArrayBlockingQueue pQueue) {
        super(pQueue);

        _queueType = pQueue.getType();
    }

    @Override
    protected void processQuery(IQuery pQuery) {
        getLogger().log("QUEUE_TYPE= "+ _queueType +","+pQuery.toString());
    }
}
