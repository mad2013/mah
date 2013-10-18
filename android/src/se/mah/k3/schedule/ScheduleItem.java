package se.mah.k3.schedule;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.fortuna.ical4j.model.component.VEvent;
import android.util.Log;
/*
 *  TODO: 
 *  Kronox gives us a very oddly formatted SUMMARY field. We do not want to show this as is.
 *  So we need to figure out what information we can extract here and categorize it.
 *  You get the summary by the command v.getSummary().getValue() in the constructor below
 *  A typical string can look like:
 *
 *  Coursegrp: KD330A-20132-62311- Sign: K3LARA Description: Project room Activity type: Okänd
 *  Programme: VGSJU13h VGSJU13h1 VGSJU13h2 Coursegrp: OM113A-20132-OM113-D16 Sign: HSANMOS Description: Övning Injektionsgivning VP. 16:1 Activity type: Okänd
 *  
 *  Can we make it presentable?
 */
public class ScheduleItem {
	private final static Pattern course_pattern = Pattern.compile("Coursegrp:\\s*(\\S+)", Pattern.CASE_INSENSITIVE);
	private final static SimpleDateFormat time_format = new SimpleDateFormat("HH:mm", Locale.US);
	private final static SimpleDateFormat week_format = new SimpleDateFormat("w", Locale.US);
	private final static SimpleDateFormat dayoftheweek_format = new SimpleDateFormat("EEEE", Locale.US);
	private final static SimpleDateFormat month_short_format = new SimpleDateFormat("MMM", Locale.US);
	private final static SimpleDateFormat year_format = new SimpleDateFormat("yyyy", Locale.US);
	private final String startTime;
	private final String endTime;
	private final String location;
	private final String courseName;
	private final String dayoftheweek;
	private final String month_short;
	private final String week;
	private final String year;
	// text += "Summary:" + v.getSummary().getValue() + "\n";
	// text += "Last modified:" +
	// date_format.format(v.getLastModified().getDate()) + "\n";
	public ScheduleItem(VEvent v) {
		startTime = time_format.format(v.getStartDate().getDate());
		endTime = time_format.format(v.getEndDate().getDate());
		location = v.getLocation().getValue();
		String summary = v.getSummary().getValue();
		Log.i("ScheduleGUI", "summary:" + summary);
		Matcher m = course_pattern.matcher(summary);
		m.find();
		Log.i("ScheduleGUI", "m:" + m.group(0));
		String courseCode = m.group(1);
		courseName = NameDatabase.getCourse(courseCode);
		dayoftheweek = dayoftheweek_format.format(v.getStartDate().getDate());
		week = week_format.format(v.getStartDate().getDate());
		month_short = month_short_format.format(v.getStartDate().getDate()); 
		year = year_format.format(v.getStartDate().getDate());
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
	public String getDayOfTheWeek() {
		return dayoftheweek;
	}
	public String getWeek() {
		return week;
	}
	public String getMonthShort() {
		return month_short;
	}
	public String getYear() {
		return year;
	}
}
