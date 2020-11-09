package com.solvo.mthreads.domain.processing;

import com.solvo.mthreads.domain.log.FileLogger;
import com.solvo.mthreads.domain.log.IStringLogger;
import com.solvo.mthreads.domain.query.IQuery;
import com.solvo.mthreads.domain.query.QueryType;
import com.solvo.mthreads.domain.queue.QueryArrayBlockingQueue;

public abstract class QueryCallableTask implements Runnable {

    private IStringLogger _logger = new FileLogger(QueryCallableTask.class);
    private QueryArrayBlockingQueue _queue;

    public QueryCallableTask(QueryArrayBlockingQueue pQueue) {
        _queue = pQueue;
    }

    @Override
    public void run() {
        while(true) {
            try {
                IQuery query = _queue.take();

                if (query.getType() == QueryType.awaitProcessingType) {
                    Thread.sleep(2*1000);
                    continue;
                }

                if (query.getType() == QueryType.stopProcessingType) {
                    _queue.clear();
                    _queue = null;
                    break;
                }

                processQuery(query);
            } catch (InterruptedException e) {
                _logger.log(e.getStackTrace().toString());
                _logger.log(e.getLocalizedMessage());
            }
        }
    }

    public IStringLogger getLogger() {
        return _logger;
    }

    public QueryArrayBlockingQueue getQueue() {
        return _queue;
    }

    protected abstract void processQuery(IQuery pQuery);
}
