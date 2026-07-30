package server.service;

import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Contador de tempo usado para determinar a execução de uma tarefa em intervalo regular.
 *
 * @author Bruna Brito Muniz Filgueiras
 * @author Laís de Assis Doria da Silva
 * @author Sérgio Lopes Júnior
 *
 * @version 1.0
 */
public class WordScheduler {
    private final Instant startTime;

    /** Atributo que define em segundos o tempo para a troca da palavra secreta.*/
    private static final long timeInterval = 1800;

    /** Interface que cria um serviço gerenciador de threads (Pool de serviços)
     *  para executar uma tarefa com base em um intervalo de tempo.*/
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    /** Recebe uma instância da interface Runnable para ser executada com intervalo de tempo definido pelo atributo da classe.
     * Qualquer método pode ser um Runable deste que retorne void e não receba parâmetros.
     * */
    public void startSecretWordMonitor(Runnable task) {
        scheduler.scheduleAtFixedRate(task, timeInterval, timeInterval, TimeUnit.SECONDS);
    }

    /** Inicializa a classe tendo como referência um dado de tempo.*/
    public WordScheduler() {
        this.startTime = Instant.now();
    }

    public Instant getStartTime() {
        return startTime;
    }

    public long getTIME_INTERVAL() {
        return timeInterval;
    }

    public void shutdown() {
        scheduler.shutdown(); //Finaliza o ScheduledExecutorService usando o método da classe.
    }
}
