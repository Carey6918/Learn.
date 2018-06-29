package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "StatisticsByCourseNIns")
public class StatisticsByCourseNIns {
    @Id
    @Column(name = "type")
    public String type;
    @Column(name = "top1")
    public Integer top1;

    @Column(name = "topProfit")
    public Double topProfit;

    @Column(name = "totalProfit")
    public Double totalProfit;


}
