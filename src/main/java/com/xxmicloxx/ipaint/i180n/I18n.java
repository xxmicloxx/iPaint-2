package com.xxmicloxx.ipaint.i180n;

import com.xxmicloxx.ipaint.MainPlugin;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mewking
 */
public class I18n {

    private Map<String, String> i18ns = new HashMap<String, String>();
    private String language;

    private static I18n i18n;

    private I18n() {}

    public static I18n i18n() {
        if (i18n == null) {
            i18n = new I18n();
        }
        return i18n;
    }

    public void reloadLanguage(String s) {
        InputStream is;
        if (s.equalsIgnoreCase("de_DE")) {
            is = getClass().getResourceAsStream("/com/xxmicloxx/ipaint/i18n/de_DE");
        } else if (s.equalsIgnoreCase("en_US")) {
            is = getClass().getResourceAsStream("/com/xxmicloxx/ipaint/i18n/en_US");
        } else {
            return;
        }
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        } catch (UnsupportedEncodingException ignored) {}
        String line;
        try {
            int state = 0;
            while ((line = br.readLine()) != null) {
                if (!line.trim().startsWith("#")) {
                    if (line.trim().startsWith(";")) {
                        state++;
                    } else {
                        String[] lspl = line.trim().split("=", 2);
                        if (state == 1) {
                            if (line.trim().startsWith("name=")) {
                                language = lspl[1];
                            }
                        } else if (state == 2) {
                            if (line.trim().contains("=")) {
                                if (!lspl[0].startsWith("§")) {
                                    i18ns.put(lspl[0], "\u00A7f" + lspl[1].replace("{nl}", "\n"));
                                } else {
                                    i18ns.put(lspl[0].substring(1), lspl[1].replace("{nl}", "\n"));
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getLanguageSelectorIndex() {
        if (MainPlugin.getInstance().getConfig().getLanguage().equalsIgnoreCase("en_US")) {
            return 0;
        } else if (MainPlugin.getInstance().getConfig().getLanguage().equalsIgnoreCase("en_US")) {
            return 1;
        }
        return 0;
    }

    public String getLanguage() {
        return language;
    }

    public String get(String i18n) {
        return i18ns.get(i18n);
    }

    public String get(String s, String[] strings) {
        if (strings.length % 2 != 0) {
            throw new IllegalArgumentException("Arguments invalid.");
        }
        String pre = get(s);
        for (int i = 0; i < strings.length; i += 2) {
            if (strings[i] != null && strings[i + 1] != null) {
                pre = pre.replace(strings[i], strings[i + 1]);
            }
        }
        return pre;
    }
}
