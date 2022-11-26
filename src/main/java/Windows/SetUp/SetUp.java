package Windows.SetUp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SetUp {
    public static void main(String[] args) {
        NormalSettings settings=new NormalSettings();
        settings.startMenu.setAlwaysPvP(false);
        try(ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream("src/main/resources/Settings/Settings.ser")))
        {
            out.writeObject(settings);
            System.out.printf("Serialized data is saved.");
        }catch(IOException i)
        {
            i.printStackTrace();
        }
    }

}
