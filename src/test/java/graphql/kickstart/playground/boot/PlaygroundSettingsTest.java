package graphql.kickstart.playground.boot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PlaygroundTestConfig.class)
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-playground-settings-test.properties")
class PlaygroundSettingsTest {

  private static final String OS_SYSTEM = "os.name";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void shouldProperlyLoadSettings() throws Exception {

    final ObjectNode tabs = objectMapper.createObjectNode();
    final ArrayNode tabResponses = objectMapper.createArrayNode();
    tabResponses.add("{\"response\":\"data\"}");
    final ObjectNode tabHeaders = objectMapper.createObjectNode();
    tabHeaders.put("tabHeader", "custom value");
    final ObjectNode tab = objectMapper.createObjectNode();
    tab.put("endpoint", "/tab-endpoint");
    tab.put("query", "query {}");
    tab.put("name", "Test Tab");
    if (System.getProperty(OS_SYSTEM).toLowerCase().contains("win")) {
        tab.put("variables", "{\r\n  \"test\" : \"Test Value\"\r\n}");
    } else {
        tab.put("variables", "{\n  \"test\" : \"Test Value\"\n}");
    }
    tab.set("responses", tabResponses);
    tab.set("headers", tabHeaders);
    tabs.set("tab", tab);
    final ObjectNode settings = objectMapper.createObjectNode();
    settings.put("editor.cursorShape", "underline");
    settings.put("editor.fontFamily", "monospace");
    settings.put("editor.fontSize", 14);
    settings.put("editor.reuseHeaders", true);
    settings.put("editor.theme", "dark");
    settings.put("prettier.printWidth", 80);
    settings.put("prettier.tabWidth", 2);
    settings.put("prettier.useTabs", false);
    settings.put("request.credentials", "same-origin");
    settings.put("schema.polling.enable", true);
    settings.put("schema.polling.endpointFilter", "*");
    settings.put("schema.polling.interval", 4000);
    settings.put("schema.disableComments", true);
    settings.put("tracing.hideTracingResponse", true);
    final ObjectNode headers = objectMapper.createObjectNode();
    headers.put("test", "test-header-value");
    final ObjectNode expectedNode = objectMapper.createObjectNode();
    expectedNode.put("endpoint", "/test-endpoint");
    expectedNode.put("subscriptionEndpoint", "/test-subscription-endpoint");
    expectedNode.set("settings", settings);
    expectedNode.set("headers", headers);
    expectedNode.set("tabs", tabs);

    mockMvc.perform(get(PlaygroundTestHelper.DEFAULT_PLAYGROUND_ENDPOINT))
        .andExpect(status().isOk())
        .andExpect(model().attribute("properties", expectedNode));
  }
}
