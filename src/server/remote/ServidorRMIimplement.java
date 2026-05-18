package server.remote;

import client.GameClient;
import server.service.DictionaryService;
import shared.dto.PalpiteRequestDto;
import shared.remote.ServidorInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServidorRMIimplement extends UnicastRemoteObject implements ServidorInterface {
    private final List<GameClient> gameClients = new ArrayList<>();
    DictionaryService dict = new DictionaryService();

    public ServidorRMIimplement() throws RemoteException {
    }

    @Override
    public void registerUser(GameClient gameClient) throws RemoteException {
        gameClients.add(gameClient);
    }

    @Override
    public void verifyGuess(GameClient gameClient, PalpiteRequestDto word) throws RemoteException {
        String secretWord = this.dict.getWord(gameClient.getIndex());


    }
}
