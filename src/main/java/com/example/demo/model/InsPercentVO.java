package com.example.demo.model;

import java.util.LinkedList;
import java.util.List;

public class InsPercentVO {
    public List<String> name;
    public List<Integer> value;

    public InsPercentVO() {
        name=new LinkedList<>();
        value = new LinkedList<>();
    }


    public List<String> getInsName() {
        return name;
    }

    public void setInsName(List<String> insName) {
        this.name = insName;
    }

    public List<Integer> getPercent() {
        return value;
    }

    public void setPercent(List<Integer> percent) {
        this.value = percent;
    }
    public void addIns(String name, Integer percent){
        if (this.name.contains(name)){
            int index = this.name.indexOf(name);
            int cur = this.value.get(index);
            cur=cur+1;
            this.value.set(index,cur);
        }else {
            this.name.add(name);
            this.value.add(percent);
        }
    }
}
