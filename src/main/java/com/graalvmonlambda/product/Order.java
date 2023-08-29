package com.graalvmonlambda.product;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.ArrayList;
import java.util.List;

@DynamoDbBean
public class Order {

    private long id;

    private long customerId;

    private List<OrderLine> orderLine;

    public Order() {
        super();
        orderLine = new ArrayList<OrderLine>();
    }

    public void setId(long id) {
        this.id = id;
    }

    @DynamoDbPartitionKey
    public long getId() {
        return id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public List<OrderLine> getOrderLine() {
        return orderLine;
    }

    public Order(long customerId) {
        super();
        this.customerId = customerId;
        this.orderLine = new ArrayList<OrderLine>();
    }

    public void setOrderLine(List<OrderLine> orderLine) {
        this.orderLine = orderLine;
    }

    public void addLine(int count, long itemId) {
        this.orderLine.add(new OrderLine(count, itemId));
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
