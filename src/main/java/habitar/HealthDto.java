package habitar;

public class HealthDto extends Dto{

    public String healthStatus;
    public String mood;

    private HealthDto(){}

    public static HealthDto healthDto(){
        return new HealthDto();
    }

    public HealthDto withHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
        return this;
    }

    public HealthDto withMood(String mood) {
        this.mood = mood;
        return this;
    }
}
