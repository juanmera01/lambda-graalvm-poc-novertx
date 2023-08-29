package com.graalvmonlambda.product;


public class Request {

    private String httpMethod;

    private Long id;

    private Item item;

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCustomer(Item item) {
        this.item = item;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public Long getId() {
        return id;
    }

    public Item getCustomer() {
        return item;
    }
}