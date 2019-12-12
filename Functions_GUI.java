package Ex1;

import java.awt.Color;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.google.gson.Gson;

public class Functions_GUI implements functions{
	
	private ArrayList<function> functionsList;
	
	public Functions_GUI(){
		functionsList = new ArrayList<>();
	}

	@Override
	public void initFromFile(String file) throws IOException {
		String line = "";
        try {
        	BufferedReader br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
    			function f = new ComplexFunction().initFromString(line);
                this.functionsList.add(f);
            }
            br.close();
        } 
        catch (IOException e) {
        	e.printStackTrace();
        	throw new IOException ("could not read file");
        }
	}

	@Override
	public void saveToFile(String file) throws IOException {
		try {
			PrintWriter pw = new PrintWriter(new File(file));
			StringBuilder sb = new StringBuilder();
			
			int i=0;
			while (this.functionsList.size()>i) {
//				function f = functionsList.get(i);
//				pw.write(i + ") " + "f(x)=" + f.toString() + '\n');
				
				sb.append(functionsList.get(i));
				sb.append("\n");
				i++;
			}
			pw.write(sb.toString());
			pw.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
	public static Color[] Colors = {Color.blue, Color.cyan,
			Color.MAGENTA, Color.ORANGE, Color.red, Color.GREEN, Color.PINK};

	@Override
	public void drawFunctions(int width, int height, Range rx, Range ry, int resolution) {
		StdDraw.setCanvasSize(width, height);

		StdDraw.setXscale(rx.get_min(), rx.get_max());
		StdDraw.setYscale(ry.get_min(), ry.get_max());
		
		StdDraw.line(rx.get_min(), 0, rx.get_max(), 0);
		StdDraw.line(0, ry.get_min(), 0, ry.get_max());
		
		double step = (rx.get_max() - rx.get_min())/resolution;
		double f0, f1;
		for (int i=0; i <this.functionsList.size() ;i++) {
			
			StdDraw.setPenColor(Colors[i % Colors.length]);
			
			for(double x=rx.get_min(); x<rx.get_max(); x+=step) {
				f0 = this.functionsList.get(i).f(x);
				f1 = this.functionsList.get(i).f(x+step);

				if(f0>ry.get_min() && f0<ry.get_max() && f0>ry.get_min() && f0<ry.get_max())
					StdDraw.line(x, f0, x+step, f1);
			}
		}
			
	}

	@Override
	public void drawFunctions(String json_file) {
		Gson gson = new Gson();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(json_file));
			GUIparameters param = gson.fromJson(reader,GUIparameters.class);
			
			drawFunctions(param.Width, param.Height, param.Range_X, param.Range_Y, param.Resolution);
	            
		} catch (Exception e) { //FileNotFoundException?
			System.out.println("the file is unreadable or in wrong format");
			GUIparameters param = new GUIparameters();
			drawFunctions(param.Width, param.Height, param.Range_X, param.Range_Y, param.Resolution);
		}
	}
	
	@Override
	public boolean add(function arg0) {
		return functionsList.add(arg0);
	}

	@Override
	public boolean addAll(Collection<? extends function> arg0) {
		return functionsList.addAll(arg0);
	}

	@Override
	public void clear() {
		functionsList.clear();
	}

	@Override
	public boolean contains(Object arg0) {
		return functionsList.contains(arg0);
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		return functionsList.containsAll(arg0);
	}

	@Override
	public boolean isEmpty() {
		return functionsList.isEmpty();
	}

	@Override
	public Iterator<function> iterator() {
		return functionsList.iterator();
	}

	@Override
	public boolean remove(Object arg0) {
		return functionsList.remove(arg0);
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		return functionsList.removeAll(arg0);
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		return functionsList.retainAll(arg0);
	}

	@Override
	public int size() {
		return functionsList.size();
	}

	@Override
	public Object[] toArray() {
		return functionsList.toArray();
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		return functionsList.toArray(arg0);
	}
	
	
	public function get(int i) {
		return functionsList.get(i);
	}

}
