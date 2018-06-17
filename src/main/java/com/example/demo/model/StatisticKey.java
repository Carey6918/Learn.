package com.example.demo.model;

import javax.persistence.Embeddable;
import java.util.Date;

@Embeddable
public class StatisticKey {
    public Date date;
    public String id;

}
