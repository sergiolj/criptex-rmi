package shared.dto;

import java.io.Serializable;
import java.util.List;

public class ResultadoPalpiteDto implements Serializable {

    private List<ResultadoLetraDto> letras;

    private boolean vencedor;

    public ResultadoPalpiteDto() {
    }

    public ResultadoPalpiteDto(List<ResultadoLetraDto> letras, boolean vencedor) {
        this.letras = letras;
        this.vencedor = vencedor;
    }

    public List<ResultadoLetraDto> getLetras() {
        return letras;
    }

    public void setLetras(List<ResultadoLetraDto> letras) {
        this.letras = letras;
    }

    public boolean isVencedor() {
        return vencedor;
    }

    public void setVencedor(boolean vencedor) {
        this.vencedor = vencedor;
    }
}
