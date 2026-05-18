package client;

import shared.dto.PalpiteRequestDto;
import shared.config.Configuracao;
import shared.remote.ServidorInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class GameClient {
    private final Registry registry;
    private final ServidorInterface proxy;
    private int index = 0;

    private List<Word> guesesList = new ArrayList<>();

     public GameClient() throws RemoteException {
        super();
        try {
            this.registry = LocateRegistry.getRegistry(Configuracao.IP_ADDRESS, Configuracao.SERVER_PORT);
            this.proxy = (ServidorInterface) this.registry.lookup(Configuracao.SERVER_NAME);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    public int getIndex() {
        return index;
    }

    public void verifyGuess(GameClient gameClient, PalpiteRequestDto word) throws RemoteException{
        try{
            this.proxy.verifyGuess(this, word);
            //Se a palavra for a correta o index deve ser atualizado.
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

}
