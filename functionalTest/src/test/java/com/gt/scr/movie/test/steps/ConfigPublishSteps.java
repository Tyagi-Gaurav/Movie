package com.gt.scr.movie.test.steps;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.gt.scr.movie.test.resource.ResponseHolder;
import com.gt.scr.movie.test.resource.TestConfigPublishResource;
import io.cucumber.java8.En;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigPublishSteps implements En {
    @Autowired
    private TestConfigPublishResource testConfigPublishResource;

    @Autowired
    private ResponseHolder responseHolder;

    public ConfigPublishSteps() {
        When("^the configuration is published for content upload application$", () -> {
            testConfigPublishResource.invokeContentUploadPublishConfig();
        });

        And("^any sensitive data should be protected$", () -> {
            String configResponse = responseHolder.readResponse(String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode configRootNode = objectMapper.readTree(configResponse);
            List<JsonNode> passwords = configRootNode.findValues("password");
            assertThat(passwords.stream()
                    .map(field -> {
                        if (field.getNodeType() == JsonNodeType.STRING) {
                            return field.asText();
                        } else if (field.getNodeType() == JsonNodeType.OBJECT) {
                            return Optional.ofNullable(field.get("value")).map(JsonNode::asText).orElse(null);
                        } else
                            return null;
                    }).filter(Objects::nonNull)
                    .allMatch(s -> s.equals("******"))).isTrue();
        });

        When("^the configuration is published for user application$", () -> {
            testConfigPublishResource.invokeUsersPublishConfig();
        });
    }

}
