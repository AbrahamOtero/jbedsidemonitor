/*
 * TimeRepresentation.java
 *
 * Created on 29 de mayo de 2007, 13:26
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.jsignalmonitor;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * <p>Clase de utilidad que permite convertir tiempo medido en milisegundos desde
 * el 1 de enero de 1970 a una cadena de caracteres y cadenas de caracteres a
 * tiempo medido como milisegundos desde el 1 de enero de 1970.</p>
 *
 * <p> El formato de las cadenas de caracteres con las que trabaja esta clase
 * es <code>HH:mm:ss[.SSS] [dd/MM/yyyy]</code>, siendo optativo el contenido
 * entre corchetes. </p>
 * @author This software is under the Apache License Version 2.0
 *   (http://www.apache.org/licenses/). Copyright 2006-2007 Roman Segador y
 *   Abraham Otero
 */
public class TimeRepresentation {
    //no tiene sentido instanciar esta clase.
    private TimeRepresentation() {}

    /**
     * Recibe un instante de tiempo medido en milisegundos desde la fecha base y
     * devuelve una cadena de caracteres conteniendo fecha, hora y milisegundos.
     *
     * @param time fecha en milisegundos desde  00:00:00 01/01/1970.
     * @return String representando ese instante del tiempo.
     */
    public static String timeToString(long time) {
        return timeToString(time, true, true, true);
    }

    /**
     * Recibe un instante de tiempo medido en milisegundos desde la fecha
     * base y devuelve una cadena de caracteres.
     *
     * @param time fecha en milisegundos desde 00:00:00 01/01/1970.
     * @param milisec true si se desea que la cadena de caracteres contenga
     *   milisegundos, false en caso contrario.
     * @return String representando ese instante del tiempo.
     */
    public static String timeToString(long time,
                                      boolean milisec) {
        return timeToString(time, true, true, milisec);
    }

    /**
     * Recibe un instante de tiempo medido en milisegundos desde la fecha
     * base y devuelve una cadena de caracteres.
     *
     * @param time fecha en milisegundos desde 00:00:00 01/01/1970.
     * @param date true si se desea que la hora contenga la fecha, false en
     *   caso contrario.
     * @param hour true si se desea que la cadena de caracteres contenga la
     *   hora, minutos y segundos, false en caso contrario.
     * @param milisec true si se desea que la cadena de caracteres contenga
     *   milisegundos, false en caso contrario.
     * @return String representando ese instante del tiempo.
     */
    public static String timeToString(long time, boolean date, boolean hour,
                                      boolean milisec) {
        DateTimeFormatter fmt = getFormatter(date, hour, milisec);
        DateTime dt = new DateTime(time);

        return dt.toString(fmt);
    }


    /*public static String timeToHourString(long time,boolean milisec){
        DateTimeFormatter fmt;
        if(milisec)
            fmt=DateTimeFormat.forPattern("HH:mm:ss.SSS");
        else
            fmt=DateTimeFormat.forPattern("HH:mm:ss");
        DateTime dt=new DateTime(time);
        return dt.toString(fmt);
             }*/

    private static DateTimeFormatter getFormatter(boolean date, boolean hour,
                                                  boolean milisec) {
        String format = "";
        if (hour) {
            format += "HH:mm:ss";
            if (milisec) {
                format += ".SSS";
            }
        }
        if (date) {
            if (hour) {
                format += " || ";
            }
            format += "dd/MM/yyyy";
        }

        return DateTimeFormat.forPattern(format);
    }

    /**
     * Recibe una cadena de caracteres con formato <code>HH:mm:ss[.SSS]
     * [dd/MM/yyyy]</code>, siendo optativo el contenido entre corchetes, y
     * devuelve el tiempo que ha transcurrido desde  00:00:00 01/01/1970
     * hasta ese instante en milisegundos.
     *
     * @param time fecha como cadena de caracteres.
     * @param date true si la cadena de caracteres contiene dia a mes y
     *   anho, false en caso contrario.
     * @param hour true si la cadena de caracteres contiene horas, minutos
     *   y segundos, false en caso contrario.
     * @param milisec true si la cadena de caracteres contienen
     *   milisegundos, false en caso contrario.
     * @return milisegundos transcurridos desde la fecha base.
     */
    public static long stringToMillis(String time, boolean date, boolean hour,
                                      boolean milisec) {
        DateTimeFormatter fmt = getFormatter(date, hour, milisec);
        DateTime dt = fmt.parseDateTime(time);
        //System.out.println(dt.toString(fmt));
        return dt.getMillis();

    }

    /**
     * Recibe una cadena de caracteres con formato <code>HH:mm:ss[.SSS]
     * [dd/MM/yyyy]</code>, siendo optativo el contenido entre corchetes, y
     * devuelve el tiempo que ha transcurrido desde  00:00:00 01/01/1970
     * hasta ese instante en milisegundos.
     *
     * @param time fecha como cadena de caracteres.
     * @param milisec true si la cadena de caracteres contienen
     *   milisegundos, false en caso contrario.
     * @return milisegundos transcurridos desde la fecha base.
     */
    public static long stringToMillis(String time,
                                      boolean milisec) {
        return stringToMillis(time, true, true, milisec);

    }

    /**
     * Recibe una cadena de caracteres con formato <code>HH:mm:ss.SSS
     * dd/MM/yyyy</code>, siendo optativo el contenido entre corchetes, y
     * devuelve el tiempo que ha transcurrido desde  00:00:00 01/01/1970
     * hasta ese instante en milisegundos.
     *
     * @param time fecha como cadena de caracteres.
     * @return milisegundos transcurridos desde la fecha base.
     */
    public static long stringToMillis(String time) {
        return stringToMillis(time, true, true, true);

    }


}
