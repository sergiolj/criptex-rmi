package server;

import server.remote.APServerImplement;
import shared.config.Config;
import shared.status.Color;
import shared.status.DateTimeLog;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Inicializa o serviço do servidor RMI com base nas informações de configuração compartilhadas no pacote shared Config.
 *
 * @author Bruna Brito Muniz Filgueiras
 * @author Laís de Assis Doria da Silva
 * @author Sérgio Lopes Júnior
 *
 * @version 1.0
 */
public class MainServer {

    public static void main(String[] args) {
        APServerImplement criptexService = null;
        try{
            System.out.println("Servidor RMI \n[ServerName: " + Config.SERVER_NAME + "] " +
                    "[port:" + Config.SERVER_PORT + "] online...");
            criptexService = new APServerImplement();
            Registry registry = LocateRegistry.createRegistry(Config.SERVER_PORT);
            registry.rebind(Config.SERVER_NAME, criptexService);

            registerShutdownHook(criptexService);

        }catch (Exception e ){
            System.out.println("[" + Color.RED + DateTimeLog.dateTimeNow() + Color.RESET +
                    "] Erro ao inicializar o Servidor RMI: " + e.getMessage());
            if(criptexService != null){
                criptexService.shutdown();
            }
            System.exit(1); // System.exit(1) indica ao Sistema Operacional que o programa terminou com erro
        }
    }

    /* Rotina de shutdown em cascata para evitar que a thread de WordScheduler continue rodando após o desligamento*/
    private static void registerShutdownHook(APServerImplement service) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("[" + Color.RED + "ServerName: " + Config.SERVER_NAME +
                    Color.RESET + "] Sinal de encerramento recebido. Iniciando desligamento seguro...");
            if(service != null){
                service.shutdown(); // Para a thread do WordScheduler
            }
            System.out.println("[" + Color.RED + "ServerName: " + Config.SERVER_NAME +
                    Color.RESET + "] Servidor encerrado com sucesso!");
        }));
    }
}