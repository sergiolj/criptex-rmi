package shared;

import model.Client;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface que determina o contrato entre cliente e servidor RMI para o jogo Adivinha Palavra.
 *
 * @author Bruna Brito Muniz Filgueiras
 * @author Lais de Assis Doria da Silva
 * @author Sergio Lopes Júnior
 *
 * @version 1.0
 */
public interface ServerInterface extends Remote {
    /** Registra o usuário para que seja possível quantificar vitórias e lidar com índices de palavras
     * diferentes para cada usuário. */
    void registerUser(Client client) throws RemoteException;

    /** Verifica se a palavra enviada pelo cliente corresponde à palavra secreta. */
    int [] verifyGuess(Client client, String word) throws RemoteException;

    /** Recupera o index da palavra para o cliente específico. */
    int requestIndex(int index) throws RemoteException;
}
