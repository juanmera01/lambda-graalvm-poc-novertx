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

    static final TableSchema<Item> itemTableSchema =
            TableSchema.builder(Item.class)
                    .newItemSupplier(Item::new)
                    .addAttribute(Long.class, a -> a.name("id")
                            .getter(Item::getId)
                            .setter(Item::setId)
                            .tags(StaticAttributeTags.primaryPartitionKey()))
                    .addAttribute(String.class, a -> a.name("name")
                            .getter(Item::getName)
                            .setter(Item::setName))
                    .addAttribute(Double.class, a -> a.name("price")
                            .getter(Item::getPrice)
                            .setter(Item::setPrice))
                    .build();

    @Override
    public Object handleRequest(Request request, Context context) {

        DynamoDbClient dynamodbClient = DynamoDbClient.create();
        final DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamodbClient).build();
        DynamoDbTable<Item> itemTable = enhancedClient.table(
                "dxcassure-feature-127-java-lambda-research-service-item_table",
                itemTableSchema
        );

        List<Item> items = new ArrayList<>();
        PageIterable<Item> res = itemTable.scan();
        for(Page<Item> p : res){
            items.addAll(p.items());
        }
        return items;
    }

}
