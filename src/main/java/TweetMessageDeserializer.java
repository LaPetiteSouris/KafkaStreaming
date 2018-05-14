import com.google.gson.Gson;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.io.Closeable;
import java.nio.charset.Charset;
import java.util.Map;

public class TweetMessageDeserializer implements Closeable, AutoCloseable, Deserializer<TweetMessage> {

    private static final Charset CHARSET = Charset.forName("UTF-8");
    static private Gson gson = new Gson();

    @Override
    public void configure(Map<String, ?> map, boolean b) {
    }

    @Override
    public TweetMessage deserialize(String t, byte[] data) {
        String mes = new String(data, CHARSET);
        return gson.fromJson(mes, TweetMessage.class);

    }

    @Override
    public void close() {

    }
}