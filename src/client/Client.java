package client;

import server.engine.Word;
import shared.status.Color;
import shared.config.Config;
import shared.dto.GuessRequestDTO;
import shared.remote.ServerInterface;
import shared.dto.GuessResponseDTO;

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

    private int score = 0;

    /** Parâmetro que cria um identificador único para o jogador (UUID Universally Unique Identifier) */
    private final UUID uuid;
    private String name = "Anonymous";

     public Client() throws RemoteException {
        super();
        try {
            this.uuid = UUID.randomUUID();
            this.registry = LocateRegistry.getRegistry(Config.IP_ADDRESS, Config.SERVER_PORT);
            this.proxy = (ServerInterface) this.registry.lookup(Config.SERVER_NAME);
            this.proxy.registerUser(this);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Client(String name) throws RemoteException {
        super();
        try {
            this.uuid = UUID.randomUUID();
            this.name = name;
            this.registry = LocateRegistry.getRegistry(Config.IP_ADDRESS, Config.SERVER_PORT);
            this.proxy = (ServerInterface) this.registry.lookup(Config.SERVER_NAME);
            this.proxy.registerUser(this);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    public int getScore() {
        return score;
    }

    public String getName() {
         return name;
    }

    public UUID getUuid() {
         return uuid;
    }

    /**
     *
     * @param guess
     * @throws RemoteException
     */
    public boolean verifyGuess(String guess) throws RemoteException {
        Word word = new Word();
        GuessRequestDTO request;
        GuessResponseDTO response = new GuessResponseDTO();

         if(word.validate(guess)){
             try {
                 request = new GuessRequestDTO(this.uuid, guess);
                 response = this.proxy.verifyGuess(request);

                 formatResponse(response);
             } catch (RemoteException e) {
                 throw new RuntimeException(e);
             }
         }else{
             System.out.println("Palpite inválido, palavra não reconhecida ou com menos de 5 caracteres.");
         }
         return response.isWordMatch();
    }

    public void formatResponse(GuessResponseDTO response) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 5; i++) {
            if(response.getStatus()[i] == 0) {
                sb.append(Color.BG_GRAY).append(response.getGuess().charAt(i));
            }else if(response.getStatus()[i] == 1) {
                sb.append(Color.BG_YELLOW).append(response.getGuess().charAt(i));
            }else if(response.getStatus()[i] == 2) {
                sb.append(Color.BG_GREEN).append(response.getGuess().charAt(i));
            }
        }
        sb.append(Color.RESET);
        System.out.println("< " + sb.toString() + " >");
//        System.out.println("-------------------------------------");
//        System.out.printf("Tentativa: %d/5 | Tempo decorrido: %s%n",
//                response.getCurrentAttempt(),
//                response.getElapsedTime());
//        System.out.println("-------------------------------------");
    }
}
