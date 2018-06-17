package com.example.demo.model;

import java.util.LinkedList;
import java.util.List;

public class CourseGradeVO {
    //类型
    public List<String> name;
    //成绩
    public List<Integer> value;
    //课次
    public List<Integer> number;

    public CourseGradeVO(){
        this.name = new LinkedList<>();
        this.value = new LinkedList<>();
        this.number = new LinkedList<>();
    }
    public void addCourse(String name,Integer grade){
        if (this.name.contains(name)){
            int index = this.name.indexOf(name);
            int number = this.number.get(index);
            int avg = (this.value.get(index)*number+grade)/(number+1);
            number+=1;
            this.number.set(index,number);
            this.value.set(index,avg);
        }else {
            this.name.add(name);
            this.value.add(grade);
            this.number.add(1);
        }
    }
}
