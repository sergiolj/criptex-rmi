package shared.remote;

import shared.dto.PalpiteRequestDto;
import shared.dto.ResultadoPalpiteDto;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServidorInterface extends Remote {
    ResultadoPalpiteDto validarPalpite(PalpiteRequestDto dto)
        throws RemoteException;
}
