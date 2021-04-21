package com.kim.freeswitch.cli;

import lombok.Data;
import org.freeswitch.esl.client.inbound.Client;
import org.freeswitch.esl.client.inbound.InboundConnectionFailure;
import org.freeswitch.esl.client.transport.CommandResponse;
import org.freeswitch.esl.client.transport.message.EslMessage;
import org.junit.Test;

import javax.annotation.PostConstruct;

/**
 * @author huangjie
 * @description   freeswitch客户端测试
 * @date 2021-1-4
 */
@Data
public class FreeswitchCliTest {

    private Client client;

    //初始化连接
    @Test
    public void CliConnect() throws InboundConnectionFailure {
        Client cliClient=new Client();
        cliClient.connect("localhost",8021,"ClueCon",10);
        client=cliClient;
    }

    //订阅事件
    @Test
    public void eventSubscription() throws InboundConnectionFailure {
        CliConnect();
        CommandResponse commandResponse = client.setEventSubscriptions("plain", "all");
        System.out.println(commandResponse.getReplyText());
    }

    //执行命令
    @Test
    public void apiCommand() throws InboundConnectionFailure {
        CliConnect();
        EslMessage eslMessage = client.sendSyncApiCommand("status",null );
        System.out.println(eslMessage);
    }

}
