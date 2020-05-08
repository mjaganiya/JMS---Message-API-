package com.jms.p2p;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Enumeration;

public class QueueBrowserExample {
    public static void main(String[] args) {
        InitialContext initialContext = null;
        Connection connection = null;
        try {
            initialContext = new InitialContext();
            //step 1: create connection
            ConnectionFactory connectionFactory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
            connection = connectionFactory.createConnection();
            //step 2: create session
            Session session = connection.createSession();
            //step 3: create Queue
            Queue queue = (Queue) initialContext.lookup("queue/testQueue");

            //step 4: create Producer
            MessageProducer producer = session.createProducer(queue);
            //step 5: send Message
            TextMessage textMessage1 = session.createTextMessage("Hi I am Test Message 1 Value. ");
            TextMessage textMessage2 = session.createTextMessage("hi I am test Message 2 Value. ");
            producer.send(textMessage1);
            producer.send(textMessage2);

            QueueBrowser queueBrowser = session.createBrowser(queue);
            Enumeration enumeration = queueBrowser.getEnumeration();
            while (enumeration.hasMoreElements()) {
                TextMessage textMessage = (TextMessage) enumeration.nextElement();
                System.out.println("Message Browse : " + textMessage.getText());
            }
            
            //step 6: create Consumer
            MessageConsumer consumer = session.createConsumer(queue);
            connection.start();
            //step 7: start consumer
            TextMessage messageRecieved = (TextMessage) consumer.receive(5000);
            System.out.println("Message Recieved : " + messageRecieved.getText());

            messageRecieved = (TextMessage) consumer.receive(5000);
            System.out.println("Message Recieved : " + messageRecieved.getText());

        } catch (NamingException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            if (initialContext != null) {
                try {
                    initialContext.close();
                } catch (NamingException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
