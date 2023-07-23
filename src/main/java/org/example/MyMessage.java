package org.example;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyMessage {

    private String name;
    private String message;

}

// ObjectMapper 객체에서 매핑이 제대로 이뤄지기 위해서 DTO 클래스에 getter, setter 메소드가 정의되어 있어야 한다.