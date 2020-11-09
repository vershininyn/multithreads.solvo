package com.solvo.mthreads;

import com.solvo.mthreads.domain.processing.QueryProcessor;
import com.solvo.mthreads.domain.processing.QuerySheduler;
import com.solvo.mthreads.domain.query.IQuery;
import com.solvo.mthreads.domain.queue.QueryArrayBlockingQueue;

import java.util.ArrayList;
import java.util.HashMap;

public class TestableQuerySheduler extends QuerySheduler {

    private HashMap<Integer, ArrayList<IQuery>> _dataMap;

    public TestableQuerySheduler(QueryArrayBlockingQueue pQueue) {
        super(pQueue);
    }

    @Override
    public QueryProcessor getProcessor(int pFeatureX, QueryArrayBlockingQueue pQueue) {
        if (_dataMap == null) {
            _dataMap = new HashMap<>(128);
        }

        ArrayList<IQuery> featureList = new ArrayList<>(128);
        _dataMap.put(pFeatureX, featureList);

        return new TestableQueryProcessor(pQueue, featureList);
    }

    public HashMap<Integer, ArrayList<IQuery>> getDataMap() {
        return _dataMap;
    }
}
