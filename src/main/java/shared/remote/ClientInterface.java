package shared.remote;

import java.rmi.Remote;
import java.util.UUID;

/**
 *
 * @author Sérgio Lopes Júnior
 *
 * @version 1.0
 */
public interface ClientInterface extends Remote {
    String getName();
    UUID getUuid();
}
