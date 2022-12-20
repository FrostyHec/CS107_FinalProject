package InternetGaming.Internet.Message;

import Windows.SetUp.Settings;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

public class MessageHandler {
    private InputStream inputStream;
    private BufferedReader reader;
    private OutputStream outputStream;
    private ObjectOutputStream objWriter;
    private ObjectInputStream objReader;
    private PrintWriter writer;
    private Socket client;
    private DataOutputStream dataWriter;
    private DataInputStream dataReader;

    private boolean isFastModeStart= Settings.read(Settings.url).gameSettings.isFastModeOpen();
    public MessageHandler(Socket client, HandlerType handlerType) {
        this.client = client;
        switch (handlerType) {
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

    private void generateInputStream() {
        //输入流
        try {
            inputStream = client.getInputStream();
            objReader = new ObjectInputStream(inputStream);
            reader = new BufferedReader(new InputStreamReader(inputStream));
            dataReader = new DataInputStream(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateOutputStream() {
        //输出流
        try {
            outputStream = client.getOutputStream();
            objWriter = new ObjectOutputStream(outputStream);
            writer = new PrintWriter(outputStream);
            dataWriter = new DataOutputStream(client.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(MessageType type, String message) {
        writer.println(type);
        writer.println(message);
        writer.flush();
    }

    //Don't use sendObj, it will create mountains of problem.
    public void sendObj(Object o) {
        int sleepTime=600;
        if(isFastModeStart){
            sleepTime=100;
        }
        try {
            Thread.sleep(sleepTime);//TODO 多睡会，少粘包
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
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
        //Thread.sleep(20);
        Object o = null;
        try {
            o = objReader.readObject();
        } catch (OptionalDataException e) {
            if (!e.eof) {
                throw new RuntimeException(e);
            }
        }
        return o;
    }

    public void close() {
        try {
            writer.close();
            reader.close();
            dataWriter.close();

            objWriter.close();
            objReader.close();
            client.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    public Object hearObjInByte() throws IOException {
//        //文件名和长度
//        ObjectOutputStream foStream =  new ObjectOutputStream(outputStream);
//
//        //开始接收文件
//        byte[] bytes = new byte[1024];
//        int length = 0;
//        while ((length = dataReader.read(bytes, 0, bytes.length)) != -1) {
//            foStream.write(bytes, 0, length);
//            foStream.flush();
//        }
//        dataReader.close();
        return null;
    }

    @Deprecated
    public void sendObjInByte(Object o) throws Exception {
//        ByteArrayOutputStream byt=new ByteArrayOutputStream();
//        ObjectOutputStream obj=new ObjectOutputStream(byt);
//        obj.writeObject(o);
//        dataWriter.writeLong(o.length());
//        dataWriter.flush();
//        //开始传输文件
//        byte[] bytes = new byte[1024];
//        int length = 0;
//        while ((length = byt.read(bytes, 0, bytes.length)) != -1) {
//            dataWriter.write(bytes, 0, length);
//            dataWriter.flush();
//        }
//        System.out.println("字节流传输成功");
//        fileIn.close();
    }

    @Deprecated
    public void sendByte(Object o) throws IOException {
//        ByteArrayOutputStream byt=new ByteArrayOutputStream();
//
//        ObjectOutputStream obj=new ObjectOutputStream(byt);
//
//        obj.writeObject(o);
//        byte[] bytes=byt.toByteArray();

    }
}
