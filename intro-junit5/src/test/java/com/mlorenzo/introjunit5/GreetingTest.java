package com.mlorenzo.introjunit5;

import org.junit.jupiter.api.*;

class GreetingTest {
    Greeting greeting;

    @BeforeAll
    static void beforeClass() {
        System.out.println("Before - I am only called once!!!");
    }

    @BeforeEach
    void setUp() {
        System.out.println("In Before Each...");
        greeting = new Greeting();
    }

    @Test
    void helloWorld() {
        System.out.println(greeting.helloWorld());
    }

    @Test
    void testHelloWorld() {
        System.out.println(greeting.helloWorld("Jhon"));
    }

    @AfterEach
    void tearDown() {
        System.out.println("In After Each...");
    }

    @AfterAll
    static void afterClass() {
        System.out.println("After - I am only called once!!!");
    }
}