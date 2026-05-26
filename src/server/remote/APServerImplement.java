package server;

import client.Client;
import server.service.Dictionary;
import shared.dto.GuessRequestDTO;
import shared.remote.ServerInterface;
import shared.dto.GuessResponseDTO;

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
    private final Dictionary dictionary = Dictionary.getInstance();
    private final String secretWord;

    public APServerImplement() throws RemoteException {
        this.secretWord = dictionary.getWord().toUpperCase();
        System.out.print("Palavra secreta da rodada: " + secretWord);
    }

    @Override
    public void registerUser(Client client) throws RemoteException {
        for(Client registeredClient : clients) {
            if(registeredClient.getUuid().equals(client.getUuid())) {
                System.out.println("Jogador já registrado: " + client.getName());
                return;
            }
        }
        System.out.println("Registering online player: " + client.getName() + " UUID[" + client.getUuid() + "]");
        clients.add(client);
    }


    @Override
    public GuessResponseDTO verifyGuess(GuessRequestDTO guessRequestDTO) throws RemoteException {
        String guessWord = guessRequestDTO.getGuess().toUpperCase();

        int[] status = new int[5];

        boolean [] secretPositionsUsed = new boolean [5];
        boolean [] guessPositionsUsed = new boolean [5];

        for (int i = 0; i < 5; i++) {
            if (guessWord.charAt(i) == secretWord.charAt(i)) {
                status[i] = 2;
                secretPositionsUsed[i] = true;
                guessPositionsUsed[i] = true;
            }
        }

        for (int i = 0; i < 5; i++) {
            if (guessPositionsUsed[i]) {
                continue;
            }

            for (int j = 0; j < 5; j++) {
                if(!secretPositionsUsed[j] && guessWord.charAt(i) == secretWord.charAt(j)) {
                    status[i] = 1;
                    secretPositionsUsed[j] = true;
                    break;
                }
            }
        }
        System.out.println("Result from player [UUID: " + guessRequestDTO.getUuid() + "] STATUS: " + Arrays.toString(status));

        boolean wordMatch = Arrays.stream(status).allMatch(v -> v == 2);
        return new GuessResponseDTO(status, guessWord, wordMatch);
    }


    @Override
    public int requestIndex() throws RemoteException {
        return dictionary.getIndexOfWorld();
    }

   /* private boolean letterVerified(char c){
        for(int i = 0; i < countCharOk; i++) {
            if (charVerified[i] == c) {
                return true;
            }
        }
        return false;
    } */
}
