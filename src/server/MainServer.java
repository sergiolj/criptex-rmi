package server;

import server.remote.APServerImplement;
import shared.config.Config;

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
            ServidorRMIimplement serviceMsgSrv = new ServidorRMIimplement();
            Registry registry = LocateRegistry.createRegistry(Configuracao.SERVER_PORT);
            registry.rebind(Configuracao.SERVER_NAME, serviceMsgSrv);
            System.out.println("Servidor RMI \n[ServerName: " + Configuracao.SERVER_NAME + "] [port:" + Configuracao.SERVER_PORT + "] online...");
        }catch (Exception e ){
            System.out.println("Erro ao inicializar o Servidor RMI: " + e.getMessage());
        }
    }
}