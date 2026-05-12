package model;

import dto.WordDto;
import shared.Config;
import shared.ServerInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private final Registry registry;
    private final ServerInterface proxy;
    private int index = 0;

    private List<Word> guesesList = new ArrayList<>();

     public Client() throws RemoteException {
        super();
        try {
            this.registry = LocateRegistry.getRegistry(Config.IP_ADDRESS, Config.SERVER_PORT);
            this.proxy = (ServerInterface) this.registry.lookup(Config.SERVER_NAME);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    public int getIndex() {
        return index;
    }

    public void verifyGuess(Client client, WordDto word) throws RemoteException{
        try{
            this.proxy.verifyGuess(this, word);
            //Se a palavra for a correta o index deve ser atualizado.
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

}
