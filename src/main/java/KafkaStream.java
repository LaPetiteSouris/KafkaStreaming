import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.KStream;

import org.apache.kafka.streams.kstream.Produced;

import java.util.Properties;


public class KafkaStream {

    public static void main(String[] arg) {
        final Serde<String> stringSerde = Serdes.String();
        System.out.println("Kafka Streaming started");

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "fraud");

        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(
                StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG,
                Serdes.String().getClass().getName());
        props.put(
                StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,
                Serdes.String().getClass().getName());

        final StreamsBuilder builder = new StreamsBuilder();

        KStream<String, String> source = builder.stream("user");

        System.out.println("Forwarding stream...");

        source.to("forward", Produced.with(stringSerde, stringSerde));

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
