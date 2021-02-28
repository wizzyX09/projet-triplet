package ht.kreyola.kreyolaparser.util;

import ht.kreyola.kreyolaparser.contants.OperationType;

import java.util.HashSet;
import java.util.Set;

import static ht.kreyola.kreyolaparser.contants.OperationType.*;
import static ht.kreyola.kreyolaparser.contants.Operations.*;
import static ht.kreyola.kreyolaparser.contants.Operations.writeOperations;

public class TripletHelpers {
    final static Set<String> inputOperationSet = new HashSet<>(inputOperations);
    final static Set<String> outputOperationSet = new HashSet<>(outputOperations);
    final static Set<String> readOperationSet = new HashSet<>(readOperations);
    final static Set<String> writeOperationSet = new HashSet<>(writeOperations);

    public static OperationType getOperationType(final String verb) {
        if (inputOperationSet.contains(verb))
            return IN;
        else if (outputOperationSet.contains(verb))
            return OUT;
        else if (readOperationSet.contains(verb))
            return READ;
        else if (writeOperationSet.contains(verb))
            return WRITE;
        else return UNKNOWN;
    }

}
