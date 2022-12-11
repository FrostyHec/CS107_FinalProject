package InternetGaming.Internet.Message;

import java.io.*;
import java.net.Socket;

public class MessageHandler {
    private InputStream inputStream;
    private BufferedReader reader;
    private OutputStream outputStream;
    private ObjectOutputStream objWriter;
    private ObjectInputStream objReader;
    private PrintWriter writer;
    private Socket client;

    public MessageHandler(Socket client,HandlerType handlerType) {
        this.client = client;
        switch (handlerType){
            case Sever -> {
                generateOutputStream();
                generateInputStream();
            }
            case Client -> {
                generateInputStream();
                generateOutputStream();
            }
        }
    }
    private void generateInputStream(){
        //输入流
        try {
            inputStream = client.getInputStream();
            objReader = new ObjectInputStream(inputStream);
            reader = new BufferedReader(new InputStreamReader(inputStream));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void generateOutputStream(){
        //输出流
        try {
            outputStream = client.getOutputStream();
            objWriter = new ObjectOutputStream(outputStream);
            writer = new PrintWriter(outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(MessageType type, String message) {
        writer.println(type);
        writer.println(message);
        writer.flush();
    }

    public void sendObj(Object o) {
        try {
            try {
                Thread.sleep(50);//TODO 我真的是无语，为什么睡会就好了啊
            } catch (Exception e) {
            }
            objWriter.writeObject(o);
            objWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String[] hear() throws Exception {//返回一个args,0为消息类型，1为消息信息
        try {
            String[] args = new String[2];
            args[0] = reader.readLine();
            args[1] = reader.readLine();
            return args;
        } catch (IOException e) {
            throw new Exception(e);
        }
    }

    public Object hearObj() throws Exception {
        Object o=objReader.readObject();
        return o;
    }
}
