//$Header: /mec_as2/de/mendelson/comm/as2/timing/FileDeleteController.java 30    15/04/26 12:43 Heller $
package de.mendelson.comm.as2.timing;

import de.mendelson.comm.as2.partner.Partner;
import de.mendelson.comm.as2.partner.PartnerAccessDB;
import de.mendelson.comm.as2.preferences.PreferencesAS2;
import de.mendelson.comm.as2.server.AS2Server;
import de.mendelson.util.AS2Tools;
import de.mendelson.util.IOFileFilterCreationDate;
import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.database.IDBDriverManager;
import de.mendelson.util.systemevents.SystemEvent;
import de.mendelson.util.systemevents.SystemEventManagerImplAS2;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Controls the timed deletion of AS2 file entries from the file system
 *
 * @author S.Heller
 * @version $Revision: 30 $
 */
public class FileDeleteController {

    /**
     * Logger to log information to
     */
    private static final Logger logger = Logger.getLogger(AS2Server.SERVER_LOGGER_NAME);
    private final PreferencesAS2 preferences;
    private final TmpFileDeleteThread tempFileDeleteThread;
    private final LogFileDeleteThread logFileDeleteThread;
    private final MecResourceBundle rb;
    private final IDBDriverManager dbDriverManager;

    public FileDeleteController(IDBDriverManager dbDriverManager) {
        this.preferences = new PreferencesAS2(dbDriverManager);
        this.dbDriverManager = dbDriverManager;
        try {
            this.rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleFileDeleteController.class.getName());
        } //load up resourcebundle
        catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
        this.tempFileDeleteThread = new TmpFileDeleteThread();
        this.logFileDeleteThread = new LogFileDeleteThread();
    }

    /**
     * Starts the embedded task that guards the files to delete
     */
    public void startAutoDeleteControl() {
        TimingScheduledThreadPool.scheduleWithFixedDelay(this.tempFileDeleteThread, 15, 30, TimeUnit.MINUTES);
        TimingScheduledThreadPool.scheduleWithFixedDelay(this.logFileDeleteThread, 30, 30, TimeUnit.MINUTES);
    }

    /**
     * Deletes a path recursive and throws an exception with details if this
     * fails
     */
    public void deleteDirectoryRecursive(Path dir) throws IOException {
        List<Path> pathsToDelete = new ArrayList<Path>();
        //try-with-resource pattern to close the file stream
        try (Stream<Path> stream = Files.walk(dir)) {
            pathsToDelete.addAll(
                    stream.sorted(Comparator.reverseOrder())
                            .collect(Collectors.toList()));
        }
        for (Path path : pathsToDelete) {
            Files.deleteIfExists(path);
        }
    }

    /**
     * Deletes all log and event files of the system
     */
    public class LogFileDeleteThread implements Runnable {

        @Override
        public void run() {
            if (preferences.getBoolean(PreferencesAS2.AUTO_LOGDIR_DELETE)) {
                long maxAgeInDays = preferences.getInt(PreferencesAS2.AUTO_LOGDIR_DELETE_OLDERTHAN);
                long olderThanTimeAbsolute = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(maxAgeInDays);
                StringBuilder deleteLog = new StringBuilder();
                int foundEntries = 0;
                SystemEvent.Severity eventSeverity = SystemEvent.Severity.INFO;
                try {
                    Path logDirRoot = Paths.get("log");
                    List<Path> subDirList = listDirsNIO(logDirRoot, olderThanTimeAbsolute);
                    foundEntries = subDirList.size();
                    deleteLog.append(rb.getResourceString("delete.header.logfiles", String.valueOf(maxAgeInDays)));
                    deleteLog.append(System.lineSeparator()).append("---");
                    for (Path logDir : subDirList) {
                        deleteLog.append(System.lineSeparator())
                                .append(logDir.toAbsolutePath());
                        try {
                            deleteDirectoryRecursive(logDir);
                            deleteLog.append(" [").append(rb.getResourceString("success")).append("]");
                        } catch (Exception e) {
                            deleteLog.append(" [").append(rb.getResourceString("failure")).append("]");
                            eventSeverity = SystemEvent.Severity.WARNING;
                            deleteLog.append(" [").append(e.getClass().getSimpleName()).append("]: ").append(e.getMessage());
                        }
                    }
                } catch (Throwable e) {
                    eventSeverity = SystemEvent.Severity.WARNING;
                    deleteLog.append(System.lineSeparator());
                    deleteLog.append("[").append(e.getClass().getSimpleName()).append("]: ").append(e.getMessage());
                }
                if (foundEntries > 0 || eventSeverity != SystemEvent.Severity.INFO) {
                    SystemEvent event = new SystemEvent(
                            eventSeverity, SystemEvent.Origin.SYSTEM, SystemEvent.Type.FILE_DELETE);
                    event.setSubject(rb.getResourceString("delete.title.log"))
                            .setBody(deleteLog.toString());
                    SystemEventManagerImplAS2.instance().newEvent(event);
                }
            }
        }

        /**
         * Non blocking subdir list
         */
        private List<Path> listDirsNIO(Path parentDir, long olderThanTimeAbsolute) throws Exception {
            IOFileFilterCreationDate dirOnlyFilter
                    = new IOFileFilterCreationDate(IOFileFilterCreationDate.MODE_OLDER_THAN, olderThanTimeAbsolute);
            dirOnlyFilter.setIncludeFiles(false);
            dirOnlyFilter.setIncludeDirecories(true);
            List<Path> result = new ArrayList<Path>();
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(parentDir, dirOnlyFilter)) {
                for (Path entry : stream) {
                    result.add(entry);
                }
            }
            return result;
        }
    }

    /**
     * Deletes all tmp files of the system. This includes the temporary files in
     * the directories temp, sent and rawincoming
     */
    public class TmpFileDeleteThread implements Runnable {

        public TmpFileDeleteThread() {
        }

        @Override
        public void run() {
            if (preferences.getBoolean(PreferencesAS2.AUTO_MSG_DELETE)) {
                AtomicInteger foundEntries = new AtomicInteger(0);
                StringBuilder deleteLog = new StringBuilder();
                SystemEvent.Severity eventSeverityRawIncoming = this.deleteRawIncomingFiles(foundEntries, deleteLog);
                SystemEvent.Severity eventSeverityTemp = this.deleteTempFiles(foundEntries, deleteLog);
                SystemEvent.Severity eventSeveritySent = this.deleteSentFiles(foundEntries, deleteLog);
                SystemEvent.Severity eventSeverity 
                        = SystemEvent.Severity.of( Math.max(eventSeveritySent.toInt(), 
                                Math.max(eventSeverityRawIncoming.toInt(), eventSeverityTemp.toInt())));
                if (foundEntries.intValue() > 0) {
                    SystemEvent event = new SystemEvent(
                            eventSeverity, SystemEvent.Origin.SYSTEM, SystemEvent.Type.FILE_DELETE);
                    event.setSubject(rb.getResourceString("delete.title"))
                            .setBody(deleteLog.toString());
                    SystemEventManagerImplAS2.instance().newEvent(event);
                }
            }
        }

        /**
         * Delete old rawincoming files
         *
         * @return The event severity
         */
        private SystemEvent.Severity deleteRawIncomingFiles(AtomicInteger foundEntries, StringBuilder deleteLog) {
            Path rawIncomingDir
                    = Paths.get(Paths.get(preferences.get(PreferencesAS2.DIR_MSG)).toAbsolutePath().toString(),
                            "_rawincoming");
            //delete all files that are older than MDN wait time + delete log time
            long maxAgeInS = (preferences.getInt(PreferencesAS2.AUTO_MSG_DELETE_OLDERTHAN)
                    * preferences.getInt(PreferencesAS2.AUTO_MSG_DELETE_OLDERTHAN_MULTIPLIER_S))
                    + TimeUnit.MINUTES.toSeconds(preferences.getInt(PreferencesAS2.MDN_WAIT_TIME));
            long olderThanTimeAbsolute = System.currentTimeMillis() - TimeUnit.SECONDS.toMillis(maxAgeInS);
            //substract one additional day as buffer. There is another thread that deletes the transmissions which has priority to this file cleanup process.
            //This is to ensure that this task will not delete
            //any transmission file if it runs first and the second task runs directly afterwards. 
            //In this case the other thread would complain about
            //missing files for transmissions and throw system event errors
            olderThanTimeAbsolute = olderThanTimeAbsolute - TimeUnit.DAYS.toMillis(1);
            SystemEvent.Severity eventSeverity = SystemEvent.Severity.INFO;
            IOFileFilterCreationDate fileFilter = new IOFileFilterCreationDate(IOFileFilterCreationDate.MODE_OLDER_THAN, olderThanTimeAbsolute);
            try {
                deleteLog.append(rb.getResourceString("delete.title._rawincoming"))
                        .append(System.lineSeparator()).append("---").append(System.lineSeparator());
                //delete _rawincoming entries
                SystemEvent.Severity severity = this.deleteFilesInDirectory(rawIncomingDir, fileFilter, deleteLog, foundEntries);
                if (severity == SystemEvent.Severity.WARNING) {
                    eventSeverity = SystemEvent.Severity.WARNING;
                }
            } catch (Exception e) {
                eventSeverity = SystemEvent.Severity.WARNING;
                deleteLog.append("[").append(e.getClass().getSimpleName()).append("]: ").append(e.getMessage());
            }
            return (eventSeverity);
        }

        /**
         * Delete old temp files
         *
         * @return The event severity
         */
        private SystemEvent.Severity deleteTempFiles(AtomicInteger foundEntries, StringBuilder deleteLog) {
            //delete all files that are older than MDN wait time + delete log time
            long maxAgeInS = (preferences.getInt(PreferencesAS2.AUTO_MSG_DELETE_OLDERTHAN)
                    * preferences.getInt(PreferencesAS2.AUTO_MSG_DELETE_OLDERTHAN_MULTIPLIER_S))
                    + TimeUnit.MINUTES.toSeconds(preferences.getInt(PreferencesAS2.MDN_WAIT_TIME));
            long olderThanTimeAbsolute = System.currentTimeMillis() - TimeUnit.SECONDS.toMillis(maxAgeInS);
            //substract one additional day as buffer. There is another thread that deletes the transmissions which has priority to this file cleanup process.
            //This is to ensure that this task will not delete
            //any transmission file if it runs first and the second task runs directly afterwards. 
            //In this case the other thread would complain about
            //missing files for transmissions and throw system event errors
            olderThanTimeAbsolute = olderThanTimeAbsolute - TimeUnit.DAYS.toMillis(1);
            IOFileFilterCreationDate fileFilter = new IOFileFilterCreationDate(IOFileFilterCreationDate.MODE_OLDER_THAN, olderThanTimeAbsolute);
            SystemEvent.Severity eventSeverity = SystemEvent.Severity.INFO;
            try {
                //delete temp dir entries and subdirectories
                fileFilter.setIncludeDirecories(true);
                deleteLog.append(System.lineSeparator())
                        .append(System.lineSeparator())
                        .append(rb.getResourceString("delete.title.tempfiles"))
                        .append(System.lineSeparator()).append("---").append(System.lineSeparator());
                SystemEvent.Severity severity = this.deleteFilesInDirectory(Paths.get("temp"), fileFilter, deleteLog, foundEntries);
                if (severity == SystemEvent.Severity.WARNING) {
                    eventSeverity = SystemEvent.Severity.WARNING;
                }
            } catch (Exception e) {
                eventSeverity = SystemEvent.Severity.WARNING;
                deleteLog.append("[").append(e.getClass().getSimpleName()).append("]: ").append(e.getMessage());
            }
            return (eventSeverity);
        }

        /**
         * Delete old sent files. These files are (normally) already deleted if
         * a transaction has been deleted by the maintenance process. But if
         * there is any problem or move of the database or anything else that
         * could happen these files might be still available. This method will
         * finally clean up the files with the rawincoming/temp delete attempt
         *
         * @return The event severity
         */
        private SystemEvent.Severity deleteSentFiles(AtomicInteger foundEntries, StringBuilder deleteLog) {
            //delete all files that are older than MDN wait time + delete log time
            long maxAgeInS = (preferences.getInt(PreferencesAS2.AUTO_MSG_DELETE_OLDERTHAN)
                    * preferences.getInt(PreferencesAS2.AUTO_MSG_DELETE_OLDERTHAN_MULTIPLIER_S))
                    + TimeUnit.MINUTES.toSeconds(preferences.getInt(PreferencesAS2.MDN_WAIT_TIME));
            long olderThanTimeAbsolute = System.currentTimeMillis() - TimeUnit.SECONDS.toMillis(maxAgeInS);
            //substract one additional day as buffer. There is another thread that deletes the transmissions which has priority to this file cleanup process.
            //This is to ensure that this task will not delete
            //any transmission file if it runs first and the second task runs directly afterwards. 
            //In this case the other thread would complain about
            //missing files for transmissions and throw system event errors
            olderThanTimeAbsolute = olderThanTimeAbsolute - TimeUnit.DAYS.toMillis(1);
            IOFileFilterCreationDate fileFilter = new IOFileFilterCreationDate(IOFileFilterCreationDate.MODE_OLDER_THAN, olderThanTimeAbsolute);
            SystemEvent.Severity eventSeverity = SystemEvent.Severity.INFO;
            PartnerAccessDB partnerAccess = new PartnerAccessDB(dbDriverManager);
            List<Partner> localStationList = partnerAccess.getLocalStations();
            List<Partner> nonLocalStationList = partnerAccess.getNonLocalStations();
            //delete sent dir entries and subdirectories
            fileFilter.setIncludeDirecories(true);
            deleteLog.append(System.lineSeparator())
                    .append(System.lineSeparator())
                    .append(rb.getResourceString("delete.title.sentfiles"))
                    .append(System.lineSeparator()).append("---").append(System.lineSeparator());
            String messageDir = Paths.get(preferences.get(PreferencesAS2.DIR_MSG)).toAbsolutePath().toString();
            for (Partner nonLocalStation : nonLocalStationList) {
                String receiverName = AS2Tools.convertToValidFilename(nonLocalStation.getName());
                for (Partner localStation : localStationList) {
                    String senderName = AS2Tools.convertToValidFilename(localStation.getName());
                    try {
                        Path deleteDir = Paths.get(
                                messageDir,
                                receiverName,
                                "sent",
                                senderName);
                        if (Files.exists(deleteDir)) {
                            //it is possible that this directory does not exist as it is optional that this sender/receiver combination exists
                            SystemEvent.Severity severity = this.deleteFilesInDirectory(deleteDir, fileFilter, deleteLog, foundEntries);
                            if (severity == SystemEvent.Severity.WARNING) {
                                eventSeverity = SystemEvent.Severity.WARNING;
                            }
                        }
                    } catch (Exception e) {
                        eventSeverity = SystemEvent.Severity.WARNING;
                        deleteLog.append("[").append(e.getClass().getSimpleName()).append("]: ").append(e.getMessage());
                    }
                }
            }
            return (eventSeverity);
        }

        /**
         * Deletes all files found in the passed path that matches the file
         * filter
         *
         * @return The severity of the operation
         */
        private SystemEvent.Severity deleteFilesInDirectory(Path directory, IOFileFilterCreationDate fileFilter,
                StringBuilder deleteLog, AtomicInteger foundEntries) throws Exception {
            SystemEvent.Severity eventSeverity = SystemEvent.Severity.INFO;
            List<Path> fileList = listFilesNIO(directory, fileFilter);
            if (fileList.isEmpty()) {
                deleteLog.append(rb.getResourceString("no.entries", directory.toAbsolutePath().toString()));
                deleteLog.append(System.lineSeparator());
            }
            for (Path singleFilePath : fileList) {
                foundEntries.incrementAndGet();
                String fileSizeStr = "";
                //if its a directory descent into it and delete it first
                if (Files.isDirectory(singleFilePath)) {
                    SystemEvent.Severity severity = this.deleteFilesInDirectory(singleFilePath, fileFilter, deleteLog, foundEntries);
                    if (severity == SystemEvent.Severity.WARNING) {
                        eventSeverity = SystemEvent.Severity.WARNING;
                    }
                } else {
                    long size = Files.size(singleFilePath);
                    fileSizeStr = AS2Tools.getDataSizeDisplay(size);
                }
                try {
                    Files.delete(singleFilePath);
                    deleteLog.append(rb.getResourceString("success") + ": ")
                            .append(singleFilePath.toAbsolutePath().toString());
                    if (fileSizeStr.length() > 0) {
                        deleteLog.append("      ")
                                .append("[")
                                .append(fileSizeStr)
                                .append("]");
                    }
                    deleteLog.append(System.lineSeparator());
                    logger.config(rb.getResourceString("autodelete", singleFilePath.toAbsolutePath().toString()));
                } catch (Exception delEx) {
                    deleteLog.append(rb.getResourceString("failure") + " [" + delEx.getClass().getSimpleName() + "]: ")
                            .append(singleFilePath.toAbsolutePath().toString())
                            .append(System.lineSeparator());
                    eventSeverity = SystemEvent.Severity.WARNING;
                }
            }
            return (eventSeverity);
        }

        /**
         * Non blocking file directory list
         */
        private List<Path> listFilesNIO(Path dir, DirectoryStream.Filter fileFilter) throws Exception {
            List<Path> result = new ArrayList<Path>();
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, fileFilter)) {
                for (Path entry : stream) {
                    result.add(entry);
                }
            }
            return result;
        }

    }
}
