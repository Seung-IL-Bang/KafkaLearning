package org.example;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class Consumer1 {

    private final static String BOOTSTRAP_SERVER = "localhost:9092";

    private final static String TOPIC_NAME = "topic5";

    private final static String GROUP_ID = "group_one";

    public static void main(String[] args) {

        // consumer 가 사용할 옵션들을 java.util 의 Properties 를 사용하여 세팅해준다.
        Properties configs = new Properties();

        // required;
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // optional;
        // consumer 를 구분하기 위한 그룹 ID 설정
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        // --from-beginning 옵션의 역할; 첫 레코드부터 읽어들인다.
        configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(configs);

        // 보통 한 개의 topic 을 구독하지만 Collection 타입으로 topic 을 여러 개 구독할 수도 있다.
        consumer.subscribe(Arrays.asList(TOPIC_NAME));

        while (true) {
            // polling 방식으로 kafka broker 의 지정 topic 으로 부터 데이터를 읽어들인다.
            // Duration; consumer 측의 버퍼에 데이터를 모으기 위해 기다리는 타임 아웃 간격.
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));

            for (ConsumerRecord<String, String> record : records) {
                System.out.println(">>> " + record);
                System.out.println(">>>> " + record.value());
            }
        }

    }

}
