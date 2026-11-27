# Criptex RMI (Servidor)

Servidor do **Criptex**, um jogo de adivinhação de palavras de cinco letras no estilo *Wordle*, escrito em
Java 21 com **Java RMI**. O servidor sorteia a palavra secreta, valida os palpites dos jogadores e mantém o
ranking.

> Cliente gráfico (JavaFX): [sergiolj/criptexClientJFX](https://github.com/sergiolj/criptexClientJFX).
> Este repositório também traz um cliente de linha de comando simples (`model.MainClient`).

## Sumário

- [Como funciona](#como-funciona)
- [Arquitetura](#arquitetura)
- [Requisitos para compilar](#requisitos-para-compilar)
- [Executar em modo de desenvolvimento](#executar-em-modo-de-desenvolvimento)
- [Gerar um executável que não precisa de JVM instalada](#gerar-um-executável-que-não-precisa-de-jvm-instalada)
  - [Passo 1: runtime enxuto com jlink](#passo-1-runtime-enxuto-com-jlink)
  - [Passo 2: executável nativo com jpackage](#passo-2-executável-nativo-com-jpackage)
  - [Passo 3 (opcional): instalador](#passo-3-opcional-instalador)
- [Modo demonstração](#modo-demonstração)
- [Rodar em rede](#rodar-em-rede)
- [Solução de problemas](#solução-de-problemas)

## Como funciona

- Ao iniciar, o servidor cria um **RMI Registry na porta 1099** e publica o serviço com o nome
  **`CRIPTEX_RMI`**.
- O dicionário é carregado de `src/main/resources/icf`, uma lista de palavras com o seu índice de
  frequência (ICF). Apenas palavras de 5 letras e suficientemente comuns entram no jogo.
- A palavra secreta é a mesma para todos os jogadores e **troca a cada 30 minutos**
  (`WordScheduler.timeInterval = 1800` segundos).
- Para cada palpite, o servidor devolve um vetor `status` de 5 posições:
  `2` = letra certa na posição certa, `1` = letra existe em outra posição, `0` = letra não existe.
- Quem acerta entra no ranking, ordenado pelo tempo entre o login e o acerto.

## Arquitetura

```
src/main/java
├── server/
│   ├── MainServer.java              # Ponto de entrada: cria o Registry e publica o serviço
│   ├── remote/CriptexSrvImplement   # Implementação de ServerInterface (login, palpites, ranking)
│   └── service/
│       ├── GameManager.java         # Palavra secreta atual e troca de palavras
│       ├── WordScheduler.java       # Agendamento da troca a cada 30 min
│       └── Dictionary.java          # Carrega e consulta o dicionário
├── shared/                          # Contrato compartilhado com o cliente
│   ├── remote/                      #   ServerInterface, ClientInterface, Ranking
│   ├── dto/                         #   GuessRequest, GuessResponse
│   ├── config/Config.java           #   Nome do serviço, porta e IP
│   └── status/                      #   Logs coloridos no console
├── model/                           # Player e cliente de linha de comando (MainClient, CmdLayout)
├── rmi/network/GameSession.java
└── util/Word.java                   # Validação dos palpites (tamanho e existência no dicionário)
```

O pacote `shared` precisa ser **idêntico** no servidor e no cliente (mesmos nomes de classe e
`serialVersionUID`), pois os objetos trafegam serializados via RMI.

O projeto é **modular** (`module-info.java`, módulo `criptexrmi`) e depende só de `java.base` e
`java.rmi`. Por isso o runtime gerado pelo `jlink` fica pequeno (~65 MB).

## Requisitos para compilar

| Ferramenta | Versão | Observação |
|---|---|---|
| JDK | 21 ou superior | Precisa ser um **JDK** completo (tem `jlink` e `jpackage`), não apenas uma JRE. Ex.: Eclipse Temurin 21. |
| Maven | 3.8+ | |

> Apenas **quem compila** precisa do JDK. Quem recebe o executável gerado não precisa ter Java instalado.

## Executar em modo de desenvolvimento

```bash
mvn clean compile
java -p target/classes -m criptexrmi/server.MainServer
```

Para o modo demonstração, acrescente `d` ao final (veja [Modo demonstração](#modo-demonstração)).

Cliente de linha de comando (em outro terminal, com o servidor rodando):

```bash
mvn exec:java
```

ou, sem passar pelo Maven:

```bash
java -p target/classes -m criptexrmi/model.MainClient
```

## Gerar um executável que não precisa de JVM instalada

A estratégia usa duas ferramentas que já vêm no JDK:

- **`jlink`** monta uma JVM reduzida contendo apenas os módulos usados (`java.base`, `java.rmi`,
  `java.logging`) junto com o próprio servidor.
- **`jpackage`** embrulha esse runtime em um executável nativo (`CriptexServer.exe` no Windows,
  `CriptexServer` no Linux) e, se quiser, gera um instalador.

```
código-fonte ──mvn jlink:jlink──▶ target/maven-jlink/default/ ──jpackage──▶ CriptexServer.exe (+ runtime embutido)
                                  (JVM enxuta + servidor)
```

> **Importante: gere o executável no mesmo sistema operacional em que ele vai rodar.** O `jlink` e o
> `jpackage` não fazem compilação cruzada. Para distribuir para Windows, gere no Windows; para Linux,
> gere no Linux.

### Passo 1: runtime enxuto com jlink

O `pom.xml` já declara o `maven-jlink-plugin`:

```bash
mvn clean compile jlink:jlink
```

Resultado:

| Caminho | Conteúdo |
|---|---|
| `target/maven-jlink/default/` | Runtime: JVM reduzida + módulo `criptexrmi` |
| `target/criptex-rmi-1.0-SNAPSHOT.zip` | A mesma pasta compactada |

Esse runtime já roda sem Java instalado na máquina:

```bash
target/maven-jlink/default/bin/java -m criptexrmi/server.MainServer
```

No Windows: `target\maven-jlink\default\bin\java.exe -m criptexrmi/server.MainServer`.

Para conferir os módulos incluídos:

```bash
target/maven-jlink/default/bin/java --list-modules
```

### Passo 2: executável nativo com jpackage

**Windows** (comando usado originalmente no projeto, com ícone e janela de console):

```bat
jpackage --type app-image --name "CriptexServer" --dest target/instalador --module criptexrmi/server.MainServer --runtime-image target/maven-jlink/default --vendor "github.com/sergiolj" --app-version "2.0.0" --icon src/main/resources/criptex.ico --win-console
```

**Linux / macOS:**

```bash
jpackage --type app-image --name CriptexServer --dest target/instalador --module criptexrmi/server.MainServer --runtime-image target/maven-jlink/default --vendor "github.com/sergiolj" --app-version 2.0.0
```

O que cada opção faz:

| Opção | Significado |
|---|---|
| `--type app-image` | Gera uma pasta com o executável (sem instalador). |
| `--name CriptexServer` | Nome do executável e da pasta gerada. |
| `--dest target/instalador` | Onde a saída é gravada. |
| `--module criptexrmi/server.MainServer` | Módulo e classe principal, no formato `módulo/classe`. |
| `--runtime-image target/maven-jlink/default` | Reaproveita o runtime gerado pelo jlink. |
| `--icon src/main/resources/criptex.ico` | Ícone do executável (`.ico` no Windows, `.icns` no macOS, `.png` no Linux). |
| `--win-console` | **Essencial no Windows**: o servidor escreve o log no console; sem essa opção o `.exe` abre sem janela e o log não aparece. |

Resultado:

- **Windows:** `target\instalador\CriptexServer\CriptexServer.exe`
- **Linux:** `target/instalador/CriptexServer/bin/CriptexServer`

Para distribuir, **copie a pasta `CriptexServer` inteira**, não só o executável: a JVM embutida fica na
subpasta `runtime/`.

> Nota: se o `jpackage` não for reconhecido no terminal, use o caminho completo do JDK (por exemplo
> `"C:\Program Files\Eclipse Adoptium\jdk-21...\bin\jpackage.exe"`) ou adicione a pasta `bin` do JDK ao
> `PATH`.

### Passo 3 (opcional): instalador

Trocando `--type app-image` por um tipo de instalador:

| Sistema | `--type` | Pré-requisito extra |
|---|---|---|
| Windows | `exe` ou `msi` | [WiX Toolset](https://wixtoolset.org/) instalado e no `PATH` |
| Linux | `deb` ou `rpm` | `dpkg-deb` (Debian/Ubuntu) ou `rpm-build` (Fedora) |
| macOS | `dmg` ou `pkg` | nenhum |

## Modo demonstração

Passando o argumento `d`, a palavra secreta **não troca por tempo**: ela muda somente quando alguém acerta.
É útil para apresentações.

```bat
CriptexServer d
```

O arquivo `src/main/resources/CriptexDemoMode.bat` faz exatamente isso. Copie-o para dentro da pasta
`CriptexServer` gerada pelo jpackage (ao lado do `.exe`) e dê dois cliques nele.

Para encerrar o servidor, use `Ctrl+C` no console: o servidor desliga o agendador e remove o serviço do
registro antes de sair. Se ele ficar preso em segundo plano no Windows, `PowerShellCommands.txt` traz os
comandos para localizar e encerrar o processo:

```powershell
Get-Process | Where-Object { $_.ProcessName -match "criptex" }
Stop-Process -Id <número do processo>
```

## Rodar em rede

- A porta do registro é **1099** (`shared.config.Config.SERVER_PORT`). Libere-a no firewall da máquina do
  servidor.
- O objeto remoto é exportado em uma **porta dinâmica** escolhida pelo RMI, que também precisa estar
  acessível. Em redes com firewall restrito, a forma mais simples é permitir o executável
  `CriptexServer.exe` no firewall do Windows (e não só a porta).
- Se a máquina tiver várias interfaces de rede e os clientes não conseguirem conectar, informe o IP
  correto ao RMI ao iniciar o servidor. Com o runtime do jlink:

  ```bash
  target/maven-jlink/default/bin/java -Djava.rmi.server.hostname=192.168.0.10 -m criptexrmi/server.MainServer
  ```

  Com o jpackage, a mesma opção pode ser embutida no executável na hora de gerá-lo:
  `--java-options "-Djava.rmi.server.hostname=192.168.0.10"`.
- No cliente JavaFX, o IP e a porta do servidor ficam no arquivo `conf.cfg` (formato `IP;PORTA`).

## Solução de problemas

| Sintoma | Causa provável / solução |
|---|---|
| `jlink`/`jpackage` não encontrado | Está usando uma JRE ou um JDK antigo. Instale um JDK 21+. |
| `Port already in use: 1099` | Já existe um servidor (ou outro `rmiregistry`) rodando. Encerre-o antes. |
| Acentos aparecem como `?` no console do Windows | O console não está em UTF-8. Rode `chcp 65001` antes de iniciar, ou gere o executável com `--java-options "-Dstdout.encoding=UTF-8"`. |
| Clientes de outras máquinas não conectam | Veja [Rodar em rede](#rodar-em-rede): firewall e `java.rmi.server.hostname`. |
| `ClassNotFoundException`/erro de desserialização vindo do RMI | O pacote `shared` está diferente entre cliente e servidor. |

## Autoria

Bruna Brito Muniz Filgueiras, Laís de Assis Doria da Silva e Sérgio Lopes Júnior.
