package com.strong.wind.easyexcel;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
public class FillData {
    private String name;
    private double number;
    private Date date;

    public FillData(String name, double number, Date date) {
        this.name = name;
        this.number = number;
        this.date = date;
    }
}