package main;

import server.APServerImplement;
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
            APServerImplement serviceMsgSrv = new APServerImplement();
            Registry registry = LocateRegistry.createRegistry(Config.SERVER_PORT);
            registry.rebind(Config.SERVER_NAME, serviceMsgSrv);
            System.out.println("Servidor RMI \n[ServerName: " + Config.SERVER_NAME + "] [port:" + Config.SERVER_PORT + "] online...");
        }catch (Exception e ){
            System.out.println("Erro ao inicializar o Servidor RMI: " + e.getMessage());
        }
    }
}