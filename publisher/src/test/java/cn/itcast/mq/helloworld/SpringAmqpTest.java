package cn.itcast.mq.helloworld;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class SpringAmqpTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Test
    public void TestSimpleQueue(){
        //队列名称
        String queue = "simple.queue";
        //消息
        String msg = "hello simple queue";
        //发送消息
        rabbitTemplate.convertAndSend(queue,msg);
    }
    @Test
    public void TestWorkQueue() throws InterruptedException {
        //队列名称
        String queue = "work.queue";
        //消息
        String msg = "hello work queue";
        //发送消息
        for(int i =0; i < 50; i++) {
            rabbitTemplate.convertAndSend(queue, msg + i);
            Thread.sleep(20);
        }
    }
    @Test
    public void testFanoutExchange() {
        // 队列名称
        String exchangeName = "momo.fanout";
        // 消息
        String message = "hello, everyone!";
        rabbitTemplate.convertAndSend(exchangeName, "", message);
    }
    @Test
    public void testSendDirectExchange() {
        // 交换机名称
        String exchangeName = "momo.direct";
        // 消息
        String message = "红色警报！日本乱排核废水，导致海洋生物变异，惊现哥斯拉！";
        // 发送消息
        rabbitTemplate.convertAndSend(exchangeName, "blue", message);
    }
    /**
     * topicExchange
     */
    @Test
    public void testSendTopicExchange() {
        // 交换机名称
        String exchangeName = "momo.topic";
        // 消息
        String message = "喜报！孙悟空大战哥斯拉，胜!";
        // 发送消息
        rabbitTemplate.convertAndSend(exchangeName, "china.news", message);
    }
    @Test
    public void testSendMap() throws InterruptedException {
        // 准备消息
        Map<String,Object> msg = new HashMap<>();
        msg.put("name", "Jack");
        msg.put("age", 21);
        // 发送消息
        rabbitTemplate.convertAndSend("simple.queue", msg);
    }
}
