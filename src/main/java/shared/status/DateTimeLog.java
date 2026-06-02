package shared.status;

import java.time.LocalDateTime;

/**
 * Classe para padronização da resposta de data e horário para registro de log de ações do servidor.
 *
 *  * @author Bruna Brito Muniz Filgueiras
 *  * @author Laís de Assis Doria da Silva
 *  * @author Sérgio Lopes Júnior
 *  *
 *  * @version 1.0
 */
public class DateTimeLog {

    public static String dateTimeNow(){
        LocalDateTime now = LocalDateTime.now();
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm:ss");

        return now.format(formatter);
    }
}
