package InternetGaming.Internet;

import GameLogic.Game;
import InternetGaming.Internet.Message.*;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class Client extends ClientData {
    ClientData[] totalClient;

    public Client(MessageHandler m) {
        super(m);
    }

    public Client(String name, MessageHandler m, PlayerType playerTurns) {
        super(name, m, playerTurns);
    }

    public static Socket connect(String host, int port) throws IOException {
        Socket client = new Socket(host, port);
        System.out.println("成功连接服务器");
        return client;
    }

    public void start() {
        new MessageParse().start();
    }

    @SuppressWarnings("RedundantLabeledSwitchRuleCodeBlock")
    class MessageParse extends Thread {
        @Override
        public void run() {
            while (true) {
                String[] args;
                try {
                    args = m.hear();
                } catch (Exception e) {
                    throw new RuntimeException("Server is closed unexpectedly!");
                }
                System.out.println(args[0]);
                switch (MessageType.valueOf(args[0])) {
                    case PlayerSetting -> {
                        new SetPlayer().parse(args[1]);
                    }
                    case NewPlayer -> {
                        new NewPlayer().parse(args[1]);
                    }
                    case StartGame -> {
                        new StartGame().parse(args[1]);
                    }
                    case ChessBoardRefresh -> {
                        new FlushChessBoard().parse(args[1]);//解析构造传入消息处理器有两种可能：一种是发消息，一种是获取进一步消息
                    }
                    case GameOver -> {
                        // new Server.GameOver().parse(args[1]);
                    }
                    default -> {
                        throw new RuntimeException();
                    }
                }
            }
        }
    }

    class SetPlayer implements Parser {
        @Override
        public void parse(String message) {
            Transmitter.preparingWindow.setThisPlayerType(PlayerType.valueOf(message));
        }
    }

    class NewPlayer implements Parser {
        @Override
        public void parse(String message) {
            try {
                System.out.println("试图收取玩家列表");
                totalClient = (ClientData[]) m.hearObj();
                System.out.println("收到玩家列表");
                //totalClient = (ClientData[]) o;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            Transmitter.preparingWindow.refresh(totalClient);
        }
    }

    @SuppressWarnings("RedundantLabeledSwitchRuleCodeBlock")
    class StartGame implements Parser {

        @Override
        public void parse(String message) {
            switch (StartGameType.valueOf(message)) {
                case Permit -> {
                    Transmitter.preparingWindow.ableToStartGame();
                }
                case Start -> {
                    Transmitter.preparingWindow.startGame();
                }
            }
        }
    }

    class FlushChessBoard implements Parser {
        @Override
        public void parse(String message) {
            Game game;
            try {
                game = (Game) m.hearObj();
                Transmitter.gameArea.remoteRefresh(game);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}

