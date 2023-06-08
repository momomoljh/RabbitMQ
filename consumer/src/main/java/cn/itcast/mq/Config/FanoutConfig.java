package cn.itcast.mq.Config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.Backoff;

@Configuration
public class FanoutConfig {
    /**
     * fanout交换机
     * @return Fanout类型交换机
     */
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("momo.fanout");
    }

    /**
     *第一个队列
     * @return
     */
    @Bean
    public Queue FanoutQueue1(){
        return new Queue("fanout.queue1");
    }

    /**
     * 第二个队列
     * @return
     */
    @Bean
    public Queue FanoutQueue2(){
        return new Queue("fanout.queue2");
    }

    /**
     * 绑定第一个队列和交换机
     * @param fanoutExchange
     * @param FanoutQueue1
     * @return
     */
    @Bean
    public Binding bindingQueue1(FanoutExchange fanoutExchange,Queue FanoutQueue1){
        return BindingBuilder.bind(FanoutQueue1).to(fanoutExchange);
    }
    /**
     * 绑定第二个队列和交换机
     */
    @Bean
    public Binding bindingQueue2(Queue FanoutQueue2, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(FanoutQueue2).to(fanoutExchange);
    }
}
