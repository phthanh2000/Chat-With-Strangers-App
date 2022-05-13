package Client;

import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class JFrameLogin extends javax.swing.JFrame {

    private final String host = "localhost";
    private final int port = 1234;
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private String username;

    public JFrameLogin() {
        initComponents();

    }

    public String Start(String username) {
        try {
            Connect();
            dos.writeUTF("Bắt đầu");
            dos.writeUTF(username);
            dos.flush();
            String response = dis.readUTF();
            return response;

        } catch (IOException e) {
            e.printStackTrace();
            return "Network error: Log in fail";
        }
    }

    public void Connect() {
        try {
            if (socket != null) {
                socket.close();
            }
            socket = new Socket(host, port);
            this.dis = new DataInputStream(socket.getInputStream());
            this.dos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getUsername() {
        return this.username;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        JButtonlogin = new javax.swing.JButton();
        JTextFieldtxtUsername = new javax.swing.JTextField();
        jLabelthongbao = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CHAT WITH STRANGER LOGIN");
        setBackground(new java.awt.Color(204, 204, 204));
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Nhập tên");

        JButtonlogin.setBackground(new java.awt.Color(204, 204, 255));
        JButtonlogin.setFont(new java.awt.Font("Times New Roman", 3, 16)); // NOI18N
        JButtonlogin.setText("Bắt đầu");
        JButtonlogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JButtonloginActionPerformed(evt);
            }
        });

        JTextFieldtxtUsername.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        JTextFieldtxtUsername.setForeground(java.awt.Color.red);
        JTextFieldtxtUsername.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        JTextFieldtxtUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTextFieldtxtUsernameActionPerformed(evt);
            }
        });
        JTextFieldtxtUsername.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JTextFieldtxtUsernameKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                JTextFieldtxtUsernameKeyReleased(evt);
            }
        });

        jLabelthongbao.setFont(new java.awt.Font("Times New Roman", 2, 14)); // NOI18N
        jLabelthongbao.setForeground(new java.awt.Color(204, 0, 0));
        jLabelthongbao.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(JTextFieldtxtUsername)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                .addComponent(jLabelthongbao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JButtonlogin, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(3, 3, 3)
                .addComponent(JTextFieldtxtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(JButtonlogin, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelthongbao, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JButtonloginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JButtonloginActionPerformed
        String response = Start(JTextFieldtxtUsername.getText());
        if (response.equals("Ok")) {
            username = JTextFieldtxtUsername.getText();
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        JFrameFormChat frame = new JFrameFormChat(username, dis, dos);
                        frame.jLabelme.setText(username);
                        frame.setVisible(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            dispose();
        } else {
            jLabelthongbao.setText(response);
        }
    }//GEN-LAST:event_JButtonloginActionPerformed

    private void JTextFieldtxtUsernameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JTextFieldtxtUsernameKeyReleased
        // TODO add your handling code here:
        if (JTextFieldtxtUsername.getText().isBlank()) {
            JButtonlogin.setEnabled(false);
        } else {
            JButtonlogin.setEnabled(true);
        }
    }//GEN-LAST:event_JTextFieldtxtUsernameKeyReleased

    private void JTextFieldtxtUsernameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JTextFieldtxtUsernameKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String response = Start(JTextFieldtxtUsername.getText());
            // đăng nhập thành công thì server sẽ trả về  chuỗi "Log in successful"
            if (response.equals("Ok")) {
                username = JTextFieldtxtUsername.getText();
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JFrameFormChat frame = new JFrameFormChat(username, dis, dos);
                            frame.jLabelme.setText(username);
//                        ChatFrame frame = new ChatFrame(username, dis, dos);
                            frame.setVisible(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                dispose();
            } else {
                jLabelthongbao.setText(response);
            }
        }
    }//GEN-LAST:event_JTextFieldtxtUsernameKeyPressed

    private void JTextFieldtxtUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTextFieldtxtUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JTextFieldtxtUsernameActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrameLogin login = new JFrameLogin();
                login.setLocationRelativeTo(null);
                login.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JButton JButtonlogin;
    public static javax.swing.JTextField JTextFieldtxtUsername;
    javax.swing.ButtonGroup buttonGroup1;
    javax.swing.JLabel jLabel1;
    javax.swing.JLabel jLabelthongbao;
    // End of variables declaration//GEN-END:variables
}
