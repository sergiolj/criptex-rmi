package server.service;

import shared.config.Config;
import shared.status.Color;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * GameManager gerencia e instancia as classes necessárias para o servidor RMI.
 *
 *  * @author Bruna Brito Muniz Filgueiras
 *  * @author Laís de Assis Doria da Silva
 *  * @author Sérgio Lopes Júnior
 *  *
 *  * @version 1.0
 *
 */
public class GameManager {
    //Cria uma classe cronograma para agendar a mudança das palavras por intervalo de tempo
    private final WordScheduler scheduler;
    private int wordIndex = 0;

    //Cria o dicionário de palavras
    private final Dictionary dictionary;

    /**Consumer é uma "Interface Funcional" do Java que armazena um bloco de código que sabe o que fazer quando recebe um
    tipo de dado como <String> nesse caso.*/
    private final Consumer<String> onWordChanged;

    //Cria uma referência Atômica para a palavra secreta para evitar problemas de concorrência.
    private final AtomicReference<String> secretWord;


    public GameManager(Consumer<String> onWordChanged) {
        this.scheduler = new WordScheduler();
        this.dictionary = Dictionary.getInstance();
        this.secretWord = new AtomicReference<>(dictionary.getWord(wordIndex)); //inicia sempre com a mesma referência.

        this.onWordChanged = onWordChanged;
        if(onWordChanged != null) {
            onWordChanged.accept(secretWord.get());
        }
        //scheduler.startSecretWordMonitor(this::updateSecretWord); //inicia o monitor de tempo para a troca da palavra
    }

    /**
     * Atualiza a referência String da palavra secreta com base no tempo que o WordSchedule foi instanciado. O índice
     * inicia em 0 e é incrementado com base no tempo especificado pela constante da classe.
     */
    public void updateSecretWord(){
        Instant now = Instant.now();

        long segundosDecorridos = now.getEpochSecond() - scheduler.getStartTime().getEpochSecond();

        // 2. Descobre em qual "rodada" ou ciclo de tempo nós estamos atualmente
        long rodadaAtual = segundosDecorridos / scheduler.getTIME_INTERVAL();

        // 3. Usa o resto da divisão (%) para não estourar o tamanho da lista de palavras
        wordIndex = (int) (rodadaAtual % dictionary.size());

        String word = dictionary.getWord(wordIndex);
        this.secretWord.set(word);

        /* Teste utilizado para verificar se a palavra é uma String e atualizar a palavra no console do servidor com
          base na classe Consumer<String> inserida no construtor da GameManager e no construtor da APServerImplement com as
          instruções de como atualizar o console do servidor.
         */
        if(onWordChanged != null){
            this.onWordChanged.accept(word);
        }
    }

    public void updateSecretWordDemoMode(){
        wordIndex++;
        String word = dictionary.getWord(wordIndex);
        this.secretWord.set(word);
        if(onWordChanged != null){
            this.onWordChanged.accept(word);
        }
    }

    public AtomicReference<String> getSecretWord() {
        return secretWord;
    }

    /** Método em cascata para desligar a thread do scheduler. Esse método é acionado pela classe MainServer */
    public void shutdown() {
        if(scheduler !=null){
            scheduler.shutdown();
            System.out.println("[" + Color.RED + "ServerName: " + Config.SERVER_NAME + "] " +
                    Color.RESET + "Encerrando controle de atualização da palavra secreta...");
        }
    }

    public Long getTimeInterval(){
        return this.scheduler.getTIME_INTERVAL();
    }

    public WordScheduler getScheduler(){
        return scheduler;
    }
}
