package com.jms.p2p;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.activemq.artemis.jndi.ActiveMQInitialContextFactory;

import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.InitialContext;

public class JMSContextExample {
    public static void main(String[] args) throws  Exception{
        InitialContext initialContext = new InitialContext();
        Queue queue = (Queue) initialContext.lookup("queue/testQueue");

        try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
            JMSContext jmsContext = cf.createContext()){
             jmsContext.createProducer().send(queue,"Raise Awake and stop not till the goal is reached.");
            String message = jmsContext.createConsumer(queue).receiveBody(String.class);
            System.out.println(message);
        }


    }
}
