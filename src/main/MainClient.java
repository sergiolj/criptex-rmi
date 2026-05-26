package main;


import client.Client;

import java.rmi.RemoteException;
import java.util.Scanner;

/**
 *
 * @author Bruna Brito Muniz Filgueiras
 * @author Laís de Assis Doria da Silva
 * @author Sérgio Lopes Júnior
 *
 * @version 1.0
 */
public class MainClient {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("===== LETRECO - ADIVINHE A PALAVRA =====");
            System.out.println("Regras:");
            System.out.println("- A palavra possui 5 letras.");
            System.out.println("- Você tem 5 tentativas.");
            System.out.println("- Verde: letra certa no lugar certo.");
            System.out.println("- Amarelo: letra existe, mas está no lugar errado.");
            System.out.println("- Cinza: letra não existe na palavra.");
            System.out.println("========================================");

            Client client = new Client();
            boolean wordMatch = false;

            while (!wordMatch && client.getGameSession().hasAttempts()) {
                System.out.print("\nDigite seu palpite: ");
                String guess = sc.nextLine();

                wordMatch = client.verifyGuess(guess);
            }

            if (wordMatch) {
                System.out.println("\nParabéns! Você descobriu a palavra da rodada.");
            } else {
                System.out.println("\nSuas tentativas acabaram. Você não descobriu a palavra.");
            }

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
