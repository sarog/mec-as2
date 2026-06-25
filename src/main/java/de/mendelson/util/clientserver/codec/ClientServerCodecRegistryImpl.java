//$Header: /mec_as2/de/mendelson/util/clientserver/codec/ClientServerCodecRegistryImpl.java 2     15/04/26 9:57 Heller $
package de.mendelson.util.clientserver.codec;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
* This software is subject to the license agreement set forth in the license.
* Please read and agree to all terms before using this software.
* Other product and brand names are trademarks of their respective owners.
*/
/**
* Registry that stores the used keys and links them to the allowed class. This
* is also a whitelist for the decoding
*
* @author S.Heller
* @version $Revision: 2 $
*/
public final class ClientServerCodecRegistryImpl extends ClientServerCodecRegistry{

    private static ClientServerCodecRegistryImpl instance;
    
    private ClientServerCodecRegistryImpl() {
        super();
        super.register( 1, de.mendelson.util.clientserver.messages.LoginRequest.class );
        super.register( 2, de.mendelson.util.clientserver.messages.LoginRequired.class );
        super.register( 3, de.mendelson.util.clientserver.messages.LoginState.class );
        super.register( 4, de.mendelson.util.clientserver.messages.ServerInfo.class );
        super.register( 5, de.mendelson.util.clientserver.about.ServerInfoRequest.class );
        super.register( 848091651, de.mendelson.comm.as2.api.message.CommandRequest.class);
        super.register( 1030166813, de.mendelson.comm.as2.api.message.CommandResponse.class);
        super.register( 780546106, de.mendelson.comm.as2.cem.clientserver.CEMCancelRequest.class);
        super.register( 1308663317, de.mendelson.comm.as2.cem.clientserver.CEMDeleteRequest.class);
        super.register( 1252428638, de.mendelson.comm.as2.cem.clientserver.CEMListRequest.class);
        super.register( 2122235337, de.mendelson.comm.as2.cem.clientserver.CEMListResponse.class);
        super.register( 375940765, de.mendelson.comm.as2.cem.clientserver.CEMSendRequest.class);
        super.register( 959039521, de.mendelson.comm.as2.cem.clientserver.CEMSendResponse.class);
        super.register( 22415812, de.mendelson.comm.as2.client.manualsend.ManualSendRequest.class);
        super.register( 876358179, de.mendelson.comm.as2.client.manualsend.ManualSendResponse.class);
        super.register( 1716496649, de.mendelson.comm.as2.clientserver.message.ConfigurationCheckRequest.class);
        super.register( 1642019562, de.mendelson.comm.as2.clientserver.message.ConfigurationCheckResponse.class);
        super.register( 1371159580, de.mendelson.comm.as2.clientserver.message.DeleteMessageRequest.class);
        super.register( 1534250792, de.mendelson.comm.as2.clientserver.message.ExternalLogRequest.class);
        super.register( 251297953, de.mendelson.comm.as2.clientserver.message.IncomingMessageRequest.class);
        super.register( 1127348165, de.mendelson.comm.as2.clientserver.message.IncomingMessageResponse.class);
        super.register( 202345524, de.mendelson.comm.as2.clientserver.message.PartnerConfigurationChanged.class);
        super.register( 1345146295, de.mendelson.comm.as2.clientserver.message.PerformNotificationTestRequest.class);
        super.register( 225120092, de.mendelson.comm.as2.clientserver.message.RefreshClientCEMDisplay.class);
        super.register( 651195348, de.mendelson.comm.as2.clientserver.message.RefreshClientMessageOverviewList.class);
        super.register( 87047389, de.mendelson.comm.as2.clientserver.message.RefreshTablePartnerData.class);
        super.register( 940355340, de.mendelson.comm.as2.clientserver.message.ServerShutdown.class);
        super.register( 514308082, de.mendelson.comm.as2.database.migration.clientserver.HSQLDBMigrationRequest.class);
        super.register( 1946781193, de.mendelson.comm.as2.database.migration.clientserver.HSQLDBMigrationResponse.class);
        super.register( 2045296691, de.mendelson.comm.as2.database.migration.clientserver.HSQLDBPartnerRequest.class);
        super.register( 1401449175, de.mendelson.comm.as2.database.migration.clientserver.HSQLDBPartnerResponse.class);
        super.register( 158405361, de.mendelson.comm.as2.message.clientserver.MessageDetailRequest.class);
        super.register( 353102809, de.mendelson.comm.as2.message.clientserver.MessageDetailResponse.class);
        super.register( 1961619451, de.mendelson.comm.as2.message.clientserver.MessageLogRequest.class);
        super.register( 1206547704, de.mendelson.comm.as2.message.clientserver.MessageLogResponse.class);
        super.register( 107012844, de.mendelson.comm.as2.message.clientserver.MessageOverviewRequest.class);
        super.register( 361836160, de.mendelson.comm.as2.message.clientserver.MessageOverviewResponse.class);
        super.register( 1942956581, de.mendelson.comm.as2.message.clientserver.MessagePayloadRequest.class);
        super.register( 1679568090, de.mendelson.comm.as2.message.clientserver.MessagePayloadResponse.class);
        super.register( 1563351506, de.mendelson.comm.as2.message.clientserver.MessageRequestLastMessage.class);
        super.register( 1711624938, de.mendelson.comm.as2.message.clientserver.MessageResponseLastMessage.class);
        super.register( 1052013424, de.mendelson.comm.as2.partner.clientserver.PartnerListRequest.class);
        super.register( 948742249, de.mendelson.comm.as2.partner.clientserver.PartnerListResponse.class);
        super.register( 1670586716, de.mendelson.comm.as2.partner.clientserver.PartnerModificationRequest.class);
        super.register( 229203525, de.mendelson.comm.as2.partner.clientserver.PartnerSystemRequest.class);
        super.register( 524870188, de.mendelson.comm.as2.partner.clientserver.PartnerSystemResponse.class);
        super.register( 1011250353, de.mendelson.comm.as2.partner.clientserver.SinglePartnerAddRequest.class);
        super.register( 1821096096, de.mendelson.comm.as2.partner.clientserver.SinglePartnerAddResponse.class);
        super.register( 1002789153, de.mendelson.comm.as2.partner.clientserver.SinglePartnerDeleteRequest.class);
        super.register( 2023731995, de.mendelson.comm.as2.partner.clientserver.SinglePartnerDeleteResponse.class);
        super.register( 106071572, de.mendelson.comm.as2.partner.clientserver.SinglePartnerModificationRequest.class);
        super.register( 1556666563, de.mendelson.comm.as2.partner.clientserver.SinglePartnerModificationResponse.class);
        super.register( 207559488, de.mendelson.comm.as2.statistic.clientserver.QuotaResetRequest.class);
        super.register( 1314294385, de.mendelson.comm.as2.statistic.clientserver.ServerInteroperabilityRequest.class);
        super.register( 342340136, de.mendelson.comm.as2.statistic.clientserver.ServerInteroperabilityResponse.class);
        super.register( 1272296140, de.mendelson.comm.as2.statistic.clientserver.StatisticDetailRequest.class);
        super.register( 1521863168, de.mendelson.comm.as2.statistic.clientserver.StatisticDetailResponse.class);
        super.register( 1282769659, de.mendelson.comm.as2.statistic.clientserver.StatisticOverviewRequest.class);
        super.register( 1648471339, de.mendelson.comm.as2.statistic.clientserver.StatisticOverviewResponse.class);
        super.register( 341178515, de.mendelson.comm.as2.statistic.StatisticExportRequest.class);
        super.register( 261526370, de.mendelson.comm.as2.statistic.StatisticExportResponse.class);
        super.register( 1522912604, de.mendelson.util.clientserver.about.ServerInfoResponse.class);
        super.register( 91235619, de.mendelson.util.clientserver.clients.datatransfer.DownloadRequest.class);
        super.register( 1166394072, de.mendelson.util.clientserver.clients.datatransfer.DownloadRequestFile.class);
        super.register( 303943606, de.mendelson.util.clientserver.clients.datatransfer.DownloadRequestFileChunk.class);
        super.register( 402899314, de.mendelson.util.clientserver.clients.datatransfer.DownloadRequestFileLimited.class);
        super.register( 530461847, de.mendelson.util.clientserver.clients.datatransfer.DownloadResponse.class);
        super.register( 204506735, de.mendelson.util.clientserver.clients.datatransfer.DownloadResponseFile.class);
        super.register( 1061180389, de.mendelson.util.clientserver.clients.datatransfer.DownloadResponseFileChunk.class);
        super.register( 662528982, de.mendelson.util.clientserver.clients.datatransfer.DownloadResponseFileLimited.class);
        super.register( 651953891, de.mendelson.util.clientserver.clients.datatransfer.UploadRequestChunk.class);
        super.register( 850193049, de.mendelson.util.clientserver.clients.datatransfer.UploadRequestFile.class);
        super.register( 1512791642, de.mendelson.util.clientserver.clients.datatransfer.UploadResponseChunk.class);
        super.register( 209486210, de.mendelson.util.clientserver.clients.datatransfer.UploadResponseFile.class);
        super.register( 469248060, de.mendelson.util.clientserver.clients.fileoperation.FileDeleteRequest.class);
        super.register( 154563183, de.mendelson.util.clientserver.clients.fileoperation.FileDeleteResponse.class);
        super.register( 432416910, de.mendelson.util.clientserver.clients.fileoperation.FileRenameRequest.class);
        super.register( 711697294, de.mendelson.util.clientserver.clients.fileoperation.FileRenameResponse.class);
        super.register( 1075026753, de.mendelson.util.clientserver.clients.filesystemview.FileSystemViewRequest.class);
        super.register( 928713591, de.mendelson.util.clientserver.clients.filesystemview.FileSystemViewResponse.class);
        super.register( 1292289840, de.mendelson.util.clientserver.clients.preferences.ConfigurationChangedOnServer.class);
        super.register( 286735635, de.mendelson.util.clientserver.clients.preferences.ConfigurationChangedOnServerNotification.class);
        super.register( 1676897849, de.mendelson.util.clientserver.clients.preferences.ConfigurationChangedOnServerPreferences.class);
        super.register( 725726041, de.mendelson.util.clientserver.clients.preferences.PreferencesRequest.class);
        super.register( 1358734061, de.mendelson.util.clientserver.clients.preferences.PreferencesResponse.class);
        super.register( 769692976, de.mendelson.util.clientserver.connectiontest.clientserver.ConnectionTestRequest.class);
        super.register( 247736815, de.mendelson.util.clientserver.connectiontest.clientserver.ConnectionTestResponse.class);
        super.register( 1836091051, de.mendelson.util.clientserver.log.search.ServerlogfileSearchRequest.class);
        super.register( 1930261473, de.mendelson.util.clientserver.log.search.ServerlogfileSearchResponse.class);
        super.register( 458100523, de.mendelson.util.clientserver.messages.ClientServerResponse.class);
        super.register( 2058294311, de.mendelson.util.clientserver.messages.ClientToServerLogRequest.class);
        super.register( 1519102697, de.mendelson.util.clientserver.messages.QuitRequest.class);
        super.register( 344344986, de.mendelson.util.clientserver.messages.ServerLogListMessage.class);
        super.register( 675058351, de.mendelson.util.clientserver.messages.ServerLogMessage.class);
        super.register( 1634131900, de.mendelson.util.clientserver.messages.ServerSideNotification.class);
        super.register( 90940800, de.mendelson.util.clientserver.ServerHelloMessage.class);
        super.register( 1626393028, de.mendelson.util.ha.clientserver.ServerInstanceHAListRequest.class);
        super.register( 1093662259, de.mendelson.util.ha.clientserver.ServerInstanceHAListResponse.class);
        super.register( 558957655, de.mendelson.util.httpconfig.clientserver.DisplayHTTPServerConfigurationRequest.class);
        super.register( 1510003209, de.mendelson.util.httpconfig.clientserver.DisplayHTTPServerConfigurationResponse.class);
        super.register( 1463119735, de.mendelson.util.mailautoconfig.clientserver.MailAutoConfigDetectRequest.class);
        super.register( 486305148, de.mendelson.util.mailautoconfig.clientserver.MailAutoConfigDetectResponse.class);
        super.register( 382375886, de.mendelson.util.modulelock.message.ModuleLockRequest.class);
        super.register( 1709361555, de.mendelson.util.modulelock.message.ModuleLockResponse.class);
        super.register( 1628845338, de.mendelson.util.security.cert.clientserver.CertificateExportRequest.class);
        super.register( 6364729, de.mendelson.util.security.cert.clientserver.CertificateExportResponse.class);
        super.register( 1205543703, de.mendelson.util.security.cert.clientserver.CRLVerificationRequest.class);
        super.register( 1095085337, de.mendelson.util.security.cert.clientserver.CRLVerificationResponse.class);
        super.register( 1637462770, de.mendelson.util.security.cert.clientserver.CRMFGenerationRequest.class);
        super.register( 1917590731, de.mendelson.util.security.cert.clientserver.CRMFGenerationResponse.class);
        super.register( 2031455463, de.mendelson.util.security.cert.clientserver.CSRAnswerImportRequest.class);
        super.register( 1467770131, de.mendelson.util.security.cert.clientserver.CSRAnswerImportResponse.class);
        super.register( 1345674315, de.mendelson.util.security.cert.clientserver.CSRGenerationRequest.class);
        super.register( 243413800, de.mendelson.util.security.cert.clientserver.CSRGenerationResponse.class);
        super.register( 1880019947, de.mendelson.util.security.cert.clientserver.DownloadRequestKeystore.class);
        super.register( 1481508904, de.mendelson.util.security.cert.clientserver.DownloadResponseKeystore.class);
        super.register( 1015424321, de.mendelson.util.security.cert.clientserver.ExportRequestKeystore.class);
        super.register( 40432597, de.mendelson.util.security.cert.clientserver.ExportRequestPrivateKey.class);
        super.register( 1683223202, de.mendelson.util.security.cert.clientserver.ExportResponseKeystore.class);
        super.register( 1414904576, de.mendelson.util.security.cert.clientserver.ExportResponsePrivateKey.class);
        super.register( 2010217259, de.mendelson.util.security.cert.clientserver.KeyCopyRequest.class);
        super.register( 98024208, de.mendelson.util.security.cert.clientserver.KeyCopyResponse.class);
        super.register( 1053026005, de.mendelson.util.security.cert.clientserver.RefreshKeystoreCertificates.class);
        super.register( 1233615672, de.mendelson.util.security.cert.clientserver.UploadRequestKeystore.class);
        super.register( 721230245, de.mendelson.util.security.cert.clientserver.UploadResponseKeystore.class);
        super.register( 1883004944, de.mendelson.util.systemevents.clientserver.SystemEventSearchRequest.class);
        super.register( 910718644, de.mendelson.util.systemevents.clientserver.SystemEventSearchResponse.class);
        super.register( 518612847, de.mendelson.util.systemevents.notification.clientserver.NotificationGetRequest.class);
        super.register( 130737721, de.mendelson.util.systemevents.notification.clientserver.NotificationGetResponse.class);
        super.register( 1663727716, de.mendelson.util.systemevents.notification.clientserver.NotificationSetMessage.class);
    }

     /**
     * Singleton for the whole application
     */
    public static synchronized ClientServerCodecRegistryImpl instance() {
        if (instance == null) {
            instance = new ClientServerCodecRegistryImpl();
        }
        return instance;
    }
    
}
