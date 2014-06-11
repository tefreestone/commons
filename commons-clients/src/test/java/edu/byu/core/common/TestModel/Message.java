package edu.byu.core.common.TestModel;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "message", namespace = "http://ws.byu.edu/namespaces/authentication/example/v1")
@XmlType(namespace = "http://ws.byu.edu/namespaces/authentication/example/v1")
public class Message {

    private String greeting;

    public Message() {
        setGreeting("Hello World");
    }

    public Message(String greeting) {
        setGreeting(greeting);
    }

    @XmlElement(required = true, namespace = "http://ws.byu.edu/namespaces/authentication/example/v1")
    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }
}