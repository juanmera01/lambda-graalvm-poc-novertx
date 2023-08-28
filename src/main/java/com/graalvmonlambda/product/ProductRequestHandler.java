package com.graalvmonlambda.product;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import java.util.List;

public class ProductRequestHandler implements RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {


    @Override
    public APIGatewayV2HTTPResponse handleRequest(APIGatewayV2HTTPEvent event, Context context) {
        DynamoDBMapper mapper;
        try {
            AmazonDynamoDB db = AmazonDynamoDBClientBuilder.defaultClient();
            mapper = new DynamoDBMapper(db);
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Could not initialize Spring Boot Application", e);
        }
        List<Customer> customers;
        customers = mapper.scan(Customer.class, new DynamoDBScanExpression());

        return APIGatewayV2HTTPResponse.builder()
                .withBody(customers.toString())
                .withStatusCode(200)
                .build();
    }
}
