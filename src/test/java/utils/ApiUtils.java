package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiUtils {

    static ObjectMapper objectMapper = new ObjectMapper();
    static String json;

    public static String serializePOJO(Object object) {
        try {
            json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}
