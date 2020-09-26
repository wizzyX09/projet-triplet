package ht.kreyola.kreyolaparser.controller;

import ht.kreyola.kreyolaparser.model.ApiRequest;
import ht.kreyola.kreyolaparser.model.Response;
import ht.kreyola.kreyolaparser.model.RichSentence;
import ht.kreyola.kreyolaparser.model.Triplet;
import ht.kreyola.kreyolaparser.service.Parser;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3001")
public class ParsingController {

    private Parser parser;

    public ParsingController(Parser parser) {
        this.parser = parser;
    }

    @PostMapping("/parser")
    public Response getTriplets(@RequestBody ApiRequest<List<RichSentence>> apiRequest) {
        List<Triplet> triplets = parser.toTriplets(apiRequest.getBody());
        Response response = new Response(triplets);
        response.setFunctionalSize(triplets.size());
        return response;
    }

}
