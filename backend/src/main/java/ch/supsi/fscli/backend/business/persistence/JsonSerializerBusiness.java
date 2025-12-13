package ch.supsi.fscli.backend.business.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonSerializerBusiness {

    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
    }

    private JsonSerializerBusiness() {}

    public static String serialize(PersistedWrapper toSerialize) {
        try {
            return mapper.writeValueAsString(toSerialize);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}