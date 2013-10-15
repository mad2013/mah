package se.mah.k3.schedule;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
public class ScheduleAdapter extends ArrayAdapter<ScheduleItem> {
	private final Context context;
	private final List<ScheduleItem> items;
	public ScheduleAdapter(Context context, List<ScheduleItem> items) {
		super(context, R.layout.schedule_item, items);
		this.context = context;
		this.items = items;
	}
	static class ViewHolder {
		public TextView startTime;
		public TextView endTime;
		public TextView courseName;
		public TextView roomCode;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null) {
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.schedule_item, parent, false);
			holder = new ViewHolder();
			holder.startTime = (TextView)convertView.findViewById(R.id.textStartTime);
			holder.endTime = (TextView)convertView.findViewById(R.id.textEndTime);
			holder.courseName = (TextView)convertView.findViewById(R.id.textCourseName);
			holder.roomCode = (TextView)convertView.findViewById(R.id.textRoomCode);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder)convertView.getTag();
		}
		ScheduleItem item = items.get(position);
		holder.startTime.setText(item.getStartTime());
		holder.endTime.setText(item.getEndTime());
		holder.courseName.setText(item.getCourseName());
		holder.roomCode.setText(item.getRoomCode());
		return convertView;
	}
}
