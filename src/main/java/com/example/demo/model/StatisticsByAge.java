package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "StatisticsByAge")
public class StatisticsByAge {
    @Id
    @Column(name = "age")
    public String age;
    @Column(name = "profit")
    public int profit;
}
