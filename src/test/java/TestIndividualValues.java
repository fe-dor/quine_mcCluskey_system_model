import my_system_model.Wrapper2;
import org.junit.Assert;
import org.junit.Test;
import system_model.Wrapper1;

public class TestIndividualValues {

    @Test
    public void testAll0(){
        Assert.assertEquals(Wrapper2.calculate("00000000000000000000000000000000", 5), "");
        Assert.assertEquals(Wrapper2.calculate("0000000000000000", 4), "");
        Assert.assertEquals(Wrapper2.calculate("00000000", 3), "");
    }

    @Test
    public void testAll1(){
        Assert.assertEquals(Wrapper2.calculate("11111111111111111111111111111111", 5), "1");
        Assert.assertEquals(Wrapper2.calculate("1111111111111111", 4), "1");
        Assert.assertEquals(Wrapper2.calculate("11111111", 3), "1");
    }

    @Test
    public void testOneLetter(){
        Assert.assertEquals(Wrapper2.calculate("11111111111111110000000000000000", 5), "a");
        Assert.assertEquals(Wrapper2.calculate("1111111100000000", 4), "a");
        Assert.assertEquals(Wrapper2.calculate("00001111", 3), "A");

        Assert.assertEquals(Wrapper2.calculate("11110000111100000000000000000000", 5), "ac");
        Assert.assertEquals(Wrapper2.calculate("0011001100110011", 4), "C");
        Assert.assertEquals(Wrapper2.calculate("00110011", 3), "B");
    }
}
