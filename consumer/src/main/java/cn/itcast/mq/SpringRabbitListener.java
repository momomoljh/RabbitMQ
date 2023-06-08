package cn.itcast.mq;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SpringRabbitListener {
    @RabbitListener(queues = "simple.queue")
    public void listenSimpleQueueMessage(String msg){
        System.out.println("接收到【"+msg+"】");
    }
  /*  若先启动生产者端发送消息，再启动消费者端接收消息，
    则最先连接到RabbitMQ服务器的消费者将接收所有的消息，所以通常而言需要先启动消费者端；
    而先启动消费者端时，若将配置类置于生产者端，则可能会出现消费者端中使用，
    但尚未被配置到RabbitMQ的配置（若配置类在生产者端的话），
    所以将配置类置于消费者端更有利于测试代码，另外，在项目启动时就进行配置显然是很好的选择*/

    @RabbitListener(queues = "work.queue")
    public void listenWorkQueue1Message(String msg) throws InterruptedException {
        System.out.println("消费者1接收到【"+msg+"】"+LocalDateTime.now());
        Thread.sleep(200);
    }
    @RabbitListener(queues = "work.queue")
    public void listenWorkQueue2Message(String msg) throws InterruptedException {
        System.out.println("消费者2接收到【"+msg+"】"+ LocalDateTime.now());
        Thread.sleep(20);
    }
    @RabbitListener(queues = "fanout.queue1")
    public void listenFanoutQueue1(String msg) {
        System.out.println("消费者1接收到Fanout消息：【" + msg + "】");
    }

    @RabbitListener(queues = "fanout.queue2")
    public void listenFanoutQueue2(String msg) {
        System.out.println("消费者2接收到Fanout消息：【" + msg + "】");
    }
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue1"),
            exchange = @Exchange(name = "momo.direct"),
            key = {"red","blue"}
    ))
    public void listenDirectQueue1(String msg) {
        System.out.println("消费者1接收到direct消息：【" + msg + "】");
    }
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue2"),
            exchange = @Exchange(name = "momo.direct"),
            key = {"red","yellow"}
    ))
    public void listenDirectQueue2(String msg) {
        System.out.println("消费者2接收到direct消息：【" + msg + "】");
    }
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.queue1"),
            exchange = @Exchange(name = "momo.topic", type = ExchangeTypes.TOPIC),
            key = "china.#"
    ))
    public void listenTopicQueue1(String msg){
        System.out.println("消费者接收到topic.queue1的消息：【" + msg + "】");
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.queue2"),
            exchange = @Exchange(name = "momo.topic", type = ExchangeTypes.TOPIC),
            key = "#.news"
    ))
    public void listenTopicQueue2(String msg){
        System.out.println("消费者接收到topic.queue2的消息：【" + msg + "】");
    }
}
