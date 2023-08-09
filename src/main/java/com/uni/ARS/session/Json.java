package com.uni.ARS.session;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class Json {

    private ObjectMapper mapper = new ObjectMapper();

    public JsonNode toJson(Object a){
        return mapper.valueToTree(a);
    }

    public String stringify(JsonNode node) throws JsonProcessingException {
        ObjectWriter writer = mapper.writer();
        return writer.writeValueAsString(node);
    }
}
