package ht.kreyola.kreyolaparser.model;

import java.util.List;
public class TripletResponse {
    List<Triplet> tripletList;
    int functionalSize;
    int inputOperationCount;
    int outputOperationCount;
    int readOperationCount;
    int writeOperationCount;

    public TripletResponse(List<Triplet> tripletList) {
        this.tripletList = tripletList;
    }

    public TripletResponse(List<Triplet> tripletList, int functionalSize, int inputOperationCount, int outputOperationCount, int readOperationCount, int writeOperationCount) {
        this.tripletList = tripletList;
        this.functionalSize = functionalSize;
        this.inputOperationCount = inputOperationCount;
        this.outputOperationCount = outputOperationCount;
        this.readOperationCount = readOperationCount;
        this.writeOperationCount = writeOperationCount;
    }

    public List<Triplet> getTripletList() {
        return tripletList;
    }

    public void setTripletList(List<Triplet> tripletList) {
        this.tripletList = tripletList;
    }

    public int getFunctionalSize() {
        return functionalSize;
    }

    public void setFunctionalSize(int functionalSize) {
        this.functionalSize = functionalSize;
    }

    public int getInputOperationCount() {
        return inputOperationCount;
    }

    public void setInputOperationCount(int inputOperationCount) {
        this.inputOperationCount = inputOperationCount;
    }

    public int getOutputOperationCount() {
        return outputOperationCount;
    }

    public void setOutputOperationCount(int outputOperationCount) {
        this.outputOperationCount = outputOperationCount;
    }

    public int getReadOperationCount() {
        return readOperationCount;
    }

    public void setReadOperationCount(int readOperationCount) {
        this.readOperationCount = readOperationCount;
    }

    public int getWriteOperationCount() {
        return writeOperationCount;
    }

    public void setWriteOperationCount(int writeOperationCount) {
        this.writeOperationCount = writeOperationCount;
    }
}
