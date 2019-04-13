package cn.yeamin.actions.simple.produces;


import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *  简单队列的消息生产者
 */
public class Send {

    /**
     * 设置队列名称
     */
    private final static String QUEUE_NAME = "hello";


    public static void main(String[] args) throws IOException, TimeoutException {

        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置连接主机名
        factory.setHost("localhost");
        //设置端口号,不设置默认为5672
        factory.setPort(5672);
        /*
         * 如果可以通过连接工厂创建一个连接,则继续在连接基础上继续创建通道.
         * 这里我们可以使用try-with-resources语句，因为Connection和Channel都实现了java.io.Closeable。
         * 这样我们就不需要在代码中明确地关闭它们。
         */
        try (Connection connection = factory.newConnection();
             // 创建通道
             Channel channel = connection.createChannel()) {

            /*
             * 为了发送消息成功,我们必须声明一个队列供我们发送,队列只声明一次,不可能存在重复队列
             *    参数1: queue表示队列名称
             *    参数2: durable表示是否持久化
             *    参数3: exclusive表示仅创建者可以使用的私有队列，断开后自动删除
             *    参数4: autoDelete表示当所有消费客户端连接断开后，是否自动删除队列
             *    参数5: arguments表示其他的构造参数,为队列构造而准备
             */
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            // 要发送的消息
            String message = "Hello World, lt, Welcome RabbitMQ!";
            /*
             *  最基本的消息发送
             *    参数1: exchange表示交换机
             *    参数2: routingKey表示路由Key
             *    参数3: props表示息的其他参数
             *    参数4: autoDelete表示当所有消费客户端连接断开后，是否自动删除队列
             *    参数5: body表示息体,是个字节数组,意味着可以传递任何数据
             */
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            //消息发送方的日志打印
            System.out.println(" [x] Sent '" + message + "'");

        }

    }


}
