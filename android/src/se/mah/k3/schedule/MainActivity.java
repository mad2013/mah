package se.mah.k3.schedule;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.component.VEvent;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
public class MainActivity extends Activity {
	ListView listView;
	private ScheduleAdapter adapter;
	ArrayList<ScheduleItem> items;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedule_main);
		ArrayList<KronoxCourse> courses = new ArrayList<KronoxCourse>();
		courses.add(new KronoxCourse("KD330A-20132-62311"));
		try {
			KronoxCalendar.createCalendar(KronoxReader.getFile(getApplicationContext()));
		} catch(IOException e) {
			new UpdateTask().execute(courses.get(0));
		} catch(ParserException e) {
			new UpdateTask().execute(courses.get(0));
		}
		setupListView();
		listToday();
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
			} catch(IOException e) {
				e.printStackTrace();
				// TODO: toast on error?
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void _void) {
			// TODO: update current view
		}
	}
	private void setupListView() {
		listView = (ListView)findViewById(R.id.listView);
		items = new ArrayList<ScheduleItem>();
		adapter = new ScheduleAdapter(this, items);
		listView.setAdapter(adapter);
	}
	private void listToday() {
		Collection<?> kronox_events = KronoxCalendar.todaysEvents();
		adapter.setNotifyOnChange(false);
		items.clear();
		for(Iterator<?> i = kronox_events.iterator(); i.hasNext();) {
			Component c = (Component)i.next();
			if(c instanceof VEvent) {
				items.add(new ScheduleItem((VEvent)c));
			}
		}
		adapter.notifyDataSetChanged();
	}
}
