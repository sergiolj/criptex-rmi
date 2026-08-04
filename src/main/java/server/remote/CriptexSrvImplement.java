package server.remote;

import server.service.GameManager;
import shared.remote.Ranking;
import shared.config.Config;
import shared.dto.GuessRequest;
import shared.remote.ClientInterface;
import shared.remote.ServerInterface;
import shared.dto.GuessResponse;
import shared.status.Color;
import shared.status.FormatLog;
import util.Word;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

/**
 *
 * @author Sérgio Lopes Júnior
 *
 * @version 2.0
 */
public class CriptexSrvImplement extends UnicastRemoteObject implements ServerInterface {
    private final Map<UUID, ClientInterface> clients = new HashMap<>();
    private final Map<UUID, Instant> loginSession = new HashMap<>();

    private final List<Ranking> ranking = new ArrayList<>();
    private GameManager gameManager;

    private boolean demoMode = false;

    public CriptexSrvImplement(String[] args) throws RemoteException {
        super();
        setConfigArgs(args);
        if (demoMode) {
            this.gameManager = new GameManager(FormatLog::newSecretWord);
            FormatLog.special("DEMO MODE - Próxima palavra somente em caso de palavra desvendada.");
            resetRanking();
        } else {
            this.gameManager = new GameManager(FormatLog::newSecretWord);
            FormatLog.nextWordPeriod(String.valueOf(gameManager.getTimeInterval()/60));
            gameManager.getScheduler().startSecretWordMonitor(gameManager::updateSecretWord);
            resetRanking();
        }
    }

    private void setConfigArgs(String[] args) {
        if(args.length != 0){
            if(args[0].equalsIgnoreCase("d")){
                demoMode = true;
            }
        }
    }

    /**
     * Falta implementar uma verificação para reconhecer o usuário, caso esse já tenha registro no servidor.
     *
     * @param client
     * @throws RemoteException
     */
    @Override
    public void loginUser(ClientInterface client) throws RemoteException {
        FormatLog.logInOut(client, "login");

        clients.put(client.getUuid(),client);
        loginSession.put(client.getUuid(), Instant.now());
    }

    @Override
    public void logoutUser(ClientInterface client) throws RemoteException {
        FormatLog.logInOut(client, "logout");
        clients.remove(client.getUuid());
        loginSession.remove(client.getUuid());
    }


    @Override
    public GuessResponse verifyGuess(GuessRequest request) throws RemoteException {
        String secretWord = gameManager.getSecretWord().toString();

        String wordAttempt = request.getGuess().toUpperCase();
        Word validator = new Word();

        if(validator.validate(wordAttempt)){
            FormatLog.attempt(request);
            /**
             * Array que irá receber os valores verificados para as letras da palavra do palpite do jogador:
             * 0 - Letra não encontrada
             * 1 - Letra encontrada na posição errada
             * 2 - Letra encontrada na posição certa
             */
            int[] status = new int[5];

            boolean[] secretPositionsUsed = new boolean[5];
            boolean[] guessPositionsUsed = new boolean[5];
            /**
             * Algoritmo que busca se a letra na posição i da palavra do palpite do jogador é igual a letra na mesma posição
             * da palavra secreta. Se sim o array de verificação recebe o valor 2 que indica letra certa na posição certa.
             */
            for (int i = 0; i < 5; i++) {
                if (wordAttempt.charAt(i) == secretWord.charAt(i)) {
                    status[i] = 2;
                    secretPositionsUsed[i] = true;
                    guessPositionsUsed[i] = true;
                }
            }

            /**
             * Se uma letra do palpite já foi verificada e encontrada na palavra secreta a mesma não pode mais ser
             * verificada em outras posições da palavra secreta.
             */
            for (int i = 0; i < 5; i++) {
                if (guessPositionsUsed[i]) continue;

                for (int j = 0; j < 5; j++) {
                    if (!secretPositionsUsed[j] && wordAttempt.charAt(i) == secretWord.charAt(j)) {
                        status[i] = 1;
                        secretPositionsUsed[j] = true;
                        break;
                    }
                }
            }
            FormatLog.result(status, request);

            boolean wordMatch;
            wordMatch = Arrays.stream(status).allMatch(v -> v == 2);
            if(wordMatch){
                registerWinner(request.getUuid(), Instant.now());
                if (demoMode) gameManager.updateSecretWordDemoMode();
            }

            return new GuessResponse(status, wordAttempt, wordMatch);
        }
        return null;
    }

    @Override
    public List<Ranking> getRanking() throws RemoteException{
        return this.ranking;
    }

    public void shutdown() {
        gameManager.shutdown();
        try{
            FormatLog.shutdown("Desconectando servidor RMI...");

            //Remove o nome do servidor do registro RMI
            Naming.unbind("rmi://" + Config.IP_ADDRESS + ":" + Config.SERVER_PORT + "/" + Config.SERVER_NAME);

            //Remove o objeto da infraestrutura de execução do RMI fechando a porta que recebia requisições.
            UnicastRemoteObject.unexportObject(this, true);
        }catch (RemoteException e){
            System.out.println("Erro ao desligar servidor RMI: " + e.getMessage());
        } catch (MalformedURLException | NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void registerWinner(UUID playerUUID, Instant currentTime){
        Duration elapsedTime = Duration.between(loginSession.get(playerUUID), currentTime);
        ClientInterface player = clients.get(playerUUID);

        String name = (player != null) ? player.getName() : "Jogador não encontrado";

        ranking.add(new Ranking(name,elapsedTime));
        ranking.sort(Comparator.comparing(Ranking::duration));

    }

    private void resetRanking(){
        Duration duration;
        for(int i = 1; i<11; i++){
            duration = Duration.ofMinutes(i);
            ranking.add(new Ranking("AAA", duration));
        }
    }

}
