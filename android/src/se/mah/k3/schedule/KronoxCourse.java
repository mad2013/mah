package se.mah.k3.schedule;
public class KronoxCourse {
	private String code;
	public void setCode(String code) {
		assert code.matches("^\\w+-\\d+-\\w+$");
		this.code = code;
	}
	/**
	 * @return Returns all three parts of the course code bound together by
	 *         dashes. For example: "KD330A-20132-62311"
	 */
	public String getFullCode() {
		assert code.matches("^\\w+-\\d+-\\w+$");
		return code;
	}
}
