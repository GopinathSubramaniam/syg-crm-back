package com.syg.crm.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.syg.crm.dto.AppDTO;
import com.syg.crm.dto.DashboardDTO;
import com.syg.crm.dto.GraphDTO;
import com.syg.crm.dto.GraphData;
import com.syg.crm.dto.GraphSeriesDTO;
import com.syg.crm.enums.LeadStatus;
import com.syg.crm.enums.UserType;
import com.syg.crm.repository.BranchRepository;
import com.syg.crm.repository.CallLeadRepository;
import com.syg.crm.repository.LeadRepository;
import com.syg.crm.repository.MeetingRepository;
import com.syg.crm.repository.UserRepository;

@Service
public class DashboardService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private LeadRepository leadRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CallLeadRepository callLeadRepository;

	@Autowired
	private MeetingRepository meetingRepository;

	@Autowired
	private BranchRepository branchRepository;

	public DashboardDTO overview(AppDTO app) {
		DashboardDTO d = new DashboardDTO();
		d.setNewLeadCount(leadRepository.countByStatus(LeadStatus.N));
		d.setOwnLeadCount(leadRepository.countByStatus(LeadStatus.O));
		d.setLossLeadCount(leadRepository.countByStatus(LeadStatus.L));

		d.setTotalEmployeeCount(userRepository.countByUserType(UserType.E));
		d.setTotalAdminCount(userRepository.countByUserType(UserType.A));
		d.setTotalLeadCount(leadRepository.count());

		// <> Last 1 week calls count
		Calendar weekBefore = Calendar.getInstance();
		weekBefore.add(Calendar.DAY_OF_MONTH, -7);
		// </>

		d.setNewCallCount(callLeadRepository.countByStartTimeGreaterThan(weekBefore.getTime()));
		d.setTotalCallCount(callLeadRepository.count());

		// <> Upcoming today events count
		Calendar today = Calendar.getInstance();
		Date todayStart = today.getTime();
		today.set(Calendar.HOUR_OF_DAY, 23);
		today.set(Calendar.MINUTE, 59);
		today.set(Calendar.SECOND, 59);
		today.set(Calendar.MILLISECOND, 0);
		Date todayEnd = today.getTime();
		d.setTodayEventCount(meetingRepository.countByStartTimeBetween(todayStart, todayEnd));
		// </>

		// <> Tomorrow event count
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.add(Calendar.DATE, 1);
		tomorrow.set(Calendar.HOUR_OF_DAY, 0);
		tomorrow.set(Calendar.MINUTE, 0);
		tomorrow.set(Calendar.SECOND, 0);
		tomorrow.set(Calendar.MILLISECOND, 0);

		Date start = tomorrow.getTime();

		tomorrow.set(Calendar.HOUR_OF_DAY, 23);
		tomorrow.set(Calendar.MINUTE, 59);
		tomorrow.set(Calendar.SECOND, 59);
		tomorrow.set(Calendar.MILLISECOND, 0);
		Date end = tomorrow.getTime();

		System.out.println("Start = " + start);
		System.out.println("End = " + end);

		d.setTomorrowEventCount(meetingRepository.countByStartTimeBetween(start, end));
		// </>

		d.setTotalBranchCount(branchRepository.count());
		d.setOwnLeadsMonthWise(leadRepository.graphDataByYearAndMonth(LeadStatus.O));
		d.setLossLeadsMonthWise(leadRepository.graphDataByYearAndMonth(LeadStatus.L));

		// <> Preparing traffic summary data
		String getData = "SELECT created_date, lead_source, COUNT(*) count FROM lead "
				+ " WHERE MONTH(created_date) >= MONTH(NOW()) GROUP BY YEAR(created_date), MONTH(created_date), DATE(created_date), lead_source";

		List<GraphData> leadSources = jdbcTemplate.query(getData, (rs,
				rowNum) -> new GraphData(rs.getDate("created_date"), rs.getString("lead_source"), rs.getLong("count")));
		List<String> categories = new ArrayList<>();
		List<GraphSeriesDTO> gSeriesData = new ArrayList<>();

		leadSources.forEach((o) -> {
			SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
			String dte = myFormat.format(o.getDate());

			if (!categories.contains(dte))
				categories.add(dte);

			OptionalInt result = IntStream.range(0, gSeriesData.size())
					.filter(idx -> o.getLeadSource().equalsIgnoreCase(gSeriesData.get(idx).getName())).findFirst();

			if (result.isPresent()) {
				gSeriesData.get(result.getAsInt()).getData().add(o.getCount());
			} else {
				GraphSeriesDTO gs = new GraphSeriesDTO();
				gs.setDate(dte);
				gs.setName(o.getLeadSource());
				gs.getData().add(o.getCount());
				gSeriesData.add(gs);
			}
		});

		d.setTraficSummaryGData(new GraphDTO(categories, gSeriesData));
		// </> Preparing traffic summary data

		// <> Preparing the lead source count
		String getLeadCountBySource = "SELECT created_date, lead_source, COUNT(*) count, (SELECT COUNT(*) FROM lead) total FROM lead GROUP BY lead_source";
		List<GraphData> leadsBySource = jdbcTemplate.query(getLeadCountBySource,
				(rs, rowNum) -> new GraphData(rs.getString("lead_source"), rs.getLong("count"), rs.getLong("total")));
		d.setLeadCountBySource(leadsBySource);
		// </>

		return d;
	}

}
