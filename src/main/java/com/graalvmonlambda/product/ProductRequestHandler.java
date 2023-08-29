package com.graalvmonlambda.product;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.ArrayList;
import java.util.List;

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
        );

        List<Customer> customers = new ArrayList<>();
        //
        return customers;
    }

}
