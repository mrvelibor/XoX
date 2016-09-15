
public class Tabla {
	
	private char [][]polja;
	private int p, q;
	
	public Tabla(int i, int j)
	{ polja = new char[p = i][q = j]; }
	
	public Tabla(int i)
	{ this(i, i); }
	
	public Tabla()
	{ this(3); }
	
	protected boolean opseg(int i, int j) {
		if(i < p && i >= 0 && j < q && j >= 0) return true;
		else return false;
	}

	public char polje(int i, int j) {
		if(opseg(i, j)) return polja[i][j];
		else return '\0';
	}
	
	public boolean prazno(int i, int j) {
		if(opseg(i, j)) return polja[i][j] == '\0';
		else return false;
	}
	
	public void postavi(int i, int j, char c)
	{ polja[i][j] = c; }
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < 3; s.append("\n"), i++) 
			for(int j = 0; j < 3; j++)
				s.append(" " + (prazno(i, j) ? '_' : polje(i, j)));
		return s.toString();
	}
	
	public static void main(String[] args) {
		Tabla t = new Tabla();
		System.out.println(t);
		
		t.postavi(1, 1, 'x');
		t.postavi(1, 2, 'o');
		
		System.out.println(t);
	}
}
