package parking;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;

public class VipParkingStrategyTest {

	@Test
    public void testPark_givenAVipCarAndAFullParkingLog_thenGiveAReceiptWithCarNameAndParkingLotName() {

	    /* Exercise 4, Write a test case on VipParkingStrategy.park()
	    * With using Mockito spy, verify and doReturn */
        VipParkingStrategy spy = spy(new VipParkingStrategy());
        Car vipCar = mock(Car.class);
        doReturn(true).when(spy).isAllowOverPark(vipCar);

        List<ParkingLot> parkingLots = new ArrayList<>();
        ParkingLot fullParkingLot = mock(ParkingLot.class);
        parkingLots.add(fullParkingLot);
        when(fullParkingLot.isFull()).thenReturn(true);

        spy.park(parkingLots,vipCar);

        verify(spy,times(1)).createReceipt(fullParkingLot,vipCar);
        verify(spy,times(0)).createNoSpaceReceipt(vipCar);

    }

    @Test
    public void testPark_givenCarIsNotVipAndAFullParkingLog_thenGiveNoSpaceReceipt() {

        VipParkingStrategy spy = spy(new VipParkingStrategy());
        Car vipCar = mock(Car.class);
        doReturn(false).when(spy).isAllowOverPark(vipCar);

        List<ParkingLot> parkingLots = new ArrayList<>();
        ParkingLot fullParkingLot = mock(ParkingLot.class);
        parkingLots.add(fullParkingLot);
        when(fullParkingLot.isFull()).thenReturn(true);

        spy.park(parkingLots,vipCar);

        verify(spy,times(0)).createReceipt(fullParkingLot,vipCar);
        verify(spy,times(1)).createNoSpaceReceipt(vipCar);

        /* Exercise 4, Write a test case on VipParkingStrategy.park()
         * With using Mockito spy, verify and doReturn */
    }

    @Test
    public void testIsAllowOverPark_givenCarNameContainsCharacterAAndIsVipCar_thenReturnTrue(){

        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */

        VipParkingStrategy spy = spy(new VipParkingStrategy());
        CarDao mockCarDao = mock(CarDao.class);

        doReturn(mockCarDao).when(spy).getCarDao();
        when(mockCarDao.isVip("FelicityA")).thenReturn(true);

        assertTrue(spy.isAllowOverPark(createMockCar("FelicityA")));

    }

    @Test
    public void testIsAllowOverPark_givenCarNameDoesNotContainsCharacterAAndIsVipCar_thenReturnFalse(){

        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
    }

    @Test
    public void testIsAllowOverPark_givenCarNameContainsCharacterAAndIsNotVipCar_thenReturnFalse(){
        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
    }

    @Test
    public void testIsAllowOverPark_givenCarNameDoesNotContainsCharacterAAndIsNotVipCar_thenReturnFalse() {
        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
    }

    private Car createMockCar(String carName) {
        Car car = mock(Car.class);
        when(car.getName()).thenReturn(carName);
        return car;
    }
}
