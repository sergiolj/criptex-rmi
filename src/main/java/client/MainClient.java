package client;

import server.engine.Word;
import shared.config.Config;
import shared.status.Color;

import java.rmi.RemoteException;
import java.util.Scanner;

/**
 * Classe cliente com a interface de command line do app CRIPTEX RMI.
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
        boolean wordMatch = false;
        Client client;
        String name;
        final int MAX_ATTEMPTS = 6;
        final int WORD_LENGTH=5;
        int currentAttempt = 0;
        Word word = new Word();

        CmdLayout.printHeader();
        CmdLayout.printRules(MAX_ATTEMPTS, WORD_LENGTH);

        try{
            System.out.print("Digite o nome do jogador ou <ENTER>: ");
            name = sc.nextLine().trim();

            if(name.isEmpty()){
                client = new Client();
            }else{
                client = new Client(name);
            }

            System.out.println(Color.YELLOW + "\n>>> Bem-vindo(a), " + name +
                    "! O jogo começou. <<<\n" + Color.RESET);

            while(!wordMatch && currentAttempt < MAX_ATTEMPTS){
                System.out.print(Color.BOLD + "Tentativa [" + (currentAttempt + 1) + "/" + MAX_ATTEMPTS +
                        "] - Digite seu palpite: " + Color.RESET);

                String guess = sc.nextLine();
                if(word.validate(guess)){
                    currentAttempt++;
                    wordMatch = client.verifyGuess(guess);
                }else{
                    System.out.println("Palpite inválido, palavra não reconhecida ou com menos de 5 caracteres.");
                }
            }

        if (wordMatch) {
            System.out.println(Color.GREEN + Color.BOLD +
                    "🎉 PARABÉNS! Você descobriu a palavra em " + currentAttempt + " tentativa(s)!" + Color.RESET);
        } else {
            System.out.println(Color.RED + Color.BOLD +
                    "💀 Fim de jogo! Você esgotou suas " + MAX_ATTEMPTS + " tentativas." + Color.RESET);
        }

        } catch (RemoteException e) {
            sc.close();
            System.out.println(Color.RED + "\nServidor RMI: " + Config.SERVER_NAME +
                    " não encontrado no endereço " + Config.IP_ADDRESS + "\nVerifique se o servidor foi iniciado " +
                    "corretamente.");
        }
        sc.close();
   }
}
