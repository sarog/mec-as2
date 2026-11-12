//$Header: /mec_as2/de/mendelson/comm/as2/statistic/StatisticDetailEntry.java 2     14/04/26 17:02 Heller $
package de.mendelson.comm.as2.statistic;
import de.mendelson.comm.as2.message.MessageDirectionType;
import java.io.Serializable;
/**
 * Stores a statistic overview entry
 * @author S.Heller
 * @version $Revision: 2 $
 */
public class StatisticDetailEntry implements Serializable{
        
    public static final long serialVersionUID = 1L;
        
    private int counter = 0;    
    private String localStation = null;
    private String partner = null;
    private long startTime = 0;
    private long endTime = 0;
    private MessageDirectionType direction = MessageDirectionType.ALL;
    private String seriesName = "";
    
    public StatisticDetailEntry() {
    }
    
    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public MessageDirectionType getDirection() {
        return direction;
    }

    public void setDirection(MessageDirectionType direction) {
        this.direction = direction;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getLocalStation() {
        return localStation;
    }

    public void setLocalStation(String localStation) {
        this.localStation = localStation;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    /**
     * @return the seriesName
     */
    public String getSeriesName() {
        return seriesName;
    }

    /**
     * @param seriesName the seriesName to set
     */
    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

}
