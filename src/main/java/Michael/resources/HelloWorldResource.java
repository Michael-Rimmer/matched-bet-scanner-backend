package Michael.resources;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;

import Michael.api.Saying;

@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {
    private final String template;
    private final String defaultName;
    private final AtomicLong counter;

    private final String willHillUrl = "https://sports.williamhill.com/betting/en-gb/football/competitions/OB_TY295/English-Premier-League/matches/OB_MGMB/Match-Betting";

    public HelloWorldResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
    }

    @GET
    @Timed
    public Saying sayHello(@QueryParam("name") Optional<String> name) {
        final String value = String.format(template, name.orElse(defaultName));
        return new Saying(counter.incrementAndGet(), value);
    }

    @POST
    @Timed
    public Saying sayHelloPost(@QueryParam("name") Optional<String> name) {
        final String value = String.format("%s just POSTED!", name.orElse(defaultName));
        return new Saying(counter.incrementAndGet(), value);
    }

//     parse and return in a list of events
//    @GET
//    @Timed
//    public Saying scan() {
//        
//        //make request to william hill
////        System.setProperty("webdriver.chrome.driver","G:\\chromedriver.exe");
//        WebDriver driver = new ChromeDriver();
////        
//        String expectedTitle = "Welcome: Mercury Tours";
//        String actualTitle = "";
//
//        // launch Fire fox and direct it to the Base URL
//        driver.get(willHillUrl);
//
//        // get the actual value of the title
//        actualTitle = driver.getTitle();
//        
//        // parse html
//        System.out.println(willHillContent);
//        // create new event instances
//        
////        final String value = "";
//        return new Saying(counter.incrementAndGet(), willHillContent);
//    }
}
