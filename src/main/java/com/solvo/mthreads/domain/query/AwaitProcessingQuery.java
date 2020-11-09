package com.solvo.mthreads.domain.query;

public class AwaitProcessingQuery extends Query{
    public AwaitProcessingQuery() {
        super(QueryType.awaitProcessingType, Integer.MIN_VALUE);
    }
}
