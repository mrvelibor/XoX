
public class GTabla extends Exception {

	public static final int OPSEG = 0,
							PUNO = 1,
							CIGA = 2;
	
	private static final String[] poruka = {
		"Nepostojece polje.",
		"Polje je vec popunjeno.",
		"Cigan!"
	};
	
	private int kod;
	
	public GTabla(int k) { kod = k; }
	
	public String toString() { return "*** " + poruka[kod]; }
}
