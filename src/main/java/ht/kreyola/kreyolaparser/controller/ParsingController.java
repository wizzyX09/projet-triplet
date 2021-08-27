package ht.kreyola.kreyolaparser.controller;

import ht.kreyola.kreyolaparser.contants.Language;
import ht.kreyola.kreyolaparser.model.*;
import ht.kreyola.kreyolaparser.service.parsers.Parser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ht.kreyola.kreyolaparser.contants.OperationType.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ParsingController {

    private final Parser frenchParser;
    private final Parser englishParser;
    private final Set<String> excludedTriplets = new HashSet<>();

    public ParsingController(Parser frenchParser, Parser englishParser) {
        this.frenchParser = frenchParser;
        this.englishParser = englishParser;
    }

    @PostMapping("/triplets/parser")
    public TripletResponse getTriplets(@RequestBody ApiRequest<TripletRequest> apiRequest) {
        final boolean useFrench = Language.FR.equals(apiRequest.getBody().getUseCaseLanguage());
        List<Triplet> triplets = useFrench ? frenchParser.toTriplets(apiRequest.getBody()): englishParser.toTriplets(apiRequest.getBody());
        List<Triplet> filteredTriplets = triplets.stream().map(triplet -> {
            String stringBuilder = triplet.getSubject() +
                    triplet.getVerb() +
                    triplet.getComplement();
            triplet.setInclude(!excludedTriplets.contains(stringBuilder));
            return triplet;
        }).collect(Collectors.toList());
        int inputOperationCount = (int) filteredTriplets.stream().filter(triplet -> IN.equals(triplet.getOperationType()) && triplet.isInclude()).count();
        int outputOperationCount = (int) filteredTriplets.stream().filter(triplet -> OUT.equals(triplet.getOperationType()) && triplet.isInclude()).count();
        int readOperationCount = (int) filteredTriplets.stream().filter(triplet -> READ.equals(triplet.getOperationType()) && triplet.isInclude()).count();
        int writeOperationCount = (int) filteredTriplets.stream().filter(triplet -> WRITE.equals(triplet.getOperationType()) && triplet.isInclude()).count();
        TripletResponse tripletResponse = new TripletResponse(filteredTriplets);
        tripletResponse.setFunctionalSize(filteredTriplets.size());
        tripletResponse.setInputOperationCount(inputOperationCount);
        tripletResponse.setOutputOperationCount(outputOperationCount);
        tripletResponse.setReadOperationCount(readOperationCount);
        tripletResponse.setWriteOperationCount(writeOperationCount);

        return tripletResponse;
    }

    @PostMapping("/triplet/exclude")
    public ResponseEntity<HttpStatus> excludeTriplet(@RequestBody Triplet triplet) {
        String tripletId = triplet.getSubject() +
                triplet.getVerb() +
                triplet.getComplement();
        excludedTriplets.add(tripletId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/triplet/include")
    public ResponseEntity<HttpStatus> includeTriplet(@RequestBody Triplet triplet) {
        String tripletId = triplet.getSubject() +
                triplet.getVerb() +
                triplet.getComplement();
        excludedTriplets.remove(tripletId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
