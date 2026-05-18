package server.remote;

import server.service.ServicoJogo;
import shared.dto.PalpiteRequestDto;
import shared.dto.ResultadoPalpiteDto;
import shared.remote.ServidorInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class ServidorRMIimplement extends UnicastRemoteObject implements ServidorInterface {
    private final ServicoJogo servicoJogo =  new ServicoJogo();

    public ServidorRMIimplement() throws RemoteException {
    }

    @Override
    public ResultadoPalpiteDto validarPalpite(PalpiteRequestDto dto)
        throws RemoteException {
        return servicoJogo.processarPalpite(dto);
    }
    }

