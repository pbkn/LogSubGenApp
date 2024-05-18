package helloworld;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class AppTest {
    @Test
    public void successfulResponse() {
        App app = new App();
        Map<String, String> input = new HashMap<>();
        input.put("key1", "value1");
        input.put("key2", "value2");
        input.put("key3", "value3");
        Object result = app.handleRequest(input, null);
        assertNotNull(result);
    }
}
