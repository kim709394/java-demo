package com.kim.common.util;

import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 * @author huangjie
 * @date 2018/9/4
 * 运行命令行工具类
 */
public class ExecCmdUtil {


    private static final String CREATE_3RDSESSION_SHELL_SCRIPT="head -n 80 /dev/urandom | tr -dc A-Za-z0-9 | head -c 168";

    /**
     * 执行cmd命令(shell脚本)
     * @param cmd linux sh/windows bat命令
     * @return String 返回打印信息
     */
    public static String exec(String... cmd) throws IOException {
        Process process = null;
        String result="";
        if(System.getProperty("os.name").indexOf("Windows")!=-1){
            if (cmd != null && cmd.length == 1) {
                process = Runtime.getRuntime().exec(cmd[0]);
            }
        }else{
            cmd = ArrayUtils.addAll(new String[]{"/bin/sh", "-c"}, cmd);
        }
        process = Runtime.getRuntime().exec(cmd);
        String print=readProcess(process.getInputStream());
        String err=readProcess(process.getErrorStream());
        return print+" "+err;
    }

    private static String readProcess(InputStream in){
        try ( LineNumberReader print=new LineNumberReader(new InputStreamReader(in,"GBK"))){
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = print.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 执行linux命令(shell脚本)生成3rd_session随机数
     */
    public static String create3rdSessionToken() throws IOException {

        return exec(CREATE_3RDSESSION_SHELL_SCRIPT);
    }
}
