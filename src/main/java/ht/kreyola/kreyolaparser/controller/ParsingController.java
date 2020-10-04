package ht.kreyola.kreyolaparser.controller;

import ht.kreyola.kreyolaparser.contants.Language;
import ht.kreyola.kreyolaparser.model.*;
import ht.kreyola.kreyolaparser.service.parsers.Parser;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

import static ht.kreyola.kreyolaparser.contants.OperationType.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ParsingController {

    private final Parser frenchParser;
    private final Parser englishParser;

    public ParsingController(Parser frenchParser, Parser englishParser) {
        this.frenchParser = frenchParser;
        this.englishParser = englishParser;
    }

    @PostMapping("/parser")
    public TripletResponse getTriplets(@RequestBody ApiRequest<TripletRequest> apiRequest) {
        final boolean useFrench = Language.FR.equals(apiRequest.getBody().getUseCaseLanguage());
        List<Triplet> triplets = useFrench ? frenchParser.toTriplets(apiRequest.getBody()): englishParser.toTriplets(apiRequest.getBody());
        int inputOperationCount = (int) triplets.stream().filter(triplet -> IN.equals(triplet.getOperationType())).count();
        int outputOperationCount = (int) triplets.stream().filter(triplet -> OUT.equals(triplet.getOperationType())).count();
        int readOperationCount = (int) triplets.stream().filter(triplet -> READ.equals(triplet.getOperationType())).count();
        int writeOperationCount = (int) triplets.stream().filter(triplet -> WRITE.equals(triplet.getOperationType())).count();
        TripletResponse tripletResponse = new TripletResponse(triplets);
        tripletResponse.setFunctionalSize(triplets.size());
        tripletResponse.setInputOperationCount(inputOperationCount);
        tripletResponse.setOutputOperationCount(outputOperationCount);
        tripletResponse.setReadOperationCount(readOperationCount);
        tripletResponse.setWriteOperationCount(writeOperationCount);

        return tripletResponse;
    }

}
