package model;

import shared.ServerInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
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

    public APServerImplement() throws RemoteException {
    }

    @Override
    public void registerUser(Client client) throws RemoteException {
        clients.add(client);
    }


    @Override
    public int [] verifyGuess(Client client, String word) throws RemoteException {
        System.out.printf("Analysing attempt from player: %s [UUID: %s)%n",
                client.getName(),
                client.getId());

        String secretWord = dictionary.getWord(client.getIndex());
        String guessWord = word.toUpperCase();

        /**
         * Array de verificação que irá receber os valores possíveis para as letras da palavra:
         * 0 - Letra não encontrada
         * 1 - Letra encontrada na posição errada
         * 2 - Letra encontrada na posição certa
         */
        int [] status = new int[5];


        boolean[] usedInSecret = new boolean[5];
        boolean[] usedInGuess = new boolean[5];

        /**
         * Algoritmo que busca se a letra na posição i da palavra do palpite do jogador é igual a letra na mesma posição
         * da palavra secreta. Se sim o array de verificação recebe o valor 2 que indica letra certa na posição certa.
         */
        for(int i = 0; i < 5; i++) {
            if(guessWord.charAt(i) == secretWord.charAt(i)) {
                status[i] = 2;
                usedInSecret[i] = true;
                usedInGuess[i] = true;
            }
        }

        for(int i = 0; i < 5; i++) {
            if(status[i] == 2) continue;
                for(int j = 0; j < 5; j++) {
                    if(!usedInSecret[j] && guessWord.charAt(i) == secretWord.charAt(j)) {
                        status[j] = 1;
                        usedInSecret[j] = true;
                        break;
                    }
                }
            }
        return status;
        }


    @Override
    public int requestIndex(int index) throws RemoteException {
        return 0;
    }
}
