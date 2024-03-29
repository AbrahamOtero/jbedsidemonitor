package signals;

import java.util.logging.Level;
import java.util.logging.Logger;
import signals.CircularBuffer.ConsecutiveSamplesAvailableInfo;

public class WriterRunnableTimeSeries extends WriterRunnableOneSignal {

    //@documentacion en el dataToWrite tienen que ser floats validos no pueden ser Float.NaN
    private float[] dataToWrite;
    private int indexInitToWrite;
    //@pendiente pasarlo a un objeto propio y no aqui como estan metidos
    private int olderSampleAvailable;
    private int samplesReadyToReadInOrder;

    public WriterRunnableTimeSeries(String identifier) {
        super(identifier);
        this.dataToWrite = null;
        this.indexInitToWrite = -1;
    }

    public WriterRunnableTimeSeries(String identifier, float[] dataToWrite, int indexInitToWrite) {
        this(identifier);
        this.dataToWrite=dataToWrite;
        this.indexInitToWrite = indexInitToWrite;
    }

    public WriterRunnableTimeSeries(WriterRunnableTimeSeries writerRunnableTimeS) {
            super(writerRunnableTimeS.identifier);
            this.dataToWrite=new float[writerRunnableTimeS.dataToWrite.length];
        System.arraycopy(writerRunnableTimeS.dataToWrite, 0, this.dataToWrite, 0, writerRunnableTimeS.dataToWrite.length);
      
            this.indexInitToWrite=writerRunnableTimeS.indexInitToWrite;
            this.olderSampleAvailable=writerRunnableTimeS.olderSampleAvailable;
            this.samplesReadyToReadInOrder=writerRunnableTimeS.samplesReadyToReadInOrder;

    }

    @Override
    protected void write() {
        if(this.indexInitToWrite%100000==0){
            System.out.println("Writing"+this.getIdentifier()+" "+this.indexInitToWrite);
        }
        //@debug BORRAR System.out.println("----Escribiendo en " + this.getIdentifier() + " Desde" + this.getIndexInitToWrite() + "Cantidad " + this.getDataToWrite().length);

        SignalManager signalManager = SignalManager.getInstance();
        //@pendiente pasarlo a un objeto propio y no aqui como estan metidos
        ConsecutiveSamplesAvailableInfo resultWrite = signalManager.writeToTimeSeries(identifier, dataToWrite, indexInitToWrite);
        this.olderSampleAvailable = resultWrite.getOlderSampleAvailable();
        this.samplesReadyToReadInOrder = resultWrite.getSamplesReadyToReadInOrder();

    }

    public void setDataToWrite(float[] dataToWrite) {
        this.copyArray(dataToWrite);
    }

    public void setIndexInitToWrite(int indexInitToWrite) {
        this.indexInitToWrite = indexInitToWrite;
    }

    private void copyArray(float[] dataToWrite) {
        float[] copy = new float[dataToWrite.length];
        System.arraycopy(dataToWrite, 0, copy, 0, dataToWrite.length);
        this.dataToWrite = copy;
    }

    public float[] getDataToWrite() {
        return dataToWrite;
    }

    public int getIndexInitToWrite() {
        return indexInitToWrite;
    }

    public int getOlderSampleAvailable() {
        return olderSampleAvailable;
    }

    public int getSamplesReadyToReadInOrder() {
        return samplesReadyToReadInOrder;
    }
    //@debug

    public void setOlderSampleAvailable(int sampleInitToReadInOrder) {
        this.olderSampleAvailable = sampleInitToReadInOrder;
    }
//@debug

    public void setSamplesToReadInOrder(int samplesToReadInOrder) {
        this.samplesReadyToReadInOrder = samplesToReadInOrder;
    }
}
