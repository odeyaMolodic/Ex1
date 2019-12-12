package Ex1;

public class GUIparameters {
	
	int Width;
	int Height;
	Range Range_X;
	Range Range_Y;
	int Resolution;
		
	public GUIparameters() {
		this.Width = 1000;
		this.Height = 600;
		this.Range_X = new Range (-10, 10);
		this.Range_Y = new Range (-5, 15);
		this.Resolution = 200;
	}
	
	public GUIparameters(int Width, int Height, Range Range_X, Range Range_Y, int Resolution) {
		this.Width = Width;
		this.Height = Height;
		this.Range_X = Range_X;
		this.Range_Y = Range_Y;
		this.Resolution = Resolution;
	}

}
