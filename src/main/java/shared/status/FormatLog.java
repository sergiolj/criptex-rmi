package shared.status;

import shared.config.Config;
import shared.dto.GuessRequest;
import shared.remote.ClientInterface;

import java.util.Arrays;

public class FormatLog {

    public static void newSecretWord(String message){
        System.out.println(Color.BLUE + "[" + DateTimeLog.dateTimeNow() + "] " + Color.RESET +
                "[" + Config.SERVER_NAME + "] A palavra secreta atual é: " +
                message);
    }

    public static void shutdown(String message){
        System.out.println(Color.RED + "[ServerName: " + Config.SERVER_NAME  + "] " + Color.RESET +
                message);

    }

    public static void nextWordPeriod(String message){
        System.out.println(Color.BLUE + "[" + DateTimeLog.dateTimeNow() + "] " + Color.RESET +
                "[" + Config.SERVER_NAME + "] Próxima palavra em " +
                message + " minutos");
    }

    public static void special(String message){
        System.out.println(Color.PURPLE + "[" + DateTimeLog.dateTimeNow() + "] " + Color.RESET +
                "[" + Config.SERVER_NAME + "] " +
                message);
    }

    public static void logInOut(ClientInterface client, String type){
        if(type.equals("login")){
            System.out.println("[" + DateTimeLog.dateTimeNow()+ "] [" + Config.SERVER_NAME + "] " +
                    "Login player: " + client.getName() + " UUID[" + client.getUuid() + "]");
            return;
        } else if (type.equals("logout")) {
            System.out.println("[" + DateTimeLog.dateTimeNow()+ "] [" + Config.SERVER_NAME + "] " +
                    "Logout player: " + client.getName() + " UUID[" + client.getUuid() + "]");
            return;
        }
        System.out.println("Argument error");
    }

    public static void attempt(GuessRequest request) {
        System.out.printf(Color.GREEN + "[" + DateTimeLog.dateTimeNow() + "] "+ Color.RESET +
                         "Analysing attempt from player [UUID: %s]%n", request.getUuid());
    }

    public static void result(int[] status, GuessRequest request) {
        System.out.println(Color.GREEN + "[" + DateTimeLog.dateTimeNow() + "] " + Color.RESET +
                "Result from player [UUID: " + request.getUuid() + "] STATUS: " + Arrays.toString(status));
    }

    public static void starting() {
        System.out.println("Servidor RMI \n[ServerName: " + Config.SERVER_NAME + "] " +
                "[port:" + Config.SERVER_PORT + "] online...");
    }
}
