package com.solvo.mthreads;

import com.solvo.mthreads.domain.query.IQuery;
import com.solvo.mthreads.domain.query.QueryA;
import com.solvo.mthreads.domain.query.QueryB;
import com.solvo.mthreads.domain.query.QueryFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class PushCommandTests {

    private static ArrayList<String> _failCmdData = new ArrayList<>(32);
    private static ArrayList<String> _successCmdData = new ArrayList<>(16);

    @BeforeAll
    public static void setUpCommands() {
        _failCmdData.add("");
        _failCmdData.add(",");
        _failCmdData.add("typeA");
        _failCmdData.add("typeA 1 2.0 3.0");
        _failCmdData.add("typeA,");
        _failCmdData.add("typeA,-1.0");
        _failCmdData.add("-1.0");
        _failCmdData.add("1");

        _successCmdData.add("typeA,-1");
        _successCmdData.add("typea,-1");
        _successCmdData.add("typeB,1");
        _successCmdData.add("typeb,1");
    }

    public void pushCommandFailedTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            _failCmdData.forEach((fcmddata) -> {
                QueryFactory.parseAndCreateQuery(fcmddata);
            });
        });
    }

    @Test
    public void pushCommandSuccessTest() {
        ArrayList<IQuery> expected = new ArrayList<IQuery>();
        expected.add(new QueryA(-1));
        expected.add(new QueryA(-1));
        expected.add(new QueryB(1));
        expected.add(new QueryB(1));

        ArrayList<IQuery> actuals = new ArrayList<IQuery>();
        _successCmdData.forEach((val) -> actuals.add(QueryFactory.parseAndCreateQuery(val)));

        Assertions.assertArrayEquals(expected.toArray(), actuals.toArray());
    }
}
