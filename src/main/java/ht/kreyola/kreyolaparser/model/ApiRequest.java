package ht.kreyola.kreyolaparser.model;

public class ApiRequest<T> {
    T body;
    String requestId;

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
