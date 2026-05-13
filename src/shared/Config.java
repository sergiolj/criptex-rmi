package shared;

/**
 * Classe que define a configuração do servidor RMI.
 * Essas configurações serão usadas para determinar os parâmetros de inicialização do servidor e do cliente, mantendo o
 * arquivo de execução do serviço e do cliente desacoplados habilitando os mesmos para serem utilizados em outros
 * projetos com mínimas modificações.
 *
 * @author Bruna Brito Muniz Filgueiras
 * @author Laís de Assis Doria da Silva
 * @author Sérgio Lopes Júnior
 *
 * @version 1.0
 */
public class Config {
    /** Nome único do serviço registrado no RMI Registry. */
    public static final String SERVER_NAME ="APServer";

    /** Porta padrão usada para o registro RMI. */
    public static final Integer SERVER_PORT =1099;

    /** Endereço IP do servidor que será usado na configuração do Client.*/
    public static final String IP_ADDRESS = "10.10.82.78";
}
