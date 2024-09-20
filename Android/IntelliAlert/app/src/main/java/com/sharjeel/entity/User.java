package com.sharjeel.entity;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private  String cnic;
    private String pw;
    private String name;
    private String gender;
    private String type;
    private int age;
    private String contact;
    private int deptId;
    private String department;
    private String designation;
    private String address;
    private int status;
    private String StatusStr;

    public User() {
        this.cnic = "";
        this.pw = "";
        this.name = "";
        this.gender="";
        this.type="";
        this.age = 0;
        this.contact = "";
        this.deptId=0;
        this.department = "";
        this.designation = "";
        this.address = "";
        this.status=0;
    }
    // at registration
    public User(int id, String cnic, String pw, String name, String gender, String type, int age, String contact, String designation, String address) {
        this.id = id;
        this.cnic = cnic;
        this.pw = pw;
        this.name = name;
        this.gender = gender;
        this.type = type;
        this.age = age;
        this.contact = contact;
        this.deptId=0;
        this.department = "";
        this.designation = designation;
        this.address = address;
        this.status=0;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusStr() {
        return (this.status==0?"Active":"In-Active");
    }
}
