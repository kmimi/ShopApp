package org.shop.retail.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlRootElement(name = "customer")
@XmlType(propOrder = {"name", "phoneNumber", "register"})
public class Customer {

    private Long id;

    private String name;

    private String phoneNumber;

    private List<Long> register;
    
    
    @XmlAttribute
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "phoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    @XmlElement(name = "register")
    public List<Long> getRegister() {
        return register;
    }

    public void setRegister(List<Long> register) {
    	this.register = new ArrayList<Long>();
    	this.register.addAll(register);
    }
 
    public void addToRegister(Long item_id) {
    	this.register.add(item_id);
    }
    
    public void removeFromRegister(Long item_id) {
    	this.register.remove(item_id);
    }
}
