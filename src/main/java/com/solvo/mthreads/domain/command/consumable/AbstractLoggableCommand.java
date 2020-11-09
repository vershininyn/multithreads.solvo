package com.solvo.mthreads.domain.command.consumable;

import com.solvo.mthreads.domain.command.ICommand;
import com.solvo.mthreads.domain.log.IStringLogger;

public abstract class AbstractLoggableCommand<TLogger extends IStringLogger> implements ICommand{
    private final TLogger _logger;

    public AbstractLoggableCommand(TLogger pLogger) {
        _logger = pLogger;
    }

    public TLogger getLogger(){
        return _logger;
    }
}
