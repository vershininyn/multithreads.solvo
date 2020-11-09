package com.solvo.mthreads.domain.query;

import java.util.Objects;

public class Query implements IQuery {

    private QueryType _type;
    private int _featureX = 0;

    public Query(QueryType pType, int pFeatureX) {
        setType(pType);
        setFeatureX(pFeatureX);
    }

    public QueryType getType() {
        return _type;
    }

    public void setType(QueryType pType) {
        _type = pType;
    }

    public int getFeatureX() {
        return _featureX;
    }

    public void setFeatureX(int pFeatureX) {
        _featureX = pFeatureX;
    }

    @Override
    public String toString() {
        return "QUERY_TYPE= "+getType()+",X= "+getFeatureX();
    }

    @Override
    public int hashCode() {
        return Objects.hash(_type,_featureX);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        IQuery query = (IQuery) obj;
        return ((this.getType().equals(query.getType()))&&(getFeatureX() == query.getFeatureX()));
    }
}
