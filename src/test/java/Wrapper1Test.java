import org.junit.jupiter.api.Test;
import system_model.Wrapper1;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Wrapper1Test {

    @Test
    public void testConvertToLowUpForm(){
        assertEquals(Wrapper1.convertToLowUpForm("a'b'c'"), "abc");
    }
}
