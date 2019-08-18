package parking;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class InOrderParkingStrategyTest {

	@Test
    public void testCreateReceipt_givenACarAndAParkingLog_thenGiveAReceiptWithCarNameAndParkingLotName() {

	    /* Exercise 1, Write a test case on InOrderParkingStrategy.createReceipt()
	    * With using Mockito to mock the input parameter */

        ParkingLot mockParkingLot = mock(ParkingLot.class);
        Car mockCar = mock(Car.class);
        when(mockParkingLot.getName()).thenReturn("Felicity");
        when(mockCar.getName()).thenReturn("Jerry");

        InOrderParkingStrategy inOrderParkingStrategy = new InOrderParkingStrategy();
        Receipt receipt = inOrderParkingStrategy.createReceipt(mockParkingLot, mockCar);
        assertEquals("Felicity", receipt.getParkingLotName());
        assertEquals("Jerry", receipt.getCarName());

    }

    @Test
    public void testCreateNoSpaceReceipt_givenACar_thenGiveANoSpaceReceipt() {

        /* Exercise 1, Write a test case on InOrderParkingStrategy.createNoSpaceReceipt()
         * With using Mockito to mock the input parameter */

    }

    @Test
    public void testPark_givenNoAvailableParkingLot_thenCreateNoSpaceReceipt(){

	    /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for no available parking lot */

        List<ParkingLot> parkingLots = new ArrayList<>();
        ParkingLot fullParkingLot = mock(ParkingLot.class);
        parkingLots.add(fullParkingLot);
        Car mockCar = mock(Car.class);
        when(fullParkingLot.isFull()).thenReturn(true);

        InOrderParkingStrategy spyInOrderParkingStrategy = Mockito.spy(new InOrderParkingStrategy());
        spyInOrderParkingStrategy.park(parkingLots, mockCar);

        verify(spyInOrderParkingStrategy, times(1)).createNoSpaceReceipt(mockCar);
        verify(spyInOrderParkingStrategy, times(0)).createReceipt(fullParkingLot, mockCar);


    }

    @Test
    public void testPark_givenThereIsOneParkingLotWithSpace_thenCreateReceipt(){

        /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for one available parking lot */

    }

    @Test
    public void testPark_givenThereIsOneFullParkingLot_thenCreateReceipt(){

        /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for one available parking lot but it is full */

    }

    @Test
    public void testPark_givenThereIsMultipleParkingLotAndFirstOneIsFull_thenCreateReceiptWithUnfullParkingLot(){

        /* Exercise 3: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for multiple parking lot situation */

    }


}
