package lesson4.spoonaccular.test;

import lesson4.spoonaccular.AbstractTest;

public class BaseTest extends AbstractTest {
    void test() throws Exception {
        System.out.println(getResource("text.txt"));
    }
}
