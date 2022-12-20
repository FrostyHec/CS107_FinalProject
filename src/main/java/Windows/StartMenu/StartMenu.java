package Windows.StartMenu;

import InternetGaming.Internet.LinkingWindow;
import UserFiles.UserManager;
import Windows.GameArea.MainGame;
import Windows.GameArea.Transmitter;
import Windows.RankingList.RankingListController;
import Windows.SetUp.MainSetUp;
import Windows.SetUp.Settings;
import Windows.Userfiles.UserfilesWindow;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class StartMenu {
    public Pane paneStartMenu;
    public ImageView avatarPic;
    public Label userName;
    public Label toolTipper;
    public Button btnInter;
    public Button btnNewGame;
    public Button btnCOntinueGame;
    public Button btnSwitch;
    public Button btnRankList;
    public Button btnSetUp;
    public Button btnQuiz;
    private String defaultAvatar = "src/main/resources/Windows/images/UserImage/tempUser.png";

    private Settings settings;
    private ResourceBundle t;

    public StartMenu() {
        settings = Settings.read(Settings.url);
        t = ResourceBundle.getBundle("Language/StartMenuLanguage", settings.visualSettings.getLanguage());

    }

    @FXML
    public void initialize() throws Exception {
        generateUserInfo();
        setToolTip();
        setText();
    }

    private void setText() {
        btnSwitch.setText(t.getString("Switch"));
        btnInter.setText(t.getString("Intel"));
        btnQuiz.setText(t.getString("Exit"));
        btnSetUp.setText(t.getString("SetUp"));
        btnNewGame.setText(t.getString("New"));
        btnCOntinueGame.setText(t.getString("Continue"));
        btnRankList.setText(t.getString("Rank"));
    }

    private void setToolTip() {
        final Tooltip tooltip = new Tooltip();
        tooltip.setText(
                "点击切换头像"
        );
        toolTipper.setTooltip(tooltip);
    }

    public void generateUserInfo() throws Exception {
        String name = UserManager.read().nowPlay().getName();
        userName.setText(name);
        generateAvatar();
    }

    public void generateAvatar() {
        FileInputStream f;
        try {
            String url = UserManager.read().nowPlay().getAvatarUrl();
            f = new FileInputStream(url);
        } catch (Exception e) {
            try {
                f = new FileInputStream(defaultAvatar);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
        avatarPic.setImage(new Image(f));
        try {
            f.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startNextPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("startNextPage.fxml"));
        ((Stage) paneStartMenu.getScene().getWindow()).setScene(new Scene(fxmlLoader.load()));
        Windows.Transmitter.startNextPage.jump();
    }

    public void openSetUp() throws Exception {
        new MainSetUp().start(new Stage());
        ((Stage) paneStartMenu.getScene().getWindow()).close();
    }

    public void exitGame() {
        Platform.exit();
    }

    public void startLinkingPage() throws IOException {
        Application.setUserAgentStylesheet(getClass().getResource(settings.visualSettings.getSkin()).toExternalForm());
        FXMLLoader fxmlLoader = new FXMLLoader(LinkingWindow.class.getResource("LinkingWindow.fxml"));
        ((Stage) paneStartMenu.getScene().getWindow()).setScene(new Scene(fxmlLoader.load()));
    }

    public void changeUser() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UserfilesWindow.class.getResource("UserfilesWindow.fxml"));
        ((Stage) paneStartMenu.getScene().getWindow()).setScene(new Scene(fxmlLoader.load()));
    }

    public static void show(Stage stage) {
        new StartMenu().thisStylesheet();
        FXMLLoader fxmlLoader = new FXMLLoader(StartMenu.class.getResource("StartMenu.fxml"));
        try {
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void thisStylesheet() {
        Application.setUserAgentStylesheet(StartMenu.class.getResource(settings.visualSettings.getSkin()).toExternalForm());
    }

    public void continueGame() {
        ContinueGame.show((Stage) paneStartMenu.getScene().getWindow());
    }

    public void changeAvatar(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("All Images", "*.*")
        );
        fileChooser.setTitle("选择头像");
        File file = fileChooser.showOpenDialog(new Stage());
        if (file == null) {
            return;
        }
        try {
            String path = UserManager.read().nowPlay().getAvatarUrl();
            avatarPic.setImage(null);
            Files.deleteIfExists(Path.of(path));
            Files.copy(Paths.get(file.getPath()), Paths.get(path));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        generateAvatar();
    }

    public void openRankList(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RankingListController.class.getResource("RankingList.fxml"));
        ((Stage) paneStartMenu.getScene().getWindow()).setScene(new Scene(fxmlLoader.load()));
    }

}
