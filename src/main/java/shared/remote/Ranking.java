package shared.remote;

import java.io.Serial;
import java.io.Serializable;
import java.time.Duration;

public record Ranking(String name, Duration duration) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String getElapsedTime(){
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.toSeconds() % 60;
       return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    @Override
    public String toString() {
        return name + " | " + getElapsedTime();
    }
}
