package Windows.RankingList;

import UserFiles.User;
import UserFiles.UserManager;
import Windows.GameArea.Difficulty;
import Windows.StartMenu.StartMenu;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

public class RankingListController {

    public Label numCreateTime;
    public Label numAlreadyPlay;
    public Label lbAlreadyPlay;
    public Label numGameFrequency;
    public Label numGameTime;
    public Label lbGameTime;
    public Label lbFrom;
    public Button btnBack;
    public Label lbUserName;
    public TableView dataTable;
    public TableColumn playKind;
    public TableColumn winRates;
    public TableColumn longestWin;
    public TableColumn playTimes;
    public TableColumn nowLongestWin;
    public ImageView avatar;

    private User user;
    private String defaultAvatar = "src/main/resources/Windows/images/UserImage/tempUser.png";

    @FXML
    public void initialize() throws Exception {
        Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
        user = UserManager.read().nowPlay();
        setInfo();
        setRanks();
        setAvatar();
    }

    private void setInfo() {
        lbUserName.setText(user.getName());
        numGameTime.setText(generatePlayTimes(user.getTimeList().getPlaySec()));
        numGameFrequency.setText(user.getTotalPlayTimes() + "次");
        numAlreadyPlay.setText(user.getTimeList().getPlayingDays().getDays()+"天");
        numCreateTime.setText(generateCreateDate(user.getTimeList().getCreateDate()));
    }
    private void setAvatar(){
        FileInputStream f;
        try {
            f = new FileInputStream(user.getAvatarUrl());
        } catch (Exception e) {
            try {
                f = new FileInputStream(defaultAvatar);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
        avatar.setImage(new Image(f));
        try {
            f.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private String generatePlayTimes(long sec) {
        StringBuilder sb = new StringBuilder();
        long hour = sec / 3600;
        if (hour > 0) {
            sb.append(hour).append("时");
        }
        long min = (sec % 3600) / 60;
        sb.append(min).append("分");
        return sb.toString();
    }

    private String generateCreateDate(LocalDate date) {
        StringBuilder sb = new StringBuilder();
        sb.append(date.getYear()).append(".");
        sb.append(date.getMonthValue()).append(".");
        sb.append(date.getDayOfMonth());
        return sb.toString();
    }

    private void setRanks() {
        ObservableList<ShowingScore> data = FXCollections.observableArrayList();
        playKind.setCellValueFactory(new PropertyValueFactory<>("playKind"));
        winRates.setCellValueFactory(new PropertyValueFactory<>("winRates"));
        longestWin.setCellValueFactory(new PropertyValueFactory<>("longestWin"));
        playTimes.setCellValueFactory(new PropertyValueFactory<>("playTimes"));
        nowLongestWin.setCellValueFactory(new PropertyValueFactory<>("nowLongestWin"));
        for (Difficulty d : user.getScoreList().getScore().keySet()) {
            data.add(new ShowingScore(user.getScoreList().getScore().get(d), d.toString()));
        }
        data.add(new ShowingScore(user.getScoreList().getInternetScore(), "网络对战"));
        dataTable.setItems(data);
    }

    public void backClick(ActionEvent event) {
        StartMenu.show((Stage) dataTable.getScene().getWindow());
    }


    /* TODO 积分榜
    TODO 1.修改胜利界面
    TODO 2.在胜利之后判断游戏类型，如果是人机，把最后游戏时间， 游戏时长，分差录入到积分榜里面
    TODO 3.记录最长连胜
    TODO 4.累加当前连胜，超过最长赋值到最长，没有超过继续累计，失败清零
    TODO 5.记录游戏场数
    TODO 6.每次游戏结束后返回累计时长，累积到游戏时长里面
    TODO 7.在游戏部分添加音效&动画（吃将帅）&完善封面画面&做个简单的切换主题
    TODO 8.把文字导入到properties里面
     */
}
