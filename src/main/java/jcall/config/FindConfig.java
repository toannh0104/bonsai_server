/**
 * Created on 2007/02/15
 *
 * @author wang Copyrights @kawahara lab
 */
package jcall.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class FindConfig {

    private static FindConfig aConfigSingleton;
    private Properties props = null;
    private static final boolean debug = false;
    private static final String ConfigUrl = "/config.properties"; // same
                                                                                            // as

    // ConfigUrl =
    // "./config.properties"

    public FindConfig() {
        try {
            props = new Properties();
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            java.io.InputStream inputstream = null;
            if (cl != null) {
                // inputstream = cl.getResourceAsStream(ConfigUrl);
                inputstream = getClass().getResourceAsStream(ConfigUrl);
            }
            if (inputstream == null) {
                System.out.println("inputstream is null in FindInConfig ");
            } else {
                props.load(inputstream);
            }
        } catch (Exception exception) {
            System.out.println("file not find in FindInConfig" + exception.toString());
            return;
        }
    }

    public static FindConfig getConfig() {
        if (aConfigSingleton == null) aConfigSingleton = new FindConfig();
        return aConfigSingleton;
    }

    public String findStr(String s) {
        String s1;
        s1 = props.getProperty(s);
        s1 = s1.replace("./", "/");
        if (s1 == null) {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            java.io.InputStream inputstream = null;
            if (cl != null) {
                // inputstream = cl.getResourceAsStream(ConfigUrl);
                inputstream = getClass().getResourceAsStream(ConfigUrl);

            }
            // java.io.InputStream inputstream =
            // getClass().getResourceAsStream(ConfigUrl);
            if (inputstream == null) {
                System.out.println("file is null in findstr");
            }
            try {
                props.load(inputstream);
                s1 = props.getProperty(s);
            } catch (IOException e) {
                System.out.println("file not find " + e.toString());
            }
        }
        return s1;
    }

    public static void main(String args[]) throws Exception {
        File file = new File("config.properties");
        BufferedReader fw = new BufferedReader(new FileReader(file));
        System.out.println(fw.readLine());
        if (file == null) {
            System.out.println("file is null,path is wrong");
        }
        String s = getConfig().findStr("FirstExperimentODBC");
        System.out.println(s);

    }

}
