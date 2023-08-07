package com.syg.crm.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class DashboardDTO {

	private Long newLeadCount;
	private Long ownLeadCount;
	private Long lossLeadCount;
	private Long totalLeadCount;
	private Long currentBranchLeadCount;

	private Long todayEventCount;
	private Long tomorrowEventCount;

	private Long newCallCount;
	private Long totalCallCount;

	private Long totalEmployeeCount;
	private Long totalAdminCount;

	private Long totalBranchCount;

	private List<Long> newnLeadsMonthWise;
	private List<Long> ownLeadsMonthWise;
	private List<Long> lossLeadsMonthWise;

	private GraphDTO traficSummaryGData;
	
	private List<GraphData> leadCountBySource;

}
