package se.mah.k3.schedule;
import java.text.SimpleDateFormat;
import java.util.Locale;
import net.fortuna.ical4j.model.component.VEvent;
public class ScheduleItem {
	SimpleDateFormat time_format = new SimpleDateFormat("HH:mm", Locale.US);
	SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm",
	                                                    Locale.US);
	private String startTime;
	private String endTime;
	private String location;
	private String courseName;
	// text += "Summary:" + v.getSummary().getValue() + "\n";
	// text += "Last modified:" +
	// date_format.format(v.getLastModified().getDate()) + "\n";
	public ScheduleItem(VEvent v) {
		startTime = time_format.format(v.getStartDate().getDate());
		endTime = time_format.format(v.getEndDate().getDate());
		location = v.getLocation().getValue();
		courseName = "Mobile Applications Development";
	}
	public String getStartTime() {
		return startTime;
	}
	public String getRoomCode() {
		return location;
	}
	public String getEndTime() {
		return endTime;
	}
	public String getCourseName() {
		return courseName;
	}
}
