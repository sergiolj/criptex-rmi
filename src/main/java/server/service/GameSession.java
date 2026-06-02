package server.service;

import java.io.Serializable;


/**
 *
 * Deve armazenar as informações do jogo como tentativas e tempo decorrido.
 *
 * @author Bruna Brito Muniz Filgueiras
 * @author Laís de Assis Doria da Silva
 * @author Sérgio Lopes Júnior
 *
 * @version 1.0
 */
public class GameSession implements Serializable {
    private int attempt = 0;
    private final int maxAttempts = 6;

    public void increaseAttempt() {
        attempt++;
    }

    public int getAttempt() {
        return attempt;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public boolean hasAttempts() {
        return attempt < maxAttempts;
    }
}
