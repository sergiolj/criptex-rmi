package shared;

public class Color {
    public static final String RESET = "\u001B[0m";

    public static final String VERDE = "\u001B[32m";   // Letra certa, lugar certo
    public static final String AMARELO = "\u001B[33m"; // Letra certa, lugar errado
    public static final String CINZA = "\u001B[37m";   // Letra não existe


    public static final String BG_VERDE = "\u001B[42m";
    public static final String BG_AMARELO = "\u001B[43m";
    public static final String BG_CINZA = "\u001B[47m";
}

//Exemplo de uso
//System.out.println(Color.VERDE + "Verde" + Color.AMARELO + "Amarela");
