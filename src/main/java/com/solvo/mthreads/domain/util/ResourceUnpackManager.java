package com.solvo.mthreads.domain.util;

import com.solvo.mthreads.main.Main;

import java.io.*;

public class ResourceUnpackManager {

    public static File unpack(String pResourceName) {
        File file = null;

        InputStream resourceStream = null;
        OutputStream outFileStream = null;

        try {
            String path = System.getProperty("user.dir")+File.separator+pResourceName;

            file = new File(path);

            if (!file.exists())  {
                resourceStream = Main.class.getClassLoader().getResourceAsStream(pResourceName);

                byte[] buffer = new byte[resourceStream.available()];
                resourceStream.read(buffer);

                file = new File(path);
                outFileStream = new FileOutputStream(file);
                outFileStream.write(buffer);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try {
                if (resourceStream != null) {
                    resourceStream.close();
                }
                if (outFileStream != null) {
                    outFileStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }

}
