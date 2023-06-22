package br.ce.wcaquino.taskbackend.utils;
import java.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;

public class DateUtilsTest {

    @Test
    public void deveRetornarTrueParaDatasFuturas(){
        LocalDate data = LocalDate.now().plusDays(1);
        System.out.println(data);
        Assert.assertTrue(DateUtils.isEqualOrFutureDate(data));
    }

    @Test
    public void deveRetornarTrueParaDataAtual(){
        LocalDate data = LocalDate.now().plusDays(1);
        System.out.println(data);
        Assert.assertTrue(DateUtils.isEqualOrFutureDate(data));
    }

    @Test
    public void deveRetornarFalseParaDatasPassadas(){
        LocalDate data = LocalDate.now().plusDays(-1);
        System.out.println(data);
        Assert.assertFalse(DateUtils.isEqualOrFutureDate(data));
    }

}