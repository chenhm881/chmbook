package com.chm.book.fileapi.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileHitConsumer {


//    @KafkaListener(id = "", topics = {"article"}, groupId = "article.first")
//    public void getArticle(ConsumerRecord<String, Article> consumerRecord) {
//        System.out.println("topic:" + consumerRecord.topic() + "; partition: " + consumerRecord.partition() + ";");
//        System.out.println("Author: " + consumerRecord.value().getAuthor() + "; title: " + consumerRecord.value().getTitle());
//    }

//    @KafkaListener(id = "", topics = "file", groupId = "topic.group")
//    public void getTest(ConsumerRecord<String, String> consumerRecord) {
//        System.out.println("topic:" + consumerRecord.topic() + "; partition: " + consumerRecord.partition() + ";");
//        System.out.println("value: " + consumerRecord.value());
//    }

//    @KafkaListener(id = "", topics = "file", groupId = "topic.group", containerFactory = "batchListener")
//    public void getTest2(List<ConsumerRecord<String, String>> consumerRecords) {
//        consumerRecords.forEach(consumerRecord -> {
//            System.out.println("topic:" + consumerRecord.topic() + "; partition: " + consumerRecord.partition() + ";");
//            System.out.println("value: " + consumerRecord.value());
//        });
//    }
//
    @KafkaListener(id = "", topics = "file", groupId = "topic.group", containerFactory = "batchListener")
    public void getTest2(List<ConsumerRecord<String, Object>> consumerRecords) {
        consumerRecords.forEach(consumerRecord -> {
            System.out.println("topic:" + consumerRecord.topic() + "; partition: " + consumerRecord.partition() + ";");
            System.out.println("value: " + consumerRecord.value());
        });
    }

}
