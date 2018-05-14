import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;

import static java.lang.System.getProperties;

public class KafkaStream {

    public static void main(String[] arg) {
        System.out.println("Kafka Streaming started");

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "fraud");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        // Here we set the Seder for the values that we are going to process.
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, TweetMessageSeder.class);

        final StreamsBuilder builder = new StreamsBuilder();


        KStream<String, TweetMessage> source = builder.stream("user");
        System.out.println("getting stream...");
        source.map((k,v) -> {
            System.out.println("key is" + k);
            return null;
        });

        source.to("streams-user-fraud");
    }
}
