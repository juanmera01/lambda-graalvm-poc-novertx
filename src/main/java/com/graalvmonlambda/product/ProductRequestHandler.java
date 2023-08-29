package com.graalvmonlambda.product;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.StaticAttributeTags;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.ArrayList;
import java.util.List;

public class ProductRequestHandler implements RequestHandler<Request, Object> {

    static final TableSchema<Order> orderTableSchema =
            TableSchema.builder(Order.class)
                    .newItemSupplier(Order::new)
                    .addAttribute(Long.class, a -> a.name("id")
                            .getter(Order::getId)
                            .setter(Order::setId)
                            .tags(StaticAttributeTags.primaryPartitionKey()))
                    .addAttribute(Long.class, a -> a.name("customerId")
                            .getter(Order::getCustomerId)
                            .setter(Order::setCustomerId))
                    .addAttribute(List.class, a -> a.name("orderLine")
                            .getter(Order::getOrderLine)
                            .setter(Order::setOrderLine))
                    .build();

    @Override
    public Object handleRequest(Request request, Context context) {

        DynamoDbClient dynamodbClient = DynamoDbClient.create();
        final DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamodbClient).build();
        DynamoDbTable<Order> orderTable = enhancedClient.table(
                "dxcassure-feature-127-java-lambda-research-service-order_table",
                orderTableSchema
        );

        List<Order> orders = new ArrayList<>();
        PageIterable<Order> res = orderTable.scan();
        for(Page<Order> p : res){
            orders.addAll(p.items());
        }
        return orders;
    }

}
