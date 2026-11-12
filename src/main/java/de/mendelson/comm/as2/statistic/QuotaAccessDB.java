//$Header: /mec_as2/de/mendelson/comm/as2/statistic/QuotaAccessDB.java 13    14/04/26 17:02 Heller $
package de.mendelson.comm.as2.statistic;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy class, not used
 * @author S.Heller
 * @version $Revision: 13 $
 */
public class QuotaAccessDB {


    public QuotaAccessDB(Object a){
    }

    public void resetCounter(String a, String b) {
    }


    public static synchronized void incSentMessages(
            Object a, Object b, Object c, Object d, Object e) {
    }
    
    public static synchronized void incReceivedMessages(
            Object a, Object b, Object c, Object d, Object e) {
    }

    
    
    public StatisticOverviewEntry getStatisticOverview(Object a, Object b){
        return( new StatisticOverviewEntry());
    }
    
    public List<StatisticOverviewEntry> getStatisticOverview(Object localStationId) {
        return( new ArrayList<StatisticOverviewEntry>());
    }    
    
    /**Closes the internal database connection.*/
    public void close() {
    }
}
