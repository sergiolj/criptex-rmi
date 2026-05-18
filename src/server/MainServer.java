package server;

import server.remote.ServidorRMIimplement;
import shared.config.Configuracao;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

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