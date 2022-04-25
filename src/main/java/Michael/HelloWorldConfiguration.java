package Michael;

import io.dropwizard.Configuration;
//import io.dropwizard.client.JerseyClientConfiguration;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import javax.validation.constraints.NotEmpty;


// configuration reads the yml file that is passed as command line argument
// it appears to automagically find attributes based on their names

public class HelloWorldConfiguration extends Configuration {
    @NotEmpty
    private String template;

    @NotEmpty
    private String defaultName = "Stranger";
    
    @Range(min=-1, max=200)
    private int defaultAge = -1;
    
//    @Valid
//    @NotNull
//    private JerseyClientConfiguration jerseyClient = new JerseyClientConfiguration();

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
    public int getDefaultAge() {
        return defaultAge;
    }

    @JsonProperty
    public void setDefaultAge(int age) {
        this.defaultAge = age;
    }
    
//    @JsonProperty("jerseyClient")
//    public JerseyClientConfiguration getJerseyClientConfiguration() {
//        return jerseyClient;
//    }

//    @JsonProperty("jerseyClient")
//    public void setJerseyClientConfiguration(JerseyClientConfiguration jerseyClient) {
//        this.jerseyClient = jerseyClient;
//    }
}
