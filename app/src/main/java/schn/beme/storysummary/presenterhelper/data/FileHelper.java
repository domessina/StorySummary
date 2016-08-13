package schn.beme.storysummary.presenterhelper.data;

import java.io.File;

import schn.beme.storysummary.MyApplication;

/**
 * Created by Dorito on 28-07-16.
 */
public class FileHelper {

    public static void createFile(String name){

        File file = new File(MyApplication.getAppContext().getFilesDir(), name);
    }

}
