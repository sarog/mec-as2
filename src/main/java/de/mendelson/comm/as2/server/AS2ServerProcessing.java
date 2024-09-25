//$Header: /mec_as2/de/mendelson/comm/as2/server/AS2ServerProcessing.java 18    12/14/17 11:26a Heller $
package de.mendelson.comm.as2.server;

import de.mendelson.comm.as2.AS2Exception;
import de.mendelson.comm.as2.AS2ServerVersion;
import de.mendelson.comm.as2.cem.CEMAccessDB;
import de.mendelson.comm.as2.cem.CEMEntry;
import de.mendelson.comm.as2.cem.CEMInitiator;
import de.mendelson.comm.as2.cem.CEMReceiptController;
import de.mendelson.comm.as2.cem.clientserver.CEMCancelRequest;
import de.mendelson.comm.as2.cem.clientserver.CEMDeleteRequest;
import de.mendelson.comm.as2.cem.clientserver.CEMListRequest;
import de.mendelson.comm.as2.cem.clientserver.CEMListResponse;
import de.mendelson.comm.as2.cem.clientserver.CEMSendRequest;
import de.mendelson.comm.as2.cem.clientserver.CEMSendResponse;
import de.mendelson.comm.as2.client.manualsend.ManualSendRequest;
import de.mendelson.comm.as2.client.manualsend.ManualSendResponse;
import de.mendelson.comm.as2.clientserver.message.ConfigurationCheckRequest;
import de.mendelson.comm.as2.clientserver.message.ConfigurationCheckResponse;
import de.mendelson.comm.as2.clientserver.message.DeleteMessageRequest;
import de.mendelson.comm.as2.clientserver.message.IncomingMessageRequest;
import de.mendelson.comm.as2.clientserver.message.IncomingMessageResponse;
import de.mendelson.comm.as2.clientserver.message.ModuleLockRequest;
import de.mendelson.comm.as2.clientserver.message.ModuleLockResponse;
import de.mendelson.comm.as2.clientserver.message.PartnerConfigurationChanged;
import de.mendelson.comm.as2.clientserver.message.PerformNotificationTestRequest;
import de.mendelson.comm.as2.clientserver.message.RefreshClientMessageOverviewList;
import de.mendelson.comm.as2.clientserver.message.RefreshTablePartnerData;
import de.mendelson.comm.as2.clientserver.message.ServerInfoRequest;
import de.mendelson.comm.as2.clientserver.message.ServerInfoResponse;
import de.mendelson.comm.as2.clientserver.message.ServerShutdown;
import de.mendelson.comm.as2.configurationcheck.ConfigurationCheckController;
import de.mendelson.comm.as2.configurationcheck.ConfigurationIssue;
import de.mendelson.comm.as2.importexport.ConfigurationExport;
import de.mendelson.comm.as2.importexport.ConfigurationExportRequest;
import de.mendelson.comm.as2.importexport.ConfigurationExportResponse;
import de.mendelson.comm.as2.importexport.ConfigurationImport;
import de.mendelson.comm.as2.importexport.ConfigurationImportRequest;
import de.mendelson.comm.as2.importexport.ConfigurationImportResponse;
import de.mendelson.comm.as2.log.LogAccessDB;
import de.mendelson.comm.as2.message.AS2Info;
import de.mendelson.comm.as2.message.AS2MDNCreation;
import de.mendelson.comm.as2.message.AS2MDNInfo;
import de.mendelson.comm.as2.message.AS2Message;
import de.mendelson.comm.as2.message.AS2MessageInfo;
import de.mendelson.comm.as2.message.AS2MessageParser;
import de.mendelson.comm.as2.message.DispositionNotificationOptions;
import de.mendelson.comm.as2.message.ExecuteShellCommand;
import de.mendelson.comm.as2.message.MDNAccessDB;
import de.mendelson.comm.as2.message.MessageAccessDB;
import de.mendelson.comm.as2.message.clientserver.MessageDetailRequest;
import de.mendelson.comm.as2.message.clientserver.MessageDetailResponse;
import de.mendelson.comm.as2.message.clientserver.MessageLogRequest;
import de.mendelson.comm.as2.message.clientserver.MessageLogResponse;
import de.mendelson.comm.as2.message.clientserver.MessageOverviewRequest;
import de.mendelson.comm.as2.message.clientserver.MessageOverviewResponse;
import de.mendelson.comm.as2.message.clientserver.MessagePayloadRequest;
import de.mendelson.comm.as2.message.clientserver.MessagePayloadResponse;
import de.mendelson.comm.as2.message.clientserver.MessageRequestLastMessage;
import de.mendelson.comm.as2.message.clientserver.MessageResponseLastMessage;
import de.mendelson.comm.as2.message.store.MessageStoreHandler;
import de.mendelson.comm.as2.modulelock.ClientInformation;
import de.mendelson.comm.as2.modulelock.ModuleLock;
import de.mendelson.comm.as2.notification.Notification;
import de.mendelson.comm.as2.notification.NotificationAccessDB;
import de.mendelson.comm.as2.notification.clientserver.NotificationGetRequest;
import de.mendelson.comm.as2.notification.clientserver.NotificationGetResponse;
import de.mendelson.comm.as2.notification.clientserver.NotificationSetMessage;
import de.mendelson.comm.as2.partner.Partner;
import de.mendelson.comm.as2.partner.PartnerAccessDB;
import de.mendelson.comm.as2.partner.PartnerSystem;
import de.mendelson.comm.as2.partner.PartnerSystemAccessDB;
import de.mendelson.comm.as2.partner.clientserver.PartnerListRequest;
import de.mendelson.comm.as2.partner.clientserver.PartnerListResponse;
import de.mendelson.comm.as2.partner.clientserver.PartnerModificationRequest;
import de.mendelson.comm.as2.partner.clientserver.PartnerSystemRequest;
import de.mendelson.comm.as2.partner.clientserver.PartnerSystemResponse;
import de.mendelson.comm.as2.preferences.PreferencesAS2;
import de.mendelson.comm.as2.send.DirPollManager;
import de.mendelson.comm.as2.sendorder.SendOrder;
import de.mendelson.comm.as2.sendorder.SendOrderSender;
import de.mendelson.comm.as2.statistic.QuotaAccessDB;
import de.mendelson.comm.as2.statistic.StatisticExport;
import de.mendelson.comm.as2.statistic.StatisticExportRequest;
import de.mendelson.comm.as2.statistic.StatisticExportResponse;
import de.mendelson.comm.as2.timing.MessageDeleteController;
import de.mendelson.util.AS2Tools;
import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.clientserver.ClientServer;
import de.mendelson.util.clientserver.ClientServerProcessing;
import de.mendelson.util.clientserver.ClientServerSessionHandler;
import de.mendelson.util.clientserver.clients.datatransfer.DownloadRequestFile;
import de.mendelson.util.clientserver.clients.datatransfer.DownloadRequestFileLimited;
import de.mendelson.util.clientserver.clients.datatransfer.DownloadResponseFile;
import de.mendelson.util.clientserver.clients.datatransfer.DownloadResponseFileLimited;
import de.mendelson.util.clientserver.clients.datatransfer.UploadRequestChunk;
import de.mendelson.util.clientserver.clients.datatransfer.UploadRequestFile;
import de.mendelson.util.clientserver.clients.datatransfer.UploadResponseChunk;
import de.mendelson.util.clientserver.clients.datatransfer.UploadResponseFile;
import de.mendelson.util.clientserver.clients.fileoperation.FileDeleteRequest;
import de.mendelson.util.clientserver.clients.fileoperation.FileDeleteResponse;
import de.mendelson.util.clientserver.clients.fileoperation.FileRenameRequest;
import de.mendelson.util.clientserver.clients.fileoperation.FileRenameResponse;
import de.mendelson.util.clientserver.clients.filesystemview.FileSystemViewProcessorServer;
import de.mendelson.util.clientserver.clients.filesystemview.FileSystemViewRequest;
import de.mendelson.util.clientserver.clients.preferences.PreferencesRequest;
import de.mendelson.util.clientserver.clients.preferences.PreferencesResponse;
import de.mendelson.util.clientserver.connectiontest.ConnectionTest;
import de.mendelson.util.clientserver.connectiontest.ConnectionTestResult;
import de.mendelson.util.clientserver.connectiontest.clientserver.ConnectionTestRequest;
import de.mendelson.util.clientserver.connectiontest.clientserver.ConnectionTestResponse;
import de.mendelson.util.clientserver.messages.ClientServerMessage;
import de.mendelson.util.clientserver.messages.ClientServerResponse;
import de.mendelson.util.httpconfig.clientserver.DisplayHTTPServerConfigurationRequest;
import de.mendelson.util.httpconfig.server.HTTPServerConfigInfo;
import de.mendelson.util.httpconfig.server.HTTPServerConfigInfoProcessor;
import de.mendelson.util.log.LoggingHandlerLogEntryArray;
import de.mendelson.util.security.cert.CertificateManager;
import de.mendelson.util.security.cert.KeystoreCertificate;
import de.mendelson.util.security.cert.clientserver.RefreshKeystoreCertificates;
import de.mendelson.util.security.cert.clientserver.UploadRequestKeystore;
import de.mendelson.util.security.cert.clientserver.UploadResponseKeystore;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.mina.core.session.IoSession;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */
/**
 * User defined processing to extend the client-server framework
 *
 * @author S.Heller
 * @version $Revision: 18 $
 * @since build 68
 */
public class AS2ServerProcessing implements ClientServerProcessing {

    private DirPollManager pollManager;
    private CertificateManager certificateManagerEncSign;
    private CertificateManager certificateManagerSSL;
    private Connection configConnection;
    private Connection runtimeConnection;
    private Logger logger = Logger.getLogger(AS2Server.SERVER_LOGGER_NAME);
    /**
     * ResourceBundle to localize messages of the server
     */
    private MecResourceBundle rb = null;
    private ClientServer clientserver;
    private final Map<String, String> uploadMap = Collections.synchronizedMap(new HashMap<String, String>());
    private int uploadCounter = 0;
    private FileSystemViewProcessorServer filesystemview;
    /**
     * Start time of this class, this is similar to the server startup time
     */
    private long startupTime = System.currentTimeMillis();
    private MessageStoreHandler messageStoreHandler;
    private MessageAccessDB messageAccess;
    private LogAccessDB logAccess;
    private MDNAccessDB mdnAccess;
    private PartnerSystemAccessDB partnerSystemAccess;
    private PartnerAccessDB partnerAccess;
    private ConfigurationCheckController configurationCheckController;
    private HTTPServerConfigInfo httpServerConfigInfo;

    public AS2ServerProcessing(ClientServer clientserver, DirPollManager pollManager, CertificateManager certificateManagerEncSign,
            CertificateManager certificateManagerSSL,
            Connection configConnection, Connection runtimeConnection,
            ConfigurationCheckController configurationCheckController,
            HTTPServerConfigInfo httpServerConfigInfo) {
        //Load default resourcebundle
        try {
            this.rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleAS2ServerProcessing.class.getName());
        } //load up  resourcebundle
        catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
        this.httpServerConfigInfo = httpServerConfigInfo;
        this.filesystemview = new FileSystemViewProcessorServer(this.logger);
        this.clientserver = clientserver;
        this.configConnection = configConnection;
        this.runtimeConnection = runtimeConnection;
        this.pollManager = pollManager;
        this.certificateManagerEncSign = certificateManagerEncSign;
        this.certificateManagerSSL = certificateManagerSSL;
        this.messageStoreHandler = new MessageStoreHandler(this.configConnection, this.runtimeConnection);
        this.configurationCheckController = configurationCheckController;
        this.messageAccess = new MessageAccessDB(this.configConnection, this.runtimeConnection);
        this.logAccess = new LogAccessDB(this.configConnection, this.runtimeConnection);
        this.mdnAccess = new MDNAccessDB(this.configConnection, this.runtimeConnection);
        this.partnerSystemAccess = new PartnerSystemAccessDB(this.configConnection, this.runtimeConnection);
        this.partnerAccess = new PartnerAccessDB(this.configConnection, this.runtimeConnection);
        this.clientserver.broadcastToClients(new RefreshTablePartnerData());
    }

    private synchronized String incUploadRequest() {
        this.uploadCounter++;
        return (String.valueOf(this.uploadCounter));
    }

    @Override
    public boolean process(IoSession session, ClientServerMessage message) {
        try {
            if (message instanceof PartnerConfigurationChanged) {
                this.pollManager.partnerConfigurationChanged();
                return (true);
            } else if (message instanceof RefreshKeystoreCertificates) {
                this.certificateManagerEncSign.rereadKeystoreCertificatesLogged();
                this.certificateManagerSSL.rereadKeystoreCertificatesLogged();
                return (true);
            } else if (message instanceof PreferencesRequest) {
                this.processPreferencesRequest(session, (PreferencesRequest) message);
                return (true);
            } else if (message instanceof DeleteMessageRequest) {
                this.processDeleteMessageRequest(session, (DeleteMessageRequest) message);
                return (true);
            } else if (message instanceof ManualSendRequest) {
                this.processManualSendRequest(session, (ManualSendRequest) message);
                return (true);
            } else if (message instanceof UploadRequestKeystore) {
                this.processUploadRequestKeystore(session, (UploadRequestKeystore) message);
                return (true);
            } else if (message instanceof FileRenameRequest) {
                this.processFileRenameRequest(session, (FileRenameRequest) message);
                return (true);
            } else if (message instanceof FileDeleteRequest) {
                this.processFileDeleteRequest(session, (FileDeleteRequest) message);
                return (true);
            } else if (message instanceof ConfigurationExportRequest) {
                this.processConfigurationExportRequest(session, (ConfigurationExportRequest) message);
                return (true);
            } else if (message instanceof ConfigurationImportRequest) {
                this.processConfigurationImportRequest(session, (ConfigurationImportRequest) message);
                return (true);
            } else if (message instanceof StatisticExportRequest) {
                this.processStatisticExportRequest(session, (StatisticExportRequest) message);
                return (true);
            } else if (message instanceof DownloadRequestFile) {
                this.processDownloadRequestFile(session, (DownloadRequestFile) message);
                return (true);
            } else if (message instanceof UploadRequestChunk) {
                this.processUploadRequestChunk(session, (UploadRequestChunk) message);
                return (true);
            } else if (message instanceof UploadRequestFile) {
                this.processUploadRequestFile(session, (UploadRequestFile) message);
                return (true);
            } else if (message instanceof FileSystemViewRequest) {
                session.write(this.filesystemview.performRequest((FileSystemViewRequest) message));
                return (true);
            } else if (message instanceof PartnerListRequest) {
                this.processPartnerListRequest(session, (PartnerListRequest) message);
                return (true);
            } else if (message instanceof PartnerModificationRequest) {
                this.processPartnerModificationMessage(session, (PartnerModificationRequest) message);
                return (true);
            } else if (message instanceof MessageOverviewRequest) {
                this.processMessageOverviewRequest(session, (MessageOverviewRequest) message);
                return (true);
            } else if (message instanceof MessageDetailRequest) {
                this.processMessageDetailRequest(session, (MessageDetailRequest) message);
                return (true);
            } else if (message instanceof MessageLogRequest) {
                this.processMessageLogRequest(session, (MessageLogRequest) message);
                return (true);
            } else if (message instanceof MessagePayloadRequest) {
                this.processMessagePayloadRequest(session, (MessagePayloadRequest) message);
                return (true);
            } else if (message instanceof NotificationGetRequest) {
                this.processNotificationGetRequest(session, (NotificationGetRequest) message);
                return (true);
            } else if (message instanceof NotificationSetMessage) {
                this.processNotificationSetRequest(session, (NotificationSetMessage) message);
                return (true);
            } else if (message instanceof PerformNotificationTestRequest) {
                this.performNotificationTest(session, (PerformNotificationTestRequest) message);
                return (true);
            } else if (message instanceof PartnerSystemRequest) {
                this.performPartnerSystemRequest(session, (PartnerSystemRequest) message);
                return (true);
            } else if (message instanceof CEMListRequest) {
                this.processCEMListRequest(session, (CEMListRequest) message);
                return (true);
            } else if (message instanceof CEMDeleteRequest) {
                this.processCEMDeleteRequest(session, (CEMDeleteRequest) message);
                return (true);
            } else if (message instanceof CEMCancelRequest) {
                this.processCEMCancelRequest(session, (CEMCancelRequest) message);
                return (true);
            } else if (message instanceof MessageRequestLastMessage) {
                this.processMessageRequestLastMessage(session, (MessageRequestLastMessage) message);
                return (true);
            } else if (message instanceof CEMSendRequest) {
                this.processCEMSendRequest(session, (CEMSendRequest) message);
                return (true);
            } else if (message instanceof ServerShutdown) {
                this.performServerShutdown(session, (ServerShutdown) message);
                return (true);
            } else if (message instanceof ModuleLockRequest) {
                this.processModuleLockRequest(session, (ModuleLockRequest) message);
                return (true);
            } else if (message instanceof ConfigurationCheckRequest) {
                this.processConfigurationCheckRequest(session, (ConfigurationCheckRequest) message);
                return (true);
            } else if (message instanceof ServerInfoRequest) {
                this.processServerInfoRequest(session, (ServerInfoRequest) message);
                return (true);
            } else if (message instanceof IncomingMessageRequest) {
                this.processIncomingMessageRequest(session, (IncomingMessageRequest) message);
                return (true);
            } else if (message instanceof ConnectionTestRequest) {
                this.processConnectionTestRequest(session, (ConnectionTestRequest) message);
                return (true);
            } else if (message instanceof DisplayHTTPServerConfigurationRequest) {
                this.processDisplayServerConfigurationRequest(session, (DisplayHTTPServerConfigurationRequest) message);
                return (true);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            this.logger.warning(this.rb.getResourceString("unable.to.process", message.toString()));
        }
        return (false);
    }

    /**
     * Async request from a client to display information about the server
     */
    private void processDisplayServerConfigurationRequest(IoSession session, DisplayHTTPServerConfigurationRequest request) {
        HTTPServerConfigInfoProcessor processor = new HTTPServerConfigInfoProcessor(this.httpServerConfigInfo);
        processor.processDisplayServerConfigurationRequest(session, request);
    }

    private void processModuleLockRequest(IoSession session, ModuleLockRequest moduleLockRequest) {
        ModuleLockResponse response = new ModuleLockResponse(moduleLockRequest);
        String remoteAddress = session.getRemoteAddress().toString();
        String uniqueId = String.valueOf(session.getId());
        String userName = (String) session.getAttribute(ClientServerSessionHandler.SESSION_ATTRIB_USER);
        ClientInformation currentClientInfo = new ClientInformation(userName, remoteAddress, uniqueId);
        if (moduleLockRequest.getType() == ModuleLockRequest.TYPE_SET) {
            ClientInformation lockKeeper = ModuleLock.setLock(moduleLockRequest.getModuleName(), currentClientInfo, this.runtimeConnection);
            response.setLockKeeper(lockKeeper);
            response.setSuccess(lockKeeper != null && lockKeeper.equals(currentClientInfo));
        } else if (moduleLockRequest.getType() == ModuleLockRequest.TYPE_RELEASE) {
            ModuleLock.releaseLock(moduleLockRequest.getModuleName(), currentClientInfo, this.runtimeConnection);
        } else if (moduleLockRequest.getType() == ModuleLockRequest.TYPE_REFRESH) {
            ModuleLock.refreshLock(moduleLockRequest.getModuleName(), currentClientInfo, this.runtimeConnection);
        } else if (moduleLockRequest.getType() == ModuleLockRequest.TYPE_LOCK_INFO) {
            ClientInformation currentLockKeeper = ModuleLock.getCurrentLockKeeper(moduleLockRequest.getModuleName(), this.runtimeConnection);
            response.setLockKeeper(currentLockKeeper);
        } else {
            this.logger.warning("AS2ServerProcessing.processModuleLockRequest: Undefined request type " + moduleLockRequest.getType());
        }
        session.write(response);
    }

    /**
     * Performs a connection test
     */
    private void processConnectionTestRequest(IoSession session, ConnectionTestRequest connectionTestRequest) {
        ConnectionTestResponse response = new ConnectionTestResponse(connectionTestRequest);
        //initialize the handler
        Logger testLogger = Logger.getAnonymousLogger();
        testLogger.setUseParentHandlers(false);
        List<LoggingHandlerLogEntryArray.LogEntry> list = new ArrayList<LoggingHandlerLogEntryArray.LogEntry>();
        LoggingHandlerLogEntryArray handler = new LoggingHandlerLogEntryArray(list);
        testLogger.setLevel(Level.ALL);
        testLogger.addHandler(handler);
        try {
            ConnectionTest connectionTest = new ConnectionTest(testLogger, ConnectionTest.CONNECTION_TEST_AS2);
            if (connectionTestRequest.getSSL()) {
                ConnectionTestResult result = connectionTest.checkConnectionSSL(connectionTestRequest.getHost(),
                        connectionTestRequest.getPort(), connectionTestRequest.getTimeout(),
                        this.certificateManagerSSL);
                response.setResult(result);
            } else {
                ConnectionTestResult result = connectionTest.checkConnectionPlain(connectionTestRequest.getHost(),
                        connectionTestRequest.getPort(), connectionTestRequest.getTimeout());
                response.setResult(result);
            }
        } catch (Throwable e) {
            response.setException(e);
        }
        response.addLogEntries(list);
        session.write(response);
    }

    private void processConfigurationCheckRequest(IoSession session, ConfigurationCheckRequest configurationCheckRequest) {
        ConfigurationCheckResponse response = new ConfigurationCheckResponse(configurationCheckRequest);
        List<ConfigurationIssue> issueList = this.configurationCheckController.getIssues();
        for (ConfigurationIssue issue : issueList) {
            response.addIsse(issue);
        }
        session.write(response);
    }

    private void performServerShutdown(IoSession session, ServerShutdown message) {
        //log some information about who tried this
        String username = session.getAttribute(ClientServerSessionHandler.SESSION_ATTRIB_USER).toString();
        this.logger.severe(this.rb.getResourceString("server.shutdown", username));
        Runnable shutdownThread = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
                System.exit(0);
            }
        };
        Executors.newSingleThreadExecutor().submit(shutdownThread);
    }

    private void performNotificationTest(IoSession session, PerformNotificationTestRequest message) throws Exception {
        ClientServerResponse response = new ClientServerResponse(message);
        try {
            Notification notification = new Notification(message.getNotificationData(), this.configConnection, this.runtimeConnection);
            notification.sendTest();
        } catch (Exception e) {
            response.setException(e);
        }
        session.write(response);
    }

    /**
     * Appends a chunk to a formerly sent data. If this is the first chunk an
     * entry is created in the upload map of this class
     */
    private void processUploadRequestChunk(IoSession session, UploadRequestChunk request) {
        UploadResponseChunk response = new UploadResponseChunk(request);
        OutputStream outStream = null;
        InputStream inStream = null;
        try {
            if (request.getTargetHash() == null) {
                File tempFile = AS2Tools.createTempFile("upload_as2", ".bin");
                String newHash = this.incUploadRequest();
                synchronized (this.uploadMap) {
                    this.uploadMap.put(newHash, tempFile.getAbsolutePath());
                }
                request.setTargetHash(newHash);
            }
            response.setTargetHash(request.getTargetHash());
            File tempFile = null;
            synchronized (this.uploadMap) {
                tempFile = new File(this.uploadMap.get(request.getTargetHash()));
            }
            outStream = new FileOutputStream(tempFile, true);
            inStream = request.getDataStream();
            this.copyStreams(inStream, outStream);
        } catch (IOException e) {
            response.setException(e);
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (Exception e) {
                    //nop
                }
            }
            if (outStream != null) {
                try {
                    outStream.flush();
                    outStream.close();
                } catch (Exception e) {
                    //nop
                }
            }
        }
        session.write(response);
    }

    private void processStatisticExportRequest(IoSession session, StatisticExportRequest request) {
        StatisticExportResponse response = new StatisticExportResponse(request);
        StatisticExport exporter = new StatisticExport(this.configConnection, this.runtimeConnection);
        ByteArrayOutputStream outStream = null;
        try {
            outStream = new ByteArrayOutputStream();
            exporter.export(outStream,
                    request.getStartDate(),
                    request.getEndDate(),
                    request.getTimestep(), request.getLocalStation(),
                    request.getPartner());
            outStream.flush();
            response.setData(outStream.toByteArray());
        } catch (Throwable e) {
            response.setException(e);
        } finally {
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (Exception e) {
                    //nop
                }
            }
        }
        //sync respond to the request
        session.write(response);
    }

    private void processDeleteMessageRequest(IoSession session, DeleteMessageRequest request) {
        MessageDeleteController controller = new MessageDeleteController(null,
                this.configConnection, this.runtimeConnection);
        List<AS2MessageInfo> deleteList = request.getDeleteList();
        RefreshClientMessageOverviewList refreshRequest = new RefreshClientMessageOverviewList();
        refreshRequest.setOperation(RefreshClientMessageOverviewList.OPERATION_DELETE_UPDATE);
        for (int i = 0; i < deleteList.size(); i++) {
            controller.deleteMessageFromLog(deleteList.get(i), false);
            if (i % 10 == 0) {
                this.clientserver.broadcastToClients(refreshRequest);
            }
        }
        this.clientserver.broadcastToClients(refreshRequest);
    }

    private void processConfigurationImportRequest(IoSession session, ConfigurationImportRequest request) {
        ConfigurationImportResponse response = new ConfigurationImportResponse(request);
        InputStream inStream = null;
        try {
            String uploadHash = request.getUploadHash();
            File uploadFile = null;
            synchronized (this.uploadMap) {
                uploadFile = new File(this.uploadMap.get(uploadHash));
                this.uploadMap.remove(uploadHash);
            }
            inStream = new FileInputStream(uploadFile);
            ByteArrayOutputStream memOut = new ByteArrayOutputStream();
            this.copyStreams(inStream, memOut);
            byte[] importData = memOut.toByteArray();
            memOut.close();
            ConfigurationImport importer = new ConfigurationImport(this.configConnection, this.runtimeConnection);
            inStream = new ByteArrayInputStream(importData);
            importer.importData(inStream,
                    request.getPartnerListToImport(),
                    request.getImportNotification(),
                    request.getImportServerSettings());
        } catch (Exception e) {
            response.setException(e);
            e.printStackTrace();
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (Exception e) {
                    //nop
                }
            }
        }
        session.write(response);
    }

    private void processConfigurationExportRequest(IoSession session, ConfigurationExportRequest request) {
        ConfigurationExportResponse response = new ConfigurationExportResponse(request);
        try {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            ConfigurationExport export = new ConfigurationExport(this.configConnection, this.runtimeConnection);
            export.export(outStream);
            outStream.flush();
            response.setData(outStream.toByteArray());
        } catch (Exception e) {
            response.setException(e);
        }
        session.write(response);
    }

    private void processUploadRequestFile(IoSession session, UploadRequestFile request) {
        UploadResponseFile response = new UploadResponseFile(request);
        try {
            String uploadHash = request.getUploadHash();
            File tempFile = new File(this.uploadMap.get(uploadHash));
            File targetFile = new File(request.getTargetFilename());
            FileUtils.deleteQuietly(targetFile);
            FileUtils.moveFile(tempFile, targetFile);
            synchronized (this.uploadMap) {
                this.uploadMap.remove(uploadHash);
            }
        } catch (IOException e) {
            response.setException(e);
        }
        session.write(response);
    }

    private void processUploadRequestKeystore(IoSession session, UploadRequestKeystore request) {
        UploadResponseKeystore response = new UploadResponseKeystore(request);
        try {
            String uploadHash = request.getUploadHash();
            File tempFile = null;
            synchronized (this.uploadMap) {
                tempFile = new File(this.uploadMap.get(uploadHash));
            }
            File targetFile = new File(request.getTargetFilename());
            FileUtils.deleteQuietly(targetFile);
            FileUtils.moveFile(tempFile, targetFile);
            synchronized (this.uploadMap) {
                this.uploadMap.remove(uploadHash);
            }
        } catch (IOException e) {
            response.setException(e);
        }
        try {
            this.certificateManagerEncSign.rereadKeystoreCertificates();
            this.certificateManagerSSL.rereadKeystoreCertificates();
        } catch (Exception e) {
            //nop
        }
        session.write(response);
    }

    /**
     * A client performed a file rename request
     *
     */
    private void processFileRenameRequest(IoSession session, FileRenameRequest request) {
        FileRenameResponse response = new FileRenameResponse(request);
        boolean success = new File(request.getOldName()).renameTo(new File(request.getNewName()));
        response.setSuccess(success);
        session.write(response);
    }

    /**
     * A client performed a file delete request
     *
     */
    private void processFileDeleteRequest(IoSession session, FileDeleteRequest request) {
        FileDeleteResponse response = new FileDeleteResponse(request);
        boolean success = false;
        File fileToDelete = new File(request.getFilename());
        try {
            if (fileToDelete.isDirectory()) {
                FileUtils.deleteDirectory(fileToDelete);
            } else {
                FileUtils.forceDelete(fileToDelete);
            }
            success = true;
        } catch (Exception e) {
        }
        response.setSuccess(success);
        session.write(response);
    }

    /**
     * A client performed a manual send request
     *
     * @param session
     * @param request
     */
    private void processManualSendRequest(IoSession session, ManualSendRequest request) {
        ManualSendResponse response = new ManualSendResponse(request);
        SendOrderSender orderSender = new SendOrderSender(this.configConnection, this.runtimeConnection);
        try {
            String[] originalFilenames = new String[request.getFilenames().size()];
            for (int i = 0; i < request.getFilenames().size(); i++) {
                originalFilenames[i] = request.getFilenames().get(i);
            }
            List<String> uploadHashs = request.getUploadHashs();
            List<File> files = new ArrayList<File>();
            for (String uploadHash : uploadHashs) {
                File uploadedFile = null;
                synchronized (this.uploadMap) {
                    uploadedFile = new File(this.uploadMap.get(uploadHash));
                }
                files.add(uploadedFile);
            }
            File[] sendFiles = new File[files.size()];
            for (int i = 0; i < files.size(); i++) {
                sendFiles[i] = files.get(i);
            }
            AS2Message message = orderSender.send(this.certificateManagerEncSign, request.getSender(),
                    request.getReceiver(), sendFiles, originalFilenames, request.getUserdefinedId());
            if (message == null) {
                throw new Exception(this.rb.getResourceString("send.failed"));
            } else {
                response.setAS2Info((AS2MessageInfo) message.getAS2Info());
                //is this a resend? Then get the resend message id and increment the resend counter
                if (request.getResendMessageId() != null) {
                    this.messageAccess.incResendCounter(request.getResendMessageId());
                }
                this.clientserver.broadcastToClients(new RefreshClientMessageOverviewList());
            }
        } catch (Exception e) {
            this.clientserver.broadcastToClients(new RefreshClientMessageOverviewList());
            response.setException(e);
        }
        session.write(response);
    }

    /**
     * A client performed a preferences request
     *
     * @param session
     * @param request
     */
    private void processPreferencesRequest(IoSession session, PreferencesRequest request) {
        PreferencesAS2 preferences = new PreferencesAS2();
        if (request.getType() == PreferencesRequest.TYPE_GET) {
            PreferencesResponse response = new PreferencesResponse(request);
            response.setValue(preferences.get(request.getKey()));
            session.write(response);
        } else if (request.getType() == PreferencesRequest.TYPE_GET_DEFAULT) {
            PreferencesResponse response = new PreferencesResponse(request);
            response.setValue(preferences.getDefaultValue(request.getKey()));
            session.write(response);
        } else if (request.getType() == PreferencesRequest.TYPE_SET) {
            preferences.put(request.getKey(), request.getValue());
        }
    }

    /**
     * A client performed a download request
     *
     * @param session
     * @param request
     */
    private void processDownloadRequestFile(IoSession session, DownloadRequestFile request) {
        DownloadResponseFile response = null;
        if (request instanceof DownloadRequestFileLimited) {
            DownloadRequestFileLimited requestLimited = (DownloadRequestFileLimited) request;
            response = new DownloadResponseFileLimited(requestLimited);
            InputStream inStream = null;
            try {
                if (request.getFilename() == null) {
                    throw new FileNotFoundException();
                }
                File downloadFile = new File(requestLimited.getFilename());
                response.setFullFilename(downloadFile.getAbsolutePath());
                response.setReadOnly(!downloadFile.canWrite());
                response.setSize(downloadFile.length());
                if (downloadFile.length() < requestLimited.getMaxSize()) {
                    inStream = new FileInputStream(request.getFilename());
                    response.setData(inStream);
                    ((DownloadResponseFileLimited) response).setSizeExceeded(false);
                } else {
                    ((DownloadResponseFileLimited) response).setSizeExceeded(true);
                }
            } catch (Exception e) {
                response.setException(e);
            } finally {
                if (inStream != null) {
                    try {
                        inStream.close();
                    } catch (Exception e) {
                        //nop
                    }
                }
            }
        } else {
            response = new DownloadResponseFile(request);
            InputStream inStream = null;
            try {
                if (request.getFilename() == null) {
                    throw new FileNotFoundException();
                }
                File downloadFile = new File(request.getFilename());
                response.setFullFilename(downloadFile.getAbsolutePath());
                response.setReadOnly(!downloadFile.canWrite());
                response.setSize(downloadFile.length());
                inStream = new FileInputStream(downloadFile);
                response.setData(inStream);
            } catch (IOException e) {
                response.setException(e);
            } finally {
                if (inStream != null) {
                    try {
                        inStream.close();
                    } catch (Exception e) {
                        //nop
                    }
                }
            }
        }
        session.write(response);
    }

    /**
     * sync
     */
    private void processPartnerModificationMessage(IoSession session, PartnerModificationRequest request) {
        this.partnerAccess.modifyPartner(request.getData());
        //sync answer
        session.write(new ClientServerResponse(request));
    }

    private void processPartnerListRequest(IoSession session, PartnerListRequest request) {
        PartnerListResponse response = new PartnerListResponse(request);
        if (request.getListOption() == PartnerListRequest.LIST_ALL) {
            response.setList(this.partnerAccess.getPartner());
        } else if (request.getListOption() == PartnerListRequest.LIST_LOCALSTATION) {
            List<Partner> list = new ArrayList<Partner>();
            list.addAll(this.partnerAccess.getLocalStations());
            response.setList(list);
        } else if (request.getListOption() == PartnerListRequest.LIST_NON_LOCALSTATIONS) {
            response.setList(this.partnerAccess.getNonLocalStations());
        } else if (request.getListOption() == PartnerListRequest.LIST_BY_AS2_ID) {
            List<Partner> list = new ArrayList<Partner>();
            Partner partner = this.partnerAccess.getPartner(request.getAdditionalListOptionStr());
            if (partner != null) {
                list.add(partner);

            }
            response.setList(list);
        }
        //sync answer
        session.write(response);
    }

    private void processMessageOverviewRequest(IoSession session, MessageOverviewRequest request) {
        MessageOverviewResponse response = new MessageOverviewResponse(request);
        if (request.getFilter() != null) {
            response.setList(this.messageAccess.getMessageOverview(request.getFilter()));
        } else {
            response.setList(this.messageAccess.getMessageOverview(request.getMessageId()));
        }
        //sync answer
        session.write(response);
    }

    private void processMessageLogRequest(IoSession session, MessageLogRequest request) {
        MessageLogResponse response = new MessageLogResponse(request);
        response.setList(this.logAccess.getLog(request.getMessageId()));
        //sync answer
        session.write(response);
    }

    private void processMessageDetailRequest(IoSession session, MessageDetailRequest request) {
        MessageDetailResponse response = new MessageDetailResponse(request);
        response.setList(this.messageAccess.getMessageDetails(request.getMessageId()));
        //sync answer
        session.write(response);
    }

    private void processMessagePayloadRequest(IoSession session, MessagePayloadRequest request) {
        MessagePayloadResponse response = new MessagePayloadResponse(request);
        response.setList(this.messageAccess.getPayload(request.getMessageId()));
        //sync answer
        session.write(response);
    }

    /**
     * sync
     */
    private void processNotificationGetRequest(IoSession session, NotificationGetRequest request) {
        NotificationGetResponse response = new NotificationGetResponse(request);
        NotificationAccessDB access = new NotificationAccessDB(this.configConnection);
        response.setData(access.getNotificationData());
        //sync answer
        session.write(response);
    }

    /**
     * sync
     */
    private void performPartnerSystemRequest(IoSession session, PartnerSystemRequest request) {
        PartnerSystemResponse response = new PartnerSystemResponse(request);
        response.setPartnerSystem(this.partnerSystemAccess.getPartnerSystem(request.getPartner()));
        //sync answer
        session.write(response);
    }

    /**
     * async
     */
    private void processNotificationSetRequest(IoSession session, NotificationSetMessage request) {
        NotificationAccessDB access = new NotificationAccessDB(this.configConnection);
        access.updateNotification(request.getData());
    }

    /**
     * sync
     */
    private void processCEMListRequest(IoSession session, CEMListRequest request) {
        CEMListResponse response = new CEMListResponse(request);
        CEMAccessDB access = new CEMAccessDB(this.configConnection, this.runtimeConnection);
        response.setList(access.getCEMEntries());
        //sync answer
        session.write(response);
    }

    /**
     * sync
     */
    private void processCEMDeleteRequest(IoSession session, CEMDeleteRequest request) {
        CEMEntry entry = request.getEntry();
        CEMAccessDB cemAccess = new CEMAccessDB(this.configConnection, this.runtimeConnection);
        cemAccess.setPendingRequestsToState(entry.getInitiatorAS2Id(), entry.getReceiverAS2Id(), entry.getCategory(), entry.getRequestId(),
                CEMEntry.STATUS_CANCELED_INT);
        //remove the underlaying messages
        if (entry.getRequestMessageid() != null) {
            this.logAccess.deleteMessageLog(entry.getRequestMessageid());
            this.messageAccess.deleteMessage(entry.getRequestMessageid());
        }
        if (entry.getResponseMessageid() != null) {
            this.logAccess.deleteMessageLog(entry.getResponseMessageid());
            this.messageAccess.deleteMessage(entry.getResponseMessageid());
        }
        cemAccess.removeEntry(entry.getInitiatorAS2Id(), entry.getReceiverAS2Id(), entry.getCategory(), entry.getRequestId());
        //sync answer
        session.write(new ClientServerResponse(request));
    }

    /**
     * sync
     */
    private void processCEMCancelRequest(IoSession session, CEMCancelRequest request) {
        CEMEntry entry = request.getEntry();
        CEMAccessDB cemAccess = new CEMAccessDB(this.configConnection, this.runtimeConnection);
        cemAccess.setPendingRequestsToState(entry.getInitiatorAS2Id(), entry.getReceiverAS2Id(), entry.getCategory(), entry.getRequestId(),
                CEMEntry.STATUS_CANCELED_INT);
        //sync answer
        session.write(new ClientServerResponse(request));
    }

    /**
     * sync
     */
    private void processMessageRequestLastMessage(IoSession session, MessageRequestLastMessage request) {
        MessageResponseLastMessage response = new MessageResponseLastMessage(request);
        response.setInfo(this.messageAccess.getLastMessageEntry(request.getMessageId()));
        //sync answer
        session.write(response);
    }

    /**
     * sync
     */
    private void processCEMSendRequest(IoSession session, CEMSendRequest request) {
        CEMSendResponse response = new CEMSendResponse(request);
        Partner initiator = request.getInitiator();
        KeystoreCertificate certificate = request.getCertificate();
        Date activationDate = request.getActivationDate();
        //set time to 0:01 of this day
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(activationDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 1);
        calendar.set(Calendar.SECOND, 0);
        CEMInitiator cemInitiator = new CEMInitiator(this.configConnection,
                this.runtimeConnection, this.certificateManagerEncSign);
        try {
            List<Partner> informedPartnerList = cemInitiator.sendRequests(initiator,
                    certificate, true, true, false, calendar.getTime());
            response.setInformedPartner(informedPartnerList);
        } catch (Throwable e) {
            response.setException(e);
        }
        //sync answer
        session.write(response);
    }

    /**
     * Copies all data from one stream to another
     */
    private void copyStreams(InputStream in, OutputStream out) throws IOException {
        BufferedInputStream inStream = new BufferedInputStream(in);
        BufferedOutputStream outStream = new BufferedOutputStream(out);
        //copy the contents to an output stream
        byte[] buffer = new byte[2048];
        int read = 0;
        //a read of 0 must be allowed, sometimes it takes time to
        //extract data from the input
        while (read != -1) {
            read = inStream.read(buffer);
            if (read > 0) {
                outStream.write(buffer, 0, read);
            }
        }
        outStream.flush();
    }

    /**
     * Returns some server info values
     */
    private void processServerInfoRequest(IoSession session, ServerInfoRequest infoRequest) {
        ServerInfoResponse response = new ServerInfoResponse(infoRequest);
        response.setProperty(ServerInfoRequest.SERVER_START_TIME, String.valueOf(this.startupTime));
        response.setProperty(ServerInfoRequest.SERVER_PRODUCT_NAME, AS2ServerVersion.getProductName());
        response.setProperty(ServerInfoRequest.SERVER_VERSION, AS2ServerVersion.getVersion());
        response.setProperty(ServerInfoRequest.SERVER_BUILD, AS2ServerVersion.getBuild());
        session.write(response);
    }

    private void processIncomingMessageRequest(IoSession session, IncomingMessageRequest incomingMessageRequest) {
        IncomingMessageResponse incomingMessageResponse = new IncomingMessageResponse(incomingMessageRequest);
        try {
            try {
                //inc the sent data size, this is for sync error MDN
                long size = 0;
                if (incomingMessageRequest.getHeader() != null) {
                    size += this.computeRawHeaderSize(incomingMessageRequest.getHeader());
                }
                if (incomingMessageRequest.getMessageDataFilename() != null) {
                    size += new File(incomingMessageRequest.getMessageDataFilename()).length();
                }
                //MBean counter for received data size
                AS2Server.incRawReceivedData(size);
                incomingMessageResponse = this.newMessageArrived(incomingMessageRequest);
            } catch (AS2Exception as2Exception) {
                String foundSenderId = as2Exception.getAS2Message().getAS2Info().getSenderId();
                String foundReceiverId = as2Exception.getAS2Message().getAS2Info().getReceiverId();
                Partner as2MessageReceiver = this.partnerAccess.getPartner(foundReceiverId);
                AS2MDNCreation mdnCreation = new AS2MDNCreation(this.certificateManagerEncSign);
                mdnCreation.setLogger(this.logger);
                //partner might be null - thats ok
                Partner foundSender = this.partnerAccess.getPartner(foundSenderId);
                AS2Message mdn = mdnCreation.createMDNError(as2Exception, foundSender, foundSenderId, as2MessageReceiver, foundReceiverId);
                AS2MDNInfo mdnInfo = (AS2MDNInfo) mdn.getAS2Info();
                AS2MessageInfo messageInfo = (AS2MessageInfo) as2Exception.getAS2Message().getAS2Info();
                if (messageInfo.requestsSyncMDN()) {
                    //sync error MDN
                    incomingMessageResponse.setContentType(mdn.getContentType());
                    incomingMessageResponse.setMDNData(mdn.getRawData());
                    //build up the header for the sync response
                    Properties header = mdnCreation.buildHeaderForSyncMDN(mdn);
                    incomingMessageResponse.setHeader(header);
                    //MBean counter: inc the sent data size, this is for sync error MDN
                    AS2Server.incRawSentData(this.computeRawHeaderSize(header) + mdn.getRawDataSize());
                    Partner mdnReceiver = partnerAccess.getPartner(mdnInfo.getReceiverId());
                    Partner mdnSender = partnerAccess.getPartner(mdnInfo.getSenderId());
                    this.messageStoreHandler.storeSentMessage(mdn, mdnSender, mdnReceiver, header);
                    this.mdnAccess.initializeOrUpdateMDN(mdnInfo);
                    this.logger.log(Level.INFO,
                            this.rb.getResourceString("sync.mdn.sent",
                                    new Object[]{
                                        mdnInfo.getMessageId(),
                                        mdnInfo.getRelatedMessageId()
                                    }), mdnInfo);
                    this.messageAccess.setMessageState(mdnInfo.getRelatedMessageId(), AS2Message.STATE_STOPPED);
                    this.clientserver.broadcastToClients(new RefreshClientMessageOverviewList());
                    session.write(incomingMessageResponse);
                    return;
                } else {
                    //async error MDN
                    Partner messageReceiver = this.partnerAccess.getPartner(mdnInfo.getReceiverId());
                    Partner messageSender = this.partnerAccess.getPartner(mdnInfo.getSenderId());
                    //async back to sender. There are ALWAYS required partners for the send order even if the as2 ids 
                    //are not founnd because the partners are required for the async MDN receipt URL and a well structured MDN
                    if (messageReceiver == null) {
                        messageReceiver = new Partner();
                        messageReceiver.setAS2Identification(mdnInfo.getReceiverId());
                        messageReceiver.setMdnURL(messageInfo.getAsyncMDNURL());
                    }
                    if (messageSender == null) {
                        messageSender = new Partner();
                        messageSender.setAS2Identification(mdnInfo.getSenderId());
                    }
                    this.addSendOrder(mdn, messageReceiver, messageSender);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
            StringBuilder message = new StringBuilder("AS2ServerProcessing: [" + e.getClass().getName() + "] " + e.getMessage());
            if (e.getCause() != null) {
                message.append(" - caused by [" + e.getCause().getClass().getName() + "] " + e.getCause().getMessage());
            }
            this.logger.severe(message.toString());
            Notification.systemFailure(this.configConnection, this.runtimeConnection, e);
        }
        session.write(incomingMessageResponse);
    }

    /**
     * Compute the header upload size for the jmx interface
     */
    private long computeRawHeaderSize(Properties header) {
        long size = 0;
        Enumeration enumeration = header.propertyNames();
        while (enumeration.hasMoreElements()) {
            String key = (String) enumeration.nextElement();
            //key + "="
            size += key.length() + 1;
            //value + LF
            size += header.getProperty(key).length();
        }
        return (size);
    }

    /**
     * Adds a message send order to the queue, this could also include an MDN
     *
     */
    private void addSendOrder(AS2Message message, Partner receiver, Partner sender) throws Exception {
        SendOrder order = new SendOrder();
        order.setReceiver(receiver);
        order.setMessage(message);
        order.setSender(sender);
        SendOrderSender orderSender = new SendOrderSender(this.configConnection, this.runtimeConnection);
        orderSender.send(order);
        this.clientserver.broadcastToClients(new RefreshClientMessageOverviewList());
    }

    /**
     * A communicatoin connection indicates that a new message arrived
     */
    private IncomingMessageResponse newMessageArrived(IncomingMessageRequest requestObject) throws Throwable {
        IncomingMessageResponse responseObject = new IncomingMessageResponse(requestObject);
        //is this an AS2 request? It should have a as2-to and as2-from header
        if (requestObject.getHeader().getProperty("as2-to") == null) {
            this.logger.log(Level.SEVERE, this.rb.getResourceString("invalid.request.to"));
            responseObject.setMDNData(null);
            responseObject.setHttpReturnCode(HttpServletResponse.SC_BAD_REQUEST);
            return (responseObject);
        }
        if (requestObject.getHeader().getProperty("as2-from") == null) {
            this.logger.log(Level.SEVERE, this.rb.getResourceString("invalid.request.from"));
            responseObject.setMDNData(null);
            responseObject.setHttpReturnCode(HttpServletResponse.SC_BAD_REQUEST);
            return (responseObject);
        }
        AS2MessageParser parser = new AS2MessageParser();
        parser.setCertificateManager(this.certificateManagerEncSign, this.certificateManagerEncSign);
        parser.setDBConnection(this.configConnection, this.runtimeConnection);
        parser.setLogger(this.logger);
        byte[] incomingMessageData = this.readFile(requestObject.getMessageDataFilename());
        //store raw incoming message. If the message partners are identified successfully
        //the raw data is also written to the partner dir/raw
        String[] rawFiles = this.messageStoreHandler.storeRawIncomingData(
                incomingMessageData, requestObject.getHeader(),
                requestObject.getRemoteHost());
        String rawIncomingFile = rawFiles[0];
        String rawIncomingFileHeader = rawFiles[1];
        AS2Message message = null;
        try {
            //this will throw an exception if any of the partners are unknown or the local station
            //is not the receiver or the content MIC does not match or the signature does not match. 
            //Anyway every message should be logged
            message = parser.createMessageFromRequest(incomingMessageData,
                    requestObject.getHeader(), requestObject.getContentType());
            message.getAS2Info().setRawFilename(rawIncomingFile);
            message.getAS2Info().setHeaderFilename(rawIncomingFileHeader);
            message.getAS2Info().setSenderHost(requestObject.getRemoteHost());
            message.getAS2Info().setDirection(AS2MessageInfo.DIRECTION_IN);
            //found a message without message id: stop processing
            if (!message.isMDN() && message.getAS2Info().getMessageId() == null) {
                this.logger.log(Level.SEVERE, this.rb.getResourceString("invalid.request.messageid"));
                responseObject.setMDNData(null);
                responseObject.setHttpReturnCode(HttpServletResponse.SC_BAD_REQUEST);
                return (responseObject);
            }
            //its a CEM: check data integrity before returning an MDN
            if (!message.isMDN()) {
                AS2MessageInfo messageInfo = (AS2MessageInfo) message.getAS2Info();
                if (requestObject.getHeader().getProperty("disposition-notification-options") != null) {
                    messageInfo.setDispositionNotificationOptions(
                            new DispositionNotificationOptions(requestObject.getHeader().getProperty("disposition-notification-options")));
                } else {
                    messageInfo.setDispositionNotificationOptions(new DispositionNotificationOptions(""));
                }
                if (messageInfo.getMessageType() == AS2Message.MESSAGETYPE_CEM) {
                    CEMReceiptController cemReceipt = new CEMReceiptController(
                            this.clientserver, this.configConnection, this.runtimeConnection,
                            this.certificateManagerEncSign);
                    cemReceipt.checkInboundCEM(message);
                }
                this.messageAccess.initializeOrUpdateMessage(messageInfo);
            } else {
                this.mdnAccess.initializeOrUpdateMDN((AS2MDNInfo) message.getAS2Info());
            }
            //inbound message was an sync or async MDN
            if (message.isMDN()) {
                AS2MDNInfo mdnInfo = (AS2MDNInfo) message.getAS2Info();
                this.messageAccess.setMessageState(mdnInfo.getRelatedMessageId(),
                        mdnInfo.getState());
                //ASYNC/SYNC MDN received: insert an entry into the statistic table that a message has been sent
                QuotaAccessDB.incSentMessages(this.configConnection, this.runtimeConnection,
                        mdnInfo.getReceiverId(),
                        mdnInfo.getSenderId(), mdnInfo.getState(), mdnInfo.getRelatedMessageId());
            }
            this.updatePartnerSystemInfo(requestObject.getHeader());
            this.clientserver.broadcastToClients(new RefreshClientMessageOverviewList());
        } catch (AS2Exception e) {
            //exec on MDN send makes no sense here because no valid filename exists
            AS2Info as2Info = e.getAS2Message().getAS2Info();
            as2Info.setRawFilename(rawIncomingFile);
            as2Info.setHeaderFilename(rawIncomingFileHeader);
            as2Info.setState(AS2Message.STATE_STOPPED);
            as2Info.setDirection(AS2MessageInfo.DIRECTION_IN);
            as2Info.setSenderHost(requestObject.getRemoteHost());
            if (!as2Info.isMDN()) {
                AS2MessageInfo as2MessageInfo = (AS2MessageInfo) as2Info;
                //always ensure the disposition-notification-options are set for the message else the indicator if the
                //answer should be signed or not is missing
                if (requestObject.getHeader().getProperty("disposition-notification-options") != null) {
                    as2MessageInfo.setDispositionNotificationOptions(
                            new DispositionNotificationOptions(requestObject.getHeader().getProperty("disposition-notification-options")));
                } else {
                    as2MessageInfo.setDispositionNotificationOptions(new DispositionNotificationOptions(""));
                }
                if (as2MessageInfo.getSenderId() != null && as2MessageInfo.getReceiverId() != null) {
                    //this has to be performed because of the notification                    
                    this.messageAccess.initializeOrUpdateMessage(as2MessageInfo);
                    this.messageAccess.setMessageState(as2MessageInfo.getMessageId(), AS2Message.STATE_STOPPED);
                    if (((AS2MessageInfo) as2Info).requestsSyncMDN()) {
                        //SYNC MDN received with error: insert an entry into the statistic table that a message has been sent
                        QuotaAccessDB.incReceivedMessages(this.configConnection,
                                this.runtimeConnection,
                                as2Info.getReceiverId(),
                                as2Info.getSenderId(),
                                as2Info.getState(),
                                as2Info.getMessageId());
                    }
                }
                throw e;
            } else {
                AS2MDNInfo mdnInfo = (AS2MDNInfo) as2Info;
                //if its a MDN set the state of the whole transaction
                AS2MessageInfo relatedMessageInfo = this.messageAccess.getLastMessageEntry(mdnInfo.getRelatedMessageId());
                if (relatedMessageInfo != null) {
                    relatedMessageInfo.setState(AS2Message.STATE_STOPPED);
                    mdnInfo.setState(AS2Message.STATE_STOPPED);
                    this.mdnAccess.initializeOrUpdateMDN(mdnInfo);
                    this.messageAccess.setMessageState(mdnInfo.getRelatedMessageId(), AS2Message.STATE_STOPPED);
                    //content MIC does not match or simular error
                    ExecuteShellCommand executeCommand = new ExecuteShellCommand(this.configConnection, this.runtimeConnection);
                    //execute on sync MDN processing error
                    executeCommand.executeShellCommandOnSend(relatedMessageInfo, mdnInfo);
                    //write status file                    
                    MessageStoreHandler handler = new MessageStoreHandler(this.configConnection, this.runtimeConnection);
                    handler.writeOutboundStatusFile(relatedMessageInfo);
                    this.logger.log(Level.SEVERE, e.getMessage(), as2Info);
                }
            }
            this.clientserver.broadcastToClients(new RefreshClientMessageOverviewList());
            //dont't thow an exception here if this is an MDN already, a thrown Exception
            //will result in another MDN!
            if (as2Info.isMDN()) {
                //its a MDN
                AS2MDNInfo mdnInfo = (AS2MDNInfo) as2Info;
                //there is no related message because the original message id of the received MDN does not reference a message?
                AS2MessageInfo originalMessageInfo = this.messageAccess.getLastMessageEntry(mdnInfo.getRelatedMessageId());
                if (originalMessageInfo == null) {
                    //as the related message could not be computed it is helpful to display the location of the inbound files
                    //for further investigation
                    StringBuilder errorMessage = new StringBuilder(e.getMessage());
                    errorMessage.append("\n");
                    errorMessage.append(this.rb.getResourceString("info.mdn.inboundfiles",
                            new Object[]{rawIncomingFile, rawIncomingFileHeader}));
                    this.logger.log(Level.SEVERE, errorMessage.toString());
                } else {
                }
                //an exception occured in processing an inbound MDN, signal back an error to the sender by HTTP code.
                // This will only work for ASYNC MDN because there is a logical problem in sync MDN processing:
                //If a sync mdn could not processed it is impossible to signal this back -> sender and receiver
                //will have different states of processing. Another reason to use ASYNC MDN instead of SYNC MDN
                responseObject.setHttpReturnCode(HttpServletResponse.SC_BAD_REQUEST);
                return (responseObject);
            }
        }
        AS2Info as2Info = message.getAS2Info();
        PartnerAccessDB access = new PartnerAccessDB(this.configConnection, this.runtimeConnection);
        Partner messageSender = access.getPartner(as2Info.getSenderId());
        Partner messageReceiver = access.getPartner(as2Info.getReceiverId());
        this.messageStoreHandler.storeParsedIncomingMessage(message, messageReceiver);
        if (!as2Info.isMDN()) {
            this.messageAccess.updateFilenames((AS2MessageInfo) as2Info);
            this.clientserver.broadcastToClients(new RefreshClientMessageOverviewList());
        }
        //process MDN
        if (message.isMDN()) {
            AS2MDNInfo mdnInfo = (AS2MDNInfo) message.getAS2Info();
            AS2MessageInfo originalMessageInfo = this.messageAccess.getLastMessageEntry(mdnInfo.getRelatedMessageId());
            ExecuteShellCommand executeCommand = new ExecuteShellCommand(this.configConnection, this.runtimeConnection);
            executeCommand.executeShellCommandOnSend(originalMessageInfo, mdnInfo);
            //write status file
            MessageStoreHandler handler = new MessageStoreHandler(this.configConnection, this.runtimeConnection);
            handler.writeOutboundStatusFile(originalMessageInfo);
        }
        //don't answer on signals or store them
        if (!as2Info.isMDN()) {
            AS2MessageInfo messageInfo = (AS2MessageInfo) message.getAS2Info();
            Partner mdnSender = messageReceiver;
            Partner mdnReceiver = messageSender;
            AS2MDNCreation mdnCreation = new AS2MDNCreation(this.certificateManagerEncSign);
            mdnCreation.setLogger(this.logger);
            //create the MDN that the message has been received; state "processed"
            AS2Message mdn = mdnCreation.createMDNProcessed(messageInfo, mdnSender, mdnReceiver);
            AS2MessageInfo as2RelatedMessageInfo = this.messageAccess.getLastMessageEntry(((AS2MDNInfo) mdn.getAS2Info()).getRelatedMessageId());
            if (messageInfo.requestsSyncMDN()) {
                responseObject.setContentType(mdn.getContentType());
                responseObject.setMDNData(mdn.getRawData());
                //build up the header for the sync response
                Properties header = mdnCreation.buildHeaderForSyncMDN(mdn);
                responseObject.setHeader(header);
                this.messageStoreHandler.storeSentMessage(mdn, mdnSender, mdnReceiver, header);
                this.mdnAccess.initializeOrUpdateMDN((AS2MDNInfo) mdn.getAS2Info());
                //MBean counter: inc the sent data size, this is for sync success MDN
                AS2Server.incRawSentData(this.computeRawHeaderSize(header) + mdn.getRawDataSize());
                this.logger.log(Level.INFO,
                        this.rb.getResourceString("sync.mdn.sent",
                                new Object[]{
                                    mdn.getAS2Info().getMessageId(),
                                    ((AS2MDNInfo) mdn.getAS2Info()).getRelatedMessageId()
                                }), mdn.getAS2Info());
                //SYNC MDN sent with state "processed": insert an entry into the statistic table that a message has been received
                QuotaAccessDB.incReceivedMessages(this.configConnection, this.runtimeConnection, messageReceiver,
                        messageSender,
                        mdn.getAS2Info().getState(),
                        ((AS2MDNInfo) mdn.getAS2Info()).getRelatedMessageId());
                //on sync MDN the command object is sent back to the servlet, store the payload already as good here
                if (mdn.getAS2Info().getState() == AS2Message.STATE_FINISHED) {
                    this.messageStoreHandler.movePayloadToInbox(messageInfo.getMessageType(),
                            ((AS2MDNInfo) mdn.getAS2Info()).getRelatedMessageId(),
                            messageReceiver, messageSender);
                    //dont execute the command after receipt for CEM
                    if (as2RelatedMessageInfo.getMessageType() == AS2Message.MESSAGETYPE_CEM) {
                        CEMReceiptController cemReceipt = new CEMReceiptController(this.clientserver,
                                this.configConnection, this.runtimeConnection, this.certificateManagerEncSign);
                        cemReceipt.processInboundCEM(as2RelatedMessageInfo);
                    } else {
                        ExecuteShellCommand executeCommand = new ExecuteShellCommand(this.configConnection, this.runtimeConnection);
                        executeCommand.executeShellCommandOnReceipt(messageSender, messageReceiver, as2RelatedMessageInfo);
                    }
                }
                this.messageAccess.setMessageState(((AS2MDNInfo) mdn.getAS2Info()).getRelatedMessageId(), mdn.getAS2Info().getState());
                this.clientserver.broadcastToClients(new RefreshClientMessageOverviewList());
            } else {
                //async MDN requested, dont send MDN in this case
                //process the CEM request if it requires async MDN
                if (as2RelatedMessageInfo.getMessageType() == AS2Message.MESSAGETYPE_CEM) {
                    CEMReceiptController cemReceipt = new CEMReceiptController(this.clientserver,
                            this.configConnection, this.runtimeConnection, this.certificateManagerEncSign);
                    cemReceipt.processInboundCEM(as2RelatedMessageInfo);
                }
                responseObject.setMDNData(null);
                //async back to sender
                this.addSendOrder(mdn, messageSender, messageReceiver);
            }
        }
        return (responseObject);
    }

    /**
     * Reads a file from the disk and returns its content as byte array
     */
    private byte[] readFile(String filename) throws Exception {
        FileInputStream inStream = null;
        ByteArrayOutputStream outStream = null;
        try {
            inStream = new FileInputStream(filename);
            outStream = new ByteArrayOutputStream();
            this.copyStreams(inStream, outStream);
        } finally {
            if (inStream != null) {
                inStream.close();
            }
            if (outStream != null) {
                outStream.flush();
                outStream.close();
            }
        }
        if (outStream != null) {
            return (outStream.toByteArray());
        } else {
            return (null);
        }
    }

    /**
     * Updates the system information for a partner
     */
    private void updatePartnerSystemInfo(Properties header) {
        try {
            PartnerAccessDB access = new PartnerAccessDB(this.configConnection, this.runtimeConnection);
            Partner messageSender = access.getPartner(AS2MessageParser.unescapeFromToHeader(header.getProperty("as2-from")));
            if (messageSender != null) {
                PartnerSystem partnerSystem = new PartnerSystem();
                partnerSystem.setPartner(messageSender);
                if (header.getProperty("server") != null) {
                    partnerSystem.setProductName(header.getProperty("server"));
                } else if (header.getProperty("user-agent") != null) {
                    partnerSystem.setProductName(header.getProperty("user-agent"));
                }
                String version = header.getProperty("as2-version");
                if (version != null) {
                    partnerSystem.setAs2Version(version);
                    partnerSystem.setCompression(!version.equals("1.0"));
                }
                String optionalProfiles = header.getProperty("ediint-features");
                if (optionalProfiles != null) {
                    partnerSystem.setMa(optionalProfiles.contains("multiple-attachments"));
                    partnerSystem.setCEM(optionalProfiles.contains("CEM"));
                }
                PartnerSystemAccessDB systemAccess = new PartnerSystemAccessDB(this.configConnection,
                        this.runtimeConnection);
                systemAccess.insertOrUpdatePartnerSystem(partnerSystem);
            }
        } //this feature is really NOT that important to stop an inbound message
        catch (Exception e) {
            this.logger.warning("updatePartnerSystemInfo: " + e);
        }
    }
}
