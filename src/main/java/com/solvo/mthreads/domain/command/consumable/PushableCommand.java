package com.solvo.mthreads.domain.command.consumable;

import com.solvo.mthreads.domain.log.IStringLogger;
import com.solvo.mthreads.domain.query.AwaitProcessingQuery;
import com.solvo.mthreads.domain.query.IQuery;
import com.solvo.mthreads.domain.query.QueryFactory;
import com.solvo.mthreads.domain.queue.QueryArrayBlockingQueue;

public class PushableCommand<TConsumer extends IStringLogger> extends AbstractLoggableCommand<TConsumer> {

    private IQuery _query = null;

    public PushableCommand(String pQuery, TConsumer pConsumer) {
        this(QueryFactory.parseAndCreateQuery(pQuery),pConsumer);
    }

    public PushableCommand(IQuery pQuery, TConsumer pConsumer) {
        super(pConsumer);

        if (pQuery == null) throw new NullPointerException("Unacceptable query object");

        _query = pQuery;
    }

    @Override
    public boolean execute(QueryArrayBlockingQueue pQueueA, QueryArrayBlockingQueue pQueueB){
        /*
          запросы разных типов должны обрабатываться строго параллельно
        */

        logQueusState("BEFORE_PUSHING",pQueueA,pQueueB);

        switch(_query.getType()) {
            case typeA: {
                pushToQueue(pQueueA);
                break;
            }
            case typeB: {
                pushToQueue(pQueueB);
                break;
            }
        }

        logQueusState("AFTER_PUSHING",pQueueA,pQueueB);

        return false;
    }

    public IQuery getQuery() {
        return _query;
    }

    protected void pushToQueue(QueryArrayBlockingQueue pQueue) {
        pQueue.add(new AwaitProcessingQuery());
        pQueue.add(getQuery());
    }

    private void logQueusState(String pState, QueryArrayBlockingQueue pQueueA, QueryArrayBlockingQueue pQueueB) {
        getLogger().log((new StringBuilder())
                .append(" \n ")
                .append("--------- "+pState+" ---------")
                .append(" \n ")
                .append(pQueueA.toString())
                .append(" \n ")
                .append(pQueueB.toString())
                .append(" \n ")
                .append("------------------")
                .toString());
    }
}
