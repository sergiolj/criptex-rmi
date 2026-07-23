package util;

import server.service.Dictionary;

import java.io.Serializable;
import java.text.Normalizer;
import java.util.regex.Pattern;

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
        return word != null && word.trim().length() == 5;
    }

    private boolean wordExists(String word) {
        Dictionary dictionary = Dictionary.getInstance();
        String verifyWord = removeAccent(word);

        return dictionary.wordExists(verifyWord.trim().toUpperCase());
    }

    public String removeAccent(String word) {
        String normalizedWord = Normalizer.normalize(word, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalizedWord).replaceAll("");
    }
}
