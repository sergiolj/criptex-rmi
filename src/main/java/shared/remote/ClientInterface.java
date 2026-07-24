package shared.remote;

import java.rmi.Remote;
import java.util.UUID;

public interface ClientInterface extends Remote {
    String getName();
    UUID getUuid();
}
