package server.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Banco de dados de palavras do jogo Adivinha a Palavra
 * Implementado como um Singleton Thread-Safe para garantir a integridade dos dados em acessos simultâneos via RMI.
 *
 * @author Bruna Brito Muniz Filgueiras
 * @author Laís de Assis Doria da Silva
 * @author Sérgio Lopes Júnior
 *
 * @version 1.0
 */
public class Dictionary {
    private static Dictionary instance;
    private final String dictionaryPath = "resources/palavras.txt";
    private final List<String> words = new ArrayList<>();

    /** Implementa um contador de tempo para troca da palavra secreta. */
    private final WordScheduler ws = new WordScheduler(Instant.now());


    /** Remove todos os acentos e cedilha de uma string, convertendo em caracteres básicos de A-Z. */
    private String removeAccentFromWord(String word) {
        String normalizedWord = Normalizer.normalize(word, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalizedWord).replaceAll("");
    }

    /**
     * Cria o dicionário de palavras do jogo a partir de uma lista de palavras em txt, o que pode facilitar a
     * substituição do dicionário por outro ou por outra linguagem. Possibilitando ao usuário escolher o idioma que
     * deseja jogar ou até temática de palavras, por exemplo.
     */
    private void readRawDictionary(){
        try{
            BufferedReader br = new BufferedReader(new FileReader(dictionaryPath));
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                currentLine = currentLine.trim();
                if (currentLine.length() == 5) {
                    String wordModified = removeAccentFromWord(currentLine.toUpperCase());
                    if(wordModified.matches("^[A-Z]+$")){
                        words.add(wordModified);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao acessar arquivo base do dicionário em " + dictionaryPath);
        }
    }

    /** Todas as palavras devem ter cinco letras para o estilo do jogo. */
    private Dictionary() {
        readRawDictionary();
        }

        /** Retorna a instância única do dicionário simulando um BD centralizado.
     * Utiliza synchronized para evitar problemas de concorrência na criação da instância em ambiente
     * com múltiplos usuários simultâneos
     */
    public static synchronized Dictionary getInstance() {
        if (instance == null) {
            instance = new Dictionary();
        }
        return instance;
    }

    /**
     * Recupera uma palavra específica do dicionário com base em um índice fornecido.
     *
     * @param index O índice da palavra desejada.
     * @return A palavra em caixa alta ou mensagem de erro caso o índice seja inválido.
     */
    public String getWord(int index) {
        if (index >=0 && index < words.size()) {
            return words.get(index).toUpperCase();
        }else{
            return "FIM";
        }
    }

    /** Retorna a quantidade total de palavras no dicionário. */
    public int size() {
        return words.size();
    }

    public List<String> getDictionaryList() {
        return new ArrayList<>(this.words);
    }

    /**
     * Seleciona a palavra do dicionário com base na contagem de tempo do servidor.
     * @return
     */
    public synchronized String getCurrentSecretWord(){
        Instant now = Instant.now();

        long segundosDecorridos = now.getEpochSecond() - ws.getStartTime().getEpochSecond();

        // 2. Descobre em qual "rodada" ou ciclo de tempo nós estamos atualmente
        long rodadaAtual = segundosDecorridos / ws.getINTERVAL_TO_CHANGE_SECRET_WORD();

        // 3. Usa o resto da divisão (%) para não estourar o tamanho da lista de palavras
        int wordIndex = (int) (rodadaAtual % words.size());

        return words.get(wordIndex);
    }
}
