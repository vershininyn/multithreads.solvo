package com.solvo.mthreads.domain.query;

public interface IQuery {
    QueryType getType();
    void setType(QueryType pType);

    int getFeatureX();
    void setFeatureX(int pFeatureX);
}
