package habitar;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static habitar.HealthDto.healthDto;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/diagnosis")
public class HealthResource {

    @RequestMapping(method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public HealthDto getHealth() {
        return healthDto().withHealthStatus("UP").withMood("Gloomy");
    }
}
