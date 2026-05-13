package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Banco de dados de palavras do jogo Adivinha a Palavra
 * Implementado como um Singleton Thread-Safe para garantir a integridade dos dados em acessos simultâneos via RMI.
 *
 * @author Bruna Brito Muniz Filgueiras
 * @author Laís de Assis Doria da Silva
 * @author Sérgio Lopes Júnior
 *
 * @version 1.0
 */
public class Dictionary {
    private static Dictionary instance;
    private final List<String> words = new ArrayList<>();

    /** Todas as palavras devem ter cinco letras para o estilo do jogo. */
    private Dictionary() {
        words.add("VOLEI");
        words.add("ATRIO");
        words.add("BROTO");
        words.add("SAGAZ");
        words.add("AMAGO");
    }

    /** Retorna a instância única do dicionário simulando um BD centralizado.
     * Utiliza synchronized para evitar problemas de concorrência na criação da instância em ambiente
     * com múltiplos usuários simultâneos
     */
    public static synchronized Dictionary getInstance() {
        if (instance == null) {
            instance = new Dictionary();
        }
        return instance;
    }

    /**
     * Recupera uma palavra específica do dicionário com base em um índice fornecido.
     *
     * @param index O índice da palavra desejada.
     * @return A palavra em caixa alta ou mensagem de erro caso o índice seja inválido.
     */
    public String getWord(int index) {
        if (index >=0 && index < words.size()) {
            return words.get(index).toUpperCase();
        }else{
            return "FIM";
        }
    }

    /** Retorna a quantidade total de palavras no dicionário. */
    public int size() {
        return words.size();
    }
}
