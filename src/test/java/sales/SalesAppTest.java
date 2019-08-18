package sales;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SalesAppTest {

	@Mock
	SalesDao salesDao;
	@Mock
	SalesReportDao salesReportDao;
	@Mock
	EcmService ecmService;
	@InjectMocks
	SalesApp salesApp = new SalesApp();

	@Test
	public void should_no_method_called_when_generateSalesActivityReport_given_salesId_null() {

		SalesApp salesApp = spy(new SalesApp());

		salesApp.generateSalesActivityReport(null, 1000, false, false);

		verify(salesApp, times(0)).getSalesBySaleId(any());
		verify(salesApp, times(0)).isSalesEffective(any());
		verify(salesApp, times(0)).getSalesReportDataBySales(any());
		verify(salesApp, times(0)).getHeadersByIsNatTrade(false);
		verify(salesApp, times(0)).generateReport(any(), anyList());

	}

	@Test
	public void all_the_method_called_when_generateSalesActivityReport_given_all_params() {

		SalesApp spySalesApp = spy(salesApp);
		Sales sales = mock(Sales.class);
		String salesId = "DUMMY";
		List<SalesReportData> salesReportDatas = new ArrayList<>();
		List<String> headers = new ArrayList<>();
		boolean isNatTrade = false;
		SalesActivityReport report = mock(SalesActivityReport.class);
        doReturn(sales).when(spySalesApp).getSalesBySaleId(salesId);
        doReturn(false).when(spySalesApp).isSalesEffective(sales);
        doReturn(salesReportDatas).when(spySalesApp).getSalesReportDataBySales(sales);
        doReturn(headers).when(spySalesApp).getHeadersByIsNatTrade(isNatTrade);
        doReturn(report).when(spySalesApp).generateReport(headers, salesReportDatas);

		spySalesApp.generateSalesActivityReport(salesId, 1000, isNatTrade, false);

		verify(spySalesApp, times(1)).getSalesBySaleId(salesId);
		verify(spySalesApp, times(1)).isSalesEffective(sales);
		verify(spySalesApp, times(1)).getSalesReportDataBySales(sales);
		verify(spySalesApp, times(1)).getHeadersByIsNatTrade(isNatTrade);
		verify(spySalesApp, times(1)).generateReport(headers, salesReportDatas);
		verify(report, times(1)).toXml();
		verify(ecmService, times(1)).uploadDocument(any());

	}



	@Test
	public void should_only_involve_getSalesBySaleId_when_call_generateSalesActivityReport_given_salesId_DUMMY_sales_UnEffective() {

		SalesApp salesApp = spy(new SalesApp());
		Sales sales = mock(Sales.class);
		String salesId = "DUMMY";
		doReturn(sales).when(salesApp).getSalesBySaleId(salesId);
		doReturn(true).when(salesApp).isSalesEffective(sales);

		salesApp.generateSalesActivityReport(salesId, 1000, false, false);

		verify(salesApp, times(1)).getSalesBySaleId(salesId);
		verify(salesApp, times(1)).isSalesEffective(sales);
		verify(salesApp, times(0)).getSalesReportDataBySales(any());
		verify(salesApp, times(0)).getHeadersByIsNatTrade(false);
		verify(salesApp, times(0)).generateReport(any(), anyList());

	}

	@Test
	public void should_return_sales_when_call_getSalesBySaleId_given_DUMMY_salesId() {
		String salesId = "DUMMY";
		Sales sales1 = new Sales();
		sales1.setSupervisor(true);
		when(salesDao.getSalesBySalesId(salesId)).thenReturn(sales1);

		Sales sales = salesApp.getSalesBySaleId(salesId);

		assertTrue(sales.isSupervisor());

	}

	@Test
	public void should_return_true_when_call_isSalesEffective_given_the_last_day_and_the_next_day_from_now() {
		Sales mockSales = mock(Sales.class);

		Calendar startCalendar = Calendar.getInstance();
		startCalendar.add(Calendar.DATE, -1);
		Date beforeToday = startCalendar.getTime();

		Calendar endCalendar = Calendar.getInstance();
		endCalendar.add(Calendar.DATE, 1);
		Date afterToday = endCalendar.getTime();

		when(mockSales.getEffectiveFrom()).thenReturn(afterToday);
		when(mockSales.getEffectiveTo()).thenReturn(beforeToday);

		boolean isSalesUnEffective = salesApp.isSalesEffective(mockSales);

		assertTrue(isSalesUnEffective);

	}

	@Test
	public void should_return_salesReportDatas_when_call_getSalesReportDataBySales_given_sales() {
		Sales sales = mock(Sales.class);

		SalesReportData salesReportData = mock(SalesReportData.class);
		when(salesReportData.isConfidential()).thenReturn(true);

		ArrayList<SalesReportData> salesReportDatas = new ArrayList<>();
		salesReportDatas.add(salesReportData);
		when(salesReportDao.getReportData(sales)).thenReturn(salesReportDatas);

		List<SalesReportData> salesReportDataBySales = salesApp.getSalesReportDataBySales(sales);

		assertTrue(salesReportDataBySales.get(0).isConfidential());

	}

	@Test
	public void should_return_specifyHeadersStrs_when_call_getHeadersByIsNatTrade_given_true() {
		boolean isNatTrade = true;

		List<String> headers = salesApp.getHeadersByIsNatTrade(isNatTrade);

		assertEquals("Time", headers.get(3));
	}

	@Test
	public void should_return_specifyHeadersStrs_when_call_getHeadersByIsNatTrade_given_false() {
		boolean isNatTrade = false;

		List<String> headers = salesApp.getHeadersByIsNatTrade(isNatTrade);

		assertEquals("Local Time", headers.get(3));
	}

}
