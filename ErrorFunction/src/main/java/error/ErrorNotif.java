package error;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.SnsException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class ErrorNotif implements RequestHandler<Map<String, Object>, String> {

    private static final Logger logger = LogManager.getLogger(ErrorNotif.class);

    @Override
    public String handleRequest(Map<String, Object> input, Context context) {
        // Extract the data field
        Map<String, Object> awsLogs = (Map<String, Object>) input.get("awslogs");
        String encodedData = (String) awsLogs.get("data");

        try {
            // Decode the data from Base64
            byte[] decodedData = Base64.decodeBase64(encodedData);

            // Decompress the data using GZIP
            String decompressedData = decompressGzip(decodedData);
            logger.info("Decompressed data: {}", decompressedData);

            // Parse the JSON log events
            Map<String, Object> logEvent = new ObjectMapper().readValue(decompressedData, HashMap.class);
            List<Map<String, Object>> logEvents = (List<Map<String, Object>>) logEvent.get("logEvents");

            // Process each log event
            for (Map<String, Object> event : logEvents) {
                String message = (String) event.get("message");

                // Check for exclusion phrases
                if (message.contains("Error Connection") || message.contains("Failed Connection")) {
                    logger.info("Log message excluded due to containing known error phrases.");
                    continue; // Skip this log
                }

                // Process the log event
                processLogMessage(message);
            }
        } catch (IOException e) {
            logger.error("Error processing log data", e);
        }

        return "SUCCESS";
    }

    private void processLogMessage(String message) {
        try (SnsClient snsClient = SnsClient.create()) {
            String errorMsg = "Something unexpected happened! Go check your system";
            errorMsg = errorMsg.concat(System.lineSeparator());
            errorMsg = errorMsg.concat("Go to https://ap-south-1.console.aws.amazon.com/console/home?region=ap-south-1#");
            String topicArn = "arn:aws:sns:ap-south-1:401135408972:ErrorNotification"; // Already created and subscribed
            pubTopic(snsClient, errorMsg, topicArn);
        }
    }

    public static void pubTopic(SnsClient snsClient, String message, String topicArn) {
        try {
            PublishRequest request = PublishRequest.builder().message(message).subject("Error / Exception occurred in your AWS SAM App") // Added explicitly for email subscribers
                    .topicArn(topicArn).build();

            PublishResponse result = snsClient.publish(request);
            logger.info("{} Message sent. Status is {}", result.messageId(), result.sdkHttpResponse().statusCode());

        } catch (SnsException e) {
            logger.error(e.awsErrorDetails().errorMessage());
        }
    }

    private String decompressGzip(byte[] compressedData) throws IOException {
        try (GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(compressedData)); InputStreamReader reader = new InputStreamReader(gis); BufferedReader in = new BufferedReader(reader)) {

            StringBuilder decompressedData = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                decompressedData.append(line);
            }
            return decompressedData.toString();
        }
    }
}
