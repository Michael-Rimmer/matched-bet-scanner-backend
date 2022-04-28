package Michael;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
//import io.dropwizard.client.JerseyClientConfiguration;

// configuration reads the yml file that is passed as command line argument
// it appears to automagically find attributes based on their names

public class HelloWorldConfiguration extends Configuration {
    @NotEmpty
    private String template;

    @NotEmpty
    private String defaultName = "Stranger";

    @NotEmpty
    private String chromeDriverPath;

    @JsonProperty
    public String getTemplate() {
        return template;
    }

    @JsonProperty
    public void setTemplate(String template) {
        this.template = template;
    }

    @JsonProperty
    public String getDefaultName() {
        return defaultName;
    }

    @JsonProperty
    public void setDefaultName(String name) {
        this.defaultName = name;
    }

    @JsonProperty
    public String getChromeDriverPath() {
        return chromeDriverPath;
    }

    @JsonProperty
    public void setChromeDriverPath(String path) {
        this.chromeDriverPath = path;
    }

}
