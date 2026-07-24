package shared.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

/**
 *
 * Envia ao servidor a palavra a ser testada e o ID do jogador que requisitou a verificação.
 *
 * @author Bruna Brito Muniz Filgueiras
 * @author Laís de Assis Doria da Silva
 * @author Sérgio Lopes Júnior
 *
 * @version 1.0
 */
public class GuessRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String guess;
    private final UUID uuid;

    public GuessRequest(UUID uuid, String guess) {
        this.guess = guess;
        this.uuid = uuid;
    }

    public String getGuess() {
        return guess;
    }

    public UUID getUuid() {
        return uuid;
    }
}
