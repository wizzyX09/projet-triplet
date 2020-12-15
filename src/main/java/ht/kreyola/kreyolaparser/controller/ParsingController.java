package ht.kreyola.kreyolaparser.controller;

import ht.kreyola.kreyolaparser.model.ApiRequest;
import ht.kreyola.kreyolaparser.model.TripletResponse;
import ht.kreyola.kreyolaparser.model.RichSentence;
import ht.kreyola.kreyolaparser.model.Triplet;
import ht.kreyola.kreyolaparser.service.Parser;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static ht.kreyola.kreyolaparser.contants.Operations.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ParsingController {

    private Parser parser;

    public ParsingController(Parser parser) {
        this.parser = parser;
    }

    @PostMapping("/parser")
    public TripletResponse getTriplets(@RequestBody ApiRequest<List<RichSentence>> apiRequest) {
        List<Triplet> triplets = parser.toTriplets(apiRequest.getBody());
        final Set<String> inputOperationSet = new HashSet<>(inputOperations);
        final Set<String> outputOperationSet = new HashSet<>(outputOperations);
        final Set<String> readOperationSet = new HashSet<>(readOperations);
        final Set<String> writeOperationSet = new HashSet<>(writeOperations);
        int inputOperationCount = (int) triplets.stream().filter(triplet -> inputOperationSet.contains(triplet.getVerb().toLowerCase())).count();
        int outputOperationCount = (int) triplets.stream().filter(triplet -> outputOperationSet.contains(triplet.getVerb().toLowerCase())).count();
        int readOperationCount = (int) triplets.stream().filter(triplet -> readOperationSet.contains(triplet.getVerb().toLowerCase())).count();
        int writeOperationCount = (int) triplets.stream().filter(triplet -> writeOperationSet.contains(triplet.getVerb().toLowerCase())).count();
        TripletResponse tripletResponse = new TripletResponse(triplets);
        tripletResponse.setFunctionalSize(triplets.size());
        tripletResponse.setInputOperationCount(inputOperationCount);
        tripletResponse.setOutputOperationCount(outputOperationCount);
        tripletResponse.setReadOperationCount(readOperationCount);
        tripletResponse.setWriteOperationCount(writeOperationCount);

        return tripletResponse;
    }

}
