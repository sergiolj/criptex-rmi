package shared;

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
    public static final String VERDE = "\u001B[32m";

    /** Letra certa, lugar errado. */
    public static final String AMARELO = "\u001B[33m";

    /** Letra não existe na palavra. */
    public static final String CINZA = "\u001B[37m";

    public static final String BG_VERDE = "\u001B[42m";
    public static final String BG_AMARELO = "\u001B[43m";
    public static final String BG_CINZA = "\u001B[47m";
}

//Exemplo de uso
//System.out.println(Color.VERDE + "Verde" + Color.AMARELO + "Amarela");
