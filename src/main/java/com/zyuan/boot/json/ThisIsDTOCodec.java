package com.zyuan.boot.json;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.zyuan.boot.redis.dto.ThisIsDTO;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.Iterator;

public class ThisIsDTOCodec extends ObjectCodec {

    @Override
    public Version version() {
        return null;
    }

    @SneakyThrows
    @Override
    public <T> T readValue(JsonParser jsonParser, Class<T> aClass) throws IOException {
        ThisIsDTO thisIsDTO = (ThisIsDTO)aClass.newInstance();
        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String fieldName = jsonParser.getCurrentName();
            if ("name".equals(fieldName)) {
                jsonParser.nextToken();
                thisIsDTO.setName(jsonParser.getText());
            } else if ("age".equals(fieldName)) {
                jsonParser.nextToken();
                thisIsDTO.setAge(jsonParser.getIntValue());
            } else if ("time".equals(fieldName)) {
                jsonParser.nextToken();
                thisIsDTO.setTime(jsonParser.getLongValue());
            } else if ("iiid".equals(fieldName)) {
                jsonParser.nextToken();
            }
        }
        return (T)thisIsDTO;
    }

    @Override
    public <T> T readValue(JsonParser jsonParser, TypeReference<T> typeReference) throws IOException {
        return null;
    }

    @Override
    public <T> T readValue(JsonParser jsonParser, ResolvedType resolvedType) throws IOException {
        return null;
    }

    @Override
    public <T> Iterator<T> readValues(JsonParser jsonParser, Class<T> aClass) throws IOException {
        return null;
    }

    @Override
    public <T> Iterator<T> readValues(JsonParser jsonParser, TypeReference<T> typeReference) throws IOException {
        return null;
    }

    @Override
    public <T> Iterator<T> readValues(JsonParser jsonParser, ResolvedType resolvedType) throws IOException {
        return null;
    }

    @Override
    public void writeValue(JsonGenerator jsonGenerator, Object o) throws IOException {

    }

    @Override
    public <T extends TreeNode> T readTree(JsonParser jsonParser) throws IOException {
        return null;
    }

    @Override
    public void writeTree(JsonGenerator jsonGenerator, TreeNode treeNode) throws IOException {

    }

    @Override
    public TreeNode createObjectNode() {
        return null;
    }

    @Override
    public TreeNode createArrayNode() {
        return null;
    }

    @Override
    public JsonParser treeAsTokens(TreeNode treeNode) {
        return null;
    }

    @Override
    public <T> T treeToValue(TreeNode treeNode, Class<T> aClass) throws JsonProcessingException {
        return null;
    }
}
