package shared.dto;

import java.io.Serial;
import java.io.Serializable;

/**
 *
 * Data Transfer Object que envia a resposta do servidor para a consulta da palavra do jogador.
 *
 *
 * @author Bruna Brito Muniz Filgueiras
 * @author Laís de Assis Doria da Silva
 * @author Sérgio Lopes Júnior
 *
 * @version 1.0
 */
public class GuessResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final int [] status;
    private final String guess;
    private final boolean wordMatch;
    private final int currentAttempt;

    //**A passagem do tempo pode ser uma forma de definir o Ranking dos vencedores, mas não foi implementada ainda
    // Talvez possa ser implementada na classe GameSession*/
    //private final String enlapsedTime;

    public GuessResponse(int[] status, String guess, boolean wordMatch) {
        this.status = status;
        this.guess = guess;
        this.wordMatch = wordMatch;
        this.currentAttempt = 0;
        //this.enlapsedTime = "0";
    }

    public GuessResponse(int[] status, String guess, boolean wordMatch, int currentAttempt) {
        this.currentAttempt = currentAttempt;
        //this.enlapsedTime = "0";
        this.status = status;
        this.guess = guess;
        this.wordMatch = wordMatch;
    }
    /**
     *
     * @return um array de inteiros com as respostas da verificação.
     */
    public int[] getStatus() {
        return status;
    }

    public String getGuess() {
        return guess;
    }

    public boolean isWordMatch() {
        return wordMatch;
    }

    public int getCurrentAttempt() {
        return currentAttempt;
    }

}
