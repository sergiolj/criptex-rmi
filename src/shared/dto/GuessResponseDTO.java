package shared.dto;

import java.io.Serial;
import java.io.Serializable;

/**
 *
 * Resposta do servidor para a consulta da palavra do jogador.
 *
 *
 * @author Bruna Brito Muniz Filgueiras
 * @author Laís de Assis Doria da Silva
 * @author Sérgio Lopes Júnior
 *
 * @version 1.0
 */
public class GuessResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final int [] status;
    private final String guess;
    private final boolean wordMatch;
    private final int currentAttempt;
    private final String elapsedTime;

    public GuessResponseDTO(int [] status, String guess, boolean wordMatch) {
        super();
        this.status = status;
        this.guess = guess;
        this.wordMatch = wordMatch;
        this.currentAttempt = 0;
        this.elapsedTime = "0";
    }

    public GuessResponseDTO() {
        super();
        this.currentAttempt = 0;
        this.elapsedTime = "0";
        this.status = null;
        this.guess = null;
        this.wordMatch = false;
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

    public String getElapsedTime() {
        return elapsedTime;
    }

}
