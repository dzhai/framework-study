package com.github.dzhai.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Orders implements Serializable {
    private Integer id;

    private Integer userId;

    private String number;

    private Date createtime;

    private String note;
   
    //one2one 
    private User user;
    
    //one2many
    private List<OrderDetail> orderdetails;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrderDetail> getOrderdetails() {
		return orderdetails;
	}

	public void setOrderdetails(List<OrderDetail> orderdetails) {
		this.orderdetails = orderdetails;
	}
}