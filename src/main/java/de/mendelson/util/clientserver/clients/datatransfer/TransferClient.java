//$Header: /as2/de/mendelson/util/clientserver/clients/datatransfer/TransferClient.java 29    26/02/26 12:53 Heller $
package de.mendelson.util.clientserver.clients.datatransfer;

import de.mendelson.util.clientserver.SyncRequestTransportLevelException;
import de.mendelson.util.clientserver.BaseClient;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Requests downloads from and sends new uploads to the server
 *
 * @author S.Heller
 * @version $Revision: 29 $
 */
public class TransferClient {

    /**
     * Set a timeout of 30s for these requests
     */
    public static final long TIMEOUT = TimeUnit.SECONDS.toMillis(45);
    private final BaseClient baseClient;

    public static final int CHUNK_SIZE_IN_BYTES = 512000;

    public TransferClient(BaseClient baseClient) {
        this.baseClient = baseClient;
    }

    /**
     * Uploads data to the server
     *
     * @return the answer from the server - UploadResponseFile
     */
    public UploadResponseFile uploadWaitInfinite(UploadRequestFile request) throws Throwable {
        UploadResponseFile response = (UploadResponseFile) this.getBaseClient().sendSyncWaitInfinite(request);
        if (response.getException() != null) {
            throw response.getException();
        }
        return (response);
    }

    /**
     * Uploads data to the server
     *
     * @return the answer from the server - UploadResponseFile
     */
    public UploadResponseFile upload(UploadRequestFile request) throws Throwable {
        UploadResponseFile response = (UploadResponseFile) this.getBaseClient().sendSync(request, TIMEOUT);
        if (response == null) {
            throw new SyncRequestTransportLevelException();
        }
        if (response.getException() != null) {
            throw response.getException();
        }
        return (response);
    }

    /**
     * Sends the data of the inputstream synced to the server and returns a
     * unique number from the server for the upload process. Warning: This does
     * also transfer files with the size of 0 bytes to the server Please be
     * aware of this at the server side. The size of bytes to send is unknown
     */
    public String uploadChunked(InputStream inStream) throws Throwable {
        return this.uploadChunked(inStream, -1, null, null);
    }

    /**
     * Sends the data of the inputstream to the server.
     *
     * @param inStream The stream containing the data to upload
     * @param totalByteSize The real byte size of the data. If this value is -1
     * the total size is unknown and the system will try to chunk
     */
    public String uploadChunked(InputStream inStream, long totalByteSize) throws Throwable {
        return (this.uploadChunked(inStream, totalByteSize, null, null));
    }

    /**
     * Sends the data of the inputstream to the server.
     *
     * @param inStream The stream containing the data to upload
     * @param totalByteSize The real byte size of the data. If this value is -1
     * the total size is unknown and the system will try to chunk
     * @param progressConsumer Consumer that is informed for any progress of the
     * chunked upload, may be null
     * @param uniqueProgressId Progress id that is used for the
     * progressConsumer, may be null if the progessConsumer is not used
     *
     */
    public String uploadChunked(InputStream inStream, long totalByteSize,
            BiConsumer<String, Long> progressConsumer, String uniqueProgressId) throws Throwable {
        String targetHash = null;
        long processedBytes = 0;
        //If the size is known and fits into a single chunk: transfer the data in one single request
        if (totalByteSize >= 0 && totalByteSize <= CHUNK_SIZE_IN_BYTES) {
            byte[] data = inStream.readNBytes((int) totalByteSize);
            if (data != null) {
                UploadRequestChunk uploadRequest = new UploadRequestChunk();
                uploadRequest.setData(data);
                uploadRequest.setLastChunk(true);
                uploadRequest.setTargetHash(null);
                uploadRequest.setChunkNumber(0);
                UploadResponseChunk response
                        = (UploadResponseChunk) this.getBaseClient().sendSync(uploadRequest, TIMEOUT);
                if (response == null) {
                    throw new SyncRequestTransportLevelException();
                }
                if (response.getException() != null) {
                    throw response.getException();
                }
                //Inform the consumer
                if (progressConsumer != null && uniqueProgressId != null) {
                    progressConsumer.accept(uniqueProgressId, (long) data.length);
                }                
                targetHash = response.getTargetHash();
            }
        } else {
            //Chunking mechanism for larger files or unknown file size (-1)
            int chunkCounter = 0;
            while (true) {
                int sizeToRead;
                if (totalByteSize >= 0) {
                    //If the total size is known calculate the size of the next chunk
                    long remainingBytes = totalByteSize - processedBytes;
                    if (remainingBytes <= 0) {
                        break;
                    }
                    sizeToRead = (int) Math.min(remainingBytes, CHUNK_SIZE_IN_BYTES);
                } else {
                    //Total size is not given: always request a full chunk
                    sizeToRead = CHUNK_SIZE_IN_BYTES;
                }
                byte[] data = inStream.readNBytes(sizeToRead);
                if (data != null && data.length > 0) {
                    boolean isLastChunk = false;
                    if (totalByteSize >= 0) {
                        isLastChunk = (processedBytes + data.length >= totalByteSize);
                    } else {
                        isLastChunk = (data.length < sizeToRead);
                    }
                    UploadRequestChunk uploadRequest = new UploadRequestChunk();
                    uploadRequest.setData(data);
                    uploadRequest.setChunkNumber(chunkCounter);
                    uploadRequest.setLastChunk(isLastChunk);
                    uploadRequest.setTargetHash(targetHash);
                    UploadResponseChunk response
                            = (UploadResponseChunk) this.getBaseClient().sendSync(uploadRequest, TIMEOUT);
                    if (response == null) {
                        throw new SyncRequestTransportLevelException();
                    }
                    if (response.getException() != null) {
                        throw response.getException();
                    }
                    targetHash = response.getTargetHash();
                    processedBytes += data.length;
                    chunkCounter++;
                    // Inform the consumer after each successful chunk
                    if (progressConsumer != null && uniqueProgressId != null) {
                        progressConsumer.accept(uniqueProgressId, processedBytes);
                    }
                    // Exit if the transferred file has the size 0 or the end of the stream/file is reached
                    if (data.length == 0 || (totalByteSize >= 0 && processedBytes >= totalByteSize)) {
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        if (targetHash == null) {
            throw new Exception("Internal problem: Server returns a NULL hash as answer to  UploadRequestChunk");
        }
        return targetHash;
    }

    public DownloadResponse download(DownloadRequest request) throws Throwable {
        DownloadResponse response = (DownloadResponse) this.getBaseClient().sendSync(request, TIMEOUT);
        if (response == null) {
            throw new SyncRequestTransportLevelException();
        }
        if (response.getException() != null) {
            throw response.getException();
        }
        return (response);
    }

    /**
     * @return the baseClient
     */
    public BaseClient getBaseClient() {
        return baseClient;
    }

    /**
     * Downloads the given file from the server and returns it as byte array
     *
     * @param filenameOnServerside
     * @param progressConsumer could be null if there is nobody listening to the
     * progress
     * @param uniqueProgressId Progress id of the progress listener
     * @return
     */
    public byte[] downloadChunked(String filenameOnServerside,
            BiConsumer<String, Long[]> progressConsumer, String uniqueProgressId) throws Throwable {
        long offset = 0;
        long fullsize = -1;
        try (ByteArrayOutputStream outStream = new ByteArrayOutputStream()) {
            while (offset < fullsize || fullsize == -1) {
                DownloadRequestFileChunk request = new DownloadRequestFileChunk();
                request.setOffset(offset);
                request.setFilename(filenameOnServerside);
                DownloadResponseFileChunk response
                        = (DownloadResponseFileChunk) this.getBaseClient().sendSync(request, TIMEOUT);
                if (response.getException() != null) {
                    throw (response.getException());
                }
                fullsize = response.getSize();
                byte[] chunkData = response.getData();
                outStream.write(response.getData());
                offset += chunkData.length;
                //Inform the consumer
                if (progressConsumer != null && uniqueProgressId != null) {
                    progressConsumer.accept(uniqueProgressId,
                            new Long[]{offset, fullsize});
                }
            }
            return (outStream.toByteArray());
        }
    }

}
