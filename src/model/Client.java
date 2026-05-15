package model;

import shared.Color;
import shared.Config;
import shared.ServerInterface;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.UUID;

/**
 *
 * @author Bruna Brito Muniz Filgueiras
 * @author Laís de Assis Doria da Silva
 * @author Sérgio Lopes Júnior
 *
 * @version 1.0
 */
public class Client implements Serializable {
    private final Registry registry;
    private final ServerInterface proxy;
    private int index = 0;

    private int score = 0;
    private int attempts = 0;

    /** Parâmetro que cria um identificador único para o jogador (UUID Universally Unique Identifier) */
    private final UUID id;
    private String name = "Jogador";

     public Client() throws RemoteException {
        super();
        try {
            this.id = UUID.randomUUID();
            this.registry = LocateRegistry.getRegistry(Config.IP_ADDRESS, Config.SERVER_PORT);
            this.proxy = (ServerInterface) this.registry.lookup(Config.SERVER_NAME);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void setName(String name){
         this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
         return name;
    }

    public UUID getId() {
         return id;
    }

    public int[] verifyGuess(Client client, String guess) throws RemoteException {
         Word word = new Word();
         if(word.validate(guess)){
             try {
                 int[] status;
                 this.proxy.requestIndex(index);
                 status = this.proxy.verifyGuess(this, guess);
                 formatResponse(status, guess.toUpperCase());
                 attempts++;
                 return status;
             } catch (RemoteException e) {
                 throw new RuntimeException(e);
             }
         }else{
             System.out.println("Palpite inválido, palavra não reconhecida ou com menos de 5 caracteres.");
         }
         return null;
    }

    public void formatResponse(int[] status, String guess) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 5; i++) {
            if(status[i] == 0) {
                sb.append(Color.CINZA).append(guess.charAt(i)).append(Color.RESET);
            }else if(status[i] == 1) {
                sb.append(Color.AMARELO).append(guess.charAt(i)).append(Color.RESET);
            }else if(status[i] == 2) {
                sb.append(Color.VERDE).append(guess.charAt(i)).append(Color.RESET);
            }
        }
        sb.append(Color.RESET);
        System.out.println(sb.toString());;
    }
}
