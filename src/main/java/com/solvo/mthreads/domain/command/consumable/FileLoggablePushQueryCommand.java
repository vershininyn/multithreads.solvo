package com.solvo.mthreads.domain.command.consumable;

import com.solvo.mthreads.domain.log.FileLogger;
import com.solvo.mthreads.domain.query.IQuery;

public class FileLoggablePushQueryCommand extends PushableCommand<FileLogger>{
    public FileLoggablePushQueryCommand(String pQuery) {
        super(pQuery, new FileLogger());
    }

    public FileLoggablePushQueryCommand(IQuery pQuery) {
        super(pQuery, new FileLogger());
    }
}
