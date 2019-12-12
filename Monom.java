package Ex1;

import java.util.Comparator;

/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real number and a is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply. 
 * @author Boaz
 *
 */
public class Monom implements function{
	public static final Monom ZERO = new Monom(0,0);
	public static final Monom MINUS1 = new Monom(-1,0);
	public static final double EPSILON = 0.0000001;
	public static final Comparator<Monom> _Comp = new Monom_Comperator();
	public static Comparator<Monom> getComp() {return _Comp;}
	public Monom(double a, int b){
		this.set_coefficient(a);
		this.set_power(b);
	}
	public Monom(Monom ot) {
		this(ot.get_coefficient(), ot.get_power());
	}
	
	public double get_coefficient() {
		return this._coefficient;
	}
	public int get_power() {
		return this._power;
	}
	
	/** 
	 * this method returns the derivative monom of this.
	 * @return
	 */
	public Monom derivative() { 
		if(this.get_power()==0) {return getNewZeroMonom();}
		return new Monom(this.get_coefficient()*this.get_power(), this.get_power()-1);
	}
	
	public double f(double x) {
		double ans=0;
		double p = this.get_power();
		ans = this.get_coefficient()*Math.pow(x, p);
		return ans;
	} 
	
	public boolean isZero() {return this.get_coefficient() == 0;}
	
	// ***************** add your code below **********************
	public Monom(String s) {
		s=s.replaceAll(" ", "");
		if (s=="0") {
			this.set_coefficient(0);
			return;
		}
		
		int i = 0;
		if (s.charAt(i)=='-') {
			this.set_coefficient(-1);
			i++;
		} else { this.set_coefficient(1); }
		double sign = this.get_coefficient();
		
		try {
			if (s.contains("x")) {
				int indexOfX = s.indexOf('x');
				if(indexOfX>i) {
					 this.set_coefficient(sign*Double.parseDouble(s.substring(i, indexOfX)));
				}
				if (s.contains("^")) {
					this.set_power(Integer.parseInt(s.substring(indexOfX+2, s.length())));
				}else {
					this.set_power(1);
				}
			}else { //if not contain x 
				this.set_power(0);
				this.set_coefficient(sign*Double.parseDouble(s.substring(i, s.length())));
			}
		}catch (NumberFormatException e) {
			e.printStackTrace();
			System.out.println("Error, worng format");
		}
	}
	
	public void add(Monom m) {
		if (getComp().compare(this, m) == 0) {
			this.set_coefficient(this._coefficient + m._coefficient);
		} else {
			throw new RuntimeException("ERR you can only add a monom with the same power: "+this.get_power());
		}
	}
	
	public void multipy(Monom d) {
		this.set_power(this.get_power() + d.get_power());
		this.set_coefficient(this.get_coefficient() * d.get_coefficient());
	}
	
	public String toString() {
		if (this.isZero()) return "0";
		String s = "";
		int newcoeff = (int) this.get_coefficient();
		
		if (this.get_power() == 0) {
			if (newcoeff==this.get_coefficient()) {
				return s+newcoeff;
			}else{ return s+this.get_coefficient(); }
		}
		
		if (this.get_coefficient() == -1) {
			s+= "-";
		}else if(this.get_coefficient() != 1){
			if (newcoeff==this.get_coefficient()) {
				s += newcoeff;
			}else{ s += this.get_coefficient(); }	
		}
		
		if (this.get_power() == 1) {
			return s+"x";
		}
		if(this.get_power() > 0) {
			s += "x^";
			s += this.get_power();
		}
		return s;
	}
	
	public boolean equals (Monom m) {
		double difference = this.get_coefficient() - m.get_coefficient();
		if(Math.abs(difference)>Monom.EPSILON) {
			return false;
		}
		if (this.get_coefficient() == 0) { return true; }
		if (getComp().compare(this, m) == 0) { 
			return true; 
		} else { return false; }
	}
	

	//****************** Private Methods and Data *****************

	private void set_coefficient(double a){
		this._coefficient = a;
	}
	private void set_power(int p) {
		if(p<0) {throw new RuntimeException("ERR the power of Monom should not be negative, got: "+p);}
		this._power = p;
	}
	private static Monom getNewZeroMonom() {return new Monom(ZERO);}
	private double _coefficient; 
	private int _power;
	
	
	@Override
	public function initFromString(String s) {
		function newPolynom = new Monom(s);
		return newPolynom;
	}
	@Override
	public function copy() { 
		Monom copy = new Monom(this.toString());
		return copy;
	}
	
	
}
