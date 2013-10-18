package se.mah.k3.schedule;
public class NameDatabase {
	public static String getCourse(String courseCode) {
		if(courseCode.startsWith("KD330A")) {
			return "Mobile Applications Development";
		}
		return courseCode;
	}
}
