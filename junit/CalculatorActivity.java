package examples;

import org.junit.Before;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;

public class calculatorActivity {
    public static  calculator calc;

    @BeforeAll
    static void setUp () {
        calc = new calculator();
    }

    @Test
    public void addTest(){
        int result1=calc.add(5,10);
        int result2=calc.square(5);
        System.out.println(result1);
        System.out.println(result2);
        //Assertions
        assertAll (
                () -> Assertions.assertEquals(18,result1,"The values should add upto 15"),
                () -> Assertions.assertEquals(22,result2,"The values should add upto 25")
        );
    }
}