package Servicos;

import java.time.LocalDateTime;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Logger {

    // Padrão singleton, com uma única instância estática
    // volatile garante que a mudança na instância seja visível para todas as threads
    private static volatile Logger instancia;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final String NOME_ARQUIVO_LOG = "biblioteca_log.txt";
    private PrintWriter printWriter;

    private Logger(){
        try {
            FileWriter fileWriter = new FileWriter(NOME_ARQUIVO_LOG, true);

            // Encapsula em um PrintWriter para ter o método println
            printWriter = new PrintWriter(fileWriter);
            System.out.println("Logger iniciado");
        }catch (IOException e) {
            // Se falhar ao abrir o arquivo, o log só funcionará no console
            System.err.println("ERRO CRÍTICO: Não foi possível iniciar o logger de arquivo.");
            e.printStackTrace();
            printWriter = null; // Garante que não vamos tentar usá-lo
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (printWriter != null) {
                log("SYSTEM", "Logger desligando e fechando arquivo.");
                printWriter.close();
            }
        }));
    }

    public static Logger getInstance(){
        if(instancia == null){
            synchronized (Logger.class) {
                instancia = new Logger();
            }
        }
        return instancia;
    }

    public void log(String nivel, String mensagem) {
        String dataHora = LocalDateTime.now().format(formatter);
        String logCompleto = "[" + dataHora + "] [" + nivel.toUpperCase() + "] " + mensagem;

        System.out.println(logCompleto);

        if (printWriter != null) {
            printWriter.println(logCompleto);
            printWriter.flush();
        }
    }

    // Sobrecarga para logs mais simples (nível INFO por padrão)
    public void log(String mensagem) {
        log("INFO", mensagem);
    }
}
