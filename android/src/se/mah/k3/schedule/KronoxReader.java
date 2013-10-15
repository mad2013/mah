package se.mah.k3.schedule;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.AbstractCollection;
import android.content.Context;
public class KronoxReader {
	private final static String LOCAL_FILENAME = "courses.ical";
	private final static String LENGTH_UNIT = "v"; // d=days, v=weeks, m=months
	private final static int LENGTH = 2;
	private final static String LANGUAGE = "EN"; // EN or SV
	//private static KronoxReader instance = new KronoxReader();
	protected KronoxReader() {
	}
	//public static KronoxReader getInstance() {
		//return instance;
//	}
	protected static String generateURL(AbstractCollection<KronoxCourse> courses) {
		String kurser = "";
		for(KronoxCourse course : courses) {
			kurser += String.format("k.%s-%%2C", course.getFullCode());
		}
		String url = "http://schema.mah.se/setup/jsp/SchemaICAL.ics";
		url += String.format("?startDatum=idag&intervallTyp=%s&intervallAntal=%d",
		                     LENGTH_UNIT, LENGTH);
		url += "&sprak=" + LANGUAGE;
		url += "&sokMedAND=false";
		url += "&resurser=" + kurser;
		return url;
	}
	/**
	 * @param ctx
	 *        The app's getContext()
	 * @param courses
	 *        The courses that will be included in the calendar
	 * @throws IOException
	 *         This will be thrown on network errors or file writing errors
	 */
	public static void update(Context ctx, AbstractCollection<KronoxCourse> courses) throws IOException {
		URL url = new URL(KronoxReader.generateURL(courses));
		ReadableByteChannel rbc = Channels.newChannel(url.openStream());
		FileOutputStream fos = ctx.openFileOutput(KronoxReader.LOCAL_FILENAME,
		                                          Context.MODE_PRIVATE);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
	}
}