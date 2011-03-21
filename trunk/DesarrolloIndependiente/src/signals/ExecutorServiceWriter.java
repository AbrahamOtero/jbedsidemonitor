package signals;

import algorithms.AlgorithmManager;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceWriter {

    private final ExecutorService executorService;

    public ExecutorServiceWriter() {
        executorService = Executors.newCachedThreadPool();
    }

    public void executeWriterRunnable(WriterRunnable writerRunnable) {
        this.executorService.execute(writerRunnable);
        System.out.println("Ejecutando Operacion Escritura");
        //@duda no se si esto iria aqui

        AlgorithmManager.getInstance().notifyNewData(writerRunnable);
    }
}