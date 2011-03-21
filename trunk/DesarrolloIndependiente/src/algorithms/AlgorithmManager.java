package algorithms;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import signals.ReadResult;
import signals.ReaderCallable;
import signals.SignalManager;
import signals.WriterRunnable;
import signals.WriterRunnableMultiSignal;
import signals.WriterRunnableOneSignal;

/**
 * Singlenton
 */
public class AlgorithmManager {

    private Map<String, Algorithm> algorithmsByName;
    private Map<String, LinkedList<String>> algorithmsToNotifyBySignalName;
    private Map<String, Trigger> triggersByAlgorithmName;
    private ExecutorServiceAlgorithm executorServiceAlgorithm;
    private static final AlgorithmManager INSTANCE = new AlgorithmManager();

    public static AlgorithmManager getInstance() {
        return INSTANCE;
    }

    private AlgorithmManager() {
        this.algorithmsByName = new HashMap<String, Algorithm>();
        this.algorithmsToNotifyBySignalName = new HashMap<String, LinkedList<String>>();
        this.triggersByAlgorithmName = new HashMap<String, Trigger>();
        this.executorServiceAlgorithm = new ExecutorServiceAlgorithm();
    }

    public Algorithm addAlgorithm(Algorithm algorithm) {
        if (this.algorithmsByName.get(algorithm.getIdentifier()) == null) {
            SignalManager.getInstance().addSeries(algorithm.getSignalToWrite());
            this.addTrigger(algorithm);
            this.addSignalNamesToMap(algorithm);
            return this.algorithmsByName.put(algorithm.getIdentifier(), algorithm);
        } else {
            throw new AlgorithmAlreadyExistsException("Algorithm already exists in Algorithm Manager", algorithm);
        }
    }

    private Trigger addTrigger(Algorithm algorithm) {
        return this.triggersByAlgorithmName.put(algorithm.getIdentifier(),
                new Trigger(algorithm.getIdentifier(), algorithm.getNotifyPolice()));
    }

    private void addSignalNamesToMap(Algorithm algorithm) {
        AlgorithmNotifyPolice algorithmNotifyPolice = algorithm.getNotifyPolice();
        Set<String> timeSeriesNames = algorithmNotifyPolice.getTimeSeriesTheshold().keySet();
        for (String timeSeriesName : timeSeriesNames) {
            LinkedList<String> algorithmNames = this.algorithmsToNotifyBySignalName.get(timeSeriesName);
            if (algorithmNames == null) {
                algorithmNames = new LinkedList<String>();
            }
            algorithmNames.add(algorithm.getIdentifier());
            this.algorithmsToNotifyBySignalName.put(timeSeriesName, algorithmNames);
        }
        Set<String> eventSeriesNames = algorithmNotifyPolice.getEventSeriesTheshold().keySet();
        for (String eventSeriesName : eventSeriesNames) {
            LinkedList<String> algorithmNames = this.algorithmsToNotifyBySignalName.get(eventSeriesName);
            if (algorithmNames == null) {
                algorithmNames = new LinkedList<String>();
            }
            algorithmNames.add(algorithm.getIdentifier());
            this.algorithmsToNotifyBySignalName.put(eventSeriesName, algorithmNames);
        }
    }

    public void notifyNewData(WriterRunnable writerRunnable) {
        if (writerRunnable instanceof WriterRunnableOneSignal) {
            WriterRunnableOneSignal writerRunnableOneSignal = (WriterRunnableOneSignal) writerRunnable;
            String signalName = writerRunnableOneSignal.getIdentifier();

            LinkedList<String> algorithmNames = this.algorithmsToNotifyBySignalName.get(signalName);
            if (algorithmNames != null) {
                for (String algorithmName : algorithmNames) {
                    Trigger algorithmTrigger = this.triggersByAlgorithmName.get(algorithmName);
                    algorithmTrigger.notifyNewData(writerRunnableOneSignal);
                }
                this.checkTriggers();
            }
        }
        else{
            WriterRunnableMultiSignal writerRunnableMultiSignal = (WriterRunnableMultiSignal) writerRunnable;
            LinkedList<WriterRunnableOneSignal> writerRunnables = writerRunnableMultiSignal.getWriterRunnables();
            for(WriterRunnableOneSignal writerRunnableOneSignal: writerRunnables){
                this.notifyNewData(writerRunnableOneSignal);
            }
        }
    }

    private void checkTriggers() {
        Set<String> algorithmNames = this.triggersByAlgorithmName.keySet();
        for (String algorithmName : algorithmNames) {
            Trigger triggerAlgorithm = this.triggersByAlgorithmName.get(algorithmName);
            //@pendiente
            ReaderCallable readerCallable = triggerAlgorithm.getReaderCallableIfTriggerAndReset();
            if (readerCallable!=null) {                
                signals.SignalManager.getInstance().encueReadOperation(readerCallable);
            }
        }
    }

    public void processData(ReadResult readResult) {
        Algorithm algorithm = this.algorithmsByName.get(readResult.getIdentifierOwner());
        this.encueAlgorithmReadResultOperation(algorithm, readResult);
    }

    private void encueAlgorithmReadResultOperation(Algorithm algorithm, ReadResult readResult) {
        this.executorServiceAlgorithm.executeAlgorithmReadResult(algorithm, readResult);
    }

    public Algorithm getAlgorithm(String name) {
        return this.algorithmsByName.get(name);
    }
    //@debud metodos de depuracion

    public LinkedList<String> getAllAlgorithmNames() {
        return (new LinkedList<String>(this.algorithmsByName.keySet()));
    }

    public Trigger getTrigger(String algorithmName) {
        return this.triggersByAlgorithmName.get(algorithmName);
    }

    public LinkedList<String> getAlgorithmNamesToSignal(String algorithmName) {
        return this.algorithmsToNotifyBySignalName.get(algorithmName);
    }

    public void reset() {
        this.algorithmsByName = new HashMap<String, Algorithm>();
        this.algorithmsToNotifyBySignalName = new HashMap<String, LinkedList<String>>();
        this.triggersByAlgorithmName = new HashMap<String, Trigger>();
        this.executorServiceAlgorithm = new ExecutorServiceAlgorithm();
    }
}