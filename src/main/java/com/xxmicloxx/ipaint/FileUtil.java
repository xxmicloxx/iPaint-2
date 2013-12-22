package com.xxmicloxx.ipaint;

import org.apache.commons.io.Charsets;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: Mic
 * Date: 15.11.13
 * Time: 14:59
 */
public class FileUtil {
    public static String readFile(File f) {
        InputStream in = null;
        byte[] b = new byte[0];
        try {
            in = new FileInputStream(f);
            b = new byte[(int) f.length()];
            int len = b.length;
            int total = 0;

            while (total < len) {
                int result = in.read(b, total, len - total);
                if (result == -1) {
                    break;
                }
                total += result;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return new String(b, Charsets.UTF_8);
    }
}
