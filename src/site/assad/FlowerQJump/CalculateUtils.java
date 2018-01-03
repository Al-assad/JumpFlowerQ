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

    /**
     * 计算坐标 o 以水平角所得到的俯角线，与边界的交界点
     * @param o 中心点
     * @param standardAngle 水平角
     * @param borderWidth 边界长度
     * @return 包含4个边界点的数组，顺序为从右上角开始顺时针方向
     * */
    public static Point[] getBorderPoint(Point o,double standardAngle,int borderWidth){

        double v = Math.tan(standardAngle*Math.PI/180);

        Point a = new Point( borderWidth,(int)(o.y - v * ( borderWidth - o.x)));
        Point b = new Point( borderWidth,(int)(o.y + v * ( borderWidth - o.x)));
        Point c = new Point( 0,(int)(o.y + v * o.x));
        Point d = new Point( 0,(int)(o.y - v * o.x));

        Point[] result = {a,b,c,d};
        return result;
    }



}
