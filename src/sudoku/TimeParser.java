package sudoku;

public class TimeParser {
	private long hours, minutes, seconds, millis;
	public TimeParser(long time) {
		seconds = time / 1000;
		millis = time % 1000;
		minutes = seconds / 60;
		seconds %= 60;
		hours = minutes / 60;
		minutes %= 60;
	}
	
	public String getTimeFormat(){
		convertMillis((int)millis);
		String s = convert2Digits((int)hours) + ":" + convert2Digits((int)minutes) + ":" + convert2Digits((int)seconds) + "." + convert2Digits((int)millis);
		return s;
	}
	
	private String convert2Digits(int x){
		String s = x + "";
		while(s.length() < 2){
			s = "0" + s;
		}
		return s;
	}
	private void convertMillis(int m){
		while(m > 100){
			m %= 10;
		}
	}
	public static void main(String args[]){
		TimeParser t = new TimeParser(3700000);
		System.out.println(t.getTimeFormat());
	}	
}
