import javax.microedition.rms.RecordComparator;

public class Comparator implements RecordComparator {
	public int compare(byte[] rec1, byte[] rec2) {
		String arg1 = new String(rec1);
		String arg2 = new String(rec2);
		
		int relation = arg1.compareTo(arg2);
		if	(relation < 0) return -1;
		else if (relation > 0) return 1;
		else return 0;
	}
}
