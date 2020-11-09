package com.solvo.mthreads.domain.processing;

import com.solvo.mthreads.domain.query.IQuery;
import com.solvo.mthreads.domain.queue.QueryArrayBlockingQueue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QuerySheduler extends QueryCallableTask {

    private final int _maxProcessorsCount = 5;

    private final ExecutorService _executor = Executors.newFixedThreadPool(_maxProcessorsCount, new CustomThreadFactory("PTHREAD", Thread.NORM_PRIORITY));

    private final ArrayList<Integer> _keysOrder = new ArrayList<>(_maxProcessorsCount);
    private final HashMap<Integer, QueryArrayBlockingQueue> _taskMap = new HashMap<>(_maxProcessorsCount);

    private QueryArrayBlockingQueue _queue;

    public QuerySheduler(QueryArrayBlockingQueue pQueue) {
        super(pQueue);

        for (int pindex = 0; pindex < _maxProcessorsCount; pindex++) {
            QueryArrayBlockingQueue queue = new QueryArrayBlockingQueue(128,pQueue.getType());
            _keysOrder.add(pindex);
            _taskMap.put(pindex,queue);
            _executor.execute(getProcessor(pindex, queue));
        }
    }

    public QueryProcessor getProcessor(int featureX, QueryArrayBlockingQueue pQueue) {
        return new QueryProcessor(pQueue);
    }

    public HashMap<Integer, QueryArrayBlockingQueue> getQueuesMap() {
        return _taskMap;
    }

    @Override
    protected void processQuery(IQuery pQuery) {
        int featureX = pQuery.getFeatureX();

        if (_taskMap.containsKey(featureX)) {
            _taskMap.get(featureX).add(pQuery);
            return;
        }

        // Пришел новый запрос, но для него нет QueryProcessor и очереди, следовательно нужно
        // увеличивать кол-во обработчиков. Однако, чтобы избежать OutOfMemory нужно переиспользовать существующие обработчики.
        // Чтобы переиспользовать имеющиеся обработчики, будем удалять наиболее старые и перебрасывать их на наиболее новые запросы
        if (_taskMap.keySet().size() == _maxProcessorsCount) {
            reuseExistingQueue(featureX).add(pQuery);
        }
    }

    private QueryArrayBlockingQueue reuseExistingQueue(int pFeatureX) {
        QueryArrayBlockingQueue queue = _taskMap.get(_keysOrder.get(0));

        _taskMap.put(pFeatureX, queue);
        _taskMap.remove(_keysOrder.get(0));

        _keysOrder.add(pFeatureX);
        _keysOrder.remove(0);

        return queue;
    }
}
