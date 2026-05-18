package shared.remote;

import shared.dto.PalpiteRequestDto;
import client.GameClient;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServidorInterface extends Remote {
    void registerUser(GameClient gameClient) throws RemoteException;
    void verifyGuess(GameClient gameClient, PalpiteRequestDto word) throws RemoteException;
}
