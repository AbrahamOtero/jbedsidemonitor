/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import auxiliarTools.AuxTestUtilities;
import java.util.LinkedList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import signals.Event;
import signals.WriterRunnableEventSeries;

/**
 *
 * @author USUARIO
 */
public class EventSeriesTriggerTest {

    public EventSeriesTriggerTest() {
    }

    @Test
    public void testCreate() {
        EventSeriesTrigger eventSeriesTrigger = new EventSeriesTrigger("EventSeriesOne", 300);
        assertEquals(eventSeriesTrigger.getIdentifierSignal(), "EventSeriesOne");
        assertEquals(eventSeriesTrigger.getTheshold(), 300);
        assertEquals(eventSeriesTrigger.getEventsAlreadyDeletedCopy().size(), 0);
        assertEquals(eventSeriesTrigger.getEventsAlreadyWrittenCopy().size(), 0);
        assertEquals(eventSeriesTrigger.trigger(), false);


    }

    @Test
    public void testUpdatePlain() {
        EventSeriesTrigger eventSeriesTrigger = new EventSeriesTrigger("EventSeriesOne", 300);
        WriterRunnableEventSeries writerRunnableEventSeries = new WriterRunnableEventSeries("EventSeriesOne");
        Event event1 = new Event(1000, "DebugEvent", null);
        writerRunnableEventSeries.addEventToWrite(event1);
        eventSeriesTrigger.update(writerRunnableEventSeries);
        assertEquals(eventSeriesTrigger.getEventsAlreadyDeletedCopy().size(), 0);
        assertEquals(eventSeriesTrigger.getEventsAlreadyWrittenCopy().size(), 1);
    }

    @Test
    public void testUpdateComplete() {
        EventSeriesTrigger eventSeriesTrigger = new EventSeriesTrigger("EventSeriesOne", 300);
        WriterRunnableEventSeries writerRunnableEventSeries = AuxTestUtilities.generarWriterRunnableEvents("EventSeriesOne", 33, 1000, 2000);
        eventSeriesTrigger.update(writerRunnableEventSeries);
        eventSeriesTrigger.update(writerRunnableEventSeries);
        eventSeriesTrigger.update(writerRunnableEventSeries);
        assertFalse(eventSeriesTrigger.trigger());
        assertEquals(eventSeriesTrigger.getEventsAlreadyDeletedCopy().size(), 0);
        assertEquals(eventSeriesTrigger.getEventsAlreadyWrittenCopy().size(), 99);
        writerRunnableEventSeries = AuxTestUtilities.generarWriterRunnableEvents("EventSeriesOne", 300, 1000, 2000);
        eventSeriesTrigger.update(writerRunnableEventSeries);
        assertTrue(eventSeriesTrigger.trigger());
        assertEquals(eventSeriesTrigger.getEventsAlreadyDeletedCopy().size(), 0);
        assertEquals(eventSeriesTrigger.getEventsAlreadyWrittenCopy().size(), 399);
        writerRunnableEventSeries = AuxTestUtilities.generarWriterRunnableEvents("", 99, 1000, 2000);
        eventSeriesTrigger.update(writerRunnableEventSeries);
        assertTrue(eventSeriesTrigger.trigger());
        assertEquals(eventSeriesTrigger.getEventsAlreadyDeletedCopy().size(), 0);
        assertEquals(eventSeriesTrigger.getEventsAlreadyWrittenCopy().size(), 399);

        eventSeriesTrigger = new EventSeriesTrigger("EventSeriesTwo", 100);
        writerRunnableEventSeries = AuxTestUtilities.generarWriterRunnableEvents("EventSeriesTwo", 99, 1000, 2000);
        eventSeriesTrigger.update(writerRunnableEventSeries);
        assertFalse(eventSeriesTrigger.trigger());
        assertEquals(eventSeriesTrigger.getEventsAlreadyDeletedCopy().size(), 0);
        assertEquals(eventSeriesTrigger.getEventsAlreadyWrittenCopy().size(), 99);

        writerRunnableEventSeries = AuxTestUtilities.generarWriterRunnableEvents("EventSeriesOne", 166, 1000, 2000);
        eventSeriesTrigger.update(writerRunnableEventSeries);
        assertFalse(eventSeriesTrigger.trigger());
        assertEquals(eventSeriesTrigger.getEventsAlreadyDeletedCopy().size(), 0);
        assertEquals(eventSeriesTrigger.getEventsAlreadyWrittenCopy().size(), 99);

        writerRunnableEventSeries = AuxTestUtilities.generarWriterRunnableEvents("EventSeriesTwo", 1, 1000, 2000);
        eventSeriesTrigger.update(writerRunnableEventSeries);
        assertFalse(eventSeriesTrigger.trigger());
        assertEquals(eventSeriesTrigger.getEventsAlreadyDeletedCopy().size(), 0);
        assertEquals(eventSeriesTrigger.getEventsAlreadyWrittenCopy().size(), 100);
        writerRunnableEventSeries = AuxTestUtilities.generarWriterRunnableEvents("EventSeriesTwo", 1, 1000, 2000);
        eventSeriesTrigger.update(writerRunnableEventSeries);
        assertTrue(eventSeriesTrigger.trigger());
        assertEquals(eventSeriesTrigger.getEventsAlreadyDeletedCopy().size(), 0);
        assertEquals(eventSeriesTrigger.getEventsAlreadyWrittenCopy().size(), 101);
        writerRunnableEventSeries = AuxTestUtilities.generarWriterRunnableEvents("EventSeriesTwo", 166, 1000, 2000);
        eventSeriesTrigger.update(writerRunnableEventSeries);
        assertTrue(eventSeriesTrigger.trigger());
        assertEquals(eventSeriesTrigger.getEventsAlreadyDeletedCopy().size(), 0);
        assertEquals(eventSeriesTrigger.getEventsAlreadyWrittenCopy().size(), 267);
        writerRunnableEventSeries = AuxTestUtilities.generarWriterRunnableEvents("EventSeriesTwo", 166, 1000, 2000);
        eventSeriesTrigger.update(writerRunnableEventSeries);
        assertTrue(eventSeriesTrigger.trigger());
        assertEquals(eventSeriesTrigger.getEventsAlreadyDeletedCopy().size(), 0);
        assertEquals(eventSeriesTrigger.getEventsAlreadyWrittenCopy().size(), 433);


    }

    @Test
    public void testTrigger() {
        EventSeriesTrigger eventSeriesTrigger = new EventSeriesTrigger("EventSeriesOne", 1000);
        WriterRunnableEventSeries writerRunnableEventSeries = AuxTestUtilities.generarWriterRunnableEvents("EventSeriesOne", 333, 1000, 2000);
        assertFalse(eventSeriesTrigger.trigger());
        eventSeriesTrigger.update(writerRunnableEventSeries);
        assertFalse(eventSeriesTrigger.trigger());
        eventSeriesTrigger.update(writerRunnableEventSeries);
        assertFalse(eventSeriesTrigger.trigger());
        eventSeriesTrigger.update(writerRunnableEventSeries);
        assertFalse(eventSeriesTrigger.trigger());
        eventSeriesTrigger.update(writerRunnableEventSeries);
        assertTrue(eventSeriesTrigger.trigger());
        eventSeriesTrigger.update(writerRunnableEventSeries);
        assertTrue(eventSeriesTrigger.trigger());
        eventSeriesTrigger.update(writerRunnableEventSeries);
        assertTrue(eventSeriesTrigger.trigger());
    }

    @Test
    public void testReset() {
        EventSeriesTrigger eventSeriesTrigger = new EventSeriesTrigger("EventSeriesOne", 100);
        WriterRunnableEventSeries writerRunnableEventSeries = AuxTestUtilities.generarWriterRunnableEvents("EventSeriesOne", 100, 1000, 2000);
        eventSeriesTrigger.update(writerRunnableEventSeries);
        assertFalse(eventSeriesTrigger.trigger());
        eventSeriesTrigger.update(writerRunnableEventSeries);
        assertTrue(eventSeriesTrigger.trigger());
        assertEquals(eventSeriesTrigger.getEventsAlreadyDeletedCopy().size(), 0);
        assertEquals(eventSeriesTrigger.getEventsAlreadyWrittenCopy().size(), 200);
        assertEquals(eventSeriesTrigger.getTheshold(), 100);
        eventSeriesTrigger.reset();
        assertEquals(eventSeriesTrigger.getEventsAlreadyDeletedCopy().size(), 0);
        assertEquals(eventSeriesTrigger.getEventsAlreadyWrittenCopy().size(), 0);
        assertEquals(eventSeriesTrigger.getTheshold(), 100);
        writerRunnableEventSeries = AuxTestUtilities.generarWriterRunnableEvents("EventSeriesOne", 33, 1000, 2000);
        eventSeriesTrigger.update(writerRunnableEventSeries);
        assertEquals(eventSeriesTrigger.getEventsAlreadyDeletedCopy().size(), 0);
        assertEquals(eventSeriesTrigger.getEventsAlreadyWrittenCopy().size(), 33);
        assertEquals(eventSeriesTrigger.getTheshold(), 100);
        eventSeriesTrigger.reset();
        assertEquals(eventSeriesTrigger.getEventsAlreadyDeletedCopy().size(), 0);
        assertEquals(eventSeriesTrigger.getEventsAlreadyWrittenCopy().size(), 0);
        assertEquals(eventSeriesTrigger.getTheshold(), 100);
        eventSeriesTrigger.update(writerRunnableEventSeries);
        assertEquals(eventSeriesTrigger.getEventsAlreadyDeletedCopy().size(), 0);
        assertEquals(eventSeriesTrigger.getEventsAlreadyWrittenCopy().size(), 33);
        assertEquals(eventSeriesTrigger.getTheshold(), 100);
    }
}
