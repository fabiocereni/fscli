package ch.supsi.fscli.backend.business;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonDeserializeBusiness {

    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
    }

    private JsonDeserializeBusiness() {}

    public static <T> T deserialize(String json, Class<T> clazz) {
        if (json == null || json.isBlank()) {
            return null;
        }
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
