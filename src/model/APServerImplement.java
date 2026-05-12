package model;

import dto.WordDto;
import shared.ServerInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class APServerImplement extends UnicastRemoteObject implements ServerInterface {
    private final List<Client> clients = new ArrayList<>();
    Dictionary dict = new Dictionary();

    public APServerImplement() throws RemoteException {
    }

    @Override
    public void registerUser(Client client) throws RemoteException {
        clients.add(client);
    }

    @Override
    public void verifyGuess(Client client, WordDto word) throws RemoteException {
        String secretWord = this.dict.getWord(client.getIndex());


    }
}
