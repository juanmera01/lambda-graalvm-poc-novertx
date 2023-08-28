package com.graalvmonlambda.product;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.ArrayList;
import java.util.List;

public class ProductRequestHandler implements RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {

    DynamoDbClient dynamodbClient = DynamoDbClient.create();
    TableSchema<Customer> customerSchema = TableSchema.fromBean(Customer.class);
    final DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
            .dynamoDbClient(dynamodbClient).build();
    DynamoDbTable<Customer> customerTable = enhancedClient.table(
            "dxcassure-feature-127-java-lambda-research-service-customer_table",
            customerSchema
    );;

    @Override
    public APIGatewayV2HTTPResponse handleRequest(APIGatewayV2HTTPEvent event, Context context) {



        List<Customer> customers = new ArrayList<>();
        PageIterable<Customer> res = customerTable.scan();
        for(Page<Customer> p : res){
            customers.addAll(p.items());
        }

        return APIGatewayV2HTTPResponse.builder()
                .withBody(customers.toString())
                .withStatusCode(200)
                .build();
    }
}
