package algorithms;

import java.util.LinkedList;
import signals.Event;

/**
 * Must Be Immutable
 */
public class ResultEventSeriesWriter {

    protected String identifier;
    private LinkedList<Event> eventsToDelete;
    private LinkedList<Event> eventsToWrite;

    public ResultEventSeriesWriter(String identifier, LinkedList<Event> eventsToDelete, LinkedList<Event> eventsToWrite) {
        this.identifier = identifier;
        this.eventsToDelete = eventsToDelete;
        this.eventsToWrite = eventsToWrite;
    }

    public LinkedList<Event> getEventsToDelete() {
        return eventsToDelete;
    }

    public LinkedList<Event> getEventsToWrite() {
        return eventsToWrite;
    }

    public String getIdentifier() {
        return identifier;
    }
}