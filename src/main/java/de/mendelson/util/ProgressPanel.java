//$Header: /as2/de/mendelson/util/ProgressPanel.java 26    23/02/26 11:46 Heller $
package de.mendelson.util;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.BoundedRangeModel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Progress panel to display status information.
 *
 * @author S.Heller
 * @version $Revision: 26 $
 */
public class ProgressPanel extends JPanel {

    private final CopyOnWriteArrayList<ProgressRequest> progressList = new CopyOnWriteArrayList<ProgressRequest>();
    private final BoundedRangeModel progressModel;

    /**
     * Creates new form ProgressPanel
     */
    public ProgressPanel() {
        this.initComponents();
        this.progressModel = this.jProgressBar.getModel();
        this.jProgressBar.setVisible(false);
    }

    /**
     * There is no need to create a background thread for this - it is always
     * called in a background thread
     */
    private void disableProgressDisplay() {
        jProgressBar.setIndeterminate(false);
        progressModel.setRangeProperties(0, 0, 0, 0, false);
        jProgressBar.setStringPainted(false);
        jLabelProgressDetails.setText(null);
        jProgressBar.setVisible(false);
    }

    /**
     * Sets a new max value of a progress bar
     *
     * @param uniqueId The unique id of the progress bar
     * @param maxValue The new max value
     */
    public void setProgressMax(final String uniqueId, int maxValue) {
        for (ProgressRequest request : progressList) {
            if (request.uniqueId.equals(uniqueId)) {
                request.setMaxValue(maxValue);
                break;
            }
        }
    }

    public void startProgressIndeterminate(final String progressDetails, final String uniqueId) {
        SwingWorker<Void, ProgressRequest> worker = new SwingWorker<Void, ProgressRequest>() {
            @Override
            protected Void doInBackground() {
                ProgressRequest request = new ProgressRequest(progressDetails, uniqueId);
                request.setIndeterminate(true);
                progressList.add(request);
                // publish the request to the EDT for display
                publish(request);
                return null;
            }

            @Override
            protected void process(List<ProgressRequest> chunks) {
                for (ProgressRequest request : chunks) {
                    displayProgressBar(request);
                }
            }
        };
        worker.execute();
    }

    private void displayProgressBar(final ProgressRequest progressRequest) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //Check if request still exists
                boolean valid = false;
                for (ProgressRequest request : progressList) {
                    if (request.getUniqueId().equals(progressRequest.getUniqueId())) {
                        valid = true;
                        break;
                    }
                }
                if (!valid) {
                    return;
                }
                jLabelProgressDetails.setText(progressRequest.getDisplay());
                jProgressBar.setIndeterminate(progressRequest.isIndeterminate());
                if (progressRequest.isIndeterminate()) {
                    jProgressBar.setStringPainted(false);
                } else {
                    progressModel.setRangeProperties(
                            progressRequest.getActualValue(), 0,
                            progressRequest.getMinValue(),
                            progressRequest.getMaxValue(), false);
                }
                jProgressBar.setVisible(true);
            }
        });
    }

    /**
     * Adds a new progress to display to the progress bar. Its possible to add
     * several requests, just use unique ids for each request. The last request
     * is always displayed
     */
    public void startProgress(String display, String uniqueId, int min, int max) {
        SwingWorker<Void, ProgressRequest> worker = new SwingWorker<Void, ProgressRequest>() {
            @Override
            protected Void doInBackground() {
                ProgressRequest request = new ProgressRequest(display, uniqueId);
                request.setIndeterminate(false);
                request.setMinValue(min);
                request.setMaxValue(max);
                request.setActualValue(0);
                progressList.add(request);
                publish(request);
                return null;
            }

            @Override
            protected void process(List<ProgressRequest> request) {
                for (ProgressRequest singleRequest : request) {
                    displayProgressBar(singleRequest);
                }
            }
        };
        worker.execute();
    }

    /**
     * This is ignored if the unique id is not assigned to a progress request -
     * anyway always the last progress request is displayed
     */
    public void setProgressValue(final String uniqueId, final int progress) {
        SwingWorker<Void, ProgressRequest> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                ProgressRequest foundRequest = null;
                ProgressRequest actualDisplayedProgress = null;
                //Find the progress request to update
                for (ProgressRequest request : progressList) {
                    if (request.getUniqueId().equals(uniqueId)) {
                        foundRequest = request;
                        break;
                    }
                }
                if (!progressList.isEmpty()) {
                    actualDisplayedProgress = progressList.get(progressList.size() - 1);
                }
                if (foundRequest != null && !foundRequest.isIndeterminate()) {
                    foundRequest.setActualValue(progress);
                    if (foundRequest.equals(actualDisplayedProgress)) {
                        //Send to EDT for display
                        publish(foundRequest);
                    }
                }
                return null;
            }

            @Override
            protected void process(List<ProgressRequest> chunks) {
                for (ProgressRequest request : chunks) {
                    displayProgressBar(request);
                }
            }
        };
        worker.execute();
    }

    /**
     * Tries to stop a progress and does not care if it does not exist
     */
    public void stopProgressIfExists(String uniqueId) {
        SwingWorker<Void, ProgressRequest> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                ProgressRequest foundRequest = null;
                //find the progress request to delete
                for (ProgressRequest request : progressList) {
                    if (request.uniqueId.equals(uniqueId)) {
                        foundRequest = request;
                        break;
                    }
                }
                if (foundRequest != null) {
                    //now delete the found request
                    progressList.remove(foundRequest);
                    //no more progress entries?
                    if (progressList.isEmpty()) {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                disableProgressDisplay();
                            }
                        });
                    } else {
                        //get last entry and display its progress text
                        ProgressRequest progressToDisplay = progressList.get(progressList.size() - 1);
                        this.publish(progressToDisplay);
                    }
                }
                return (null);
            }

            @Override
            protected void process(List<ProgressRequest> progressList) {
                for (ProgressRequest request : progressList) {
                    displayProgressBar(request);
                }
            }
        };
        worker.execute();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the FormEditor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabelProgressDetails = new javax.swing.JLabel();
        jProgressBar = new javax.swing.JProgressBar();

        setPreferredSize(new java.awt.Dimension(100, 12));
        setLayout(new java.awt.GridBagLayout());

        jLabelProgressDetails.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelProgressDetails.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        add(jLabelProgressDetails, gridBagConstraints);

        jProgressBar.setBorderPainted(false);
        jProgressBar.setPreferredSize(new java.awt.Dimension(100, 12));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 0);
        add(jProgressBar, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabelProgressDetails;
    private javax.swing.JProgressBar jProgressBar;
    // End of variables declaration//GEN-END:variables

    /**
     * Stores information about a progress display request
     */
    private static class ProgressRequest {

        private final String display;
        private final String uniqueId;
        private boolean indeterminate = false;
        private int minValue = 0;
        private int maxValue = 0;
        private int actualValue = 0;

        public ProgressRequest(String display, String uniqueId) {
            this.display = display;
            this.uniqueId = uniqueId;
        }

        /**
         * @return the display
         */
        public String getDisplay() {
            return display;
        }

        /**
         * @return the uniqueId
         */
        public String getUniqueId() {
            return uniqueId;
        }

        /**
         * Overwrite the equal method of object, an object will be equal if the
         * key is equal !
         *
         * @param anObject object ot compare
         */
        @Override
        public boolean equals(Object anObject) {
            if (anObject == this) {
                return (true);
            }
            if (anObject != null && anObject instanceof ProgressRequest) {
                ProgressRequest object = (ProgressRequest) anObject;
                return (object.uniqueId.equals(this.uniqueId));
            }
            return (false);
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 29 * hash + (this.uniqueId != null ? this.uniqueId.hashCode() : 0);
            return hash;
        }

        /**
         * @return the minValue
         */
        public int getMinValue() {
            return minValue;
        }

        /**
         * @param minValue the minValue to set
         */
        public void setMinValue(int minValue) {
            this.minValue = minValue;
        }

        /**
         * @return the maxValue
         */
        public int getMaxValue() {
            return maxValue;
        }

        /**
         * @param maxValue the maxValue to set
         */
        public void setMaxValue(int maxValue) {
            this.maxValue = maxValue;
        }

        /**
         * @return the actualValue
         */
        public int getActualValue() {
            return actualValue;
        }

        /**
         * @param actualValue the actualValue to set
         */
        public void setActualValue(int actualValue) {
            this.actualValue = actualValue;
        }

        /**
         * @return the indeterminate
         */
        public boolean isIndeterminate() {
            return indeterminate;
        }

        /**
         * @param indeterminate the indeterminate to set
         */
        public void setIndeterminate(boolean indeterminate) {
            this.indeterminate = indeterminate;
        }
    }
}
