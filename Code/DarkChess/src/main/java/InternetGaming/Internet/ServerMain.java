package InternetGaming.Internet;

public class ServerMain {
    public Server s;
    public static void main(String[] args) {

    }
    public void startSever(int port){
        s= new Server(port);
        s.start();
    }
    public void closeSever(){
        s.stopSever();
    }
}
