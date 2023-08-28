package com.graalvmonlambda.product;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ProductRequestHandler implements RequestHandler<Request, Object> {

    @Override
    public Object handleRequest(Request request, Context context) {

        DynamoDbClient dynamodbClient = DynamoDbClient.create();
        TableSchema<Customer> customerSchema = TableSchema.fromBean(Customer.class);
        final DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamodbClient).build();
        DynamoDbTable<Customer> customerTable = enhancedClient.table(
                "dxcassure-feature-127-java-lambda-research-service-customer_table",
                customerSchema
        );;


        Customer c = null;
        if(request.getHttpMethod() == null){
            request.setHttpMethod("GET");
        }
        switch (request.getHttpMethod()){
            case "POST":
                c = request.getCustomer();
                if(c != null) {
                    c.setId(ThreadLocalRandom.current().nextLong(0, 10000));
                    customerTable.putItem(c);
                }
                return c;
            case "DELETE":
                c = customerTable.getItem(Key.builder().partitionValue(request.getId()).build());
                if (c != null)
                    customerTable.deleteItem(c);
                return c;
            default:
                if(request.getId() == null){
                    List<Customer> customers = new ArrayList<>();
                    PageIterable<Customer> res = customerTable.scan();
                    for(Page<Customer> p : res){
                        customers.addAll(p.items());
                    }
                    return customers;
                }else {
                    c = customerTable.getItem(Key.builder().partitionValue(request.getId()).build());
                    return c;
                }
        }
    }

}
