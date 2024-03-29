package signals;

import algorithms.AlgorithmManager;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceWriter {

    private final ExecutorService executorService;

    public ExecutorServiceWriter() {
        executorService = Executors.newFixedThreadPool(30);
    }

    public void executeWriterRunnable(WriterRunnable writerRunnable) {
        this.executorService.execute(writerRunnable);
//@debug        System.out.println("Ejecutando Operacion Escritura");        
        //pendiente @a he cambiado esto que sino no funciona
        //AlgorithmManager.getInstance().notifyNewData(writerRunnable);
    }
}
