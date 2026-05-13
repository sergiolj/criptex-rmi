package dto;

import model.Client;
import model.Word;

import java.io.Serializable;

/**
 *
 * @author Bruna Brito Muniz Filgueiras
 * @author Laís de Assis Doria da Silva
 * @author Sérgio Lopes Júnior
 *
 * @version 1.0
 */
public class GuessDTO implements Serializable {
    private Word guess;
    private Client client;


    public GuessDTO(Client client, Word guess) {
        this.guess = guess;
        this.client = client;
    }

    public Word getGuess() {
        return guess;
    }

    public Client getClient() {
        return client;
    }
}
