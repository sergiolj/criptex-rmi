package shared.dto;

import shared.enums.StatusLetra;
import java.io.Serializable;

public class ResultadoLetraDto implements Serializable {
    private char letra;
    private StatusLetra status;

    public ResultadoLetraDto() {

    }

    public ResultadoLetraDto(char letra, StatusLetra status) {
        this.letra = letra;
        this.status = status;
    }

    public char getLetra() {
        return letra;
    }

    public void setLetra(char letra) {
        this.letra = letra;
    }

    public StatusLetra getStatus() {
        return status;
    }

    public void setStatus(StatusLetra status) {
        this.status = status;
    }
}
