import java.util.Random;

public class Igra extends Tabla {

	private boolean prvi;
	
	Igra() {
		super();
		prvi = true;
	}
	
	public void postavi(int i, int j) {
		if(prazno(i, j)) {
			postavi(i, j, (prvi ? 'x' : 'o'));
			prvi = !prvi;
		}
	}
	
	public char naRedu()
	{ return (prvi ? 'x' : 'o'); }
	
	public char kraj() {
		for(int i = 0; i < 3; i++) {
			if(!prazno(i, 0)) 
				if(polje(i, 0) == polje(i, 1) && polje(i, 0) == polje(i, 2)) return polje(i, 0);
			if(!prazno(0, i)) 
				if(polje(0, i) == polje(1, i) && polje(0, i) == polje(2, i)) return polje(0, i);
		}
		
		if(!prazno(0, 0))
			if(polje(0, 0) == polje(1, 1) && polje(0, 0) == polje(2, 2)) return polje(0, 0);
		if(!prazno(0, 2))
			if(polje(0, 2) == polje(1, 1) && polje(0, 2) == polje(2, 0)) return polje(0, 2);
		
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++)
				if(prazno(i, j)) return '\0';
		
		return 'n';
	}
	
	public void komp(int a, int b) {
		Random rand = new Random();
		do {
			a = rand.nextInt(3); b = rand.nextInt(3);
		} while(!prazno(a, b));
		postavi(a, b);
	}
	
	public String toString()
	{ return super.toString().toUpperCase(); }
	
	public static void main(String[] args) {
		Igra ig = new Igra();
		System.out.println(ig);
		
		ig.postavi(0, 0); ig.postavi(1, 0); ig.postavi(0, 1); ig.postavi(1, 1); ig.postavi(0, 2);
		
		System.out.println(ig + "\n" + ig.kraj());
	}

}
