package Ex1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Predicate;

//import Ex1.Monom;
/**
 * This class represents a Polynom with add, multiply functionality, it also should support the following:
 * 1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral
 * 2. Finding a numerical value between two values (currently support root only f(x)=0).
 * 3. Derivative
 * 
 * @author Boaz
 *
 */
public class Polynom implements Polynom_able{
	
	private ArrayList<Monom> monomsList;

	/**
	 * Zero (empty polynom)
	 */
	public Polynom() {
		this.monomsList = new ArrayList<>();
		
	}
	
	/**
	 * init a Polynom from a String such as:
	 *  {"x", "3+1.4X^3-34x", "(2x^2-4)*(-1.2x-7.1)", "(3-3.4x+1)*((3.1x-1.2)-(3X^2-3.1))"};
	 * @param s: is a string represents a Polynom
	 */
	public Polynom(String s) {
		s=s.replaceAll(" ", "");
		monomsList = new ArrayList<>();
		if(s=="" || s=="0") {
			monomsList.add(Monom.ZERO);
			return;
		}
		String monomStr = "";
		Monom m;
		int indexStr = 0;
		for (int i=1;i<s.length();i++) {
			if (s.charAt(i)=='+' || s.charAt(i)=='-') {
				try {
				monomStr = s.substring(indexStr,i); 
				}catch (IndexOutOfBoundsException e) {
					e.printStackTrace();
					System.out.println("Error, worng format"); //if indexStr>i
				}
				m = new Monom(monomStr);
				add(m);
				indexStr = i;
			}
		}
		try {
		monomStr = s.substring(indexStr,s.length());
		}catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			System.out.println("Error, worng format");
		}
		m = new Monom(monomStr);
		add(m);	
	}
	
	@Override
	public double f(double x) {
		double ans = 0;
		Iterator<Monom> i = this.iteretor();
		while (i.hasNext()) {
			ans+=i.next().f(x);
		}
		return ans;
	}

	@Override
	public void add(Polynom_able p1) {
		Iterator<Monom> i = p1.iteretor();
		while (i.hasNext()) {
			this.add(i.next());
		}
	}

	@Override
	public void add(Monom m1) {
		Monom temp = new Monom(m1);
		Iterator<Monom> i = this.iteretor();
		while (i.hasNext()) {
			Monom next = i.next();
			if(Monom.getComp().compare(temp, next) == 0){
				next.add(temp);
				return;
			}
		}
		this.monomsList.add(temp);
		sort(this);	
	}

	@Override
	public void substract(Polynom_able p1) {
		Polynom thisPolynom = new Polynom(this.toString());
		p1.multiply(Monom.MINUS1);
		p1.add(thisPolynom);
		
	}

	@Override
	public void multiply(Polynom_able p1) {
		Polynom copy = new Polynom(this.toString()); 
		boolean flag = true;
		
		Iterator<Monom> i = p1.iteretor();
		while (i.hasNext()) {
			if (flag) {
				this.multiply(i.next());
				flag = false;
			}else {
				Polynom_able temp = copy.copy();
				temp.multiply(i.next());
				this.add(temp);
			}
		}
	}

	@Override
	public boolean equals(Object p1) {
		if(p1 instanceof Monom)
			return equals(new Polynom(p1.toString()));
		
		if(!(p1 instanceof Polynom))
			throw new RuntimeException("Error, unknown object");
		
		Iterator<Monom> j = ((Polynom)p1).iteretor();
		Iterator<Monom> i = this.iteretor();
		
		// if the sizes are different
		if(this.monomsList.size()!=polynomSize((Polynom)p1)) {return false;} 
		
		while (i.hasNext() && j.hasNext()) {
			if(!i.next().equals(j.next())) {return false;}
		}
		
		if(i.hasNext() || j.hasNext()) {return false;}
		
		return true;
	}
	
	public int polynomSize(Polynom_able p1) {
		Iterator<Monom> j = p1.iteretor();
		
		int count = 0;
		while (j.hasNext()) { 
			j.next();
			count++;
		}
		return count;
	}

	@Override
	public boolean isZero() {
		Iterator<Monom> i = this.iteretor();
		if (!i.hasNext()) return true;
		while (i.hasNext()) {
			Monom next = i.next();
			if(next.get_coefficient() != 0) {
				return false;
			}
		}		
		return true;
	}

	@Override
	public double root(double x0, double x1, double eps) {
		if (f(x0)*f(x1)>0) {
			throw new RuntimeException("Error: function has no root");
		}

		double newX = (x0+x1)/2;
		double newY = f(newX);
		if (Math.abs(newY)<Math.abs(eps)) return newX;

		if (f(x0)<0){
			if (newY<0){
				return root(newX,x1,eps);
			}
			return root(x0,newX,eps);
		} else {
			if (newY<0){
				return root(x0,newX,eps);
			}
			return root(newX,x1,eps);
		}
	}

	@Override
	public Polynom_able copy() {
		Polynom copy = new Polynom(this.toString());
		return copy;
	}

	@Override
	public Polynom_able derivative() {
		Polynom derivative = new Polynom();
		Iterator<Monom> i = this.iteretor();
		while (i.hasNext()) {
			Monom temp = new Monom(i.next());
			derivative.add(temp.derivative());
		}
		return derivative;
	}

	@Override
	public double area(double x0, double x1, double eps) {
		if (x1 == x0) {
			return 0; 
		}
		double max = Math.max(x1, x0);
		double min = Math.min(x1, x0);
		int numOfSquares = (int)((max-min)/eps);		
		double sum = 0;
		for (int i = 0; i < numOfSquares; i++) {
			if(this.f(min)>0) {
				sum = sum+(eps*this.f(min));
			}
			min = min+eps;
		}
		return sum;
	}

	@Override
	public Iterator<Monom> iteretor() {
		return monomsList.iterator();
	}
	
	@Override
	public void multiply(Monom m1) {
		Monom temp = new Monom(m1);
		Iterator<Monom> i = this.iteretor();
		while (i.hasNext()) {
			i.next().multipy(temp);
		}
	}
	
	public String toString() {
		String ans = "";
		boolean flag = false;
		
		Iterator<Monom> i = this.iteretor();
		while (i.hasNext()) {
			Monom m = new Monom(i.next()); 
			if(m.equals(Monom.ZERO)) {
				continue;
			}
			if(flag) { // no need in the first monom
				if (m.get_coefficient()>=0) { 
					ans+="+";
				}
			}
			flag = true;
			ans+=m.toString();
		}
		return ans;
	}
	
	public void sort(Polynom_able p1) {
		 // sorting by power
		this.monomsList.sort(Monom.getComp());
		
	}

	
	@Override
	public function initFromString(String s) {
		function newPolynom = new Polynom(s);
		return newPolynom;
	}
	
	
}
