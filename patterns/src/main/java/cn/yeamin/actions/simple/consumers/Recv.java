package cn.yeamin.actions.simple.consumers;

import com.rabbitmq.client.*;


/**
 *  简单队列的消息消费者
 */
public class Recv {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置RabbitMQ主机名
        factory.setHost("localhost");
        // 新建连接
        Connection connection = factory.newConnection();
        // 新建通道
        Channel channel = connection.createChannel();

        //绑定队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");


        // 创建消费者,消费消息
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };

        /**
         *
         *  消费者消费
         * 参数1 ：queue队列名
         * 参数2 ：autoAck 是否自动ACK
         * 参数3 ：callback消费者对象的一个接口，用来配置回调
         *
         */
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });

    }
}
