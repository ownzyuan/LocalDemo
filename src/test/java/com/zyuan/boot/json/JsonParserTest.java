package com.zyuan.boot.json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.zyuan.boot.redis.dto.ThisIsDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JsonParserTest {


    /**
     * 直接在业务层做取字段的操作
     */
    @Test
    public void useJsonParser() {
        String jsonStr = "{\"name\":\"name88\",\"age\":240,\"time\":160,\"iiid\":null}";
        ThisIsDTO thisIsDTO = new ThisIsDTO();
        JsonFactory jsonFactory = new JsonFactory();
        String key;
        try {
            JsonParser parser = jsonFactory.createParser(jsonStr);
            // ObjectMapper的_initForReading方法做的事情
//            JsonToken currentToken = parser.getCurrentToken();
//            if (currentToken == null) {
//                currentToken = parser.nextToken();
//            }
//            // UntypedObjectDeserializer的mapObject(JsonParser p, DeserializationContext ctxt)做的事情
//            if (currentToken == JsonToken.START_OBJECT) {
//                key = parser.nextFieldName();
//            } else if (currentToken == JsonToken.FIELD_NAME) {
//                key = parser.currentName();
//            } else {
//                if (currentToken != JsonToken.END_OBJECT) {
//                    System.out.println("要结束了");
//                }
//                key = null;
//            }
            while (parser.nextToken() != JsonToken.END_OBJECT) {
                String fieldName = parser.getCurrentName();
                if ("name".equals(fieldName)) {
                    parser.nextToken();
                    thisIsDTO.setName(parser.getText());
                } else if ("age".equals(fieldName)) {
                    parser.nextToken();
                    thisIsDTO.setAge(parser.getIntValue());
                } else if ("time".equals(fieldName)) {
                    parser.nextToken();
                    thisIsDTO.setTime(parser.getLongValue());
                } else if ("iiid".equals(fieldName)) {
                    parser.nextToken();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 自定义一个 ObjectCodec，ThisIsDTO专用：用于把JSON串自动绑定到实例属性
     */
    @Test
    public void useJsonParserByCodec() {
        String jsonStr = "{\"name\":\"name88\",\"age\":240,\"time\":160,\"iiid\":null}";
        JsonFactory jsonFactory = new JsonFactory();
        try {
            JsonParser parser = jsonFactory.createParser(jsonStr);
            parser.setCodec(new ThisIsDTOCodec());
            ThisIsDTO thisIsDTO = parser.readValueAs(ThisIsDTO.class);
            System.out.println(thisIsDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
