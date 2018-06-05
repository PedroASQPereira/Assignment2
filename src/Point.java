import java.util.ArrayList;

public class Point {

	ArrayList<Object> data_array;
	
	public Point()	{
		data_array = new ArrayList<Object>();
	}
	
	public void fillZeros(ArrayList<Point> data) {	
		for(int i = 0; i < data.get(0).data_array.size(); i++) {
			Object o = new Object("Nothing",0);
			o.addangle(0);
			o.addvoltage(0);
			this.data_array.add(o);
		}
	}
}
