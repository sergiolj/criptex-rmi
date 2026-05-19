package shared.dto;

import shared.status.StatusLetter;

import java.io.Serializable;

/**
 *
 * @author Bruna Brito Muniz Filgueiras
 * @author Laís de Assis Doria da Silva
 * @author Sérgio Lopes Júnior
 *
 * @version 1.0
 */
public class LetterResponseDTO implements Serializable {

        private char letter;
        private StatusLetter status;

        public LetterResponseDTO() {}

        public LetterResponseDTO(char letter, StatusLetter status) {
            this.letter = letter;
            this.status = status;
        }

        public char getLetter() {
            return letter;
        }

        public void setLetter(char letter) {
            this.letter = letter;
        }

        public StatusLetter getStatus() {
            return status;
        }

        public void setStatus(StatusLetter status) {
            this.status = status;
        }
}
