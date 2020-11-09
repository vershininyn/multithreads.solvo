package com.solvo.mthreads.main;

import com.solvo.mthreads.domain.command.CommandFactory;
import com.solvo.mthreads.domain.command.ICommand;
import com.solvo.mthreads.domain.log.FileLogger;
import com.solvo.mthreads.domain.log.IStringLogger;
import com.solvo.mthreads.domain.processing.CustomThreadFactory;
import com.solvo.mthreads.domain.processing.QuerySheduler;
import com.solvo.mthreads.domain.query.QueryType;
import com.solvo.mthreads.domain.queue.QueryArrayBlockingQueue;
import com.solvo.mthreads.domain.util.ResourceUnpackManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;

import java.io.*;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final BufferedReader _reader = new BufferedReader(new InputStreamReader(System.in, Charset.forName("UTF-8")));

    private static final ExecutorService _executors = Executors.newFixedThreadPool(2,new CustomThreadFactory("STHREAD", Thread.MAX_PRIORITY));
    private static final QueryArrayBlockingQueue
            _queueA = new QueryArrayBlockingQueue(1024,QueryType.typeA),
            _queueB = new QueryArrayBlockingQueue(1024,QueryType.typeB);

    private static final IStringLogger _logger = new FileLogger(Main.class);

    static {
        LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(true);

        context.setConfigLocation(ResourceUnpackManager.unpack("log4j2.properties").toURI());
    }

    public static void main(String[] args) {
        _executors.execute(new QuerySheduler(_queueA));
        _executors.execute(new QuerySheduler(_queueB));

        System.out.println("Please, enter command and press enter. Type \'help\' if you want see command list. Waiting for command...");

        while(true) {
            try {
                System.out.print(">>> ");
                String line = _reader.readLine();

                ICommand cmd = CommandFactory.parseAndCreateCommand(line);

                if (cmd.execute(_queueA, _queueB)) {
                    System.out.println("await for termination of shedulers...");
                    _executors.awaitTermination(3, TimeUnit.SECONDS);
                    System.out.println("exiting...");
                    break;
                }
            } catch (Exception e) {
                _logger.log(e.getStackTrace().toString());
                _logger.log(e.getLocalizedMessage());
            }
        }
    }

}
