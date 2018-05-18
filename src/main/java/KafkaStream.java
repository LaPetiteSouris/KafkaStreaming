import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.KStream;

import org.apache.kafka.streams.kstream.Produced;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;


public class KafkaStream {
    private static List<String> forbiddenKeywords = Arrays.asList("porn", "tits", "sex");

    // Verify if comment containts forbidden keyword
    private static boolean isinvalidComment(String s) {
        return forbiddenKeywords.stream().
                anyMatch(forbiddenName -> s.toLowerCase().
                        contains(forbiddenName.toLowerCase()));
    }

    public static void main(String[] arg) {
        final Serde<String> stringSerde = Serdes.String();
        System.out.println("Kafka Streaming started");

        // Set up Kafka Stream props
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "fraud");

        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(
                StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG,
                Serdes.String().getClass().getName());
        props.put(
                StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,
                Serdes.String().getClass().getName());

        // Construct Kafka stream builder
        final StreamsBuilder builder = new StreamsBuilder();

        KStream<String, String> source = builder.stream("user");

        // Process, seprate valid comment (not containing forbidden keywords
        // and invalid comments
        System.out.println("Forwarding stream...");
        KStream<String, String>[] branches = source.branch(
                (k, v) -> isinvalidComment(v),
                (k, v) -> true
        );
        KStream<String, String> invalidComment = branches[0];
        KStream<String, String> validComment = branches[1];

        // Redirect comments to correct topics in real-time
        source.to("fwd", Produced.with(stringSerde, stringSerde));

        invalidComment.to("forward_invalid", Produced.with(stringSerde, stringSerde));
        validComment.to("forward_valid", Produced.with(stringSerde, stringSerde));

        // Intialize and start Kafka Stream
        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
