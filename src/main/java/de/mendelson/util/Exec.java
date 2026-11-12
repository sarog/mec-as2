//$Header: /as4/de/mendelson/util/Exec.java 17    8/10/25 13:48 Heller $
package de.mendelson.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Executes a native command
 *
 * @author S.Heller
 * @version $Revision: 17 $
 */
public class Exec {

    /**
     * Indicates if the exec should stop the calling thread to wait for a return
     */
    private boolean waitFor = false;

    /**
     * Creates new Exec
     */
    public Exec() {
    }

    /**
     * Indicates if the exec should stop the calling thread to wait for a return
     */
    public void setWaitFor(boolean waitFor) {
        this.waitFor = waitFor;
    }

    /**
     * Starts a native command and writes the output to the passed printstreams
     *
     * @param command command line to execute on the system
     * @param out PrintStream to write normal output to, System.out if parameter
     * is null
     * @param err PrintStream to write error to, System.err if parameter is null
     * @return Returnvalue of the call if waitfor is set, else 0
     */
    public int start(String command, PrintStream out, PrintStream err)
            throws IOException, InterruptedException {
        if (out == null) {
            out = System.out;
        }
        if (err == null) {
            err = System.err;
        }
        int returnValue = 0;
        ExecArgumentParser parser = new ExecArgumentParser();
        String[] arguments = parser.parse(command);
        Process process = Runtime.getRuntime().exec(arguments);
        //copy input and error to the output stream
        StreamPumper inputPumper = new StreamPumper(process.getInputStream(), out);
        StreamPumper errorPumper = new StreamPumper(process.getErrorStream(), err);
        //starts pumping away the generated output/error
        inputPumper.start();
        errorPumper.start();
        if (this.waitFor) {
            //Close the streams else the process will hang
            process.getOutputStream().close();
            returnValue = process.waitFor();
            //wait for pumpers to finish reading output
            inputPumper.join();
            errorPumper.join();
            process.destroy();
        }
        return (returnValue);
    }

    /**
     * Starts a native command and writes the output to stdout and stderr
     *
     * @param command command line to execute on the system
     */
    public int start(String command) throws IOException, InterruptedException {
        return (this.start(command, null, null));
    }

    /**
     * Thread that reads contiguously the output/input stream data from the
     * native thread and redirects it to a print stream
     */
    public static class StreamPumper extends Thread {

        private final BufferedReader reader;
        private final PrintStream outputStream;

        public StreamPumper(InputStream in, PrintStream outputStream) {
            this.reader = new BufferedReader(new InputStreamReader(in));
            this.outputStream = outputStream;
        }

        @Override
        public void run() {
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    outputStream.println(line);
                    outputStream.flush();
                }
            } catch (IOException e) {
            } finally {
                try {
                    reader.close();
                } catch (IOException ignored) {
                }
            }
        }
    }
}
