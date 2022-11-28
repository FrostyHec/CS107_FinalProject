package units;

import java.io.File;

public class traceFiles {

    public static void main(String[] args) {
        String path = "D:\\";
        for(String s : subFiles(path)){
            System.out.println(s);
        }
    }

    String path ;

    public static String[] subFiles(String path){
        File file = new File(path);
        if(file.isDirectory()) {
            return file.list();
        }
        return null;
    }

    public static void Loop(String path){
        File file = new File(path);
        System.out.println(path);
        while(file.isDirectory()){
            if(subFiles(path) != null){
                for(String s : subFiles(path)){
                    path += s;
                    path += "\\";
                    System.out.print(" ");
                    System.out.println(s);
                    Loop(path);
                }
            }
        }
    }

}
