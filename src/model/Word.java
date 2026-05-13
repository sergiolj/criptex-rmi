package model;

import java.io.Serializable;

/**
 *
 * @author Bruna Brito Muniz Filgueiras
 * @author Laís de Assis Doria da Silva
 * @author Sérgio Lopes Júnior
 *
 * @version 1.0
 */
public class Word implements Serializable {
    String word;

    public String getWord() {
        return word;
    }

    /** Valida se a palavra enviada pelo jogador existe e cumpre com a regra de possuir 5 letras.
     *
     * Como validar se a palavra existe??
     */
    public boolean validate(String word) {
        if(word.length() == 5){
            return true;
        }
        return false;
    }

}
