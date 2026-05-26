package client;


import server.engine.Word;
import shared.status.Color;

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
    private static final String RESET = Color.RESET;
    private static final String GREEN = Color.GREEN;
    private static final String YELLOW = Color.YELLOW;
    private static final String RED = Color.RED;
    private static final String BOLD = Color.BOLD;
    private static final String CYAN = Color.CYAN;
    private static final String PURPLE = Color.PURPLE;
    private final String WHITE_BG_BLACK = Color.WHITE_BG_BLACK;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean wordMatch = false;
        Client client;
        String name;
        final int MAX_ATTEMPTS = 6;
        final int WORD_LENGTH=5;
        int currentAttempt = 0;
        Word word = new Word();

        printHeader();
        printRules(MAX_ATTEMPTS, WORD_LENGTH);

        try{
            System.out.print("Digite o nome do jogador ou <ENTER>:");
            name = sc.nextLine().trim();

            if(name.isEmpty()){
                client = new Client();
            }else{
                client = new Client(name);
            }
            System.out.println(YELLOW + "\n>>> Bem-vindo(a), " + name + "! O jogo começou. <<<\n" + RESET);

            while(!wordMatch && currentAttempt < MAX_ATTEMPTS){
                System.out.print(BOLD + "Tentativa [" + (currentAttempt + 1) + "/" + MAX_ATTEMPTS + "] - Digite seu palpite: " + RESET);

                String guess = sc.nextLine();
                if(word.validate(guess)){
                    currentAttempt++;
                    wordMatch = client.verifyGuess(guess);
                }else{
                    System.out.println("Palpite inválido, palavra não reconhecida ou com menos de 5 caracteres.");
                }
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        if (wordMatch) {
            System.out.println(GREEN + BOLD + "🎉 PARABÉNS! Você descobriu a palavra em " + currentAttempt + " tentativa(s)!" + RESET);
        } else {
            System.out.println(RED + BOLD + "💀 Fim de jogo! Você esgotou suas " + MAX_ATTEMPTS + " tentativas." + RESET);
        }
   }
    private static void printHeader() {
        // Linha horizontal sólida de separação (largura total de 71 caracteres)
        final String LINHA_HORIZONTAL = PURPLE + "───────────────────────────────────────────────────────────────────────" + RESET;
        final int LARGURA_TOTAL = 71;

        System.out.println(LINHA_HORIZONTAL);
        // Título Centralizado
        imprimirCentralizado(CYAN + BOLD + "BEM-VINDO AO CRIPTEX RMI" + RESET, LARGURA_TOTAL);
        System.out.println(LINHA_HORIZONTAL);

        // Regras do Jogo Centralizadas
        imprimirCentralizado("Sua missão é decifrar a palavra secreta de 5 letras", LARGURA_TOTAL);
        imprimirCentralizado("antes que o mecanismo se auto-destrua (" + RED + BOLD + "6 tentativas" + RESET + ").", LARGURA_TOTAL);
        System.out.println();
        imprimirCentralizado(CYAN + "A cada tentativa, o Criptex responderá com pistas:" + RESET, LARGURA_TOTAL);
        System.out.println(LINHA_HORIZONTAL);

        // Legenda de Cores (Centralizadas individualmente)
        imprimirCentralizado("🟩 " + GREEN + BOLD + "VERDE   " + RESET + ": A letra faz parte do segredo e está na " + GREEN + BOLD + "POSIÇÃO CORRETA" + RESET + ".", LARGURA_TOTAL);
        System.out.println();
        imprimirCentralizado("🟨 " + YELLOW + BOLD + "AMARELO " + RESET + ": A letra faz parte do segredo, mas está na " + YELLOW + BOLD + "POSIÇÃO ERRADA" + RESET + ".", LARGURA_TOTAL);
        System.out.println();
        imprimirCentralizado("⬛ " + BOLD + "CINZA   " + RESET + ": A letra " + RED + "NÃO FAZ PARTE" + RESET + " da palavra secreta atual.", LARGURA_TOTAL);

        System.out.println(LINHA_HORIZONTAL);
        // Alertas Finais Centralizados
        imprimirCentralizado(RED + BOLD + "ATENÇÃO:" + RESET + " O Criptex altera sua senha dinamicamente com o passar do tempo.", LARGURA_TOTAL);
        imprimirCentralizado("Seja rápido na sua descriptografia!", LARGURA_TOTAL);
        System.out.println(LINHA_HORIZONTAL);
        System.out.println();
    }

    /**
     * Método auxiliar que calcula o espaçamento e imprime qualquer texto centralizado na tela.
     * Ele limpa os códigos ANSI antes de medir o tamanho real do texto visível.
     */
    private static void imprimirCentralizado(String texto, int larguraTotal) {
        // Remove códigos de cores ANSI e emojis conhecidos da contagem para o cálculo não errar
        String textoLimpo = texto.replaceAll("\u001B\\[[;\\d]*m", "")
                .replace("🟩", "  ") // Emojis ocupam 2 espaços visíveis
                .replace("🟨", "  ")
                .replace("⬛", "  ");

        int tamanhoTexto = textoLimpo.length();
        int espaçosEsquerda = (larguraTotal - tamanhoTexto) / 2;

        if (espaçosEsquerda > 0) {
            // Imprime a quantidade de espaços calculada antes do texto
            System.out.printf("%" + espaçosEsquerda + "s%s%n", "", texto);
        } else {
            System.out.println(texto);
        }
    }

    // Método extraído para as regras
    private static void printRules(int maxAttempts, int wordLength) {
        System.out.println(BOLD + "📖 REGRAS DO JOGO:" + RESET);
        System.out.println("1. Você tem " + maxAttempts + " tentativas para descobrir a palavra secreta.");
        System.out.println("2. A palavra sempre terá " + wordLength + " letras.");
        System.out.println("3. Digite palavras válidas e aguarde a validação do servidor.\n");
    }
}
