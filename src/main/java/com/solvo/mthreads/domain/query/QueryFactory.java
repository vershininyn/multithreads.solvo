package com.solvo.mthreads.domain.query;

public class QueryFactory {

    public static IQuery parseAndCreateQuery(String pQueryAndFeatureString) {
        if ((pQueryAndFeatureString == null) || (pQueryAndFeatureString.isEmpty())) throw new IllegalArgumentException("string is null or empty");

        if (!pQueryAndFeatureString.contains(",")) throw new IllegalArgumentException("Wrong format of string");

        String[] queryAndFeature = pQueryAndFeatureString.toLowerCase().split(",");

        if (queryAndFeature.length > 2) throw new IllegalArgumentException("Wrong format of string");

        if (queryAndFeature[0] == null
                || queryAndFeature[0].isEmpty()
                || queryAndFeature[1] == null
                || queryAndFeature[1].isEmpty()) throw new IllegalArgumentException("Wrong format of string");

        if (queryAndFeature[1].contains(".")) throw new IllegalArgumentException("Feature X must be INTEGER number");

        Integer featureX = 0;

        try {
            featureX = Integer.parseInt(queryAndFeature[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Wrong format of feature X");
        }

        IQuery result = null;

        switch(queryAndFeature[0]) {
            case "typea" : {
                result = new QueryA(featureX);
                break;
            }
            case "typeb" : {
                result = new QueryB(featureX);
                break;
            }
            default: throw new IllegalArgumentException("Unknown type of query");
        }

        return result;
    }

}
