package parking;

import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

@RunWith(JMockit.class)
public class VipParkingStrategyJMockitTest {

    @Test
    public void testCalculateHourlyPrice_givenSunday_thenPriceIsDoubleOfSundayPrice() {

        /* Exercise 6: Write test case for VipParkingStrategy calculateHourlyPrice
         * by using JMockit to mock static method */
        new MockUp<ParkingLot>() {
            @Mock
            public int getBasicHourlyPrice() {
                return 25;
            }
        };

        VipParkingStrategy vipParkingStrategy = new VipParkingStrategy();
        Integer result = vipParkingStrategy.calculateHourlyPrice();

        assertEquals(50, result.intValue());


    }

    @Test
    public void testCalculateHourlyPrice_givenNotSunday_thenPriceIsDoubleOfNonSundayPrice() {

        /* Exercise 6: Write test case for VipParkingStrategy calculateHourlyPrice
         * by using JMockit to mock static method */

    }
}
