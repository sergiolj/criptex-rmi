package server.remote;

import client.Client;
import server.service.GameManager;
import shared.config.Config;
import shared.dto.GuessRequestDTO;
import shared.remote.ServerInterface;
import shared.dto.GuessResponseDTO;
import shared.status.Color;
import shared.status.DateTimeLog;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Bruna Brito Muniz Filgueiras
 * @author Laís de Assis Doria da Silva
 * @author Sérgio Lopes Júnior
 *
 * @version 1.0
 */
public class APServerImplement extends UnicastRemoteObject implements ServerInterface {
    private final List<Client> clients = new ArrayList<>();
    private final GameManager gameManager;

    public APServerImplement() throws RemoteException {
        super();
        this.gameManager = new GameManager(newSecretWord ->{
            System.out.println("[\u001B[34m" + DateTimeLog.dateTimeNow() + "\u001B[0m] " +
                    "[" + Config.SERVER_NAME + "] A palavra secreta atual é: " + newSecretWord);
        });
    }

    /**
     * Falta implementar uma verificação para reconhecer o usuário, caso esse já tenha registro no servidor.
     *
     * @param client
     * @throws RemoteException
     */
    @Override
    public void registerUser(Client client) throws RemoteException {
        System.out.println("[" + DateTimeLog.dateTimeNow()+ "] [" + Config.SERVER_NAME + "] " +
                "Registering online player: " + client.getName() + " UUID[" + client.getUuid() + "]");

        // Antes de adicionar tem que testar se ele já não está registrado.
        clients.add(client);
    }


    @Override
    public GuessResponseDTO verifyGuess(GuessRequestDTO guessRequestDTO) throws RemoteException {
        String secretWord = gameManager.getSecretWord().toString();

        String guessWord = guessRequestDTO.getGuess().toUpperCase();

        System.out.printf("[" + Color.GREEN + DateTimeLog.dateTimeNow() + Color.RESET + "] Analysing attempt from player [UUID: %s]%n",
                guessRequestDTO.getUuid());

        /**
         * Array que irá receber os valores verificados para as letras da palavra do palpite do jogador:
         * 0 - Letra não encontrada
         * 1 - Letra encontrada na posição errada
         * 2 - Letra encontrada na posição certa
         */
        int[] status = new int[5];

        boolean[] secretPositionsUsed = new boolean[5];
        boolean[] guessPositionsUsed = new boolean[5];
        /**
         * Algoritmo que busca se a letra na posição i da palavra do palpite do jogador é igual a letra na mesma posição
         * da palavra secreta. Se sim o array de verificação recebe o valor 2 que indica letra certa na posição certa.
         */
        for (int i = 0; i < 5; i++) {
            if (guessWord.charAt(i) == secretWord.charAt(i)) {
                status[i] = 2;
                secretPositionsUsed[i] = true;
                guessPositionsUsed[i] = true;
            }
        }

        /**
         * Se uma letra do palpite já foi verificada e encontrada na palavra secreta a mesma não pode mais ser
         * verificada em outras posições da palavra secreta.
         */
        for (int i = 0; i < 5; i++) {
            if (guessPositionsUsed[i]) continue;

            for (int j = 0; j < 5; j++) {
                if (!secretPositionsUsed[j] && guessWord.charAt(i) == secretWord.charAt(j)) {
                    status[i] = 1;
                    secretPositionsUsed[j] = true;
                    break;
                }
            }
        }
        System.out.println("[" + Color.GREEN + DateTimeLog.dateTimeNow() + Color.RESET + "] Result from player [UUID: " + guessRequestDTO.getUuid() + "] STATUS: " + Arrays.toString(status));

        boolean wordMatch;
        wordMatch = Arrays.stream(status).allMatch(v -> v == 2);
        return new GuessResponseDTO(status, guessWord, wordMatch);
    }

    public void shutdown() {
        gameManager.shutdown();
        try{
            UnicastRemoteObject.unexportObject(this, true);
            System.out.println( "["+ Color.RED + "ServerName: " + Config.SERVER_NAME + Color.RESET + "] " +
                    "Desconectando servidor RMI...");
        }catch (RemoteException e){
            System.out.println("Erro ao desligar servidor RMI: " + e.getMessage());
        }
    }
}
