package ch.supsi.fscli.backend.business;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonSerializerBusiness {

    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
    }

    private JsonSerializerBusiness() {}


    public static String serialize(AbstractFSBusiness abstractFSBusiness) {
        try {
            return mapper.writeValueAsString(abstractFSBusiness);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}