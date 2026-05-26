package shared.status;

/**
 * Classe que define as cores para as respostas do console quando da verificação pelo servidor da palavra enviada
 * pelo jogador.
 *
 * @author Bruna Brito Muniz Filgueiras
 * @author Laís de Assis Doria da Silva
 * @author Sérgio Lopes Júnior
 *
 * @version 1.0
 */
public class Color {

    /** Escrita no padrão do console. */
    public static final String RESET = "\u001B[0m";

    /** Letra certa, lugar certo. */
    public static final String GREEN = "\u001B[32m";

    /** Letra certa, lugar errado. */
    public static final String YELLOW = "\u001B[33m";

    /** Letra não existe na palavra. */
    public static final String GRAY = "\u001B[37m";

    public static final String BG_GREEN = "\u001B[42m";
    public static final String BG_YELLOW = "\u001B[43m";
    public static final String BG_GRAY = "\u001B[47m";
    public static final String BOLD = "\u001B[1m";

    public static final String CYAN = "\u001B[36m";
    public final static String PURPLE = "\u001B[35m";
    public final static String WHITE_BG_BLACK = "\u001B[37;40m";

    public static final String RED = "\u001B[31m";
}

//Exemplo de uso
//System.out.println(Color.VERDE + "Verde" + Color.AMARELO + "Amarela");
