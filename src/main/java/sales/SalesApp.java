package sales;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SalesApp {

	private SalesDao salesDao;
	private SalesReportDao salesReportDao;
	private EcmService ecmService;

	public SalesApp() {
		this.salesDao = new SalesDao();
		this.salesReportDao = new SalesReportDao();
		this.ecmService = new EcmService();
	}

	public void generateSalesActivityReport(String salesId, int maxRow, boolean isNatTrade, boolean isSupervisor) {

		if (salesId == null) return;
		Sales sales = getSalesBySaleId(salesId);
		if (isSalesEffective(sales)) return;
		SalesActivityReport report = getReportByHeadersAndReportDatas(isNatTrade, sales);
		ecmService.uploadDocument(report.toXml());

	}

	private SalesActivityReport getReportByHeadersAndReportDatas(boolean isNatTrade, Sales sales) {
		List<SalesReportData> reportDataList = getSalesReportDataBySales(sales);
		List<String> headers = getHeadersByIsNatTrade(isNatTrade);
		return this.generateReport(headers, reportDataList);
	}

	protected List<String> getHeadersByIsNatTrade(boolean isNatTrade) {
		if (isNatTrade) {
			return Arrays.asList("Sales ID", "Sales Name", "Activity", "Time");
		}
		return Arrays.asList("Sales ID", "Sales Name", "Activity", "Local Time");
	}

	protected List<SalesReportData> getSalesReportDataBySales(Sales sales) {
		return salesReportDao.getReportData(sales);
	}

	protected boolean isSalesEffective(Sales sales) {
		Date today = new Date();
		if (today.after(sales.getEffectiveTo()) || today.before(sales.getEffectiveFrom())) {
			return true;
		}
		return false;
	}

	protected Sales getSalesBySaleId(String salesId) {
		return salesDao.getSalesBySalesId(salesId);
	}

	protected SalesActivityReport generateReport(List<String> headers, List<SalesReportData> reportDataList) {
		// TODO Auto-generated method stub
		return null;
	}

}
