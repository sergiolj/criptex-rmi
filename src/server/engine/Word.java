package server.engine;

import java.io.Serializable;

/**
 * Classe de validação de palavras. Deve conter métodos para validar o tamanho da palavra enviada pelo
 * jogador, assim como poderá validar se a palavra existe, para evitar jogadas como AEIOU.
 *
 * @author Bruna Brito Muniz Filgueiras
 * @author Laís de Assis Doria da Silva
 * @author Sérgio Lopes Júnior
 *
 * @version 1.0
 */
public class Word implements Serializable {
    private String word;

    public Word() {}

    /**
     * Valida as regras de negócio da palavra enviada pelo jogador.
     *
     * @param word palpite enviado pelo jogador.
     * @return true caso todas as regras sejam validadas e falso caso não valide ao menos uma.
     */
    public boolean validate(String word) {
        return wordSize(word) && wordExists(word);
    }

    private boolean wordSize(String word) {
        return word.length() == 5;
    }

    private boolean wordExists(String word) {
        return true;
    }
}
