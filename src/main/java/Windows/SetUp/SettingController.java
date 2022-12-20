package Windows.SetUp;

import UserFiles.UserManager;
import Windows.StartMenu.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class SettingController {

    public AnchorPane paneStart;
    public AnchorPane paneGame;
    public AnchorPane paneSound;
    public AnchorPane paneView;
    public Button btnCancel;
    public Button btnStart;
    public Button btnGame;
    public Button btnView;
    public RadioButton btnAlwaysPVE;
    public RadioButton btnAlwaysPVP;
    public Button btnMakerList;
    public Button btnCleanAllData;
    public Button btnToDefault;
    public Button btnCommit;
    public RadioButton btnNoAlways;
    public Label lbVisualEffect;
    public RadioButton btnStartVisualEffect;
    public ChoiceBox<String> cbStyleSheet;
    public Label lbStyleSheet;
    public ChoiceBox<String> cbLanguage;
    public Label lbLanguageChange;
    private final StartMenu startMenu = new StartMenu();
    private final GameSettings gameSettings = new GameSettings();
    private final VisualSettings visualSettings = new VisualSettings();
    private final SoundsSettings soundsSettings = new SoundsSettings();
    public Label lbPVP;
    public Label lbPVE;
    public RadioButton btnStartPromptLabel;
    public RadioButton btnStartFast;
    public RadioButton btnPlayBGM;
    public ChoiceBox<String> cbMusicBag;
    public Label lbMusicBag;
    public RadioButton btnPlaySoundEffect;
    public Label lbSoundEffect;
    private Settings settings;
    public Button btnSound;
    public RadioButton btnPVPCanRetract;
    public RadioButton btnPVPCanCheat;
    public RadioButton btnPVECanRetract;
    public RadioButton btnPVECanCheat;
    public Button btnNewUser;
    ResourceBundle t;
    private static Settings temporarySettings;

    @FXML
    public void initialize() {
        setSettings();
        textHandler();
        startMenu.initialize();
        visualSettings.initialize();
        soundsSettings.initialize();
        gameSettings.initialize();
        startMenu.invoke();
    }

    private void setSettings() {
        if (temporarySettings == null) {//newOpen
            settings = Settings.read(Settings.url);
        } else {//OpenAfterTempClose
            settings = temporarySettings;
            temporarySettings = null;
        }
    }

    private void textHandler() {
        Locale locale = settings.visualSettings.getLanguage();
        t = ResourceBundle.getBundle("Language/SetUpLanguage", locale);
        btnCommit.setText(t.getString("Commit"));
        btnCancel.setText(t.getString("Cancel"));
        btnToDefault.setText(t.getString("ToDefault"));
        btnAlwaysPVE.setText(t.getString("Start.alwaysPVE"));
        btnAlwaysPVP.setText(t.getString("Start.alwaysPVP"));
        btnMakerList.setText(t.getString("Start.makerList"));
        btnCleanAllData.setText(t.getString("Start.cleanAllData"));

        //菜单栏
        btnStart.setText(t.getString("Menu.Start"));
        btnStart.setOnAction(e -> startMenu.invoke());
        btnGame.setText(t.getString("Menu.Game"));
        btnGame.setOnAction(e -> gameSettings.invoke());
        btnSound.setText(t.getString("Menu.View"));
        btnSound.setOnAction(e -> visualSettings.invoke());
        btnView.setText(t.getString("Menu.Sound"));
        btnView.setOnAction(e -> soundsSettings.invoke());
    }

    public void toDefault() {
        Test.main(null);
        cancel(null);
    }

    public void cancel(ActionEvent event) {
        exit();
    }

    private void exit() {
        try {
            new Main().start(new Stage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ((Stage) paneStart.getScene().getWindow()).close();
    }

    public void commit(ActionEvent event) {
        saveToSettings();
        Settings.save(settings, Settings.url);
        exit();
    }

    private void saveToSettings() {
        startMenu.save();
        visualSettings.save();
        gameSettings.save();
        soundsSettings.save();
    }

    public void tempExit() {
        saveToSettings();
        temporarySettings = settings;
        exit();
    }

    interface handler {
        void initialize();

        void invoke();

        void save();
    }

    class StartMenu implements handler {
        private final ToggleGroup tg = new ToggleGroup();
        private RadioButton selectedModel;

        @Override
        public void initialize() {
            //按钮模块
            btnAlwaysPVE.setToggleGroup(tg);
            btnAlwaysPVP.setToggleGroup(tg);
            btnNoAlways.setToggleGroup(tg);
            if (settings.StartSettings.isAlwaysPvP()) {
                btnAlwaysPVP.setSelected(true);
                selectedModel = btnAlwaysPVP;
            } else if (settings.StartSettings.isAlwaysPvE()) {
                btnAlwaysPVE.setSelected(true);
                selectedModel = btnAlwaysPVE;
            } else {
                btnNoAlways.setSelected(true);
                selectedModel = btnNoAlways;
            }
            tg.selectedToggleProperty().addListener((observable, oldValue, newValue) -> selectedModel = (RadioButton) newValue);

            //PlayMakerList
            btnMakerList.setOnAction(e -> playMakerList());

            //CleanAllData
            btnCleanAllData.setOnAction(e -> {
                cleanAllData();
            });
        }

        private void playMakerList() {
            Alert a=new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("制作名单");
            a.setHeaderText("制作名单");
            a.setContentText("组员：祝超、黄政东\n贡献比：50：50\n 用时5周");
            a.showAndWait();
        }

        private void cleanAllData() {//TODO 测试这个模块
            //Confirm
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(t.getString("CleanData.alert.Title"));
            alert.setContentText(t.getString("CleanData.alert.Content"));
            alert.setHeaderText(t.getString("CleanData.alert.Header"));
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.CANCEL) {
                return;//取消删除
            }

            //Clean
            UserManager.deleteAll();
            toDefault();
            exit();
        }

        @Override
        public void invoke() {
            setPane();
        }

        private void setPane() {
            paneGame.setVisible(false);
            paneSound.setVisible(false);
            paneView.setVisible(false);
            paneStart.setVisible(true);
        }

        @Override
        public void save() {
            if (selectedModel.equals(btnAlwaysPVE)) {
                settings.StartSettings.setAlwaysPvE(true);
                settings.StartSettings.setAlwaysPvP(false);
            } else if (selectedModel.equals(btnAlwaysPVP)) {
                settings.StartSettings.setAlwaysPvE(false);
                settings.StartSettings.setAlwaysPvP(true);

            } else if (selectedModel.equals(btnNoAlways)) {
                settings.StartSettings.setAlwaysPvE(false);
                settings.StartSettings.setAlwaysPvP(false);
            } else {
                throw new RuntimeException("UnSelected!");
            }
        }
    }

    @SuppressWarnings("CodeBlock2Expr")
    class GameSettings implements handler {
        @Override
        public void invoke() {
            //设置界面
            paneGame.setVisible(true);
            paneSound.setVisible(false);
            paneView.setVisible(false);
            paneStart.setVisible(false);
        }

        @Override
        public void save() {
            settings.gameSettings.setPVECanCheat(btnPVECanCheat.isSelected());
            settings.gameSettings.setPVECanRetract(btnPVECanRetract.isSelected());
            settings.gameSettings.setPVPCanCheat(btnPVPCanCheat.isSelected());
            settings.gameSettings.setPVPCanRetract(btnPVPCanRetract.isSelected());
            settings.gameSettings.setFastModeOpen(btnStartFast.isSelected());
        }

        @Override
        public void initialize() {
            lbPVE.setText(t.getString("Game.PVE"));
            lbPVP.setText(t.getString("Game.PVP"));
            btnPVPCanRetract.setText(t.getString("Game.retract"));
            btnPVECanRetract.setText(t.getString("Game.retract"));
            btnPVECanCheat.setText(t.getString("Game.cheat"));
            btnPVPCanCheat.setText(t.getString("Game.cheat"));
            btnNewUser.setText(t.getString("Game.newUser"));
            btnStartFast.setText(t.getString("Game.FastMode"));

            btnPVECanCheat.setSelected(settings.gameSettings.isPVECanCheat());
            btnPVECanRetract.setSelected(settings.gameSettings.isPVECanRetract());
            btnPVPCanCheat.setSelected(settings.gameSettings.isPVPCanCheat());
            btnPVPCanRetract.setSelected(settings.gameSettings.isPVPCanRetract());
            btnStartFast.setSelected(settings.gameSettings.isFastModeOpen());
            btnNewUser.setOnAction(e -> {
                welcomeNewUser();
            });

        }

        private void welcomeNewUser() {//TODO welcomeUser
        }
    }

    class VisualSettings implements handler {
        @Override
        public void invoke() {
            //设置界面
            paneGame.setVisible(false);
            paneSound.setVisible(false);
            paneView.setVisible(true);
            paneStart.setVisible(false);
        }

        @Override
        public void save() {
            //是否开启动画特效
            settings.visualSettings.setVisualEffect(btnStartVisualEffect.isSelected());
            settings.visualSettings.setVisualAlarm(btnStartPromptLabel.isSelected());
            //保存画面风格
            settings.visualSettings.setSkinName(SkinList.valueOf(cbStyleSheet.getValue()));
            //保存语言
            settings.visualSettings.setLanguage(Language.getLanguage(cbLanguage.getValue()));
        }

        @Override
        public void initialize() {
            lbVisualEffect.setText(t.getString("Visual.visualEffect"));
            btnStartVisualEffect.setText(t.getString("Visual.startVisualEffect"));
            btnStartPromptLabel.setText(t.getString("Visual.starPromptLabel"));
            lbStyleSheet.setText(t.getString("Visual.styleSheet"));
            lbLanguageChange.setText(t.getString("Visual.languageChange"));
            for (SkinList d : SkinList.values()) {
                cbStyleSheet.getItems().add(d.toString());
            }
            for (Language l : Language.values()) {
                cbLanguage.getItems().add(l.getName());
            }
            cbStyleSheet.setValue(settings.visualSettings.getSkinInList().toString());
            cbLanguage.setValue(Language.getLanguage(settings.visualSettings.getLanguage()).getName());
            btnStartVisualEffect.setSelected(settings.visualSettings.isVisualEffect());
            btnStartPromptLabel.setSelected(settings.visualSettings.isVisualAlarm());
        }
    }

    class SoundsSettings implements handler {
        @Override
        public void invoke() {
            //设置界面
            paneGame.setVisible(false);
            paneSound.setVisible(true);
            paneView.setVisible(false);
            paneStart.setVisible(false);
        }

        @Override
        public void save() {
            settings.soundSettings.setMusicPlay(btnPlayBGM.isSelected());
            settings.soundSettings.setEffectPlay(btnPlaySoundEffect.isSelected());
            settings.soundSettings.setBagType(SoundType.getSoundType(cbMusicBag.getValue()));
        }

        @Override
        public void initialize() {
            lbSoundEffect.setText(t.getString("Sound.Title"));
            btnPlayBGM.setText(t.getString("Sound.PlayBGM"));
            btnPlaySoundEffect.setText(t.getString("Sound.PlaySoundEffect"));
            lbMusicBag.setText(t.getString("Sound.MusicBag"));
            btnPlayBGM.setSelected(settings.soundSettings.isMusicPlay());
            btnPlaySoundEffect.setSelected(settings.soundSettings.isEffectPlay());
            cbMusicBag.setValue(settings.soundSettings.getBagType().getName());
            for (SoundType st : SoundType.values()) {
                cbMusicBag.getItems().add(st.getName());
            }
        }
    }
}
