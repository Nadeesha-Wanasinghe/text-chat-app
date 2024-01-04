package lk.ijse.dep11.app.client.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class MainFormController {
    public TextField txtMessage;
    public TextArea txtDisplay;
    public Button btnSend;
    public Label lblUser;
    private Socket remoteSocket;

    public void initialize(){
        new Thread(()->{
            try {
                remoteSocket = new Socket("localhost", 5050);
                InputStream is = remoteSocket.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                while (true){
                    byte[] buffer =new byte[1024];
                    int read = bis.read(buffer);
                    if (read == -1) break;
                    Platform.runLater(()->{
                        txtDisplay.setText(new String(buffer, 0, read));
                    });
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void initData(String user){
        lblUser.setText(user);
        try {
            writeMessage("Entered: " + lblUser.getText() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnSendOnAction(ActionEvent actionEvent) throws IOException {
        if (txtMessage.getText().isBlank()) return;
        writeMessage(lblUser.getText() + ": " + txtMessage.getText() + "\n");
        txtMessage.clear();
        txtMessage.requestFocus();
    }

    private void writeMessage(String message) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(remoteSocket.getOutputStream());
        bos.write(message.getBytes());
        bos.flush();
    }
}
