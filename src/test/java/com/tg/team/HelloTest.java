package com.tg.team;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloTest {
    @Test
    public void shouldReturnHello() {

        Hello hello = new Hello();
        assertEquals("hello", hello.greet());
    }

}
