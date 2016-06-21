package com.he.func;
import android.app.Application;
import android.content.Context;

import com.he.config.HeTask;

import java.io.File;

public class HeApplication extends Application{

    public static Context getContext;
	@SuppressWarnings("static-access")
	@Override
    public void onCreate() {
        super.onCreate();
        getContext = getApplicationContext();
    }

    private File setImagePath(){
        String dbDir = HeTask.getInstance().sdCardRoot();
        if(null == dbDir){
            return null;
        }
        dbDir = dbDir + "/HE/Images";
        File dirFile = new File(dbDir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        return dirFile;
    }
}
