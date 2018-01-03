package site.assad.FlowerQJump;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
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
    //操作面板
    private JPanel operationPanel;

    //绘制图片地址
    private final String imagePath = AdbCaller.getScreenLocalPath();



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

        imagePanel = new ImagePanel();
        operationPanel = new OperationPanel();

        //面板组装
        Container content = this.getContentPane();
        content.setLayout(null);

        content.add(operationPanel);
        content.add(imagePanel);
    }


    public static void main(String[] args){
        JFrame main = new Main();
        main.setTitle("跳一跳，花Q！ -- by Al-assad");
        main.setLocationRelativeTo(null);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setResizable(false);
        main.setVisible(true);
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






    /**
     * 绘制面板类
     * */
    class ImagePanel extends JPanel{

        public ImagePanel(){
            this.setBounds(0,0,resizeWidth,resizeHeight);
        }

        //绘制截图
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
    }


    /**
     * 操作面板类
     * */
    class OperationPanel extends JPanel{

        //点对象
        private Point firstPoint = new Point(0,0);
        private Point secondPoint = new Point(0,0);
        private Point curPoint = new Point(firstPoint.x,firstPoint.y);

        //一个流程中的点击累计
        private boolean next = false;

        //TODO: 图形参数保存到Configure中
        //绘制参数
        private final int pointWidth = 20;
        private final int pointHeight = 14;
        private final Color pointColor = Color.RED;
        private final Color correctColor = Color.GREEN;
        private final double standardAngle = 30;   //俯角
        private final double deviation = 0.9;  //误差允许



        public OperationPanel(){

            this.setBounds(0,0,resizeWidth,resizeHeight);
            this.setOpaque(false);  //设置该面板背景透明

            //操作面板添加事件监听器
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //获取两个点，并计算实际距离
                    if(!next){
                        firstPoint.setLocation(e.getX(),e.getY());
                        next = !next;
                    }else{
                        secondPoint.setLocation(e.getX(),e.getY());

                        double resizeDistance = CalculateUtils.getDistance(firstPoint,secondPoint);
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
                        System.out.println("current["+e.getX()+","+e.getY()+"] "
                                + "first:["+firstPoint.x+","+firstPoint.y+"] "
                                + "second:["+secondPoint.x+","+secondPoint.y+"] "
                                + "realDistance:" + realDistance + "px "
                                + "pressMills:"+pressMills + "ms");

                        next = !next;
                    }
                }
            });
            this.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    curPoint.setLocation(e.getX(),e.getY());
                    validate();
                    repaint();
                }
            });

        }

        //辅助标识绘制
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            g.setColor(pointColor);
            g.fillOval(curPoint.x - pointWidth/2,curPoint.y - pointHeight/2,pointWidth,pointHeight);
            if(firstPoint.x != 0 && firstPoint.y != 0 ){
                if(next){
                    //当角度误差正确时，以correctColor绘制当前点和连线
                    if(CalculateUtils.checkDeviation(firstPoint,curPoint,standardAngle,deviation)){
                        g.setColor(correctColor);
                        g.fillOval(curPoint.x - pointWidth/2,curPoint.y - pointHeight/2,pointWidth,pointHeight);
                    }
                    g.fillOval(firstPoint.x - pointWidth/2,firstPoint.y - pointHeight/2,pointWidth,pointHeight);
                    g.drawLine(firstPoint.x,firstPoint.y,curPoint.x,curPoint.y);
                }

            }
        }




    }






}
