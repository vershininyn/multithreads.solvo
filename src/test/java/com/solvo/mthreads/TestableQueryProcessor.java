package com.solvo.mthreads;

import com.solvo.mthreads.domain.processing.QueryProcessor;
import com.solvo.mthreads.domain.query.IQuery;
import com.solvo.mthreads.domain.queue.QueryArrayBlockingQueue;

import java.util.ArrayList;

public class TestableQueryProcessor extends QueryProcessor {

    private ArrayList<IQuery> _data;

    public TestableQueryProcessor(QueryArrayBlockingQueue pQueue, ArrayList<IQuery> pReportList) {
        super(pQueue);

        _data = pReportList;
    }

    @Override
    protected void processQuery(IQuery pQuery) {
        _data.add(pQuery);
    }
}
