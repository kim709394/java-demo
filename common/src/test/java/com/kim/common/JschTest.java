package com.kim.common;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @Author kim
 * @Since 2021/5/13
 */
public class JschTest {

    private Session session;

    //会话建立
    @Test
    public void sessionCreate() throws Exception{
        JSch jSch=new JSch();
        Session szhw = jSch.getSession("szhw", "129.214.149.192");
        szhw.setPassword("Changeme_123");
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");//在代码里需要跳过检测。否则会报错找不到主机
        szhw.setConfig(config); // 为Session对象设置properties
        szhw.setTimeout(30000);
        szhw.connect();
        session=szhw;
    }

    //执行shell命令
    @Test
    public void execShell() throws Exception {
        sessionCreate();
        ChannelExec exec = (ChannelExec)session.openChannel("exec");
        exec.setCommand("ipconfig");
        exec.setInputStream(null);
        exec.setErrStream(System.err);//通道连接错误信息提示
        exec.connect();
        BufferedReader in = new BufferedReader(new InputStreamReader(exec.getInputStream()));
        String msg;
        while ((msg = in.readLine()) != null) {
            System.out.println(msg);
        }
        in.close();
        exec.disconnect();
    }

}
