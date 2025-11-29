package ch.supsi.fscli.backend.business;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;

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