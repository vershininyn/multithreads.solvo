package com.solvo.mthreads;

import com.solvo.mthreads.domain.command.consumable.DefaultLoggablePushQueryCommand;
import com.solvo.mthreads.domain.processing.CustomThreadFactory;
import com.solvo.mthreads.domain.query.*;
import com.solvo.mthreads.domain.queue.QueryArrayBlockingQueue;
import com.solvo.mthreads.domain.util.ResourceUnpackManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.ArrayList;
import java.util.Random;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ParallelAndSequentialLogicTests {

    private static int _typeACount = 0, _typeBCount = 0;

    private static final QueryArrayBlockingQueue
            _queueA = new QueryArrayBlockingQueue(2048,QueryType.typeA),
            _queueB = new QueryArrayBlockingQueue(2048,QueryType.typeB);

    private static final ArrayList<IQuery>
            _strictParallelEntryData = new ArrayList<>(8196),
            _strictSequentialEntryData = new ArrayList<>(1024);

    private static final QueryArrayBlockingQueue _shedulerQueue = new QueryArrayBlockingQueue(2048,QueryType.typeA);
    private static TestableQuerySheduler _querySheduler = new TestableQuerySheduler(_shedulerQueue);

    private static final CustomThreadFactory _threadFactory = new CustomThreadFactory("STHREAD", Thread.MAX_PRIORITY);
    private static final Thread _thread = _threadFactory.newThread(_querySheduler);

    @BeforeAll
    public static void prepare() {
        ResourceUnpackManager.unpack("mthreads_test_data.csv");
    }

    @ParameterizedTest
    @Order(1)
    @CsvFileSource(files = "./mthreads_test_data.csv",encoding = "UTF-8", lineSeparator = "\n", delimiter = ',')
    public void setUp(Integer pTypeACount,Integer pTypeBCount) {
        if (pTypeACount < 0) throw new IllegalArgumentException("Unacceptable count="+pTypeACount+" of typeA queries");
        if (pTypeBCount < 0) throw new IllegalArgumentException("Unacceptable count="+pTypeACount+" of typeB queries");

        _strictParallelEntryData.clear();

        Random rnd = new Random(System.currentTimeMillis());

        rnd.ints(pTypeACount, -100,100).forEach((val) ->{
            _strictParallelEntryData.add(new QueryA(val));
        });

        rnd.ints(pTypeBCount, -100,100).forEach((val) ->{
            _strictParallelEntryData.add(new QueryB(val));
        });

        _strictParallelEntryData.add(new StopProcessingQuery());

        _strictSequentialEntryData.clear();

        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 5; j++) {
                _strictSequentialEntryData.add(new QueryA(j));
            }
        }

        _strictSequentialEntryData.add(new StopProcessingQuery());

        _typeACount = pTypeACount;
        _typeBCount = pTypeBCount;
    }

    @Test
    @Order(2)
    public void strictParallelProcessingOfDifferentTypesOfQueriesTest() {

        /* строгое распределение запросов по очередям соответствующего типа*/
        _strictParallelEntryData.forEach((query) -> {
            (new DefaultLoggablePushQueryCommand(query)).execute(_queueA, _queueB);
        });

        /*Верно ли, что не потерялся ни один из запросов типа А и в ней лишних запросов?*/
        Assertions.assertEquals(_typeACount, _queueA.size());

        /* подтверждение отсутствия в очереди типа А запросов типа В*/
        Assertions.assertDoesNotThrow(() -> {
            if (!_queueA.stream().allMatch((query) -> (query.getType() == QueryType.typeA)))
                throw new IllegalArgumentException("queueA is not supported for queryB");
        });

        //Верно ли, что не потерялся ни один из запросов типа В и в ней нет лишних запросов?
        Assertions.assertEquals(_typeBCount, _queueB.size());

        // подтверждение отсутствия в очереди типа В запросов типа А
        Assertions.assertDoesNotThrow(() -> {
            if (!_queueB.stream().allMatch((query) -> (query.getType() == QueryType.typeB)))
                throw new IllegalArgumentException("queueB is not supported for queryA");
        });
    }

    @Test
    @Order(3)
    public void strictSequentialProcessingOfSameTypeQueriesWithDifferentFeatureXTest(){
        _shedulerQueue.addAll(_strictSequentialEntryData);

        _thread.start();

        try {
            _thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /*
            Исключаем случай OutOfMemory (он предусмотрен переиспользованием существующих очередей и их обработчиков).
            Число потоков = 5, для 5 featureX. Если условие строгой последовательной обработки для запросов ОДНОГО типа
            нарушено, то в очереди для этого типа фич будет присутствовать фича с другим значением.
         */
        Assertions.assertDoesNotThrow(() -> {
            _querySheduler.getDataMap().forEach((featureX, data) -> {
                if (!data.stream().allMatch((query) -> (featureX == query.getFeatureX() && query.getType() == QueryType.typeA)))
                    throw new IllegalArgumentException("sequential processing condition is not met");
            });
        });
    }
}
