package ch.supsi.fscli.backend.business;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonSerializerBusiness {

    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
    }

    private JsonSerializerBusiness() {}


    public static String serialize(FSStateBusiness stateToSerialize) {
        try {
            return mapper.writeValueAsString(stateToSerialize);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}