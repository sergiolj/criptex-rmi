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
        Client client;
        String name;

        try{
            System.out.print("Digite o nome do jogador ou <ENTER>:");
            name = sc.nextLine();

            if(name.isEmpty()){
                client = new Client();
            }else{
                client = new Client(name);
            }

            while(!isSolved){
                System.out.print("Digite seu palpite: ");
                String guess = sc.nextLine();
                client.verifyGuess(guess);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }




    }
}
