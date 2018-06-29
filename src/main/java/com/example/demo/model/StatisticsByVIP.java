package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "StatisticsByVIP")
public class StatisticsByVIP {
    @Id
    @Column(name = "value")
    public int level;
    @Column(name = "profit")
    public int profit;
    @Column(name = "sale")
    public double sale;//应当保持持平

}
