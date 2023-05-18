package com.nhnacademy.resident_project;

import com.nhnacademy.resident_project.domain.ResidentRequest;
import org.junit.jupiter.api.Test;

public class TestSomething {
    @Test
    void test1() {
        String t = "true";
        String f = "false";

        if (Boolean.parseBoolean(t)) {
            System.out.println("true true");
        }

        if (Boolean.parseBoolean(f) == false) {
            System.out.println("false false");
        }

        if (Boolean.getBoolean(t)) {
            System.out.println("real true");
        }

        if (Boolean.getBoolean(f) == false) {
            System.out.println("real false");
        }
    }

    @Test
    void test2() {
        String temp = "독고필쌍";
        String temp2 = "god";
        String temp3 = "여";
        System.out.println(temp.matches("[a-zA-Z가-힣]+"));
        System.out.println(temp2.matches("[a-z]+"));
        System.out.println(temp3.matches("남|여"));
    }
}
