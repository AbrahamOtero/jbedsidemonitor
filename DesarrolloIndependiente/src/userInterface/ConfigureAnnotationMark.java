/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ShowInfoTimeSeries.java
 *
 * Created on 13-jun-2011, 9:35:27
 */
package userInterface;

import userInterface.*;
import signals.JSignalAdapter;

/**
 *
 * @author USUARIO
 */
public class ConfigureAnnotationMark extends javax.swing.JDialog {

    JSignalAdapter jSignalAdapter;

    /** Creates new form ShowInfoTimeSeries */
    public ConfigureAnnotationMark(java.awt.Frame parent, boolean modal, JSignalAdapter jSignalAdapter) {
        super(parent, modal);
        initComponents();
        this.jSignalAdapter = jSignalAdapter;
        showInfo();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroupAnnotations = new javax.swing.ButtonGroup();
        buttonGroupMarks = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jButtonAceptarCambios = new javax.swing.JButton();
        jButtonSalir = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jRadioButtonInstantMarks = new javax.swing.JRadioButton();
        jRadioButtonIntervalMarks = new javax.swing.JRadioButton();
        jTextFieldSizeMarks = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jRadioButtonInstantAnnotations = new javax.swing.JRadioButton();
        jRadioButtonIntervalAnnotations = new javax.swing.JRadioButton();
        jTextFieldSizeAnnotations = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Configure Annotation Mark");

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jButtonAceptarCambios.setText("Save Changes");
        jButtonAceptarCambios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAceptarCambiosActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonAceptarCambios, new java.awt.GridBagConstraints());

        jButtonSalir.setText("Exit");
        jButtonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalirActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 57;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 55, 0, 24);
        jPanel1.add(jButtonSalir, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel8.setText("Marks:");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        buttonGroupMarks.add(jRadioButtonInstantMarks);
        jRadioButtonInstantMarks.setText("InstantMarks");
        jRadioButtonInstantMarks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonInstantMarksActionPerformed(evt);
            }
        });

        buttonGroupMarks.add(jRadioButtonIntervalMarks);
        jRadioButtonIntervalMarks.setText("IntervalMarks");
        jRadioButtonIntervalMarks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonIntervalMarksActionPerformed(evt);
            }
        });

        jTextFieldSizeMarks.setText("jTextField1");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel11.setText("Size");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButtonInstantMarks)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jRadioButtonIntervalMarks)
                        .addGap(56, 56, 56)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldSizeMarks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(129, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioButtonInstantMarks)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButtonIntervalMarks)
                    .addComponent(jTextFieldSizeMarks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        buttonGroupAnnotations.add(jRadioButtonInstantAnnotations);
        jRadioButtonInstantAnnotations.setText("InstantAnnotations");
        jRadioButtonInstantAnnotations.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonInstantAnnotationsActionPerformed(evt);
            }
        });

        buttonGroupAnnotations.add(jRadioButtonIntervalAnnotations);
        jRadioButtonIntervalAnnotations.setText("IntervalAnnotations");
        jRadioButtonIntervalAnnotations.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonIntervalAnnotationsActionPerformed(evt);
            }
        });

        jTextFieldSizeAnnotations.setText("jTextField1");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel10.setText("Size");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jRadioButtonIntervalAnnotations)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldSizeAnnotations, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(132, 132, 132))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jRadioButtonInstantAnnotations)
                        .addContainerGap(241, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioButtonInstantAnnotations)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButtonIntervalAnnotations)
                    .addComponent(jTextFieldSizeAnnotations, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel9.setText("Annotations:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addComponent(jLabel9))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAceptarCambiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAceptarCambiosActionPerformed
        saveInfo();
        dispose();
}//GEN-LAST:event_jButtonAceptarCambiosActionPerformed

    private void jButtonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalirActionPerformed
        dispose();
    }//GEN-LAST:event_jButtonSalirActionPerformed

    private void jRadioButtonInstantAnnotationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonInstantAnnotationsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButtonInstantAnnotationsActionPerformed

    private void jRadioButtonInstantMarksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonInstantMarksActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButtonInstantMarksActionPerformed

    private void jRadioButtonIntervalAnnotationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonIntervalAnnotationsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButtonIntervalAnnotationsActionPerformed

    private void jRadioButtonIntervalMarksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonIntervalMarksActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButtonIntervalMarksActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {


                ConfigureAnnotationMark dialog = new ConfigureAnnotationMark(new javax.swing.JFrame(), true, new JSignalAdapter());
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupAnnotations;
    private javax.swing.ButtonGroup buttonGroupMarks;
    private javax.swing.JButton jButtonAceptarCambios;
    private javax.swing.JButton jButtonSalir;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jRadioButtonInstantAnnotations;
    private javax.swing.JRadioButton jRadioButtonInstantMarks;
    private javax.swing.JRadioButton jRadioButtonIntervalAnnotations;
    private javax.swing.JRadioButton jRadioButtonIntervalMarks;
    private javax.swing.JTextField jTextFieldSizeAnnotations;
    private javax.swing.JTextField jTextFieldSizeMarks;
    // End of variables declaration//GEN-END:variables

    private void showInfo() {
        if (jSignalAdapter.isAnnotationInterval()) {
            jRadioButtonIntervalAnnotations.setSelected(true);
            jRadioButtonInstantAnnotations.setSelected(false);
        } else {
            jRadioButtonIntervalAnnotations.setSelected(false);
            jRadioButtonInstantAnnotations.setSelected(true);

        }
        if (jSignalAdapter.isMarkInterval()) {
            jRadioButtonIntervalMarks.setSelected(true);
            jRadioButtonInstantMarks.setSelected(false);
        } else {
            jRadioButtonIntervalMarks.setSelected(false);
            jRadioButtonInstantMarks.setSelected(true);
        }
        jTextFieldSizeAnnotations.setText(String.valueOf(jSignalAdapter.getAnnotationIntervalSize()));
        jTextFieldSizeMarks.setText(String.valueOf(jSignalAdapter.getMarkIntervalSize()));
    }

    private void saveInfo() {
        if (jRadioButtonIntervalAnnotations.isSelected()) {
            jSignalAdapter.setAnnotationInterval(true);
            jSignalAdapter.setAnnotationIntervalSize(Integer.parseInt(jTextFieldSizeAnnotations.getText()));
        } else {
            jSignalAdapter.setAnnotationInterval(false);
        }
        if (jRadioButtonIntervalMarks.isSelected()) {
            jSignalAdapter.setMarkInterval(true);
            jSignalAdapter.setMarkIntervalSize(Integer.parseInt(jTextFieldSizeMarks.getText()));
        } else {
            jSignalAdapter.setMarkInterval(false);
        }

    }
}
