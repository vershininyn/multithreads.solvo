package com.solvo.mthreads.domain.processing;

import java.util.concurrent.ThreadFactory;

public class CustomThreadFactory implements ThreadFactory {

   private String _prefix = null;
   private int _priority = 0;

   public CustomThreadFactory(String pPrefix, int pPriority) {
       _prefix = pPrefix;
       _priority = pPriority;
   }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);

        thread.setPriority(_priority);
        thread.setName(_prefix+thread.getId());
        thread.setDaemon(true);

        return thread;
    }
}
