package net.wicp.tams.duckula.demo.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import net.wicp.tams.common.binlog.alone.DuckulaAssit;
import net.wicp.tams.common.binlog.alone.ListenerConf;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * @author Li Changwei
 * @version 2020-11-18 15:01
 */
public class KafkaUtil {

    public static void main(String[] args) throws InvalidProtocolBufferException {
            Properties properties = new Properties();
            properties.put("bootstrap.servers", "test-kafka-001.taimei.com:9092");
            properties.put("group.id", "table_data_collector");
            properties.put("key.deserializer", org.apache.kafka.common.serialization.StringDeserializer.class.getName());
            properties.put("value.deserializer", org.apache.kafka.common.serialization.ByteArrayDeserializer.class.getName());
            properties.put("enable.auto.commit", "false");

            final KafkaConsumer<String, byte[]> kafkaConsumer = new KafkaConsumer<>(properties);
            kafkaConsumer.subscribe(Collections.singletonList("gvp-base"));

            int count = 10;
            while (count < 20) {
                final ConsumerRecords<String, byte[]> records = kafkaConsumer.poll(2000);
                System.out.println("轮训开始" + System.currentTimeMillis());
                for (ConsumerRecord<String, byte[]> record : records) {
                    final ListenerConf.DuckulaEvent parse = DuckulaAssit.parse(record.value());
                    final List<ListenerConf.DuckulaEventItem> itemsList = parse.getItemsList();
                    System.out.println(record);
                    count++;

                    kafkaConsumer.commitSync();
                }
            }

            kafkaConsumer.wakeup();
            kafkaConsumer.close();
    }

}
