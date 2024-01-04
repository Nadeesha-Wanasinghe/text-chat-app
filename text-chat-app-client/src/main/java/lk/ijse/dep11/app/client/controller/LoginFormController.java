package lk.ijse.dep11.app.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {
    public AnchorPane root;
    public TextField txtName;
    public Button btnJoin;

    public void btnJoinOnAction(ActionEvent actionEvent) throws IOException {
        if (txtName.getText().isBlank()) return;

        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainForm.fxml"));
        Scene mainScene = new Scene(fxmlLoader.load());
        MainFormController controller = fxmlLoader.getController();
        controller.initData(txtName.getText());
        stage.setScene(mainScene);
        stage.setTitle("Text Chat App");
        stage.show();
        stage.centerOnScreen();
        ((Stage)(root.getScene().getWindow())).close();
    }
}
