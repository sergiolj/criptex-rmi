package model;

import shared.status.Color;

/**
 *
 * Classe com a interface gráfica das instruções de jogo do cliente.
 *
 *
 * @author Bruna Brito Muniz Filgueiras
 * @author Laís de Assis Doria da Silva
 * @author Sérgio Lopes Júnior
 *
 * @version 1.0
 */
public class CmdLayout {
    public static void printHeader() {
        // Linha horizontal sólida de separação (largura total de 71 caracteres)
        final String LINHA_HORIZONTAL = Color.PURPLE +
                "───────────────────────────────────────────────────────────────────────" + Color.RESET;
        final int LARGURA_TOTAL = 71;

        System.out.println(LINHA_HORIZONTAL);
        // Título Centralizado
        imprimirCentralizado(Color.CYAN + Color.BOLD + "BEM-VINDO AO CRIPTEX RMI" + Color.RESET, LARGURA_TOTAL);
        System.out.println(LINHA_HORIZONTAL);

        // Regras do Jogo Centralizadas
        imprimirCentralizado("Sua missão é decifrar a palavra secreta de 5 letras", LARGURA_TOTAL);
        imprimirCentralizado("antes que o mecanismo se auto-destrua (" + Color.RED + Color.BOLD +
                "6 tentativas" + Color.RESET + ").", LARGURA_TOTAL);
        System.out.println();
        imprimirCentralizado(Color.CYAN +
                "A cada tentativa, o Criptex responderá com pistas:" + Color.RESET, LARGURA_TOTAL);
        System.out.println(LINHA_HORIZONTAL);

        // Legenda de Cores (Centralizadas individualmente)
        imprimirCentralizado("🟩 " + Color.GREEN + Color.BOLD + "VERDE   " + Color.RESET + ": " +
                "A letra faz parte do segredo e está na " + Color.GREEN + Color.BOLD +
                "POSIÇÃO CORRETA" + Color.RESET + ".", LARGURA_TOTAL);
        System.out.println();
        imprimirCentralizado("🟨 " + Color.YELLOW + Color.BOLD + "AMARELO " + Color.RESET + ": " +
                "A letra faz parte do segredo, mas está na " + Color.YELLOW + Color.BOLD +
                "POSIÇÃO ERRADA" + Color.RESET + ".", LARGURA_TOTAL);
        System.out.println();
        imprimirCentralizado("⬛ " + Color.BOLD + "CINZA   " + Color.RESET + ": " +
                "A letra " + Color.RED + "NÃO FAZ PARTE" + Color.RESET + " da palavra secreta atual.", LARGURA_TOTAL);

        System.out.println(LINHA_HORIZONTAL);
        // Alertas Finais Centralizados
        imprimirCentralizado(Color.RED + Color.BOLD + "ATENÇÃO:" + Color.RESET +
                " O Criptex altera sua senha dinamicamente com o passar do tempo.", LARGURA_TOTAL);
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
    public static void printRules(int maxAttempts, int wordLength) {
        System.out.println(Color.BOLD + "📖 REGRAS DO JOGO:" + Color.RESET);
        System.out.println("1. Você tem " + maxAttempts + " tentativas para descobrir a palavra secreta.");
        System.out.println("2. A palavra sempre terá " + wordLength + " letras.");
        System.out.println("3. Digite palavras válidas e aguarde a validação do servidor.");
        System.out.println("4. As palavra acentuadas serão processadas sem acentos\n");
    }
}
