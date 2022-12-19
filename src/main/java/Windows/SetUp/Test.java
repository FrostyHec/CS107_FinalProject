package Windows.SetUp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Locale;

public class Test {
    public static void main(String[] args) {
        Settings settings=new Settings();
        //StartSettings
        settings.StartSettings.setAlwaysPvP(false);
        settings.StartSettings.setAlwaysPvE(false);

        //VisualSettings
        settings.visualSettings.setLanguage(Locale.getDefault());
        settings.visualSettings.setVisualEffect(false);
        settings.visualSettings.setSkinName(SkinList.Default);
        //
        settings.soundSettings.setMusicPlay(true);
        settings.soundSettings.setBagType(SoundType.Classic);
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
