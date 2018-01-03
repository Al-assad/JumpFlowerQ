package site.assad.FlowerQJump;

import java.awt.*;

/**
 * Author: Al-assad 余林颖
 * E-mail: yulinying_1994@outlook.com
 * Date: 2018/1/3 18:46
 * Description: 点计算集合类
 */
public class CalculateUtils {

    /**
     * 计算2点之间的距离
     *  @param a
     *  @param b
    * */
    public static double getDistance(Point a, Point b){
        return Math.sqrt((a.getX() - b.getX()) * (a.getX() - b.getX()) + (a.getY() - b.getY()) * (a.getY() - b.getY()));
    }

    /**
     * 计算坐标 a 到 b 的水平角
     *  @param a
     *  @param b
     * */
    public static double getAngle(Point a,Point b){
        return Math.toDegrees( Math.atan(Math.abs((double)(a.y-b.y)) / Math.abs((double)(a.x-b.x))));
    }

    /**
     * 计算坐标 a 到 b 的水平角是否在误差内
     *  @param a
     *  @param b
     *  @param standardAngle 标准水平角
     *  @param deviation 误差允许
     *  @return 是否符合误差允许
     * */
    public static boolean checkDeviation(Point a,Point b,double standardAngle,double deviation){
        return Math.abs( getAngle(a,b) - standardAngle) < deviation;
    }

}
