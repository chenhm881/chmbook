package com.chm.book.fileapi.serialization;

import com.chm.book.fileapi.domain.BatchInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;


import java.util.Map;

public class FileDeserializer implements Deserializer<BatchInfo> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public BatchInfo deserialize(String s, byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();
        BatchInfo blog = null;
        try {
            blog = mapper.readValue(bytes, BatchInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return blog;
    }


    @Override
    public void close() {

    }
}
