package helloworld;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Handler for requests to Lambda function.
 */
public class App implements RequestHandler<Object, Object> {

    private static final Logger logger = LogManager.getLogger(App.class);

    public Object handleRequest(final Object input, final Context context) {
        logger.info(input.toString());
        return "SUCCESS";
    }

}
