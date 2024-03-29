package signals;

import algorithms.Algorithm;
import datasource.DataSource;
import datasource.DataSourceDefault;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import signals.CircularBuffer.ConsecutiveSamplesAvailableInfo;

/** Singleton Facade
 *
 */
public class SignalManager {

    private ConcurrentMap<String, TimeSeries> timeSeries;
    private ConcurrentMap<String, EventSeries> eventSeries;
    private LockManager lockManager;
    private ExecutorServiceWriter executorServiceWriter;
    private CompletionExecutorServiceReader completionExecutorServiceReader;
    private static final SignalManager INSTANCE = new SignalManager();
    private JSignalAdapter jSignalAdapter;
    private boolean isRunning;
    private boolean isStart;
    private ReentrantReadWriteLock lockRunning;
    private ReentrantReadWriteLock lockStart;
    private ReentrantLock lockWaitRunning;
    private ConcurrentMap<String, DataSource> dataSources;
    private ConcurrentMap<String, Boolean> activeDataSources;
    private ReentrantReadWriteLock lockDataSourcesInfo;

    private SignalManager() {
        lockManager = LockManager.getInstance();
        timeSeries = new ConcurrentHashMap<String, TimeSeries>();
        eventSeries = new ConcurrentHashMap<String, EventSeries>();
        executorServiceWriter = new ExecutorServiceWriter();
        completionExecutorServiceReader = new CompletionExecutorServiceReader();
        jSignalAdapter = new JSignalAdapter();
        isRunning = false;
        isStart = false;
        lockRunning = new ReentrantReadWriteLock();
        lockStart = new ReentrantReadWriteLock();
        lockWaitRunning = new ReentrantLock();
        dataSources = new ConcurrentHashMap<String, DataSource>();
        activeDataSources = new ConcurrentHashMap<String, Boolean>();
        lockDataSourcesInfo = new ReentrantReadWriteLock();
    }

    public static SignalManager getInstance() {
        return INSTANCE;
    }
    //@pendiente si es necesario hacer una copia del objeto ya que es mutable
    //Comentario hasta que se haga para que no se olvide copia defensiva
    //@pendiente quizas dejar solo este metodo para añadir series
    //@pendiente que ocurre si un TimeSeries y un EventSeries se llaman igual

    public Series addSeries(Series series) {
        if (series instanceof TimeSeries) {
            return this.addTimeSeries((TimeSeries) series);
        }
        if (series instanceof EventSeries) {
            return this.addEventSeries((EventSeries) series);
        }
        return null;
    }

    public TimeSeries addTimeSeries(TimeSeries ts) {
        if (this.timeSeries.get(ts.getIdentifier()) == null) {
            this.lockManager.addLock(ts.getIdentifier());
            this.jSignalAdapter.addTimeSeries(ts);
            return this.timeSeries.put(ts.getIdentifier(), ts);
        }
        throw new TimeSerieslAlreadyExistsException("TimeSeries already exists in Signal Manager", ts);
    }

    public EventSeries addEventSeries(EventSeries eventSeries) {
        if (this.eventSeries.get(eventSeries.getIdentifier()) == null) {
            this.lockManager.addLock(eventSeries.getIdentifier());
            this.jSignalAdapter.addEventSeries(eventSeries);
            return this.eventSeries.put(eventSeries.getIdentifier(), eventSeries);
        }
        throw new EventSerieslAlreadyExistsException("EventSeries already exists in Signal Manager", eventSeries);
    }

    public boolean registerDataSource(DataSource dataSource) {
        this.lockDataSourcesInfo.writeLock().lock();
        try {
            dataSources.put(dataSource.getIdentifier(), dataSource);
            activeDataSources.put(dataSource.getIdentifier(), Boolean.TRUE);
        } finally {
            this.lockDataSourcesInfo.writeLock().unlock();
        }
        return true;
    }

    public boolean inactiveDataSource(DataSource dataSource) {
        activeDataSources.put(dataSource.getIdentifier(), Boolean.FALSE);
        return true;
    }

    public boolean encueWriteOperation(WriterRunnable writerRunnable) {
        if (isRunning & isStart) {
            this.executorServiceWriter.executeWriterRunnable(writerRunnable);
            return true;
        } else {
            return false;
        }
    }

    public boolean encueReadOperation(ReaderCallable readerCallable) {
        if (isStart) {
            this.completionExecutorServiceReader.executeReaderCallable(readerCallable);
            return true;
        }
        return false;
    }

    public float[] readSecureFromTimeSeries(String identifier, int posSrc, int sizeToRead) {
        this.lockManager.getReadLock(identifier);
        float[] read = this.timeSeries.get(identifier).read(posSrc, sizeToRead);
        this.lockManager.releaseReadLock(identifier);
        return read;
    }

    public ConsecutiveSamplesAvailableInfo getConsecutiveSamplesTimeSeries(String identifier) {
        this.lockManager.getReadLock(identifier);
        ConsecutiveSamplesAvailableInfo consecutiveSamplesAvailableInfo = this.timeSeries.get(identifier).getConsecutiveSamplesAvailableInfo();
        this.lockManager.releaseReadLock(identifier);
        return consecutiveSamplesAvailableInfo;
    }

    //@metodo debug no USAR segun api
    public float[] readFromTimeSeries(String identifier, int posSrc, int sizeToRead) {
        return this.timeSeries.get(identifier).read(posSrc, sizeToRead);
    }
//@metodo debug no USAR segun api

    public float[] readNewFromTimeSeries(String identifier, int indexLastRead) {
        if (this.timeSeries.get(identifier).getLastSampleWrite() != -1) {
            float result[] = this.timeSeries.get(identifier).read(indexLastRead,
                    (this.timeSeries.get(identifier).getLastSampleWrite()
                    - indexLastRead) + 1);
            return result;
        } else {
            return new float[0];
        }
    }

    public SortedSet<Event> getEventsUnmodifiableCopy(String identifier) {
        while (!this.lockManager.tryReadLock(identifier)) {
        }
        SortedSet<Event> eventsUnmodifiableCopy = this.eventSeries.get(identifier).getEventsUnmodifiableCopy();
        this.lockManager.releaseReadLock(identifier);
        return eventsUnmodifiableCopy;

    }
    //@metodo debug no USAR segun api

    public SortedSet<Event> getEventsCopy(String identifier) {
        while (!this.lockManager.tryReadLock(identifier)) {
        }
        SortedSet<Event> eventsCopy = this.eventSeries.get(identifier).getEventsCopy();
        this.lockManager.releaseReadLock(identifier);
        return eventsCopy;

    }
//@metodo debug no USAR segun api deberían de usarse solo con locks

    public SortedSet<Event> readFromEventSeriesFromTo(String identifierSignal, long firstInstantToInclude, long lastInstantToInclude) {
        return this.eventSeries.get(identifierSignal).getEvents(firstInstantToInclude, lastInstantToInclude);
    }

    ConsecutiveSamplesAvailableInfo writeToTimeSeries(String identifier, float[] dataToWrite, int indexInitToWrite) {
        return this.timeSeries.get(identifier).write(dataToWrite, indexInitToWrite);
    }

    public float getFrecuencyTimeSeries(String identifierSignal){
        return this.timeSeries.get(identifierSignal).getFrequency();
    }

    public long getOriginTimeSeries(String identifierSignal){
        return this.timeSeries.get(identifierSignal).getOrigin();
    }
   public long getOriginEventSeries(String identifierSignal){
        return this.eventSeries.get(identifierSignal).getOrigin();
    }

    void addEventToEventSeries(String identifier, Event event) {
        this.eventSeries.get(identifier).addEvent(event);
    }

    boolean deleteEventToEventSeries(String identifier, Event event) {
        return this.eventSeries.get(identifier).deleteEvent(event);
    }

    public void reset() {
        lockManager = LockManager.getInstance();
        timeSeries = new ConcurrentHashMap<String, TimeSeries>();
        eventSeries = new ConcurrentHashMap<String, EventSeries>();
        executorServiceWriter = new ExecutorServiceWriter();
        completionExecutorServiceReader = new CompletionExecutorServiceReader();
        jSignalAdapter = new JSignalAdapter();
        isRunning = false;
        isStart = false;
        lockRunning = new ReentrantReadWriteLock();
        lockStart = new ReentrantReadWriteLock();
        lockWaitRunning = new ReentrantLock();
        dataSources = new ConcurrentHashMap<String, DataSource>();
        activeDataSources = new ConcurrentHashMap<String, Boolean>();
        lockDataSourcesInfo = new ReentrantReadWriteLock();
        this.initiateThread();

    }

    public boolean resume() {
        if (!isStart()) {
            return false;
        }
        this.lockRunning.writeLock().lock();
        try {
            this.isRunning = true;
        } finally {
            this.lockRunning.writeLock().unlock();
        }
        synchronized (lockWaitRunning) {
            this.lockWaitRunning.notifyAll();
        }
        return true;
    }

    public boolean pause() {
        if (!isStart()) {
            return false;
        }
        this.lockRunning.writeLock().lock();
        try {
            this.isRunning = false;
        } finally {
            this.lockRunning.writeLock().unlock();
        }
        synchronized (lockWaitRunning) {
            this.lockWaitRunning.notifyAll();
        }
        return true;
    }

    public boolean start() {
        this.initiateThread();
        this.initiateDataSources();
        this.lockStart.writeLock().lock();
        try {
            this.isStart = true;
        } finally {
            this.lockStart.writeLock().unlock();
        }
        this.resume();

        return true;
    }

    public boolean isStart() {
        boolean response = false;
        this.lockStart.readLock().lock();
        try {
            response = this.isStart;
        } finally {
            this.lockStart.readLock().unlock();
        }
        return response;
    }

    public boolean isRunning() {
        boolean response = false;
        this.lockRunning.readLock().lock();
        try {
            response = this.isRunning;
        } finally {
            this.lockRunning.readLock().unlock();
        }
        return response;
    }

    public ReentrantLock getLockWaitRunning() {
        return lockWaitRunning;
    }

    void initiateThread() {
        Thread threadCompletionService = new Thread(completionExecutorServiceReader, "threadCompletion");
        threadCompletionService.start();
    }

    private void initiateDataSources() {
        //Considerar hacerlo en un hilo aparte
        this.lockDataSourcesInfo.writeLock().lock();
        try {
            LinkedList<String> dataSourcesNames = new LinkedList<String>(this.dataSources.keySet());
            for (String dataSourceName : dataSourcesNames) {
                dataSources.get(dataSourceName).start();
            }
        } catch (Exception e) {
            System.out.println("Error inializando DataSources" + e.getMessage());
        } finally {
            this.lockDataSourcesInfo.writeLock().unlock();
        }
    }

    public LinkedList<String> getAllDataSourcesNames() {
        LinkedList<String> dataSourcesNames = new LinkedList<String>();
        this.lockDataSourcesInfo.readLock().lock();
        try {
            dataSourcesNames = new LinkedList<String>(this.dataSources.keySet());
        } finally {
            this.lockDataSourcesInfo.readLock().unlock();
        }
        return dataSourcesNames;
    }

/////////A partir de aqui los métodos son discutibles
    // Estan puesto public para los test
    public DataSource getDataSource(String dataSourceName) {
        DataSource dataSource = null;
        this.lockDataSourcesInfo.readLock().lock();
        try {
            dataSource = this.dataSources.get(dataSourceName);
        } finally {
            this.lockDataSourcesInfo.readLock().unlock();
        }
        return dataSource;
    }

    public void getLockToModifyDataSources() {
        this.lockDataSourcesInfo.writeLock().lock();
    }

    public void releaseLockToModifyDataSources() {
        this.lockDataSourcesInfo.writeLock().unlock();
    }

    public boolean getStateOfDataSource(String dataSourceName) {
        return this.activeDataSources.get(dataSourceName);
    }

    public LinkedList<String> getAllTimeSeriesNames() {
        return new LinkedList<String>(this.timeSeries.keySet());
    }

    public LinkedList<String> getAllEventSeriesNames() {
        return new LinkedList<String>(this.eventSeries.keySet());
    }

    public JSignalAdapter getJSignalAdapter() {
        return this.jSignalAdapter;
    }
}
