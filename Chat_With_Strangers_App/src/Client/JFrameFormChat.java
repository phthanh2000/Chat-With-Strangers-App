package Client;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class JFrameFormChat extends javax.swing.JFrame {

    private final JLabel jLabelyou = new JLabel(" ");
    private String username;
    private final DataInputStream dis;
    private final DataOutputStream dos;
    private final HashMap<String, JTextPane> chatWindows = new HashMap<>();
    Thread receiver;
    private Socket socket;
    private String host = "localhost";
    private int port = 1234;

    public void setUsername(String username) {
        this.username = username;
    }

    private void newMessage(String username, String message, Boolean yourMessage) {
        StyledDocument doc;
        if (username.equals(this.username)) {
            doc = chatWindows.get(jLabelyou.getText()).getStyledDocument();
        } else {
            doc = chatWindows.get(username).getStyledDocument();
        }

        Style userStyle = doc.getStyle("User style");
        if (userStyle == null) {
            userStyle = doc.addStyle("User style", null);
            StyleConstants.setBold(userStyle, true);
        }

        if (yourMessage == true) {
            StyleConstants.setForeground(userStyle, Color.red);
        } else {
            StyleConstants.setForeground(userStyle, Color.BLUE);
        }
        try {
            doc.insertString(doc.getLength(), username + ": ", userStyle);
        } catch (BadLocationException e) {
        }

        Style messageStyle = doc.getStyle("Message style");
        if (messageStyle == null) {
            messageStyle = doc.addStyle("Message style", null);
            StyleConstants.setForeground(messageStyle, Color.BLACK);
            StyleConstants.setBold(messageStyle, false);
        }

        // In ra nội dung tin nhắn
        try {
            doc.insertString(doc.getLength(), message + "\n", messageStyle);
        } catch (BadLocationException e) {
        }
    }

    public JFrameFormChat(String username, DataInputStream dis, DataOutputStream dos) {
        initComponents();
        this.username = username;
        this.dis = dis;
        this.dos = dos;
        receiver = new Thread(new Receiver(dis));
        receiver.start();
    }

    class Receiver implements Runnable {

        private final DataInputStream dis;

        public Receiver(DataInputStream dis) {
            this.dis = dis;
        }

        @Override
        public void run() {
            try {

                while (true) {
                    String method = dis.readUTF();

                    if (method.equals("Text")) {
                        String sender = dis.readUTF();
                        String message = dis.readUTF();
                        newMessage(sender, message, false);
                    } else if (method.equals("Online users")) {
                        String[] users = dis.readUTF().split(",");
                        jComboBox1.removeAllItems();
                        String chatting = jLabelyou.getText();
                        boolean isChattingOnline = false;
                        for (String user : users) {
                            if (user.equals(username) == false) {
                                jComboBox1.addItem(user);
                                jComboBox1.setSelectedIndex(new Random().nextInt(jComboBox1.getItemCount()));
                                if (chatWindows.get(user) == null) {
                                    JTextPane temp = new JTextPane();
                                    temp.setFont(new Font("Arial", Font.PLAIN, 14));
                                    temp.setEditable(false);
                                    chatWindows.put(user, temp);
                                }
                            }
                            if (chatting.equals(user)) {
                                isChattingOnline = true;
                            }
                        }
                        if (isChattingOnline == false) {
                            jComboBox1.setSelectedItem(" ");
                            JOptionPane.showMessageDialog(null, chatting + " đã thoát!\nVui lòng chọn người dùng khác !");
                        } else {
                            jComboBox1.setSelectedItem(chatting);
                        }
                        jComboBox1.validate();
                    } else if (method.equals("Safe to leave")) {
                        break;
                    }
                }
            } catch (IOException ex) {
                System.err.println(ex);
            } finally {
                try {
                    if (dis != null) {
                        dis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane = new javax.swing.JScrollPane();
        jTextPanems = new javax.swing.JTextPane();
        jButton2 = new javax.swing.JButton();
        jLabelme = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabelcontent = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        jPanel.setBackground(new java.awt.Color(204, 204, 204));

        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 204));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/send.png"))); // NOI18N
        jButton1.setText("Gửi");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField1.setBackground(new java.awt.Color(204, 255, 255));
        jTextField1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jLabel.setBackground(new java.awt.Color(255, 255, 255));
        jLabel.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/user.png"))); // NOI18N

        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        jScrollPane.setViewportView(jTextPanems);

        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 0, 0));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/thoat.png"))); // NOI18N
        jButton2.setText("Thoát");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabelme.setBackground(new java.awt.Color(204, 204, 204));
        jLabelme.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabelme.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 0, 51));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Chọn người dùng");

        jLabelcontent.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        jLabelcontent.setForeground(new java.awt.Color(0, 0, 153));
        jLabelcontent.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanelLayout = new javax.swing.GroupLayout(jPanel);
        jPanel.setLayout(jPanelLayout);
        jPanelLayout.setHorizontalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanelLayout.createSequentialGroup()
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                    .addComponent(jLabelcontent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(9, 9, 9)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelLayout.createSequentialGroup()
                        .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1)
                            .addComponent(jLabelme, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanelLayout.setVerticalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelLayout.createSequentialGroup()
                        .addComponent(jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelme, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabelcontent, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1)))))
        );

        getContentPane().add(jPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            jLabelyou.setText((String) jComboBox1.getSelectedItem());
            if (jTextPanems != chatWindows.get(jLabelyou.getText())) {
                jTextField1.setText("");
                jTextPanems = chatWindows.get(jLabelyou.getText());
                jScrollPane.setViewportView(jTextPanems);
                jScrollPane.validate();
                String value = jComboBox1.getSelectedItem().toString();
                jLabelcontent.setText(value);
            }

            if (jLabelyou.getText().isBlank()) {
                jButton1.setEnabled(false);
                jTextField1.setEnabled(false);
            } else {
                jButton1.setEnabled(true);
                jTextField1.setEnabled(true);
            }
        }
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        // TODO add your handling code here:
        if (jTextField1.getText().isBlank() || jLabelyou.getText().isBlank()) {
            jButton1.setEnabled(false);
        } else {
            jButton1.setEnabled(true);
        }

    }//GEN-LAST:event_jTextField1KeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        try {
            dos.writeUTF("Thoát");
            dos.flush();

            try {

                receiver.join();
                System.exit(0);

            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

            if (dos != null) {
                dos.close();
            }
            if (dis != null) {
                dis.close();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                dos.writeUTF("Text");
                dos.writeUTF(jLabelyou.getText());
                dos.writeUTF(jTextField1.getText());
                dos.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
                newMessage("ERROR", "Network error!", true);
            }
            newMessage(username, jTextField1.getText(), true);
            jTextField1.setText("");
        }
    }//GEN-LAST:event_jTextField1KeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            dos.writeUTF("Text");
            dos.writeUTF(jLabelyou.getText());
            dos.writeUTF(jTextField1.getText());
            dos.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
            newMessage("ERROR", "Network error!", true);
        }
        // In ra tin nhắn lên màn hình chat với người nhận
        newMessage(username, jTextField1.getText(), true);
        jTextField1.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private static javax.swing.JLabel jLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelcontent;
    public static javax.swing.JLabel jLabelme;
    private javax.swing.JPanel jPanel;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextPane jTextPanems;
    // End of variables declaration//GEN-END:variables
}
