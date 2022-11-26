package units;

import java.io.File;

public class traceFiles {
    String s = "D:\\祝超\\";

    public static void main(String[] args) {
        File file = new File("D:\\files");
        if(file.isDirectory()) {
            for (String x : file.list()) {
                System.out.println(x);
            }
        }
    }

}
