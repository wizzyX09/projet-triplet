package ht.kreyola.kreyolaparser.service.parsers;

import ht.kreyola.kreyolaparser.model.RichSentence;
import ht.kreyola.kreyolaparser.model.Triplet;
import ht.kreyola.kreyolaparser.model.TripletRequest;

import java.util.List;

public interface Parser {

    List<Triplet> toTriplets(TripletRequest tripletRequest);
}
