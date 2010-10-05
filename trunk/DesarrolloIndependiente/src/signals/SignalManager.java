/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package signals;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/** Singleton Facade
 *
 * @todo Usa colecciones concurrentes por los motivos ya comentados
 * en otra clase
 *
 * @author USUARIO
 */
public class SignalManager {

    private ConcurrentMap<String, TimeSeries> timeSeries;
    private ConcurrentMap<String, EventSeries> eventSeries;
    private LockManager lockManager;
    private ExecutorServiceWriter executorServiceWriter;
    private CompletionExecutorServiceReader completionExecutorServiceReader;
    private static final SignalManager INSTANCE = new SignalManager();

    private SignalManager() {
        lockManager = LockManager.getInstance();
        timeSeries = new ConcurrentHashMap<String, TimeSeries>();
        eventSeries = new ConcurrentHashMap<String, EventSeries>();
        executorServiceWriter = new ExecutorServiceWriter();
        completionExecutorServiceReader = new CompletionExecutorServiceReader();
    }

    public static SignalManager getInstance() {
        return INSTANCE;
    }
    //@duda quizas haya que hacer una copia de ts
    //@¿no es inmutable? Si lo es, no hace falta

    //@todo no anhadir, crear
    //@pendiente crear constructor de copia y hacerlo
    public TimeSeries addTimeSeries(TimeSeries ts) {
        this.lockManager.addLock(ts.getIdentifier());
        return this.timeSeries.put(ts.getIdentifier(), ts);
    }

    public EventSeries addEventSeries(EventSeries eventSeries) {
        this.lockManager.addLock(eventSeries.getIdentifier());
        return this.eventSeries.put(eventSeries.getIdentifier(), eventSeries);
    }

    public void addWriterRunnable(WriterRunnable writerRunnable) {
        this.executorServiceWriter.executeWriterRunnable(writerRunnable);
    }

    public void addReaderCallable(ReaderCallable readerCallable) {
        this.completionExecutorServiceReader.executeReaderRunnable(readerCallable);

    }

/////////A partir de aqui los métodos son discutibles
    public void initiateThread() {
        Thread threadCompletionService = new Thread(completionExecutorServiceReader, "threadComletion");
        threadCompletionService.start();
    }

    public float[] readFromTimeSeries(String identifier, int posSrc, int sizeToRead) {
        return this.timeSeries.get(identifier).read(posSrc, sizeToRead);
    }

    public float[] readNewFromTimeSeries(String identifier, int indexLastRead) {
        if (this.timeSeries.get(identifier).getIndexNewsample() != -1) {
            float result[] = this.timeSeries.get(identifier).read(indexLastRead, (this.timeSeries.get(identifier).getIndexNewsample() - indexLastRead) + 1 % this.timeSeries.get(identifier).getCapacity());
            return result;
        } else {
            return new float[0];
        }
    }

    public boolean writeToTimeSeries(String identifier, float[] dataToWrite) {
        boolean result = this.timeSeries.get(identifier).write(dataToWrite);
        return result;
    }

    public void addEventToEventSeries(String identifier, Event event) {
        this.eventSeries.get(identifier).addEvent(event);
    }

    public ArrayList<Event> getEvents(String identifier) {
        return this.eventSeries.get(identifier).getEventsCopy();
    }
}
