package com.graalvmonlambda.product;


public class Request {

    private String httpMethod;

    private Long id;

    private Order order;

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCustomer(Order order) {
        this.order = order;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public Long getId() {
        return id;
    }

    public Order getCustomer() {
        return order;
    }
}