package com.heiio.bookkafka.config;

import com.heiio.bookkafka.serialization.BlogSerializer;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.transaction.KafkaTransactionManager;

import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableKafka
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

//    @Value("${spring.kafka.consumer.group-id}")
//    private String groupId;
//
//    @Value("${spring.kafka.consumer.enable-auto-commit}")
//    private Boolean autoCommit;
//
//    @Value("${spring.kafka.consumer.auto-offset-reset}")
//    private String autoOffsetReset;
//
//    @Value("${spring.kafka.consumer.max-poll-records}")
//    private Integer maxPollRecords;
//
//    @Value("${spring.kafka.producer.linger}")
//    private int linger;

    @Value("${spring.kafka.producer.retries}")
    private Integer retries;

//    @Value("${spring.kafka.producer.batch-size}")
//    private Integer batchSize;
//
//    @Value("${spring.kafka.producer.buffer-memory}")
//    private Integer bufferMemory;

    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        //设置重试次数
        props.put(ProducerConfig.RETRIES_CONFIG, retries);
        //达到batchSize大小的时候会发送消息
        //props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        //延时时间，延时时间到达之后计算批量发送的大小没达到也发送消息
        //props.put(ProducerConfig.LINGER_MS_CONFIG, linger);
        //缓冲区的值
        //props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
        //序列化手段
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, BlogSerializer.class);
        //producer端的消息确认机制,-1和all都表示消息不仅要写入本地的leader中还要写入对应的副本中
        props.put(ProducerConfig.ACKS_CONFIG, "-1");//单个brok 推荐使用'1'
        //单条消息的最大值以字节为单位,默认值为1048576
        props.put(ProducerConfig.LINGER_MS_CONFIG, 10485760);
        //设置broker响应时间，如果broker在60秒之内还是没有返回给producer确认消息，则认为发送失败
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 60000);
        //指定拦截器(value为对应的class)
        //props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, "com.te.handler.KafkaProducerInterceptor");
        //设置压缩算法(默认是木有压缩算法的)
        //props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");//snappy
        return props;
    }

    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
        //一次拉取消息数量
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "5");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, BlogSerializer.class);
        return props;
    }

    @Bean
    public KafkaTemplate kafkaTemplate() {
        return new KafkaTemplate(producerFactory());
    }

    @Bean
    public ProducerFactory<Integer, String> producerFactory() {

        DefaultKafkaProducerFactory<Integer,String>  producerFactory =  new DefaultKafkaProducerFactory(producerConfigs());

        //设置事务Id前缀 开启事务
        producerFactory.setTransactionIdPrefix("tx-");
        return producerFactory;
    }

    @Bean
    public KafkaTransactionManager<Integer, String> kafkaTransactionManager(ProducerFactory<Integer, String> producerFactory) {
        return new KafkaTransactionManager<>(producerFactory);
    }

    @Bean //创建一个kafka管理类，相当于rabbitMQ的管理类rabbitAdmin,没有此bean无法自定义的使用adminClient创建topic
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> props = new HashMap<>();
        //配置Kafka实例的连接地址
        //kafka的地址，不是zookeeper
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        KafkaAdmin admin = new KafkaAdmin(props);
        return admin;
    }

    @Bean  //kafka客户端，在spring中创建这个bean之后可以注入并且创建topic,用于集群环境，创建对个副本
    public AdminClient adminClient() {
        return AdminClient.create(kafkaAdmin().getConfig());
    }

    //创建TopicName为topic.quick.initial的Topic并设置分区数为8以及副本数为1
    @Bean//通过bean创建(bean的名字为initialTopic)
    public NewTopic initialTopic() {
        return new NewTopic("topic.blog",1, (short) 1 );
    }

    @Bean("batchContainerFactory")
    public ConcurrentKafkaListenerContainerFactory listenerContainer() {
        ConcurrentKafkaListenerContainerFactory container = new ConcurrentKafkaListenerContainerFactory();
        container.setConsumerFactory(new DefaultKafkaConsumerFactory(consumerConfigs()));
        //设置并发量，小于或等于Topic的分区数
        //container.setConcurrency(5);
        //设置为批量监听
        //container.setBatchListener(true);
        return container;
    }



}
