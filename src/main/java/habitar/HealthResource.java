package habitar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static habitar.HealthDto.healthDto;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/diagnosis")
public class HealthResource {

    @Autowired
    private DataSource dataSource;

    @RequestMapping(method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public HealthDto getHealth() {
        return healthDto().withHealthStatus("UP").withMood("Gloomy");
    }

    @RequestMapping(path = "/db", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public HealthDto initDb() {
        HealthDto healthDto = healthDto().withHealthStatus("UP");
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
            stmt.executeUpdate("INSERT INTO ticks VALUES (now())");

            ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");
            List<String> output = new ArrayList<String>();
            while (rs.next()) {
                output.add("Read from DB: " + rs.getTimestamp("tick"));
            }
            healthDto.withMood(output.stream().collect(Collectors.joining(",")));
        } catch (Exception e){
            System.out.println("ERROR");
            System.out.println(e);
        }
        return healthDto;
    }

    @RequestMapping(path = "/resetdb", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public HealthDto resetDb() {
        HealthDto healthDto = healthDto().withHealthStatus("UP");
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("DROP TABLE ticks");
        } catch (Exception e){
            System.out.println("ERROR");
            System.out.println(e);
        }
        return healthDto;
    }


}
