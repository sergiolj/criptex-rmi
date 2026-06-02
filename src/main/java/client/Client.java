package client;
import server.service.GameSession;
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
 * Classe com as regras de negócio para a parte do jogador.
 *
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
    private int attempt = 0;
    private final GameSession gameSession;
    /** Parâmetro que cria um identificador único para o jogador (UUID Universally Unique Identifier) */
    private final UUID uuid;
    private String name = "Anonymous";

     public Client() throws RemoteException {
        try {
            this.uuid = UUID.randomUUID();
            this.gameSession = new GameSession();

            this.registry = LocateRegistry.getRegistry(Config.IP_ADDRESS, Config.SERVER_PORT);
            this.proxy = (ServerInterface) this.registry.lookup(Config.SERVER_NAME);

            this.proxy.registerUser(this);

        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    }
    public Client(String name) throws RemoteException {
        try {
            this.uuid = UUID.randomUUID();
            this.name = name;
            this.registry = LocateRegistry.getRegistry(Config.IP_ADDRESS, Config.SERVER_PORT);
            this.proxy = (ServerInterface) this.registry.lookup(Config.SERVER_NAME);
            this.proxy.registerUser(this);
            this.gameSession = new GameSession();

        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     *
     * @param guess
     * @throws RemoteException
     */

    //Deve ter um for usando a classe GameSession ou seja, usando o atributo attempt e maxattempt
    public boolean verifyGuess(String guess) throws RemoteException {
        Word word = new Word();
        guess = word.removeAccent(guess);

        if (!gameSession.hasAttempts()) {
            System.out.println("Você não possui mais tentativas.");
            return false;
        }

        if (!word.validate(guess)) {
            System.out.println("Palpite inválido. A palavra precisa ter exatamente 5 letras.");
            return false;
        }

        GuessRequestDTO request = new GuessRequestDTO(this.uuid, guess);
        GuessResponseDTO response = this.proxy.verifyGuess(request);
        gameSession.increaseAttempt();

        GuessResponseDTO responseWithAttempt = new GuessResponseDTO(
                response.getStatus(),
                response.getGuess(),
                response.isWordMatch(),
                gameSession.getAttempt()
        );

        formatResponse(responseWithAttempt);

        if (responseWithAttempt.isWordMatch()) {
            score++;
        }

        return responseWithAttempt.isWordMatch();
    }

    public void formatResponse(GuessResponseDTO response) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            if (response.getStatus()[i] == 0) {
                sb.append(Color.BG_GRAY).append(response.getGuess().charAt(i)).append(Color.RESET);
            } else if (response.getStatus()[i] == 1) {
                sb.append(Color.BG_YELLOW).append(response.getGuess().charAt(i)).append(Color.RESET);

            } else if (response.getStatus()[i] == 2) {
                sb.append(Color.BG_GREEN).append(response.getGuess().charAt(i)).append(Color.RESET);
            }
        }

        System.out.println("-------------------------------------");
        System.out.println("              < " + sb + " >");
        System.out.println("-------------------------------------");
    }

    public GameSession getGameSession() {
        return gameSession;
    }
    public void increaseAttempt() {
        this.attempt++;
    }

    public int getAttempts() {
        return gameSession.getAttempt();
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

    public UUID getUuid() {
        return uuid;
    }

}
