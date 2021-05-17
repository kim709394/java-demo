package com.kim.common;

import com.kim.common.util.ExecCmdUtil;
import org.junit.Test;

import java.io.IOException;

/**
 * @Author kim
 * @Since 2021/5/13
 */
public class CmdTest {


    @Test
    public void sshTest() throws IOException {
        String sshCon="ssh szhw@129.214.149.192";
        String passwrd="Changeme_123";
        String no="n";
        String exec = ExecCmdUtil.exec("ipconfig");
        System.out.println(exec);
    }
}
