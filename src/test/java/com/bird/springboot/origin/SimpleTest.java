package com.bird.springboot.origin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bird.elasticsearch.beans.Article;
import com.dslplatform.json.DslJson;
import com.dslplatform.json.JsonReader;
import com.dslplatform.json.JsonWriter;
import com.dslplatform.json.ObjectConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsoniter.JsonIterator;
import com.jsoniter.ValueType;
import lombok.Data;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

import java.io.*;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class SimpleTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    public static String[] factors = {};
    private static String JSON_STR = null;
    private static int count = 0;
    private final DslJson<Object> dslJson = new DslJson<Object>();

    @Test
    public void testJsoniter2() throws IOException {
        JsonIterator jsonIterator = JsonIterator.parse("[123,{'id':20, 'errorMsg':'this error'},456]".replace('\'', '"'));
        AtomicReference<ErrorMessage> errorMessage = new AtomicReference<>();
        jsonIterator.readAny().forEach(any -> {
            if (any.valueType() == ValueType.OBJECT) {
                errorMessage.set(any.as(ErrorMessage.class));
            }
        });
        System.out.println(errorMessage.get());
        Assert.isTrue(true, "Not to false");
    }

    @Test
    public void testJsoniter() throws IOException {
        JsonIterator jsonIterator = JsonIterator.parse("{\"id\":10, \"errorMsg\":\"this error\"}".replace('\'', '"'));
        ErrorMessage errorMessage = jsonIterator.read(ErrorMessage.class);
        System.out.println(errorMessage);
        Assert.notNull(errorMessage, "can't be empty ");
    }
    @Data
    public static class ErrorMessage{
        int id;
        String error;
    }

    private String err = "{\"id\":10, \"errorMsg\":\"this error\"}";

    @Test
    public void testFor() throws IOException {
        File file = new File("out.txt");
        file.mkdirs();
        PrintWriter printWriter = new PrintWriter(file);
        StringBuffer buffer = new StringBuffer();
        buffer.append("{");
        for (int i = 0; i < 1000000; i++) {
            if (i == 999999) {
                buffer.append("\"k" + i + "\":" + "\"v" + i + "\"" + "}");
                break;
            }
            buffer.append("\"k" + i + "\":" + "\"v" + i + "\"" + ",");
        }
        printWriter.println(buffer.toString());
        printWriter.flush();
    }

    @Before
    public void init() throws Exception {
        JSON_STR = "{\"id\":\"sdfasfasdfasdddddddddddddddddddddddddddddddddddddddddddddd\"}";
    }

    @Test
    public void testFastJson() {
        long startTime = System.currentTimeMillis();
        Map<String, String> map = JSON.parseObject(JSON_STR, Map.class);
        map.forEach((k, v) -> {
            count++;
        });
        System.out.println(System.currentTimeMillis() - startTime);
        System.out.println(count);
    }

    @Test
    public void testJsoniter03() throws IOException {
        long startTime = System.currentTimeMillis();
        JsonIterator jsonIterator = JsonIterator.parse(JSON_STR);
        Map<String, String> map = jsonIterator.readAny().as(Map.class);
        map.forEach((k, v) -> {
            count++;
        });
        System.out.println(System.currentTimeMillis() - startTime);
        System.out.println(count);
    }

    @Test
    public void testJackson() throws IOException {
        long startTime = System.currentTimeMillis();
        Map<String, String> map = MAPPER.readValue(JSON_STR, Map.class);
        map.forEach((k, v) -> {
            count++;
        });
        System.out.println(System.currentTimeMillis() - startTime);
        System.out.println(count);
    }

    @Test
    public void testDslJson() throws IOException {
        long startTime = System.currentTimeMillis();
        final byte[] buff = JSON_STR.getBytes("UTF-8");
        JsonReader<Object> jsonReader = dslJson.newReader(buff);
        Map map = jsonReader.next(Map.class);
        map.forEach((k, v) -> {
            count++;
        });
        System.out.println(System.currentTimeMillis() - startTime);
        System.out.println(count);
    }

    @Test
    public void writeDslJson() throws IOException {

    }
}
