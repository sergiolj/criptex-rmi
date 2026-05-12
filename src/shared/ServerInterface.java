package shared;

import dto.WordDto;
import model.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
    void registerUser(Client client) throws RemoteException;
    void verifyGuess(Client client, WordDto word) throws RemoteException;
}
