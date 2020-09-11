package ht.kreyola.kreyolaparser.model;

public class ApiRequest<T> {
    T body;

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
