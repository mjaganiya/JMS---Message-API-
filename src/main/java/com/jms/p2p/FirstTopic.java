package com.jms.p2p;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class FirstTopic {
    public static void main(String[] args) throws Exception {
        InitialContext initialContext = new InitialContext();
        ConnectionFactory connectionFactory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession();
        Topic topic = (Topic) initialContext.lookup("topic/testTopic");

        MessageProducer producer = session.createProducer(topic);
        TextMessage textMessage = session.createTextMessage("Hi I am Test Message Value. ");
        MessageConsumer consumer1 = session.createConsumer(topic);
        MessageConsumer consumer2 = session.createConsumer(topic);
        MessageConsumer consumer3 = session.createConsumer(topic);
        producer.send(textMessage);
        connection.start();

        TextMessage textMessage1 = (TextMessage) consumer1.receive();
        TextMessage textMessage2 = (TextMessage) consumer2.receive();
        TextMessage textMessage3 = (TextMessage) consumer3.receive();

        System.out.println("Message Recieved From Consumer 1: " +textMessage1.getText());
        System.out.println("Message Recieved From Consumer 2: " +textMessage2.getText());
        System.out.println("Message Recieved From Consumer 3: " +textMessage3.getText());

        connection.close();
        initialContext.close();
    }
}
