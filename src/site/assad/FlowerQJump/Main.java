package site.assad.FlowerQJump;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Author: Al-assad 余林颖
 * E-mail: yulinying_1994@outlook.com
 * Date: 2018/1/2 20:06
 * Description: 主程序
 */
public class Main extends JFrame{

    //配置文件路径
    private final static String CONF_PATH = "./conf.xml";

    //屏幕实际尺寸
    private int realHeight = 1920;
    private int realWidth = 1080;

    //屏幕缩放尺寸
    private int resizeHeight = 1000;
    private int resizeWidth = 580;

    //图像视图
    private JPanel imagePanel;

    //点对象
    private Point firstPoint = new Point(0,0);
    private Point secondPoint = new Point(0,0);

    //绘制图片地址
    private final String imagePath = AdbCaller.getScreenLocalPath();

    //一个流程中的点击累计
    private boolean next = false;


    public Main(){

        //读取换件获取参数
        ConfXMLReader conf = new ConfXMLReader(CONF_PATH);
        Configure.ADB_PATH = conf.getAdbPath();
        Configure.MAGIC_NUMBER = conf.getMagicNumber();
        Configure.INTERNAL_TIME = conf.getInternelTime();
        Configure.SCREEN_HEIGHT = conf.getScreenHeight();
        resizeHeight = Configure.SCREEN_HEIGHT;


        AdbCaller.printScreen();
        initScreenshotSize();
        this.setSize(resizeWidth,resizeHeight);

        //初始化绘制面板
        imagePanel = new JPanel(){
            protected void paintComponent(Graphics g){
                super.paintComponent(g);

                BufferedImage image = null;
                try {
                    image = ImageIO.read(new File(imagePath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                BufferedImage resizeImage = zoomInImage(image,resizeWidth,resizeHeight);
                g.drawImage(resizeImage,0,0,resizeImage.getWidth(),resizeImage.getHeight(),null);

            }
        };

        this.add(imagePanel);

        //添加绘制面板事件
        imagePanel.addMouseListener(new MouseListener(){

            @Override
            public void mouseClicked(MouseEvent e) {

                //获取两个点，并计算实际距离
                if(!next){
                    firstPoint.setLocation(e.getX(),e.getY());
                    next = !next;
                }else{
                    secondPoint.setLocation(e.getX(),e.getY());

                    double resizeDistance = getDistance(firstPoint,secondPoint);
                    int realDistance =(int)( resizeDistance * realHeight / resizeHeight);

                    //计算按压时间
                    int pressMills = (int) (realDistance * Configure.MAGIC_NUMBER);

                    //adb 操作：发送长按事件、重新获取截图
                    AdbCaller.screenPress(pressMills);
                    AdbCaller.printScreen();

                    //重新刷新组件
                    imagePanel.validate();
                    imagePanel.repaint();

                    //打印调试信息
                    System.out.println(e.getX()+" "+e.getY());
                    System.out.println(firstPoint+" "+secondPoint+" "+realDistance + "step " + pressMills + "ms");

                    next = !next;
                }


            }
            @Override
            public void mousePressed(MouseEvent e) {

            }
            @Override
            public void mouseReleased(MouseEvent e) {

            }
            @Override
            public void mouseEntered(MouseEvent e) {

            }
            @Override
            public void mouseExited(MouseEvent e) {

            }
        });


    }



    //初始化截图实际的尺寸和缩放尺寸
    private void initScreenshotSize(){
        try {
            BufferedImage image = ImageIO.read(new FileInputStream(AdbCaller.getScreenLocalPath()));
            realHeight = image.getHeight();
            realWidth = image.getWidth();
            resizeWidth = ( resizeHeight * realWidth ) / realHeight;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //缩放图片
    private BufferedImage zoomInImage(BufferedImage originalImage, int width, int height) {
        BufferedImage newImage = new BufferedImage(width, height, originalImage.getType());
        Graphics g = newImage.getGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return newImage;
    }

    //计算2点之间的距离
    private double getDistance(Point a,Point b){
        return Math.sqrt((a.getX() - b.getX()) * (a.getX() - b.getX()) + (a.getY() - b.getY()) * (a.getY() - b.getY()));
    }


    public static void main(String[] args){
        JFrame main = new Main();
        main.setTitle("跳一跳，花Q！ -- by Al-assad");
        main.setLocationRelativeTo(null);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setVisible(true);
    }




}
