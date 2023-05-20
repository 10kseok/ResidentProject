package com.nhnacademy.resident_project.domain;

public enum Relationship {
    FATHER("부"),
    MOTHER("모"),
    SPOUSE("배우자"),
    CHILD("자식");

    final private String korName;
    Relationship(String name) {
        this.korName = name;
    }
    public String getKorName() {
        return korName;
    }

    /**
     * 가족관계코드를 영어, 한글 상관없이 입력받아서 확인
     * @param relation 가족관계코드 ex_ father, mother, spouse, child, 부, 모, 배우자, 자식
     * @return 정해진 가족관계에 일치하는지 반환
     */
    public static boolean matches(String relation) {
        for (Relationship r : Relationship.values()) {
            if (r.name().equals(relation.toUpperCase()) || r.getKorName().equals(relation)) {
                return true;
            }
        }
        return false;
    }
}
