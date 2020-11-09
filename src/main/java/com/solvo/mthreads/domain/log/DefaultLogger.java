package com.solvo.mthreads.domain.log;

public class DefaultLogger implements IStringLogger {
    @Override
    public IStringLogger log(String pData) {
        return this;
    }
}
