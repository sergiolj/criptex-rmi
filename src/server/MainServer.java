package server;

import server.remote.APServerImplement;
import shared.config.Config;
import shared.status.Color;

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
        try{
            System.out.println("Servidor RMI \n[ServerName: " + Config.SERVER_NAME + "] " +
                    "[port:" + Config.SERVER_PORT + "] online...");
            APServerImplement serviceMsgSrv = new APServerImplement();
            Registry registry = LocateRegistry.createRegistry(Config.SERVER_PORT);
            registry.rebind(Config.SERVER_NAME, serviceMsgSrv);


            /* Rotina de shutdown em cascata para evitar que a thread de WordScheduler continue rodando após o desligamento*/
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("[ServerName: " + Config.SERVER_NAME +
                        "] Sinal de encerramento recebido. Iniciando desligamento seguro...");
                serviceMsgSrv.shutdown();
                System.out.println("[" + Color.RED + "ServerName: " + Config.SERVER_NAME +
                        Color.RESET + "] Servidor encerrado com sucesso!");
            }));
        }catch (Exception e ){
            System.out.println("Erro ao inicializar o Servidor RMI: " + e.getMessage());
        }
    }
}