package com.solvo.mthreads.domain.log;

public class ConsoleLogger implements IStringLogger {
    @Override
    public IStringLogger log(String pData) {
        System.out.println(pData);
        return this;
    }
}
