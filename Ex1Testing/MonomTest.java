package Ex1Testing;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import Ex1.*;

class MonomTest {
	
	@Test
	void testAdd() {
		System.out.println("**********test Add**********");
		Monom m1 = new Monom("8x^5");
		Monom m2 = new Monom("6.2x");
		try {
			m1.add(m2);
		} catch (RuntimeException e) {
			System.out.println("only add a monom with the same power");
			return;
		}
		fail("add a monom with different power");
		
	}
	
	@Test
	void testFx() { //f(x)
		System.out.println("**********test f(x)**********");
		String[] monoms = {"2", "-x","-3.2x^2","0"};
		for(int i=0;i<monoms.length;i++) {
			Monom m = new Monom(monoms[i]);
			int num = (int) (Math.random()*100);
			double fx = m.f(num);
			
			double expected = m.get_coefficient()*Math.pow(num, m.get_power());
			assertEquals(expected, fx);
			System.out.println(i+") "+m +"    \tf("+num+") = "+fx);
		}
	}
	
	@Test
	void testIsZero() {
		System.out.println("**********test Is Zero**********");
		String[] monoms = {"2", "-x","-3.2x^2","0"};
		for(int i=0;i<monoms.length;i++) {
			Monom m = new Monom(monoms[i]);
			
			if(m.get_coefficient()==0) {
				assertTrue(m.isZero());
			}
			if(m.get_coefficient()!=0) {
				assertFalse(m.isZero());
			}
			System.out.println(i+") "+m +"    \tisZero: "+m.isZero());
		}
	}
	
	@Test
	void testToString_InitFromString() {
		System.out.println("**********test To String and Init From String**********");
		ArrayList<Monom> monoms = new ArrayList<Monom>();
		monoms.add(new Monom(0,5));
		monoms.add(new Monom(-1,0));
		monoms.add(new Monom(-1.3,1));
		monoms.add(new Monom(-2.2,2));
		
		for(int i=0;i<monoms.size();i++) {
			Monom m = new Monom(monoms.get(i));
			String s = m.toString();
			Monom m1=Monom.ZERO;
			m1= (Monom) m1.initFromString(s);
			boolean e = m.equals(m1);
			
			assertTrue(m.equals(m1));
			System.out.println(i+") "+m +"    \teq: "+e);
		}
	}

}
