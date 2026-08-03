package server.service;

import shared.status.Color;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Banco de dados de palavras do jogo Adivinha a Palavra
 * Implementado como um Singleton Thread-Safe para garantir a integridade dos dados em acessos simultâneos via RMI.
 * Tem a função de ler um arquivo bruto de palavras e através de métodos de seleção adicionar apenas palavras válidas.
 *
 * @author Bruna Brito Muniz Filgueiras
 * @author Laís de Assis Doria da Silva
 * @author Sérgio Lopes Júnior
 *
 * @version 1.0
 */
public class Dictionary {
    private static Dictionary instance;
    private final String dictionaryPath = "/palavras.txt";
    private final List<String> words = new ArrayList<>();

    /** Todas as palavras devem ter cinco letras para o estilo do jogo. */
    private Dictionary() {
        createDictionaryFromICF(16.0, "/icf");
    }

    /** Remove todos os acentos e cedilha de uma string, convertendo em caracteres básicos de A-Z. */
    public String removeAccentFromWord(String word) {
        String normalizedWord = Normalizer.normalize(word, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalizedWord).replaceAll("");
    }

    /**
     * Cria o dicionário de palavras do jogo a partir de uma lista de palavras em txt, o que pode facilitar a
     * substituição do dicionário por outro ou por outra linguagem. Possibilitando ao usuário escolher o idioma que
     * deseja jogar ou até temática de palavras, por exemplo.
     */
    private void readRawDictionary() {
        /* O método getResourceAsStream é utilizado para carregar o arquivo diretamente do classpath.
        Isso evita erros de mapeamento ao usar caminhos absolutos do sistema operacional e garante
        que o recurso seja acessado corretamente, mesmo quando a aplicação for empacotada. */
        try (InputStream is = getClass().getResourceAsStream(dictionaryPath)) {
            if (is == null) {
                System.out.println("[" + Color.RED + "FATAL ERROR" + Color.RESET +
                        "] Arquivo base do dicionário não foi encontrado em " + dictionaryPath);
                return;
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                String currentLine;
                while ((currentLine = br.readLine()) != null) {
                    currentLine = currentLine.trim();
                    if (currentLine.length() == 5) {
                        String wordModified = removeAccentFromWord(currentLine.toUpperCase());
                        if (wordModified.matches("^[A-Z]+$")) {
                            words.add(wordModified);
                        }
                    }
                }
            }
            } catch (IOException e) {
                System.out.println("[" + Color.RED + "FATAL ERROR" + Color.RESET +
                        "] Erro ao ler dados no arquivo base do dicionário em " + dictionaryPath);
            }
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

    /** Retorna a quantidade total de palavras no dicionário. */
    public int size() {
        return words.size();
    }

    /**
     * Seleciona a palavra do dicionário com base em um index.
     * @return current secretWord
     */
    public String getWord(int index) {
        return words.get(index);
    }

    public boolean wordExists(String word) {
        return words.contains(word);
    }

    public List<String> readMostCommonWords(Double rareness, String fileName) {
        List<String> wordList = new ArrayList<>();

        try (InputStream is = getClass().getResourceAsStream(fileName)) {
            if (is == null) {
                throw new IllegalArgumentException("Arquivo /icf não encontrado.");
            }
            BufferedReader bf = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String word;

            double icf;
            while ((word = bf.readLine()) != null) {
                String[] lineSplit = word.split(",");
                word = lineSplit[0].trim();
                icf = Double.parseDouble(lineSplit[1].trim());

                if (icf < rareness && word.length() == 5) {
                    wordList.add(word);
                }
            }

        } catch (IOException e) {
            System.out.println("Erro ao acessar arquivo.");
            throw new RuntimeException(e.getMessage());
        }
        System.out.println("Total de palavras adicionadas ao dicionário: " + wordList.size());
        return wordList;
    }

    public void createDictionaryFromICF(Double rareness, String filename) {
        List<String> readWords = readMostCommonWords(rareness, filename);

        for (String w : readWords) {
            String wordModified = removeAccentFromWord(w.toUpperCase());
            if (wordModified.matches("^[A-Z]+$")) {
                words.add(wordModified);
            }
        }
    }
}

