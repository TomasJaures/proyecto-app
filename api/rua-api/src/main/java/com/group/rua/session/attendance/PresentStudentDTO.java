package com.group.rua.session.attendance;

public class PresentStudentDTO {
    public Integer userId;
    public String userName;
    public String lastName1;
    public String lastName2;
    public String mail;
    public String status;

    public PresentStudentDTO(Integer userId, String userName, String lastName1, String lastName2, String mail, String status) {
        this.userId = userId;
        this.userName = userName;
        this.lastName1 = lastName1;
        this.lastName2 = lastName2;
        this.mail = mail;
        this.status = status;
    }
}