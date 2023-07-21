package org.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class Producer1 {

    private final static String BOOTSTRAP_SERVER = "localhost:9092";

    private final static String TOPIC_NAME = "topic5";

    public static void main(String[] args) throws Exception {

        // producer 가 사용할 옵션들을 java.util 의 Properties 를 사용하여 세팅해준다.
        Properties configs = new Properties();

        // required;
        // 부트 스트랩 서버 연결 설정, key-value 직렬화 옵션
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        // producer 가 발행한 message 들은 네트워크를 통해 전송되므로 직렬화 설정이 필요하다.
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // optional;
        // 필수값 이외의 옵션들은 명시적으로 지정해주지 않을 경우 기본값으로 설정된다.
        configs.put(ProducerConfig.ACKS_CONFIG, "all");
        configs.put(ProducerConfig.RETRIES_CONFIG, "100");


        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(configs);

        String message = "First Message";

        // 전송할 message 는 어디로 보낼지 topic 을 정하고 ProducerRecord 에 담아서 Publish 한다.
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, message);

        // 성공적으로 message 를 발행 시 전송 결과 정보를 get() 을 통해 RecordMetaData 객체로 받을 수 있다.
        RecordMetadata metadata = producer.send(record).get();

        System.out.printf(">>> %s, %d, %d", message, metadata.partition(), metadata.offset());

        // 발행이 끝난 후엔 broker 와의 연결을 마쳐야 한다.
        producer.flush();
        producer.close();
    }

}
