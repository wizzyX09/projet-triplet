package ht.kreyola.kreyolaparser.model;

import java.util.List;

public class Response {
    List<Triplet> tripletList;
    int functionalSize;
    int input;
    int output;
    int read;
    int write;

    public Response(List<Triplet> tripletList) {
        this.tripletList = tripletList;
    }

    public Response(List<Triplet> tripletList, int functionalSize, int input, int output, int read, int write) {
        this.tripletList = tripletList;
        this.functionalSize = functionalSize;
        this.input = input;
        this.output = output;
        this.read = read;
        this.write = write;
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

    public int getInput() {
        return input;
    }

    public void setInput(int input) {
        this.input = input;
    }

    public int getOutput() {
        return output;
    }

    public void setOutput(int output) {
        this.output = output;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public int getWrite() {
        return write;
    }

    public void setWrite(int write) {
        this.write = write;
    }
}
