package InternetGaming.Internet;

import InternetGaming.Internet.Message.HandlerType;
import InternetGaming.Internet.Message.MessageHandler;
import javafx.application.Platform;
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

    public void startCreate(ActionEvent event) throws Exception {
        int port;
        try {
            ServerMain s = new ServerMain();
            port = Integer.parseInt(portField.getText());
            s.startSever(port);
        } catch (NumberFormatException nb) {
            linkingMessage.setText("连接失败！请检查您的输入是否正确");
            return;
        } catch (Exception e) {
            linkingMessage.setText("连接失败！端口号大于1000为宜，请更换端口号尝试");
            return;
        }
        startLink("127.0.0.1", port);
//        new Thread(() -> {
//            try {
//                Thread.sleep(1000);
//                Platform.runLater(()-> {
//                    try {
//                        startLink("127.0.0.1", port);
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                });
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        });
    }

    public void startLink() throws IOException {
        startLink(ipField.getText(), Integer.parseInt(portField.getText()));
    }

    public void startLink(String ip, int port) throws IOException {
        Client c;
        try {
            c = new Client(new MessageHandler(Client.connect(ip, port), HandlerType.Client));
            System.out.println("成功创建客户");
        } catch (IOException | NumberFormatException e) {
            linkingMessage.setText("连接失败！请检查您的输入是否正确\n如多次连接失败，请联系服务器管理员");
            return;
        }
        Transmitter.client = c;
        linkingMessage.setText("连接成功！");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PreparingWindow.fxml"));
        ((Stage) portField.getScene().getWindow()).setScene(new Scene(fxmlLoader.load()));

    }

}
