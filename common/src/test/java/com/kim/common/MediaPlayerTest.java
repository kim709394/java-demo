package com.kim.common;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.junit.Test;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @Author kim
 * @Since 2021/7/6
 */
public class MediaPlayerTest {


    /**
     * 使用javazoom框架播放map3音频，无法播放wav、mp4格式的音频
     * */
    @Test
    public void play() throws Exception{
        play("C:\\Users\\PC\\Desktop\\周慧敏-你的爱让我想飞.mp3");
    }

    private void play(String fileName) throws Exception{
        //指定音频文件
        Player player=new Player(new FileInputStream(fileName));
        //播放音频文件，这里会阻塞，直到音乐播放完才会往下执行。
        player.play();


    }


    /**
     * 使用javazoom框架异步播放音频文件，随时可以停止
     * */
    @Test
    public void playSync() throws Exception{
        //封装音频文件给播放器
        Player player=new Player(new FileInputStream("C:\\Users\\PC\\Desktop\\周慧敏-你的爱让我想飞.mp3"));
        //异步播放音频
        Runnable run= ()->{
            try {
                player.play();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        };
        Thread thread=new Thread(run);
        thread.start();
        //主线程控制播放时间和播放结束操作
        Thread.sleep(10*1000);   //播放十秒
        player.close();   //播放结束
        Thread.sleep(10*1000);
        System.out.println(player.getPosition());
        System.out.println(player.isComplete());
    }



    /***
     * jdk原生api播放音频,只能播放wav格式的音频
     * */
    private void playByJdk(String fileName) throws Exception{
        try(AudioInputStream as = AudioSystem.getAudioInputStream(new File(fileName))){
            AudioFormat format = as.getFormat();
            SourceDataLine sdl = null;

            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            sdl = (SourceDataLine) AudioSystem.getLine(info);
            sdl.open(format);
            sdl.start();
            int len = 0;
            byte[] b = new byte[1024*1024];
            while ((len=as.read(b))> 0) {
                sdl.write(b, 0, len);
            }
            sdl.drain();
            //关闭SourceDataLine
            sdl.close();
        }

    }

    @Test
    public void playWavByJdk() throws Exception{
        playByJdk("C:\\Users\\PC\\Desktop\\周慧敏-你的爱让我想飞.wav");

    }

    /**
     * 原生jdk异步调用wav音频
     * */
    @Test
    public void playWavByJdkSync() throws Exception{
        final SourceDataLine[] sourceDataLine = {null};
        new Thread(()->{
            try(AudioInputStream as = AudioSystem.getAudioInputStream(new File("C:\\Users\\PC\\Desktop\\周慧敏-你的爱让我想飞.wav"))){
                AudioFormat format = as.getFormat();
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
                sourceDataLine[0] = (SourceDataLine) AudioSystem.getLine(info);
                sourceDataLine[0] .open(format);
                sourceDataLine[0] .start();
                int len = 0;
                byte[] b = new byte[1024*1024];
                while ((len=as.read(b))> 0) {
                    sourceDataLine[0] .write(b, 0, len);
                }
                sourceDataLine[0] .drain();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            } catch (IOException | UnsupportedAudioFileException e) {
                e.printStackTrace();
            }

        }).start();
        Thread.sleep(10*1000);
        sourceDataLine[0].stop();
        sourceDataLine[0].close();
        Thread.sleep(10*1000);
    }


}
