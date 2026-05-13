package main;


import model.Client;
import model.Word;
import shared.Color;

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
        boolean isSolved = false;

        try{
            Client client = new Client();
            System.out.println("Digite o nome do jogador ou <ENTER>:");
            String nome = sc.nextLine();
            if(!nome.equalsIgnoreCase("")){
                client.setName(nome);
            }

            while(!isSolved){
                System.out.print("Digite seu palpite: ");
                String guess = sc.nextLine();
                client.verifyGuess(client, guess);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }




    }
}
