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
    private char [] charVerified = new char[5];
    private int countCharOk = 0;

    public APServerImplement() throws RemoteException {
    }

    @Override
    public void registerUser(Client client) throws RemoteException {
        System.out.println("Registering online player: " + client.getName() + " UUID[" + client.getUuid() + "]");
        clients.add(client);
    }


    @Override
    public GuessResponseDTO verifyGuess(GuessRequestDTO guessRequestDTO) throws RemoteException {
        String secretWord = dictionary.getWord(dictionary.getIndexOfWorld());
        String guessWord = guessRequestDTO.getGuess().toUpperCase();

        System.out.printf("Secret Word <" + secretWord + "> - Analysing attempt from player [UUID: %s)%n",
                guessRequestDTO.getUuid());

//            client = clients.get(clients.indexOf(client));
//            client.increaseAttempt();
//            if(client.getAttempts() >5){
//                System.out.println("Você não conseguiu descobrir a palavra dessa vez. :_(" );
//                //Sai do loop e impede novas tentativas
//            }


        /**
         * Array que irá receber os valores verificados para as letras da palavra do palpite do jogador:
         * 0 - Letra não encontrada
         * 1 - Letra encontrada na posição errada
         * 2 - Letra encontrada na posição certa
         */
        int[] status = new int[5];

        boolean [] secretPositionsUsed = new boolean [5];
        boolean [] guessPositionsUsed = new boolean [5];
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
                if(!secretPositionsUsed[j] && guessWord.charAt(i) == secretWord.charAt(j)) {
                    status[i] = 1;
                    secretPositionsUsed[j] = true;
                    break;
                }
            }
        }
        System.out.println("Result from player [UUID: " + guessRequestDTO.getUuid() + "] STATUS: " + Arrays.toString(status));

        boolean wordMatch;
        wordMatch = Arrays.stream(status).allMatch(v -> v == 2);
        return new GuessResponseDTO(status, guessWord, wordMatch);
    }


    @Override
    public int requestIndex(int index) throws RemoteException {
        return 0;
    }

    private boolean letterVerified(char c){
        for(int i = 0; i < countCharOk; i++) {
            if (charVerified[i] == c) {
                return true;
            }
        }
        return false;
    }
}
