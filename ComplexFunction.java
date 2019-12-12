package Ex1;

public class ComplexFunction implements complex_function{
	
	private Operation _op;
	private function _left;
	private function _right;
	
	//empty constructor
	public ComplexFunction() {
		this._op = Operation.None;
		this._left = new Polynom("0");
		this._right = null;
		
	}
	
	public ComplexFunction (function func) {
		this._op = Operation.None;
		this._left = func;
		this._right = null;
	}
	
	public ComplexFunction (Operation op, function func1, function func2) {
		this._op = op;
		this._left = func1;
		this._right = func2;
	}
	
	public ComplexFunction (String strOp, function func1, function func2) {
		this._op = opFromString(strOp);
		this._left = func1;
		this._right = func2;
	}
	

	@Override
	public double f(double x) {
		switch (getOp()) {
			case Plus: {
				return this._left.f(x) + this._right.f(x);
			}
			case Divid: {
				if (this._right.f(x) == 0) { //divide at 0
					throw new ArithmeticException("Error, can't divide by zero");
				} else {
					return this._left.f(x) / this._right.f(x);
				}
			}
			case Times: {
				return this._left.f(x) * this._right.f(x);
			}
			case Max: {
				return Math.max(this._left.f(x), this._right.f(x));
			}
			case Min: {
				return Math.min(this._left.f(x), this._right.f(x));
			}
			case Comp: {
				return this._left.f(this._right.f(x));
			}
			case None: {
			}
			default: //Error
				throw new RuntimeException("Operation does not exist"); 
		}
	}

	
	@Override
	public function initFromString(String s) {
		s=s.replaceAll(" ", "");
		
		if (!s.contains("(")) { // aka its a simple polynom
			return new Polynom(s);
			
		} else { //its a complex function
			String strOp = "";
			int beginIndex = 0; //to create a sub string
			int endIndex = 0;
			int countOpen = 0;
			boolean flag = true;
			
			for (int i = 0; i < s.length(); i++) {
				if (s.charAt(i) != '(') {
					if(countOpen==0) strOp += s.charAt(i);
					if (s.charAt(i) == ')') {
						countOpen--;
					}
				} else { // s.charAt(i)=='('
					if (flag) {
						flag = false;
						
						beginIndex = i + 1;
						endIndex = s.length();
					}
					countOpen++;
				}

				if (s.charAt(i) == ',' && countOpen == 1) {
					String strL, strR;
					strL = s.substring(beginIndex, i);
					strR = s.substring(i + 1, endIndex-1);

					return new ComplexFunction(opFromString(strOp), initFromString(strL), initFromString(strR));
				}
			}
		}
		throw new RuntimeException ("Error, worng format"); //can't find ',' in the correct place

	}

	public String toString() {
		return this._op.toString()+"("+this._left.toString()+","+this._right.toString()+")";
	}

	@Override
	public function copy() {
		function l = initFromString(this._left.toString());
		function r = initFromString(this._right.toString());
		return new ComplexFunction (this._op, l, r);
	}
	
	public boolean equals(Object obj) {
		if(!(obj instanceof function))
			return false;
		
		function func = (function)obj;
			
		for (int i = -100; i <= 100; i++) {
			if(Math.abs(this.f(i) - func.f(i)) > Monom.EPSILON) 
				return false;
		}
		return true;
	}

	@Override
	public void plus(function f1) { 
		if (this._right != null) {
			ComplexFunction toLeft = new ComplexFunction(this._op, this._left, this._right);
			this._op = Operation.Plus;
			this._left = toLeft;
			this._right = f1;
		} else {
			this._op = Operation.Plus;
			this._right = f1;
		} 
	}

	@Override
	public void mul(function f1) {
		if (this._right != null) {
			ComplexFunction toLeft = new ComplexFunction(this._op, this._left, this._right);
			this._op = Operation.Times;
			this._left = toLeft;
			this._right = f1;
		} else {
			this._op = Operation.Times;
			this._right = f1;
		}
	}

	@Override
	public void div(function f1) {
		if (this._right != null) {
			ComplexFunction toLeft = new ComplexFunction(this._op, this._left, this._right);
			this._op = Operation.Divid;
			this._left = toLeft;
			this._right = f1;
		} else {
			this._op = Operation.Divid;
			this._right = f1;
		}
	}

	@Override
	public void max(function f1) {
		if (this._right != null) {
			ComplexFunction toLeft = new ComplexFunction(this._op, this._left, this._right);
			this._op = Operation.Max;
			this._left = toLeft;
			this._right = f1;
		} else {
			this._op = Operation.Max;
			this._right = f1;
		}
	}

	@Override
	public void min(function f1) {
		if (this._right != null) {
			ComplexFunction toLeft = new ComplexFunction(this._op, this._left, this._right);
			this._op = Operation.Min;
			this._left = toLeft;
			this._right = f1;
		} else {
			this._op = Operation.Min;
			this._right = f1;
		}
	}

	@Override
	public void comp(function f1) {
		if (this._right != null) {
			ComplexFunction toLeft = new ComplexFunction(this._op, this._left, this._right);
			this._op = Operation.Comp;
			this._left = toLeft;
			this._right = f1;
		} else {
			this._op = Operation.Comp;
			this._right = f1;
		}
	}

	@Override
	public function left() {
		return this._left;
	}

	@Override
	public function right() {
		return this._right;
	}

	@Override
	public Operation getOp() { 
		return this._op;
	}
	
	public Operation opFromString(String s) {
		switch (s) {
			case ("Plus"):
			case ("plus"): {
				return Operation.Plus;
			}
			case ("div"):
			case ("Divid"):
			case ("divid"): {
				return Operation.Divid;
			}
			case ("mul"):
			case ("Times"):
			case ("times"): {
				return Operation.Times;
			}
			case ("Max"):
			case ("max"): {
				return Operation.Max;
			}
			case ("Min"):
			case ("min"): {
				return Operation.Min;
			}
			case ("Comp"):
			case ("comp"): {
				return Operation.Comp;
			}
			case ("None"):
			case ("none"): {
				return Operation.None;
			}
			default: 
				return Operation.Error;
		}
	}
	

}
