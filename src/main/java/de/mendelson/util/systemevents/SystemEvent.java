//$Header: /mec_as2/de/mendelson/util/systemevents/SystemEvent.java 86    15/04/26 13:02 Heller $
package de.mendelson.util.systemevents;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.MendelsonMultiResolutionImage;
import de.mendelson.util.clientserver.SerializationDummy;
import de.mendelson.util.systemevents.gui.UIEventCategory;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.InetAddress;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.ImageIcon;


/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */
/**
 * Stores the information about an event
 *
 * @author S.Heller
 * @version $Revision: 86 $
 */
public class SystemEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Map<String, String> NOTIFICATION_TEMPLATE_CACHE = new ConcurrentHashMap<String, String>();

    public static final MendelsonMultiResolutionImage ICON_SEVERITY_ERROR_MULTIRESOLUTION
            = MendelsonMultiResolutionImage.fromSVG(
                    "/de/mendelson/util/systemevents/gui/state_stopped.svg", 10, 64);
    public static final MendelsonMultiResolutionImage ICON_SEVERITY_WARNING_MULTIRESOLUTION
            = MendelsonMultiResolutionImage.fromSVG(
                    "/de/mendelson/util/systemevents/gui/state_pending.svg", 10, 64);
    public static final MendelsonMultiResolutionImage ICON_SEVERITY_INFO_MULTIRESOLUTION
            = MendelsonMultiResolutionImage.fromSVG(
                    "/de/mendelson/util/systemevents/gui/severity_info.svg", 10, 64);
    public static final MendelsonMultiResolutionImage ICON_ORIGIN_SYSTEM_MULTIRESOLUTION
            = MendelsonMultiResolutionImage.fromSVG(
                    "/de/mendelson/util/systemevents/gui/origin_system.svg", 10, 64);
    public static final MendelsonMultiResolutionImage ICON_ORIGIN_TRANSACTION_MULTIRESOLUTION
            = MendelsonMultiResolutionImage.fromSVG(
                    "/de/mendelson/util/systemevents/gui/messagedetails.svg", 10, 64);
    public static final MendelsonMultiResolutionImage ICON_ORIGIN_USER_MULTIRESOLUTION
            = MendelsonMultiResolutionImage.fromSVG(
                    "/de/mendelson/util/systemevents/gui/origin_user.svg", 10, 64);

    private static final DateTimeFormatter HUMAN_READABLE_EVENT_DATE_FORMAT
            = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                    .withLocale(Locale.getDefault());

    public enum Origin {
        /**
         * Its a system shutdown, restart etc
         */
        SYSTEM(1),
        /**
         * The user changed a certificate, changed configuration etc
         */
        USER(2),
        /**
         * Any transaction related event
         */
        TRANSACTION(3);

        private final int id;

        Origin(int id) {
            this.id = id;
        }

        @JsonValue
        public int toInt() {
            return this.id;
        }

        @JsonCreator
        public static Origin of(int id) {
            for (Origin origin : Origin.values()) {
                if (origin.id == id) {
                    return origin;
                }
            }
            return (Origin.SYSTEM);
        }
    }

    public enum Severity {
        /**
         * The user should be notified, e.g. a new certificate via certificate
         * exchange. No problem, just a user information
         */
        INFO(1),
        /**
         * An warning occurred in the system. Non critical, e.g. a certificate
         * will expire
         */
        WARNING(2),
        /**
         * An error occurred in the system, e.g. database problem, resource
         * problems etc
         */
        ERROR(3);

        private final int id;

        Severity(int id) {
            this.id = id;
        }

        @JsonValue
        public int toInt() {
            return this.id;
        }

        @JsonCreator
        public static Severity of(int id) {
            for (Severity severity : Severity.values()) {
                if (severity.id == id) {
                    return severity;
                }
            }
            return (ERROR);
        }
    }

    public enum Type {
        //just for the filter
        FILTER_ACCEPT_ALL(-1),
        // Server components
        SERVER_COMPONENTS_ANY(199),
        MAIN_SERVER_SHUTDOWN(100),
        MAIN_SERVER_STARTUP_BEGIN(101),
        MAIN_SERVER_RUNNING(102),
        DATABASE_SERVER_STARTUP_BEGIN(103),
        DATABASE_SERVER_RUNNING(104),
        DATABASE_SERVER_SHUTDOWN(105),
        HTTP_SERVER_STARTUP_BEGIN(106),
        HTTP_SERVER_RUNNING(107),
        HTTP_SERVER_SHUTDOWN(108),
        TRFC_SERVER_STARTUP_BEGIN(109),
        TRFC_SERVER_RUNNING(110),
        TRFC_SERVER_STATE(111),
        TRFC_SERVER_SHUTDOWN(112),
        SCHEDULER_SERVER_STARTUP_BEGIN(113),
        SCHEDULER_SERVER_RUNNING(114),
        SCHEDULER_SERVER_SHUTDOWN(115),
        DIRECTORY_MONITORING_STATE_CHANGED(116),
        PORT_LISTENER(117),
        // Connectivity
        CONNECTIVITY_ANY(200),
        CONNECTIVITY_TEST(201),
        // Transactions
        TRANSACTION_ANY(300),
        TRANSACTION_ERROR(301),
        TRANSACTION_REJECTED_RESEND(302),
        TRANSACTION_DUPLICATE_MESSAGE(303),
        TRANSACTION_DELETE(304),
        TRANSACTION_CANCEL(305),
        TRANSACTION_RESEND(306),
        // Certificates
        CERTIFICATE_ANY(400),
        CERTIFICATE_ADD(401),
        CERTIFICATE_MODIFY(402),
        CERTIFICATE_DEL(403),
        CERTIFICATE_EXCHANGE_ANY(404),
        CERTIFICATE_EXPIRE(405),
        CERTIFICATE_EXCHANGE_REQUEST_RECEIVED(406),
        CERTIFICATE_IMPORT_KEYSTORE(407),
        // Database
        DATABASE_ANY(500),
        DATABASE_CREATION(501),
        DATABASE_UPDATE(502),
        DATABASE_INITIALIZATION(503),
        DATABASE_ROLLBACK(504),
        // Configuration
        SERVER_CONFIGURATION_ANY(700),
        SERVER_CONFIGURATION_CHANGED(701),
        SERVER_CONFIGURATION_CHECK(702),
        PARTNER_MODIFY(703),
        PARTNER_DEL(704),
        PARTNER_ADD(705),
        // Quota
        QUOTA_ANY(800),
        QUOTA_SEND_EXCEEDED(801),
        QUOTA_RECEIVE_EXCEEDED(802),
        QUOTA_SEND_RECEIVE_EXCEEDED(803),
        // Notification
        NOTIFICATION_ANY(900),
        NOTIFICATION_SEND_SUCCESS(901),
        NOTIFICATION_SEND_FAILED(902),
        // Processing
        PROCESSING_ANY(1000),
        PRE_PROCESSING(1001),
        POST_PROCESSING(1002),
        // License
        LICENSE_ANY(1100),
        LICENSE_UPDATE(1101),
        LICENSE_EXPIRE(1102),
        // File operation
        FILE_OPERATION_ANY(1200),
        FILE_DELETE(1201),
        FILE_MKDIR(1202),
        FILE_MOVE(1203),
        FILE_COPY(1204),
        // Client-Server
        CLIENT_ANY(1300),
        CLIENT_LOGIN_SUCCESS(1301),
        CLIENT_LOGIN_FAILURE(1302),
        CLIENT_LOGOFF(1303),
        // XML interface
        XML_INTERFACE_ANY(1400),
        XML_INTERFACE_CERTIFICATE_MODIFICATION(1401),
        XML_INTERFACE_PARTNER_MODIFICATION(1402),
        // REST interface
        REST_INTERFACE_ANY(1500),
        REST_INTERFACE_CERTIFICATE_ADD(1501),
        REST_INTERFACE_CERTIFICATE_MODIFICATION(1502),
        REST_INTERFACE_CERTIFICATE_DEL(1503),
        REST_INTERFACE_PARTNER_ADD(1504),
        REST_INTERFACE_PARTNER_MODIFICATION(1505),
        REST_INTERFACE_PARTNER_DEL(1506),
        REST_INTERFACE_SENDORDER(1507),
        REST_INTERFACE_TRANSACTION_DEL(1508),
        // Other
        OTHER(100000);

        private final int id;

        Type(int id) {
            this.id = id;
        }

        @JsonValue
        public int toInt() {
            return this.id;
        }

        @JsonCreator
        public static Type of(int id) {
            for (Type type : Type.values()) {
                if (type.id == id) {
                    return type;
                }
            }
            return (OTHER);
        }
    }

    public enum Category {
        //just for the filter
        FILTER_ACCEPT_ALL(-1),
        SERVER_COMPONENTS(100),
        CONNECTIVITY(200),
        TRANSACTION(300),
        CERTIFICATE(400),
        DATABASE(500),
        CONFIGURATION(700),
        QUOTA(800),
        NOTIFICATION(900),
        PROCESSING(1000),
        LICENSE(1100),
        FILE_OPERATION(1200),
        CLIENT_OPERATION(1300),
        XML_INTERFACE(1400),
        REST_INTERFACE(1500),
        OTHER(100000);

        private final int id;

        Category(int id) {
            this.id = id;
        }

        @JsonValue
        public int toInt() {
            return this.id;
        }

        @JsonCreator
        public static Category of(int id) {
            for (Category category : Category.values()) {
                if (category.id == id) {
                    return category;
                }
            }
            return OTHER;
        }
    }

    private static final String SERVER_SIDE_HOSTNAME;

    static {
        String detectedHostname;
        try {
            detectedHostname = InetAddress.getLocalHost().getHostName();
        } catch (Throwable e) {
            detectedHostname = "Unknown";
        }
        SERVER_SIDE_HOSTNAME = detectedHostname;
    }

    private static final String SECTION_DESCRIPTION = "[Event description]";
    private static final String SECTION_BODY = "[Details]";
    private static final String SECTION_SUBJECT = "[Summary]";

    public static final String USER_SERVER_PROCESS = "<server_process>";

    private long timestamp = System.currentTimeMillis();
    private SystemEvent.Severity severity;
    private SystemEvent.Origin origin;
    private SystemEvent.Type type;
    private SystemEvent.Category category;
    private String subject = "";
    private String body = "";
    private String processOriginHost = SERVER_SIDE_HOSTNAME;
    private String user = USER_SERVER_PROCESS;

    private static final String NOTIFICATION_TEMPLATE_DIR = "notificationtemplates";

    private String id;
    private static final MecResourceBundle rb;
    private static final MecResourceBundle rbFilenames;

    static {
        try {
            rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleSystemEvent.class.getName());
            rbFilenames = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleSystemEventFilenames.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
    }

    public SystemEvent(SystemEvent.Severity severity, SystemEvent.Origin origin, SystemEvent.Type type) {
        this.severity = severity;
        this.origin = origin;
        this.type = type;
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
        this.category = this.computeCategoryForType(type);
    }

    /**
     * This is a dummy constructor for the deserialization process. Do not use
     * in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public SystemEvent() {
        super();
    }

    private SystemEvent.Category computeCategoryForType(SystemEvent.Type type) {
        int computedCategory = (type.toInt() / 100) * 100;
        return (SystemEvent.Category.of(computedCategory));
    }

    /**
     * Has a lookup at the cache if the template already exists, then its
     * returned - if not it is loaded into the cache and then returned to be
     * cached for the next lookup
     *
     * @param templateName
     * @return
     * @throws IOException
     */
    private String loadTemplate(String templateName) throws IOException {
        String cached = NOTIFICATION_TEMPLATE_CACHE.get(templateName);
        if (cached != null) {
            return cached;
        }
        String templateFilename = this.getLocalizedTemplateFilename(templateName);
        Path path = Paths.get(NOTIFICATION_TEMPLATE_DIR, templateFilename);
        //prevent "Files.newBufferedReader(Paths.get(templateFilename), StandardCharsets.UTF_8);"
        //because this will throw a MalformedInputException if the encoding does not match!
        //The REPLACE action will replace the unreadable character with a "?"
        CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder()
                .onMalformedInput(CodingErrorAction.REPLACE)
                .onUnmappableCharacter(CodingErrorAction.REPLACE);
        try (InputStream inStream = Files.newInputStream(path)) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, decoder))) {
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line).append("\n");
                }
                String templateStr = builder.toString();
                NOTIFICATION_TEMPLATE_CACHE.put(templateName, templateStr);
                return templateStr;
            }
        }
    }

    /**
     * Reads the notification mail template file
     */
    public void readFromNotificationTemplate(String templateName, Properties replacement) throws Exception {
        String templateStr = this.loadTemplate(templateName);
        StringBuilder bodyBuilder = new StringBuilder();
        boolean inSubject = false;
        boolean inBody = false;
        Iterator<String> lineIterator = templateStr.lines().iterator();
        while (lineIterator.hasNext()) {
            String line = lineIterator.next();
            if (line.trim().equals("[SUBJECT]")) {
                inSubject = true;
                inBody = false;
                continue;
            } else if (line.trim().equals("[BODY]")) {
                inSubject = false;
                inBody = true;
                continue;
            }
            if (inSubject) {
                this.setSubject(this.replaceAllVars(line, replacement));
                inSubject = false;
            } else if (inBody) {
                if (bodyBuilder.length() > 0) {
                    bodyBuilder.append("\n");
                }
                bodyBuilder.append(line);
            }
        }
        this.setBody(this.replaceAllVars(bodyBuilder.toString(), replacement));
    }

    /**
     * Replaces all used variables in the passed source and returns them
     *
     * @param source Source string to replace the variable occurrences in
     * @param replacement container that contains the key-value pairs of
     * replacements
     * @return The replaced string
     */
    private String replaceAllVars(String source, Properties replacement) {
        if (source == null || replacement == null) {
            return source;
        }
        for (Map.Entry<Object, Object> entry : replacement.entrySet()) {
            String key = String.valueOf(entry.getKey());
            String value = String.valueOf(entry.getValue());
            source = source.replace(key, value);
        }
        return source;
    }

    /**
     * Adds a _de _fr etc to the template name and returns it
     */
    private String getLocalizedTemplateFilename(String templateName) {
        String language = Locale.getDefault().getLanguage();
        //select language specific template
        if (Files.exists(Paths.get(NOTIFICATION_TEMPLATE_DIR, templateName + "_" + language))) {
            templateName = Paths.get(NOTIFICATION_TEMPLATE_DIR, templateName + "_" + language)
                    .getFileName().toString();
        } else {
            templateName = Paths.get(NOTIFICATION_TEMPLATE_DIR, templateName)
                    .getFileName().toString();
        }
        return (templateName);
    }

    /**
     * @return the timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the type
     */
    public SystemEvent.Type getType() {
        return this.type;
    }

    /**
     * Sets the type of this event - this will internal also compute the
     * category, there is no need to define it by parameter
     *
     * @param type the type to set
     */
    public SystemEvent setType(SystemEvent.Type type) {
        this.type = type;
        this.setCategory(this.computeCategoryForType(type));
        return (this);
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public SystemEvent setSubject(String subject) {
        this.subject = subject;
        return (this);
    }

    /**
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public SystemEvent setBody(String body) {
        this.body = body;
        return (this);
    }

    @JsonIgnore
    public String getHumanReadableTimestamp() {
        return Instant.ofEpochMilli(this.timestamp)
                .atZone(ZoneId.systemDefault())
                .format(HUMAN_READABLE_EVENT_DATE_FORMAT);
    }

    /**
     * Serializes this system event to the passed filename
     */
    public void store(Path storageDir, String storageFilePrefix, String storageFileSuffix) throws Exception {
        if (!storageDir.toFile().exists()) {
            Files.createDirectories(storageDir);
        }
        Path uniqueStorageFile = Files.createTempFile(storageDir, storageFilePrefix, storageFileSuffix);
        try (BufferedWriter writer = Files.newBufferedWriter(uniqueStorageFile, StandardCharsets.UTF_8)) {
            writer.write(SECTION_DESCRIPTION);
            writer.newLine();
            writer.write("TimestampDescription=" + getHumanReadableTimestamp());
            writer.newLine();
            writer.write("SeverityDescription=" + this.severityToTextLocalized());
            writer.newLine();
            writer.write("OriginDescription=" + this.originToTextLocalized());
            writer.newLine();
            writer.write("CategoryDescription=" + this.categoryToTextLocalized());
            writer.newLine();
            writer.write("TypeDescription=" + this.typeToTextLocalized());
            writer.newLine();
            writer.write("ProcessOriginHost=" + this.getProcessOriginHost());
            writer.newLine();
            writer.write("User=" + this.getUser());
            writer.newLine();
            writer.write("Timestamp=" + this.getTimestamp());
            writer.newLine();
            writer.write("Severity=" + this.severity.toInt());
            writer.newLine();
            writer.write("Origin=" + this.origin.toInt());
            writer.newLine();
            writer.write("Type=" + this.type.toInt());
            writer.newLine();
            writer.write("EventId=" + this.id);
            writer.newLine();
            writer.newLine();
            writer.newLine();
            writer.write(SECTION_SUBJECT);
            writer.newLine();
            if (this.getSubject() != null) {
                writer.write(this.getSubject());
            }
            writer.newLine();
            writer.newLine();
            writer.write(SECTION_BODY);
            writer.newLine();
            if (this.getBody() != null) {
                writer.write(this.getBody());
            }
            writer.newLine();
            writer.newLine();
        }
    }

    /**
     * Parses a system event from a stored system event file that has been
     * stored using the store method
     */
    public static SystemEvent parse(Path eventFile) throws Exception {
        SystemEvent event = new SystemEvent(SystemEvent.Severity.INFO, SystemEvent.Origin.SYSTEM, SystemEvent.Type.OTHER);
        String section = "";
        StringBuilder body = new StringBuilder();
        StringBuilder subject = new StringBuilder();
        int sectionCount = 0;
        //prevent to use "Files.newBufferedReader(Paths.get(templateFilename), StandardCharsets.UTF_8);"
        //because this will throw a MalformedInputException if the encoding does not match!
        //The REPLACE action will replace the unreadable character with a "?"
        CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder()
                .onMalformedInput(CodingErrorAction.REPLACE)
                .onUnmappableCharacter(CodingErrorAction.REPLACE);
        try (InputStream inStream = Files.newInputStream(eventFile)) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, decoder))) {
                String line = reader.readLine();
                while (line != null) {
                    if (line.trim().equals(SECTION_DESCRIPTION)) {
                        section = SECTION_DESCRIPTION;
                        sectionCount++;
                    } else if (line.trim().equals(SECTION_BODY)) {
                        section = SECTION_BODY;
                        sectionCount++;
                    } else if (line.trim().equals(SECTION_SUBJECT)) {
                        section = SECTION_SUBJECT;
                        sectionCount++;
                    } else {
                        try {
                            if (section.equals(SECTION_DESCRIPTION) && line.contains("=")) {
                                String[] keyValue = line.split("=");
                                if (keyValue[0].equalsIgnoreCase("user")) {
                                    event.setUser(keyValue[1]);
                                } else if (keyValue[0].equalsIgnoreCase("timestamp")) {
                                    event.setTimestamp(Long.parseLong(keyValue[1]));
                                } else if (keyValue[0].equalsIgnoreCase("severity")) {
                                    event.setSeverity(SystemEvent.Severity.of(Integer.parseInt(keyValue[1])));
                                } else if (keyValue[0].equalsIgnoreCase("origin")) {
                                    event.setOrigin(SystemEvent.Origin.of(Integer.parseInt(keyValue[1])));
                                } else if (keyValue[0].equalsIgnoreCase("type")) {
                                    event.setType(SystemEvent.Type.of(Integer.parseInt(keyValue[1])));
                                } else if (keyValue[0].equalsIgnoreCase("processoriginhost")) {
                                    event.setProcessOriginHost(keyValue[1]);
                                } else if (keyValue[0].equalsIgnoreCase("eventid")) {
                                    event.setId(keyValue[1]);
                                }
                            } else if (section.equals(SECTION_BODY)) {
                                body.append(line).append("\n");
                            } else if (section.equals(SECTION_SUBJECT)) {
                                subject.append(line).append("\n");
                            }
                        } catch (Exception e) {
                            //mainly numberformat?
                            e.printStackTrace();
                        }
                    }
                    line = reader.readLine();
                }
            }
        }
        if (sectionCount != 3) {
            throw new Exception("System event parser: "
                    + eventFile.toString()
                    + " is no event file - bad number of sections (found " + sectionCount + ")");
        }
        event.setBody(body.toString());
        event.setSubject(subject.toString());
        return (event);
    }

    /**
     * Returns the severity of this event in a human readable form that is used
     * for the storage filename
     */
    public String severityToFilename() {
        if (this.getSeverity() == SystemEvent.Severity.ERROR) {
            return ("error");
        } else if (this.getSeverity() == SystemEvent.Severity.INFO) {
            return ("info");
        } else if (this.getSeverity() == SystemEvent.Severity.WARNING) {
            return ("warning");
        }
        return ("unknown");
    }

    /**
     * Returns the severity of this event in a human readable form that is used
     * for the storage filename
     */
    public String severityToTextLocalized() {
        return (rb.getResourceString("severity." + this.severity.toInt()));
    }

    /**
     * Returns the category of this event in a human readable form
     */
    public String categoryToTextLocalized() {
        return (rb.getResourceString("category." + this.getCategory().toInt()));
    }

    /**
     * Contains a multi resolution image that displays the severity of the event
     */
    public ImageIcon getSeverityIconMultiResolution(int minResolution) {
        if (this.getSeverity() == SystemEvent.Severity.ERROR) {
            return (new ImageIcon(
                    ICON_SEVERITY_ERROR_MULTIRESOLUTION.toMinResolution(minResolution)));
        } else if (this.getSeverity() == SystemEvent.Severity.INFO) {
            return (new ImageIcon(
                    ICON_SEVERITY_INFO_MULTIRESOLUTION.toMinResolution(minResolution)));
        }
        return (new ImageIcon(
                ICON_SEVERITY_WARNING_MULTIRESOLUTION.toMinResolution(minResolution)));
    }

    /**
     * Contains a multi resolution image that displays the origin of the event
     */
    public ImageIcon getOriginIconMultiResolution(int minResolution) {
        if (this.getOrigin() == SystemEvent.Origin.SYSTEM) {
            return (new ImageIcon(
                    ICON_ORIGIN_SYSTEM_MULTIRESOLUTION.toMinResolution(minResolution)));
        } else if (this.getOrigin() == SystemEvent.Origin.TRANSACTION) {
            return (new ImageIcon(
                    ICON_ORIGIN_TRANSACTION_MULTIRESOLUTION.toMinResolution(minResolution)));
        }
        return (new ImageIcon(
                ICON_ORIGIN_USER_MULTIRESOLUTION.toMinResolution(minResolution)));
    }

    /**
     * Contains a multi resolution image that displays the severity of the event
     */
    public ImageIcon getCategoryIconMultiResolution(int minResolution) {
        return (new ImageIcon(
                UIEventCategory.getImageByCategory(
                        this.getCategory()).toMinResolution(minResolution)));
    }

    /**
     * Returns the type of this event in a human readable form that is used for
     * the storage filename
     */
    public String originToTextLocalized() {
        return (rb.getResourceString("origin." + this.origin.toInt()));
    }

    /**
     * Returns the type of this event in a human readable form that is used for
     * the storage filename
     */
    public String originToFilename() {
        if (this.getOrigin() == SystemEvent.Origin.USER) {
            return ("user");
        } else if (this.getOrigin() == SystemEvent.Origin.TRANSACTION) {
            return ("transaction");
        } else if (this.getOrigin() == SystemEvent.Origin.SYSTEM) {
            return ("system");
        }
        return ("unknown");
    }

    public String typeToFilename() {
        String englishText = rbFilenames.getResourceString("type." + this.type.toInt());
        if (englishText != null) {
            englishText = englishText.replace("(", "");
            englishText = englishText.replace(")", "");
            englishText = englishText.replace("'", "");
            englishText = englishText.toLowerCase();
            englishText = englishText.replace(" ", "-");
        }
        return (englishText);
    }

    public String typeToTextLocalized() {
        return (rb.getResourceString("type." + this.type.toInt()));
    }

    /**
     * @return the severity
     */
    public SystemEvent.Severity getSeverity() {
        return severity;
    }

    /**
     * @param severity the severity to set
     */
    public SystemEvent setSeverity(SystemEvent.Severity severity) {
        this.severity = severity;
        return (this);
    }

    /**
     * @return the origin
     */
    public SystemEvent.Origin getOrigin() {
        return origin;
    }

    /**
     * @param origin the origin to set
     */
    public SystemEvent setOrigin(SystemEvent.Origin origin) {
        this.origin = origin;
        return (this);
    }

    /**
     * @return the processOriginHost
     */
    public String getProcessOriginHost() {
        return processOriginHost;
    }

    /**
     * @param processOriginHost the processOriginHost to set
     */
    public SystemEvent setProcessOriginHost(String processOriginHost) {
        this.processOriginHost = processOriginHost;
        return (this);
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public SystemEvent setUser(String user) {
        this.user = user;
        return (this);
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public SystemEvent setId(String id) {
        this.id = id;
        return (this);
    }

    /**
     * Overwrite the equal method of object
     *
     * @param anObject object ot compare
     */
    @Override
    public boolean equals(Object anObject) {
        if (anObject == this) {
            return (true);
        }
        if (anObject != null && anObject instanceof SystemEvent) {
            SystemEvent event = (SystemEvent) anObject;
            return (event.getId().equals(this.id));
        }
        return (false);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        return hash;
    }

    /**
     * @return the category
     */
    public SystemEvent.Category getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public SystemEvent setCategory(SystemEvent.Category category) {
        this.category = category;
        return (this);
    }

}
