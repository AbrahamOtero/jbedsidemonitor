package signals;

/**
 * Representa una serie temporal.
 * identifier es una cadena de texto que representa a la serie temporal.
 * agent identifica al agente que produce los datos, si estos son parámetros fisiológicos que proceden del paciente tendrá el valor 'simulated'
 * buffer es el buffer circular que contiene los datos
 * frequency tiene la frecuencia de muestreo de la señal (Hz)
 * units es una cadena de texto que contiene las unidades del parámetro representado por la serie temporal
 *
 */
public class TimeSeries extends Series {
    //Parametros serie temporal

    private CircularBuffer buffer;
    private final int defaultBufferSize = 100000;
    private float frequency;
    private String units;

    public TimeSeries(String identifier, String agent, long origin, float frequency, String units) {
        super(identifier, agent, origin);
        this.frequency = frequency;
        this.units = units.trim();
        if (3600 * 6 * this.frequency > defaultBufferSize) {
            this.buffer = new CircularBuffer(defaultBufferSize);
        } else {
            this.buffer = new CircularBuffer((int) Math.ceil(3600 * 6 * this.frequency));
        }
    }

    public int getSamplescounter() {
        return buffer.getSize();
    }

    public float getFrequency() {
        return frequency;
    }

    /**
     * Devuelve la muestra más nueva
     * @return -1 si el buffer esta vacio en otro caso el indice
     */
 
    public int getIndexNewsample() {
        if (buffer.isEmpty()) {
            return -1;
        }
        return (buffer.getIndexNextWrite() - 1) % this.getCapacity();
    }

    /**
     * Indice de la muestra mas antigua
     * @return -1 si el buffer esta vacio en otro caso el indice
     */
    public int getIndexOldestsample() {
        if (buffer.isEmpty()) {
            return -1;
        }
        return buffer.getIndexold();
    }

    public String getUnits() {
        return units;
    }

    public int getCapacity() {
        return this.buffer.getCapacity();
    }

    public float[] read(int posSrc, int sizetoread) {
        return this.buffer.read(posSrc, sizetoread);
    }

    public boolean write(float[] datatowrite) {

        try {
            return this.buffer.write(datatowrite);
        } catch (TooMuchDataToWriteException e) {
            throw new TooMuchDataToWriteException(e, this.getIdentifier(), this.getCapacity(), datatowrite.length);
        }
    }
        public boolean write(float[] datatowrite, int indexInitToWrite) {

        try {
            return this.buffer.write(datatowrite,indexInitToWrite);
        } catch (TooMuchDataToWriteException e) {
            throw new TooMuchDataToWriteException(e, this.getIdentifier(), this.getCapacity(), datatowrite.length);
        }
    }
}