package com.graalvmonlambda.product;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.StaticAttributeTags;
import software.amazon.awssdk.enhanced.dynamodb.mapper.StaticTableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.ArrayList;
import java.util.List;

public class ProductRequestHandler implements RequestHandler<Request, Object> {

    static final TableSchema<Customer> customerTableSchema =
            TableSchema.builder(Customer.class)
                    .newItemSupplier(Customer::new)
                    .addAttribute(Long.class, a -> a.name("id")
                            .getter(Customer::getId)
                            .setter(Customer::setId)
                            .tags(StaticAttributeTags.primaryPartitionKey()))
                    .addAttribute(String.class, a -> a.name("email")
                            .getter(Customer::getEmail)
                            .setter(Customer::setEmail)
                            .tags(StaticAttributeTags.primarySortKey()))
                    .addAttribute(String.class, a -> a.name("name")
                            .getter(Customer::getName)
                            .setter(Customer::setName))
                    .addAttribute(String.class, a -> a.name("firstname")
                            .getter(Customer::getFirstname)
                            .setter(Customer::setFirstname))
                    .addAttribute(String.class, a -> a.name("city")
                            .getter(Customer::getCity)
                            .setter(Customer::setCity))
                    .addAttribute(String.class, a -> a.name("street")
                            .getter(Customer::getStreet)
                            .setter(Customer::setStreet))
                    .build();

    @Override
    public Object handleRequest(Request request, Context context) {

        DynamoDbClient dynamodbClient = DynamoDbClient.create();
        final DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamodbClient).build();
        DynamoDbTable<Customer> customerTable = enhancedClient.table(
                "dxcassure-feature-127-java-lambda-research-service-customer_table",
                customerTableSchema
        );

        List<Customer> customers = new ArrayList<>();
        //
        return customers;
    }

}
