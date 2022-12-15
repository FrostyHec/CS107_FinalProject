package Windows.SetUp;

import java.util.Locale;

public enum Language {
    Chinese_Simplyfied("中文简体", new Locale("zh","CN")),
    Chinese_Traditional("中文繁體", new Locale("tr","TW")),
    English("English", new Locale("en","US")),
    Japanese("日本語", new Locale("ja","JP"))
    ;
    Language(String name, Locale locale) {
        this.name = name;
        this.locale = locale;
    }

    private final String name;
    private final Locale locale;

    public String getName() {
        return name;
    }

    public Locale getLocale() {
        return locale;
    }
    public static Language getLanguage(Locale l){
        for (Language le: Language.values()) {
            if(le.locale.equals(l))
            {
                return le;
            }
        }
        //香港肯定不在那个foreach里面
        if(l.getLanguage().equals("zh")){
            Language lr=Language.refactorTWandHK(l);
            if(lr!=null){
                return lr;
            }
        }
        throw new RuntimeException("Language not found!");
    }
    public static Language getLanguage(String name)  {
        for (Language l: Language.values()) {
           if( l.getName().equals(name)){
               return l;
           }
        }
        throw new RuntimeException("language not found!");
    }
    private static Language refactorTWandHK(Locale l){
        if(l.getCountry().equals("HK")){
            return Chinese_Traditional;
        }
        return null;
    }
}
