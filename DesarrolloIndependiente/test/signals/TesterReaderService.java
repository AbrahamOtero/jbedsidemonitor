package signals;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

public class TesterReaderService {

    public TesterReaderService() {
    }

    @Test
    public void testReaderTimeSeries() {
        SignalManager signalManager = SignalManager.getInstance();
        signalManager.initiateThread();
        assertFalse(signalManager == null);
        signalManager.addTimeSeries(new TimeSeries("Signal 1", "Simulated", 1, 100, "mv"));
        signalManager.addTimeSeries(new TimeSeries("Signal 2", "Simulated", 1, 100, "mv"));
        TimeSeriesWriterRunnable writer1 = new TimeSeriesWriterRunnable("Signal 1");
        TimeSeriesWriterRunnable writer2 = new TimeSeriesWriterRunnable("Signal 2");
        float[] dataToWrite1 = new float[10];
        AuxTestUtilities.secuentialArray(dataToWrite1);
        float[] dataToWrite2 = new float[100];
        AuxTestUtilities.secuentialArray(dataToWrite2);
        writer1.setDataToWrite(dataToWrite1);
        writer2.setDataToWrite(dataToWrite2);
        signalManager.encueWriteOperation(writer1);
        signalManager.encueWriteOperation(writer2);
        try {
            Thread.sleep(30);
        } catch (InterruptedException ex) {
            Logger.getLogger(TesterWriterService.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertTrue(AuxTestUtilities.compareArray(dataToWrite1,
                signalManager.readFromTimeSeries("Signal 1", 0, 10), dataToWrite1.length));
        assertTrue(AuxTestUtilities.compareArray(dataToWrite2,
                signalManager.readFromTimeSeries("Signal 2", 0, 100), dataToWrite2.length));
        TimeSeriesReaderCallable reader1 = new TimeSeriesReaderCallable("Signal 1", "Algorithm 1");
        reader1.setPosInitToRead(0);
        reader1.setSizeToRead(10);
        TimeSeriesReaderCallable reader2 = new TimeSeriesReaderCallable("Signal 2", "Algorithm 1");
        reader2.setPosInitToRead(0);
        reader2.setSizeToRead(100);
        signalManager.encueReadOperation(reader1);
        signalManager.encueReadOperation(reader2);
        try {
            Thread.sleep(30);
        } catch (InterruptedException ex) {
            Logger.getLogger(TesterWriterService.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Comparar 1");
        AuxTestUtilities.printArray(dataToWrite1);
        System.out.println("Comparar 2");
        AuxTestUtilities.printArray(dataToWrite2);
    }

    @Test
    public void testReaderEventSeries() {
        SignalManager signalManager = SignalManager.getInstance();
        signalManager.initiateThread();
        assertFalse(signalManager == null);
        signalManager.addEventSeries(new EventSeries("Events1", "Agente1", 100, new ArrayList<String>(), "mv"));
        signalManager.addEventSeries(new EventSeries("Events2", "Agente2", 100, new ArrayList<String>(), "mv"));
        EventSeriesWriterRunnable writer1 = new EventSeriesWriterRunnable("Events1");
        EventSeriesWriterRunnable writer2 = new EventSeriesWriterRunnable("Events2");
        LinkedList<Event> events1 = new LinkedList<Event>();
        AuxTestUtilities.eventosAleatorios(events1, 20, 1000, 300);
        LinkedList<Event> events2 = new LinkedList<Event>();
        AuxTestUtilities.eventosAleatorios(events2, 10, 9900, 130);
        for (int i = 0; i < events1.size(); i++) {
            writer1.addEventToWrite(events1.get(i));
        }
        AuxTestUtilities.imprimirEventos(events1);
        for (int i = 0; i < events2.size(); i++) {
            writer2.addEventToWrite(events2.get(i));
        }
        signalManager.encueWriteOperation(writer1);
        signalManager.encueWriteOperation(writer2);
        try {
            Thread.sleep(30);
        } catch (InterruptedException ex) {
        }
        assertTrue(AuxTestUtilities.eventosCompararListas(events1, new LinkedList<Event>(signalManager.getEvents("Events1"))));
        assertTrue(AuxTestUtilities.eventosCompararListas(events1, new LinkedList<Event>(signalManager.readFromEventSeriesFromTo("Events1", 1000, 1300))));
        assertTrue(AuxTestUtilities.eventosCompararListas(events2, new LinkedList<Event>(signalManager.getEvents("Events2"))));
        EventSeriesReaderCallable reader1 = new EventSeriesReaderCallable("Events1", "Agente1");
        reader1.setFirstInstantToInclude(1000);
        reader1.setLastInstantToInclude(1300);
        EventSeriesReaderCallable reader2 = new EventSeriesReaderCallable("Events2", "Agente2");
        reader2.setFirstInstantToInclude(9900);
        reader2.setLastInstantToInclude(9900 + 130);
        signalManager.encueReadOperation(reader1);
        signalManager.encueReadOperation(reader2);
        EventSeriesWriterRunnable writer3 = new EventSeriesWriterRunnable("Events1");
        for (int i = 0; i < events1.size(); i++) {
            writer3.addEventToDelete(events1.get(i));
        }
        System.out.println(signalManager.getEvents("Events1").size());
        signalManager.encueWriteOperation(writer3);
        try {
            Thread.sleep(30);
        } catch (InterruptedException ex) {
        }
        System.out.println(signalManager.getEvents("Events1").size());
        assertEquals(0, signalManager.getEvents("Events1").size());

        EventSeriesReaderCallable reader3 = new EventSeriesReaderCallable("Events1", "Agente1");
        reader3.setFirstInstantToInclude(1000);
        reader3.setLastInstantToInclude(1300);
        signalManager.encueReadOperation(reader3);

    }
}