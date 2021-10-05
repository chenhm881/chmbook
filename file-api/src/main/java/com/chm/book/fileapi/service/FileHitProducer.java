package com.chm.book.fileapi.service;

import com.chm.book.fileapi.domain.BatchInfo;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Properties;

@Service
public class FileHitProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    private Properties properties = new Properties();

    private static final String topic = "file";

    private static final int partition = 1;

    private static final String key = "";

    public void send(BatchInfo batchInfo) {
        this.send(topic, partition, key, batchInfo);
    }

    public void send(String test) {
        this.send(topic, partition, key, test);
    }

    private Logger log = LoggerFactory.getLogger(FileHitProducer.class);

    @Transactional(rollbackFor = RuntimeException.class)
    public void send(String topic, Integer partition, String key, Object batchInfo) {

        ProducerRecord<String, Object>  producerRecord = new ProducerRecord<>(topic, batchInfo);
        kafkaTemplate.executeInTransaction(operations -> {
            ListenableFuture<SendResult<String, Object>> future = operations.send(producerRecord);
            future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
                @Override
                public void onFailure(Throwable throwable) {
                    //failure
                    log.info(topic + " - send failed：" + throwable.getMessage());
                }

                @Override
                public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
                    //successfully
                    log.info(topic + " - send successfully：" + stringObjectSendResult.toString());
                }
            });
            return true;
        });
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void send(String topic, Integer partition, String key, String value) {

        ProducerRecord<String, String>  producerRecord = new ProducerRecord<>(topic, value);
        kafkaTemplate.executeInTransaction( tx -> {
            tx.send(producerRecord);
            return true;
        });

    }
}
