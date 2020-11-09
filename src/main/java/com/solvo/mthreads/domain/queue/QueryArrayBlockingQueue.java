package com.solvo.mthreads.domain.queue;

import com.solvo.mthreads.domain.query.IQuery;
import com.solvo.mthreads.domain.query.QueryType;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

public class QueryArrayBlockingQueue extends ArrayBlockingQueue<IQuery> {

    private QueryType _queueType;

    public QueryArrayBlockingQueue(int pInitialCapacity, QueryType pType) {
        super(pInitialCapacity, true);

        _queueType = pType;
    }

    public QueryType getType() {
        return _queueType;
    }

    /**
     @return Map<Integer, Integer>, where key is featureX and value it's count at queue
     **/
    public Map<Integer,Integer> getState() {
        final HashMap<Integer,Integer> map = new HashMap<>(1024);

        this.forEach((query) -> {
            int x = query.getFeatureX();

            if (!map.containsKey(x)) {
                map.put(x,0);
            }

            map.put(x, map.get(x) + 1);
        });

        return map;
    }

    @Override
    public String toString() {
        StringBuilder strState = new StringBuilder("{QUEUE_TYPE="+getType()+", ");

        Map<Integer, Integer> state = getState();

        if (state.size() > 0) {
            state.forEach((featureX, featureXCount) ->{
                strState.append("X["+featureX+"]="+featureXCount+" ");
            });
        } else {
            strState.append("EMPTY");
        }

        strState.append("}");

        return strState.toString();
    }
}
