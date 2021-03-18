package graphql.kickstart.playground.boot;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.util.Map;

@JsonComponent
public class VariablesSerializer extends JsonSerializer<Map<String, Object>> {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void serialize(Map<String, Object> map, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String json = objectMapper.writeValueAsString(map);
        gen.writeString(json);
    }
}
