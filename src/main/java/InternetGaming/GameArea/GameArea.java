package InternetGaming.GameArea;

import GameLogic.Color;
import GameLogic.Game;
import InternetGaming.Internet.Message.MessageHandler;
import InternetGaming.Internet.Message.MessageType;
import InternetGaming.Internet.Message.PlayerType;
import InternetGaming.Internet.Transmitter;
import Windows.GameArea.GameState;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class GameArea extends Windows.GameArea.GameArea {
    private PlayerType type;
    private Color color = Color.UNKNOWN;

    public GameArea() throws Exception {
        super();
        setType(Transmitter.playerType);
        System.out.println(type);
    }

    public void setType(PlayerType type) {
        this.type = type;
    }

    @Override
    protected void setTransmitter() {
        Transmitter.gameArea = this;
    }

    @Override
    public void GameAreaKeyPressed(KeyEvent event) {
        //do nothing;
    }

    @Override
    public void chessMove(MouseEvent event) {
        if (type.equals(PlayerType.Viewer)) {//禁用观察者行棋
            return;
        }

        if (gameState.equals(GameState.FirstHandChoose)) {//先手行棋
            if (type.equals(PlayerType.SecondHand)) {
                return;
            }
            super.chessMove(event);
            analyzeColor();
            sendMsg();
            return;
        }

        //普通行子
        if (!game.nowPlay().getColor().equals(color)) {
            return;
        }
        super.chessMove(event);
        sendMsg();
    }

    private void analyzeColor() {
        switch (type) {
            case FirstHand -> {
                color = game.getPlayer1().getColor();
            }
            case SecondHand -> {
                color = game.getPlayer2().getColor();
            }
        }
    }

    private void sendMsg() {
        MessageHandler m = Transmitter.client.getM();
        m.send(MessageType.ChessBoardRefresh, "");
        m.sendObj(game.clone());
    }

    public void remoteRefresh(Game game) {
        this.game=game;
    }
}
