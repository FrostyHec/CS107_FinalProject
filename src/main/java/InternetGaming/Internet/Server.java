package InternetGaming.Internet;

import InternetGaming.Internet.Message.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server extends Thread {
    private ServerSocket server;
    private List<ClientData> clientList = new ArrayList<>();

    public Server(int port) {
        try {
            server = new ServerSocket(port);
            System.out.println("服务器已启动……");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        ConnectHandler cn = new ConnectHandler();
        cn.start();
        Scanner sc = new Scanner(System.in);
    }

    private boolean isGameStart = false;

    class ConnectHandler extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    //循环等待连接多个客户端
                    Socket client = server.accept();
                    System.out.println("IP:" + client.getInetAddress() + "连接成功");
                    System.out.println("端口号:" + client.getPort());
                    //开启下一个线程
                    new MessageParse(client).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class SetPlayer implements Parser {
        MessageHandler m;

        public SetPlayer(MessageHandler m) {
            this.m = m;
        }

        @Override
        public void parse(String message) {
            PlayerType p;
            if (clientList.size() == 1) {//初代版本默认服主是先手，后面再改
                p = PlayerType.FirstHand;
            } else if (clientList.size() == 2) {
                p = PlayerType.SecondHand;
            } else {
                p = PlayerType.Viewer;
            }
            set(p);
            synchronized (this) {//添加用户优先锁定
                clientList.add(new ClientData(message, m, p));
            }

            //广播新用户登场
            for (ClientData c: clientList) {
                c.m.send(MessageType.NewPlayer,"");
                c.m.sendObj(clientList.toArray());//发送的是数组
            }
            checkAbleToStart();
        }

        public void set(PlayerType p) {
            m.send(MessageType.PlayerSetting, p.toString());//消息分为两部分，一部分为消息类型，一部分为消息内容
        }
    }

    public void checkAbleToStart() {
        if (clientList.size() < 2) {
            return;
        }
        if (!(clientList.get(0).getPlayerTurns().equals(PlayerType.FirstHand) &&
                clientList.get(1).getPlayerTurns().equals(PlayerType.SecondHand))) {
            throw new RuntimeException();
        }
        clientList.get(0).getM().send(MessageType.StartGame, StartGameType.Permit.toString());
    }

    public void playerExit(MessageHandler m) {
        for (ClientData c : clientList) {
            if (!c.getM().equals(m)) {
                continue;
            }
            int index = clientList.indexOf(c);
            if (index == 0) {//还没写,切换玩家设置的模块，可以改改捏
                //腐竹润了咋整还没想明白
            } else if (index == 1) {
                if (clientList.size() > 2) {
                    ClientData c2 = clientList.get(index + 1);
                    c2.setPlayerType(PlayerType.SecondHand);
                }
            } else if (index > 1) {
                clientList.remove(c);
            }
        }
    }

    public void playerIllegalExit(MessageHandler m) {
        //还没写
    }

    @SuppressWarnings("RedundantLabeledSwitchRuleCodeBlock")
    class MessageParse extends Thread {
        MessageHandler m;

        public MessageParse(Socket client) {
            m = new MessageHandler(client);
        }

        @Override
        public void run() {
            while (true) {
                String[] args;
                try {
                    args = m.hear();
                } catch (Exception e) {
                    if (!isGameStart) {
                        playerExit(m);
                    } else {
                        playerIllegalExit(m);
                    }
                    return;
                }
                switch (MessageType.valueOf(args[0])) {
                    case PlayerSetting -> {
                        new SetPlayer(m).parse(args[1]);
                    }
                    case StartGame -> {
                        new StartGame().parse(args[1]);
                    }
                    case ChessBoardRefresh -> {
                        new FlushChessBoard(m).parse(args[1]);//解析构造传入消息处理器有两种可能：一种是发消息，一种是获取进一步消息
                    }
                    case GameOver -> {
                        new GameOver().parse(args[1]);
                    }
                    default -> {
                        throw new RuntimeException();
                    }
                }
            }
        }
    }

    class FlushChessBoard implements Parser {
        MessageHandler m;

        public FlushChessBoard(MessageHandler m) {
            this.m = m;
        }

        @Override
        public void parse(String message) {
            Object o;
            try {
                o = m.hearObj();
                //Game g = (Game) o;//暂时先不在服务器端判断是不是棋盘
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            for (ClientData c : clientList) {
                MessageHandler m = c.getM();
                m.send(MessageType.ChessBoardRefresh, "");
                m.sendObj(o);
            }
        }
    }

    class StartGame implements Parser {
        @Override
        public void parse(String message) {
            isGameStart = true;
            for (ClientData c : clientList) {
                c.getM().send(MessageType.StartGame, StartGameType.Start.toString());
            }
        }
    }

    class GameOver implements Parser {

        @Override
        public void parse(String message) {
            for (ClientData c : clientList) {
                c.getM().send(MessageType.GameOver, message);
            }
        }
    }
}


