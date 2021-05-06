package graphql.kickstart.playground.boot;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import graphql.kickstart.playground.boot.properties.PlaygroundTab;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TabsSerializer extends JsonSerializer<Map<String, PlaygroundTab>> {

    @Override
    public void serialize(Map<String, PlaygroundTab> playgroundTabMap, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        List<PlaygroundTab> playgroundTabs = new ArrayList<>(playgroundTabMap.values());
        gen.writeStartArray();
        for (PlaygroundTab playgroundTab : playgroundTabs) {
            gen.writeObject(playgroundTab);
        }
        gen.writeEndArray();
    }
}
