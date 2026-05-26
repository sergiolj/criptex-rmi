package server.service;

import java.time.Instant;
/**
 * Contador de tempo para determinar a mudança da palavra secreta.
 *
 * @author Bruna Brito Muniz Filgueiras
 * @author Laís de Assis Doria da Silva
 * @author Sérgio Lopes Júnior
 *
 * @version 1.0
 */
public class WordScheduler {
    private final Instant startTime;
    private final long INTERVAL_TO_CHANGE_SECRET_WORD = 60; // 2 minutos


    public WordScheduler(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public long getINTERVAL_TO_CHANGE_SECRET_WORD() {
        return INTERVAL_TO_CHANGE_SECRET_WORD;
    }
}
