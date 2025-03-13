package hello.servlet.basic;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HelloData { //Create an object that can be parsed in JSON format

    private String username;
    private int age;
}

