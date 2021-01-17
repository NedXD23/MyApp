package ro.mta.se.lab.controller;

import org.junit.*;

import static org.junit.Assert.assertEquals;

public class controllerTest {
    @Before
    public void setUp() throws Exception {
        System.out.println("\nBefore test");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("After test\n");
    }

    @BeforeClass
    static public void init(){
        System.out.println("Start Test\n");
    }

    @AfterClass
    static public void _final(){
        System.out.println("Testing done!");
    }

    @Test
    public void get_celsius_temp() {
        assertEquals("193.37", controller.get_celsius_temp((double) 465.52));
        assertEquals("520.17", controller.get_celsius_temp((double) 792.32));
        assertEquals("553.83", controller.get_celsius_temp((double) 825.98));

    }


}