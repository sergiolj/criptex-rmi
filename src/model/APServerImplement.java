package model;

import shared.ServerInterface;

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
        System.out.println("Registering online player: " + client.getName());
        clients.add(client);
    }


    @Override
    public int [] verifyGuess(Client client, String word) throws RemoteException {
        countCharOk = 0;
        Arrays.fill(charVerified, '\u0000');

        System.out.printf("Analysing attempt from player: %s [UUID: %s)%n",
                client.getName(),
                client.getId());

//            client = clients.get(clients.indexOf(client));
//            client.increaseAttempt();
//            if(client.getAttempts() >5){
//                System.out.println("Você não conseguiu descobrir a palavra dessa vez. :_(" );
//                //Sai do loop e impede novas tentativas
//            }

        String secretWord = dictionary.getWord(client.getIndex());
        System.out.println("Secret word: " + secretWord);

        String guessWord = word.toUpperCase();

        /**
         * Array que irá receber os valores verificados para as letras da palavra do palpite do jogador:
         * 0 - Letra não encontrada
         * 1 - Letra encontrada na posição errada
         * 2 - Letra encontrada na posição certa
         */
        int[] status = new int[5];

        /**
         * Algoritmo que busca se a letra na posição i da palavra do palpite do jogador é igual a letra na mesma posição
         * da palavra secreta. Se sim o array de verificação recebe o valor 2 que indica letra certa na posição certa.
         */
        for (int i = 0; i < 5; i++) {
            if (guessWord.charAt(i) == secretWord.charAt(i)) {
                status[i] = 2;
                charVerified[countCharOk++] = guessWord.charAt(i);
                System.out.println(charVerified);
            }
        }

        /**
         * Se uma letra do palpite já foi verificada e encontrada na palavra secreta a mesma não pode mais ser
         * verificada em outras posições da palavra secreta.
         */
        for (int i = 0; i < 5; i++) {
            if (status[i] == 0 && !letterVerified(guessWord.charAt(i))) {
                for (int j = 0; j < 5; j++) {
                    if (guessWord.charAt(i) == secretWord.charAt(j)) {
                        status[i] = 1;
                        break;
                    }
                }
            }
        }
        System.out.println("Result from player [UUID: " + client.getId() + "] guess: " + Arrays.toString(status));
        return status;
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
