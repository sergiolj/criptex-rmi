package main;

import model.APServerImplement;
import shared.Config;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

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