package com.project.sms;

import jakarta.persistence.*;

@Entity
@Table(name = "teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teacher_seq")
    @SequenceGenerator(name = "teacher_seq", sequenceName = "teacher_seq", allocationSize = 1)
    private Long id;

    private String name;
    private String email;
    private String password;
    private String subject; 

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
}
