package graphql.kickstart.playground.boot;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.util.Map;

@JsonComponent
public class VariablesSerializer extends JsonSerializer<Map<String, Object>> {

    @Override
    public void serialize(Map<String, Object> map, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(map);
        gen.writeString(json);
    }
}
