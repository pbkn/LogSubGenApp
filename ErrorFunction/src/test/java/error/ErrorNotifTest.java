package error;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

public class ErrorNotifTest {
    @Test
    public void successfulResponse() {
        ErrorNotif app = new ErrorNotif();
        Map<String, Object> input = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        data.put("data", "H4sIAAAAAAAA/62SW0/bQBCF/8pq1ce42Z2dvUp9ME2gqA1UxBQJgqp1vI4Mju36Ak0R/71KAJWqQgKpj2eOZvSdmbmj69B1fhWSTROoo5M4ib/PpvN5fDClI1rfVqGljiLjXEhkxmqgI1rWq4O2Hhrq6NjfduPSr9PMj8t61Q3pKlS+aaJPoSzrs7ots/2hWvZFXUXnrPqcnJ4edHnaPEyZ923wa+ooMMAxk2NuxhfvvsTJdJ5c5iwL6HGJmbXoc/AIwGSumfYizZWiI9oNabdsi2Y7fr8o+9B21F3Qv0CmbVu3Twwfz77Uq+hsAnHG7dX5rbiilzuS6U2o+m3zHS0y6qgwoCwozTUIIyVKK1CAtig1t2C44YxJBmisFmi4lVpJlGDoiPbFOnS9XzfUcc0VkwIsA8DR066po/MkPknISfgxhK4/zBzJ5TI1ueAR56AixJBHXqcqApVh5m3qtViSb6Htirpy5HFFi4rej/4FRqYNKqWtBIVaCQQEIVBqZSRwBdygQCWUQIvyZWD5HHh7oIjJiBvCtQPjOHsd8+HR/jEhcdM4bkhE7q7Dhn+48eUQ+Ihchw08CNgJ8SDE/UvJBOOoEVCDZExZJaVCYzgapS2CZsDQKqu4FApeTqaeJ5seTd56iP9AJ15JdzL9evz2T1n0k6H1/e5X+HttyLpb9HtFWYaM/HFgV56Fdd1uyLz4FRyRHMhsb9HP/E/yaJx2IXOEPxrb6Jf3vwFF1ZJYNwQAAA==");
        input.put("awslogs", data);
        Object result = app.handleRequest(input, null);
        assertNotNull(result);
    }
}
