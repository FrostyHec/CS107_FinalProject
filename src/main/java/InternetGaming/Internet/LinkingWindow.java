package InternetGaming.Internet;

import InternetGaming.Internet.Message.HandlerType;
import InternetGaming.Internet.Message.MessageHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class LinkingWindow {
    public Pane paneLinkingWindow;
    public TextField ipField;
    public TextField portField;
    public Label linkingMessage;
    public Label localIPtext;

    public void newSever(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("newSever.fxml"));
        ((Stage) paneLinkingWindow.getScene().getWindow()).setScene(new Scene(fxmlLoader.load()));
    }

    public void linkSever(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("linkSever.fxml"));
        ((Stage) paneLinkingWindow.getScene().getWindow()).setScene(new Scene(fxmlLoader.load()));
    }

    public void startCreate(ActionEvent event) {

    }

    public void startLink() throws IOException {
        Client c;
        try {
            c = new Client(new MessageHandler(Client.connect(ipField.getText(), Integer.parseInt(portField.getText())), HandlerType.Client));
            System.out.println("Win!");
        } catch (IOException | NumberFormatException e) {
            linkingMessage.setText("连接失败！请检查您的输入是否正确");
            return;
        }
        Transmitter.client = c;
        linkingMessage.setText("连接成功！");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PreparingWindow.fxml"));
        ((Stage) portField.getScene().getWindow()).setScene(new Scene(fxmlLoader.load()));

    }

}
