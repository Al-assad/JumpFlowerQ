package site.assad.FlowerQJump;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Author: Al-assad 余林颖
 * E-mail: yulinying_1994@outlook.com
 * Date: 2018/1/2 19:02
 * Description: adb 调用类
 */
public class AdbCaller {

    private static String adbPath = Configure.ADB_PATH;

    private static String screenLocalPath = Configure.SCREENSHOT_LOCAL_PATH;

    private static String screenRemotePath = Configure.SCREENSHOT_REMOTE_PATH;


    /**
     * 调用 adb 长按屏幕
     *  @param timeMilli 长按时间
     * */
    public static void screenPress(double timeMilli){
        String command = adbPath + " shell input touchscreen swipe 233 250 233 250 "+ (int)timeMilli;
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String s;
            while ((s = bufferedReader.readLine()) != null)
                System.out.println(s);
            process.waitFor();

            //跳动画间隔
            Thread.sleep(Configure.INTERNAL_TIME);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     *  调用 adb 获取截图
     * */
    public static void printScreen(){
        //手机截图保存在手机
        String command1 = adbPath + " shell screencap -p " + screenRemotePath ;
        //将截图文件复制到本地位置
        String command2 = adbPath + " pull " + screenRemotePath + " " + screenLocalPath;

        try {
            Process process1 = Runtime.getRuntime().exec(command1);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process1.getErrorStream()));
            String s;
            while ((s = bufferedReader.readLine()) != null)
                System.out.println(s);
            process1.waitFor();

            Process process2 = Runtime.getRuntime().exec(command2);
            bufferedReader = new BufferedReader(new InputStreamReader(process2.getErrorStream()));
            while ((s = bufferedReader.readLine()) != null)
                System.out.println(s);
            process2.waitFor();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static String getAdbPath() {
        return adbPath;
    }

    public static void setAdbPath(String adbPath) {
        AdbCaller.adbPath = adbPath;
    }

    public static String getScreenLocalPath() {
        return screenLocalPath;
    }

    public static void setScreenLocalPath(String screenLocalPath) {
        AdbCaller.screenLocalPath = screenLocalPath;
    }

    public static String getScreenRemotePath() {
        return screenRemotePath;
    }

    public static void setScreenRemotePath(String screenRemotePath) {
        AdbCaller.screenRemotePath = screenRemotePath;
    }
}
