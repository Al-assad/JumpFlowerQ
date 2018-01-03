package site.assad.FlowerQJump;

import java.awt.*;

/**
 * Author: Al-assad 余林颖
 * E-mail: yulinying_1994@outlook.com
 * Date: 2018/1/2 19:03
 * Description: 记录配置参数，数据类
 */
public class Configure {

    /** adb 路径 */
    public static String ADB_PATH = "C:\\Al-assad\\bin_dev\\ADB\\adb.exe";

    /** 截图所在本地路径 */
    public static final String SCREENSHOT_LOCAL_PATH = "./Screenshot.png";

    /** 截图所在手机文件路径 */
    public static final String SCREENSHOT_REMOTE_PATH = "/sdcard/Screenshot.png";


    /** 按压时间和距离计算参数*/
    public static double MAGIC_NUMBER = 1.40;

    /** 显示图片缩放高度*/
    public  static int SCREEN_HEIGHT = 1000;

    /** 跳跃间隔 */
    public static int INTERNAL_TIME = 1500;

    /** 辅助点宽度 */
    public static final int POINT_WIDTH = 20;

    /** 辅助点高度 */
    public static  final int POINT_HEIGHT = 14;

    /** 辅助点颜色 */
    public static  final Color POINT_COLOR = Color.RED;

    /** 通过角度校验时，辅助点的颜色 */
    public static  final Color CORRECT_COLOR = Color.GREEN;

    /** 辅助基准线颜色 */
    public static  final Color DATUM_COLOR = Color.BLUE;

    /** 水平俯角 */
    public  static final double STANDARD_ANGLE = 30;

    /** 角度误差允许 */
    public static  final double DEVIATION = 0.8;




}
