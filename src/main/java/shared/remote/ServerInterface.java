package shared.remote;

import shared.dto.GuessRequest;
import shared.dto.GuessResponse;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Interface que determina o contrato entre cliente e servidor RMI para o jogo CRIPTEX RMI.
 *
 * @author Sergio Lopes Júnior
 *
 * @version 2.0
 */
public interface ServerInterface extends Remote {
    /** Registra o usuário para que seja possível quantificar vitórias e lidar com índices de palavras
     * diferentes para cada usuário. */
    void loginUser(ClientInterface client) throws RemoteException;

    void logoutUser(ClientInterface client) throws RemoteException;

    /** Verifica se a palavra enviada pelo cliente corresponde à palavra secreta. */
    GuessResponse verifyGuess(GuessRequest guessRequest) throws RemoteException;

    List<Ranking> getRanking() throws RemoteException;
}
