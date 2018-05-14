import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class TweetMessageSeder implements Serde<TweetMessage> {
    private TweetMessageSerializer serializer = new TweetMessageSerializer();
    private TweetMessageDeserializer deserializer = new TweetMessageDeserializer();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        serializer.configure(configs, isKey);
        deserializer.configure(configs, isKey);
    }

    @Override
    public void close() {
        serializer.close();
        deserializer.close();
    }

    @Override
    public Serializer<TweetMessage> serializer() {
        return serializer;
    }

    @Override
    public Deserializer<TweetMessage> deserializer() {
        return deserializer;
    }
}