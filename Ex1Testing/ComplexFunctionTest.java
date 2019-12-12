package Ex1Testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Ex1.*;

class ComplexFunctionTest {
	
	@BeforeEach
	void setUp() {
		
	}

	@Test
	void testInitFromString() {
		function cf = new ComplexFunction();
		cf = cf.initFromString("plus(div(+1.0x +1.0,mul(mul(+1.0x +3.0,+1.0x -2.0),+1.0x -4.0)),2.0)");
		
		String s1 = "x+3";
		String s2 = "x-2";
		Polynom p1 = new Polynom(s1);
		Polynom p2 = new Polynom(s2);
		ComplexFunction temp = new ComplexFunction(p1);
		temp.mul(p2);
		temp.mul(new Polynom("x-4"));
		ComplexFunction ans =new ComplexFunction( new Polynom("x+1"));
		ans.div(temp);
		ans.plus(new Polynom("2"));
		
		assertEquals(ans.toString(), cf.toString());
	}
	
	@Test
	void testEquals_fx() {
		ComplexFunction cf1 = new ComplexFunction(Operation.Plus,new Polynom("x"),new Polynom("0.5"));
		ComplexFunction cf2 = new ComplexFunction("div", new Polynom("2x+1"),new Polynom("2"));
		
		assertTrue(cf1.equals(cf2));
		
		double num = Math.random()*100;
		double ans = num+0.5;
		assertEquals(ans, cf1.f(num));
	}
	

}
