package com.solvo.mthreads.domain.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileLogger implements IStringLogger {
    private Logger _logger = null;

    public FileLogger() {
        this(FileLogger.class);
    }

    public FileLogger(Class pClazz) {
        _logger = LogManager.getLogger(pClazz);
    }

    @Override
    public IStringLogger log(String pData) {
        _logger.info(pData);
        return this;
    }
}
