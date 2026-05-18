package shared.dto;

import java.io.Serializable;

public class PalpiteRequestDto implements Serializable {
    private String jogador;
    private String palavra;

    public PalpiteRequestDto() {
    }

    public PalpiteRequestDto(String jogador, String palavra) {
        this.jogador = jogador;
        this.palavra = palavra;
    }
    public String getJogador() {
        return jogador;
    }
    public void setJogador(String jogador) {
        this.jogador = jogador;
    }
    public String getPalavra() {
        return palavra;
    }
    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }
}
