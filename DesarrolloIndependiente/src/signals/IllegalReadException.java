package signals;

class IllegalReadException extends RuntimeException {

    private String message;
    private String identifier;
    private int bufferCapacity;
    private int lastSampleWrite;
    private int numberOfSamplesWrite;

    private int posStartReading;
    private int numDataToRead;
    public IllegalReadException(String message, int bufferCapacity,int posStartReading,int numDataToRead,int lastSampleWrite,int numberOfSamplesWrite) {
        super(message);
        this.message = new String(message);
        this.bufferCapacity = bufferCapacity;
        this.lastSampleWrite=lastSampleWrite;
        this.numberOfSamplesWrite=numberOfSamplesWrite;
        this.posStartReading=posStartReading;
        this.numDataToRead=numDataToRead;
    }

    IllegalReadException(IllegalReadException e, String identifier) {
        super(e.message + " Signal:" + identifier);
        this.message = new String(e.message);
        this.identifier = new String(identifier);
        
    }

    public int getBufferCapacity() {
        return bufferCapacity;
    }

    public String getIdentifier() {
        return identifier;
    }

    public int getLastSampleWrite() {
        return lastSampleWrite;
    }

    public int getNumDataToRead() {
        return numDataToRead;
    }

    public int getNumberOfSamplesWrite() {
        return numberOfSamplesWrite;
    }

    public int getPosStartReading() {
        return posStartReading;
    }


}
