package com.solvo.mthreads.domain.query;

public class StopProcessingQuery extends Query  {
    public StopProcessingQuery() {
        super(QueryType.stopProcessingType, Integer.MIN_VALUE);
    }
}
