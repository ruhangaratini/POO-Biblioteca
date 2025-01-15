package com.example.POO_Biblioteca.model;

import java.util.Calendar;
import java.util.Date;

public class Student extends User {
    private Date registrationExpiration;

    public Student() {
        super();
        this.registrationExpiration = new Date();
    }

    public void renewRegistration() {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.registrationExpiration);
        calendar.add(Calendar.YEAR, 1);
        this.registrationExpiration = calendar.getTime();
    }
}
