package se.mah.k3.schedule;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;
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
	private class UpdateTask extends AsyncTask<KronoxCourse, Void, Void> {
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
		protected void onPostExecute(Void v) {
			String text = "";
			@SuppressWarnings("unchecked") Collection<Component> events = (Collection<Component>)KronoxCalendar.todaysEvents();
			for(Iterator<Component> i = events.iterator(); i.hasNext();) {
				Component component = i.next();
				text += "Event: " + component.getName() + "\n";
				for(@SuppressWarnings("unchecked") Iterator<Property> j = component.getProperties()
				                                                                   .iterator(); j.hasNext();) {
					Property property = (Property)j.next();
					text += "    " + property.getName() + "=" + property.getValue()
					        + "\n";
				}
				text += "\n\n";
			}
			TextView textView1 = (TextView)findViewById(R.id.textView1);
			textView1.setText(text);
		}
	}
}
