package Ex1Testing;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import Ex1.*;

class PolynomTest {
	
	public static boolean compDouble(double a , double b) {
		double diff = a-b;
		if (a == b || (diff < 0.01 && diff > -0.01)) return true;
		return false;
	}
	
	@Test
	void testSubstruct_IsZero() {
		Polynom_able p1 = new Polynom();
		String[] monoms = {"2", "-x","-3.2x^2","4","-1.5x^2"};
		for(int i=0;i<monoms.length;i++) {
			Monom m = new Monom(monoms[i]);
			p1.add(m);
		}

		p1.substract(p1);
		boolean failedIsZero = p1.isZero();
		assertTrue(failedIsZero); 
	}
	
	@Test
	void testCreatFromSelfString_WithSpace() {
		Polynom_able p1 = new Polynom(), p2 =  new Polynom();
		String[] monoms1 = {"2", "-x","-3.2x^2","4","-1.5x^2"};
		String[] monoms2 = {"5", "1.7x","3.2x^2","-3","-1.5x^2"};
		for(int i=0;i<monoms1.length;i++) {
			Monom m = new Monom(monoms1[i]);
			p1.add(m);
		}
		for(int i=0;i<monoms2.length;i++) {
			Monom m = new Monom(monoms2[i]);
			p2.add(m);
		}
		//System.out.println("p1: "+p1);
		//System.out.println("p2: "+p2);
		boolean selfCheckFromString = p1.equals(new Polynom(p1.toString()));
		assertTrue(selfCheckFromString); //self check from string failed
		
		boolean creatPolynomWithSpases = p1.equals(new Polynom("-4.7x^2-1.0x+6.0"));
		assertTrue(creatPolynomWithSpases); //failed to creat poly with spases
		
	}
	
	@Test
	void testArea_Root() throws Exception{//test for area and root.
		String[][] polynoms = {{"3x^2","-6x^3","9x","-2"},
				{"x","5x","0","-5"},
				{"4x^6", "-5x^5", "1"}};
		double[][] res = {{0,0.2135},{0,0.83334},{2.404,0.9999}}; 
		for (int i = 0; i < polynoms.length; i++) {
			Polynom p1 = new Polynom();
			for (int j = 0; j < polynoms[i].length; j++) {
				Monom temp = new Monom(polynoms[i][j]);
				p1.add(temp);
			}
			
			boolean areaMissmatch = compDouble(p1.area(-1, 0, 0.0001), res[i][0]);
			assertTrue(areaMissmatch);
			
			boolean rootMissmatch = compDouble(p1.root(0, 1, 0.0001), res[i][1]);
			assertTrue(rootMissmatch);
				
		}
	}

	@Test
	void testAdd_Substract() {
		String[][] polynoms = {{"3x^2","-6x^3","9x","-2"},
				{"x","5x","-5"} , {"-6.0x^3" ,"3.0x^2","15.0x", "-7.0"}};
		Polynom p1 = new Polynom();
		Polynom p2 = new Polynom();
		Polynom p3 = new Polynom();
		for (int i = 0; i < polynoms[0].length; i++) {
			Monom temp = new Monom(polynoms[0][i]);
			p1.add(temp);
		}		
		for (int i = 0; i < polynoms[1].length; i++) {
			Monom temp = new Monom(polynoms[1][i]);
			p2.add(temp);
		}
		for (int i = 0; i < polynoms[2].length; i++) {
			Monom temp = new Monom(polynoms[2][i]);
			p3.add(temp);
		}
		Polynom add = new Polynom();
		Polynom substract = new Polynom();
		add = (Polynom) p1.copy();
		substract = (Polynom)p1.copy();
		add.add(p2);
		substract.substract(p2);
		boolean failAdd = add.equals(p3);
		assertTrue(failAdd);
//			System.out.println(add);
//			System.out.println(p3);
	}

	@Test
	void testMultiply() {
		String[][] polynoms = {{"3x^2","-6x^3","9x","-2"},
				{"x" , "3x^3"} , {"-18.0x^6" , "9.0x^5" ,"21.0x^4" ,"-3.0x^3", "9.0x^2", "-2.0x"}};
		Polynom p1 = new Polynom();
		Polynom p2 = new Polynom();
		Polynom p3 = new Polynom();
		for (int i = 0; i < polynoms[0].length; i++) {
			Monom temp = new Monom(polynoms[0][i]);
			p1.add(temp);
		}		
		for (int i = 0; i < polynoms[1].length; i++) {
			Monom temp = new Monom(polynoms[1][i]);
			p2.add(temp);
		}
		
		for (int i = 0; i < polynoms[2].length; i++) {
			Monom temp = new Monom(polynoms[2][i]);
			p3.add(temp);
		}
		Polynom multiply = new Polynom();
		multiply = (Polynom) p1.copy();
		multiply.multiply(p2);
		
		boolean failMult = multiply.equals(p3);
		assertTrue(failMult);

	}

	@Test
	void testEquals() {
		String[][] polynoms = {{"3x^2","-6x^3","9x","-2"},
				{"x","5x","0","-5"} , {"3.00000001x^2","-5.9999999999x^3","9.000000001x","-2"}};
		Polynom p1 = new Polynom();
		Polynom p2 = new Polynom();
		Polynom p3 = new Polynom();
		for (int i = 0; i < polynoms[0].length; i++) {
			Monom temp = new Monom(polynoms[0][i]);
			p1.add(temp);
		}		
		for (int i = 0; i < polynoms[1].length; i++) {
			Monom temp = new Monom(polynoms[1][i]);
			p2.add(temp);
		}
		
		for (int i = 0; i < polynoms[2].length; i++) {
			Monom temp = new Monom(polynoms[2][i]);
			p3.add(temp);
		}
		
		boolean failEquals = p1.equals(p2);
		assertFalse(failEquals);
		
		boolean failEqualsNoNumericChecks = p1.equals(p3);
		assertTrue(failEqualsNoNumericChecks);
		
	}
	
	@Test
	void testCopy() { //test deep copy
		Polynom_able p1 = new Polynom("-4.7x^2-1.0x+6.0");
		Polynom_able p2 = (Polynom_able) p1.copy();
		
		boolean failCopy = p1.equals(p2);
		assertTrue(failCopy);
		
		p2.add(new Monom("x^2"));
		boolean failDeepCopy = p1.equals(p2);
		assertFalse(failDeepCopy);
		
	}

}
