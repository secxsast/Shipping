/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.example;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.PurgeQueueRequest;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Element;

import java.io.StringWriter;
import java.util.*;

@Component
public class SendReceiveMessages {


        @Value("${ship.awsServices.SQSName1}")
        private String SQSName1;

        @Value("${ship.awsServices.SQSName2}")
        private String SQSName2;

 private SqsClient getClient() {
     SqsClient sqsClient = SqsClient.builder()
             .region(Region.US_WEST_2)
             .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
             .build();

        return sqsClient;
     }


    public void processMessage(com.example.Message msg) {

        SqsClient sqsClient = SqsClient.builder()
                .region(Region.US_WEST_2)
                .build();

        try {

            // Get user
            MessageAttributeValue attributeValue = MessageAttributeValue.builder()
                    .stringValue(msg.getName())
                    .dataType("String")
                    .build();

            Map myMap = new HashMap<String, MessageAttributeValue>();
            myMap.put("Name", attributeValue);

            // generate the work item ID
            UUID uuid = UUID.randomUUID();
            String msgId1 = uuid.toString();

            SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
                    .queueName(SQSName1)
                    .messageAttributes(myMap)
                    .messageGroupId("GroupA")
                    .messageDeduplicationId(msgId1)
                    .messageBody(msg.getBody())
                    .build();
            sqsClient.sendMessage(sendMsgRequest);

            SendMessageRequest sendMsgRequest1 = SendMessageRequest.builder()
                    .queueName(SQSName2)
                    .messageAttributes(myMap)
                    .messageGroupId("GroupA")
                    .messageDeduplicationId(msgId1)
                    .messageBody(msg.getBody())
                    .build();
            sqsClient.sendMessage(sendMsgRequest1);


        } catch (SqsException e) {
             e.getStackTrace();
        }

    }
	
}