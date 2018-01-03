# PlayJumpJumpWithMouse  
在PC上使用鼠标点击的方式进行微信小游戏“跳一跳”    
---  

## 程序截图
<img src="https://raw.githubusercontent.com/Al-assad/JumpFlowerQ/master/artificial/JumpFLowerQ/Screenshot.png" width="400"/><br />

## 原理
使用ADB进行进行调试，以一定的间隔对手机进行截屏，并返回PC，使用Swing组件绘制该截图，在该截图上通过鼠标点击获取距离，在计算为屏幕长按时间，由ADB发送该屏幕点击事件到手机；

```
adb shell input swipe <x1> <y1> <x2> <y2> [duration(ms)] # 模拟手机长按事件
adb shell screencap <filename>  # 截屏，并到手机
adb pull <remoteFilename> <localPath>  # 将手机屏幕截图推送到PC指定路径
```

##  使用方式
本程序只支持 Android 系统，PC 环境仅仅在 Window，Linux（Ubuntu）中测试通过；  

* 安装 JDK-1.8，或者 JRE-1.8    
* 安装 ADB  
  [ADB-window](https://dl.google.com/android/repository/platform-tools-latest-windows.zip)  
  [ADB-linux](https://dl.google.com/android/repository/platform-tools-latest-linux.zip)
* 下载仓库中 artificial/JumpFlowerQ 中的文件（为编译好的JAR和配置文件）
* 手机在开发者选项中打开“USB调试”，并确定ADB连接通过    
* 在 conf.xml 中更改 <adb-path> 节点为实际安装 ADB 执行程序的路径  
* 双击 JumpFlowerQ.jar 直接执行程序
* 使用操作，点击小人，再点击目标落点，等待大约1s的间隔时间后重复操作  

## 注意事项
* 小米手机，打开“USB调试”后，还要打开下方的“允许模拟点击”  
* 锤子手机，需要关闭“大爆炸功能”  

## 开发环境  
IntellIDEA，JDK-1.8  
  




