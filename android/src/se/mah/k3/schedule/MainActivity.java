package se.mah.k3.schedule;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.component.VEvent;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedule_main);
		ArrayList<KronoxCourse> courses = new ArrayList<KronoxCourse>();
		courses.add(new KronoxCourse("KD330A-20132-62311"));
		new UpdateTask().execute(courses.get(0));
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	private class UpdateTask extends AsyncTask<KronoxCourse,Void,Void> {
		@Override
		protected Void doInBackground(KronoxCourse... courses) {
			try {
				KronoxReader.update(getApplicationContext(), courses);
				KronoxCalendar.createCalendar(KronoxReader.getFile(getApplicationContext()));
			} catch(IOException e) {
				e.printStackTrace();
				// TODO: toast on error?
			} catch(ParserException e) {
				e.printStackTrace();
				// TODO: toast on error?
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void _void) {
			String text = "";
			Collection<?> events = KronoxCalendar.todaysEvents();
			for(Iterator<?> i = events.iterator(); i.hasNext();) {
				Component c = (Component)i.next();
				// text += "Event: " + component.getName() + "\n";
				if(c instanceof VEvent) {
					VEvent v = (VEvent)c;
					SimpleDateFormat time_format = new SimpleDateFormat("HH:mm", Locale.US);
					SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
					text += "Starts:" + time_format.format(v.getStartDate().getDate()) + "\n"; 
					text += "Ends:" + time_format.format(v.getEndDate().getDate()) + "\n";
					text += "Summary:" + v.getSummary().getValue() + "\n";
					text += "Location:" + v.getLocation().getValue() + "\n";
					text += "Last modified:" + date_format.format(v.getLastModified().getDate()) + "\n";
					text += "\n";
				}
			}
			TextView textView1 = (TextView)findViewById(R.id.textView1);
			textView1.setText(text);
		}
	}
}
