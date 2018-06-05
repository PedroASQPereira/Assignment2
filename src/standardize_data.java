import java.util.ArrayList;

public class standardize_data {
	
	public standardize_data(ArrayList<Point> data, ArrayList<Point> standardize_data) {
		double av_Volt, av_Angle;
		double max_Volt, max_Angle;
		
		av_Volt = calculate_averageVolt(data);
		av_Angle = calculate_averageAngle(data);
		
		//System.out.println("Size of data is "+ data.size());
		//System.out.println("Size of standardize data is "+ standardize_data.size());
		
		for(int i = 0; i< standardize_data.size();i++) {
			for(int j = 0; j< standardize_data.get(0).data_array.size();j++) {
				standardize_data.get(i).data_array.get(j).voltage = standardize_data.get(i).data_array.get(j).voltage-av_Volt;
				standardize_data.get(i).data_array.get(j).angle = standardize_data.get(i).data_array.get(j).angle-av_Angle;
			}
		}
		
		max_Volt = get_max_Volt(data);
		max_Angle = get_max_Angle(data);
		
		for(int i = 0; i< standardize_data.size();i++) {
			for(int j = 0; j< standardize_data.get(0).data_array.size();j++) {
				standardize_data.get(i).data_array.get(j).voltage = standardize_data.get(i).data_array.get(j).voltage/max_Volt;
				standardize_data.get(i).data_array.get(j).angle = standardize_data.get(i).data_array.get(j).angle/max_Angle;
			}
		}
		
		System.out.println("average voltage and angle "+av_Volt+"  "+av_Angle);
		System.out.println("maximum voltage and angle "+max_Volt+"  "+max_Angle);
		
		
	}
	
	
	/*public void de_standardize(ArrayList<Point> data, ArrayList<Point> standardize_data) {
		
		double av_Volt, av_Angle;
		double max_Volt, max_Angle;
		
		av_Volt = calculate_averageVolt(data);
		av_Angle = calculate_averageAngle(data);
		
		max_Volt = get_max_Volt(data);
		max_Angle = get_max_Angle(data);
		//System.out.println("Size of data is "+ data.size());
		//System.out.println("Size of standardize data is "+ standardize_data.size());
		
		for(int i = 0; i< standardize_data.size();i++) {
			for(int j = 0; j< standardize_data.get(0).data_array.size();j++) {
				standardize_data.get(i).data_array.get(j).voltage = standardize_data.get(i).data_array.get(j).voltage*max_Volt;
				standardize_data.get(i).data_array.get(j).angle = standardize_data.get(i).data_array.get(j).angle*max_Angle;
			}
		}
		

		
		for(int i = 0; i< standardize_data.size();i++) {
			for(int j = 0; j< standardize_data.get(0).data_array.size();j++) {
				standardize_data.get(i).data_array.get(j).voltage = standardize_data.get(i).data_array.get(j).voltage+av_Volt;
				standardize_data.get(i).data_array.get(j).angle = standardize_data.get(i).data_array.get(j).angle+av_Angle;
			}
		}
		
		//System.out.println("Size of data is "+ data.size() + "max voltage value is " + max_Volt  + "max angle value is " + max_Angle);
		//System.out.println("Size of standardize data is "+ standardize_data.size());
		
		for(int i = 0; i< standardize_data.size();i++) {
			for(int j = 0; j< standardize_data.get(0).data_array.size();j++) {
				System.out.print(standardize_data.get(i).data_array.get(j).voltage+"  ");
				System.out.println(standardize_data.get(i).data_array.get(j).angle );
			}
		}
		
		System.out.println("average voltage and angle "+av_Volt+"  "+av_Angle);
		System.out.println("maximum voltage and angle "+max_Volt+"  "+max_Angle);
		
		
	}*/
	
	public double calculate_averageVolt(ArrayList<Point> data) {
		double average_Volt =0;
		
		for(Point p : data) {
			for(Object o : p.data_array) {
				average_Volt += o.voltage/(data.size()*p.data_array.size());
			}	
		}
				
		return average_Volt;
	}
	
	public double calculate_averageAngle(ArrayList<Point> data) {
		double average_Angle =0;
		
		for(Point p : data) {
			for(Object o : p.data_array) {
				average_Angle += o.angle/(data.size()*p.data_array.size());
			}	
		}		
		
		return average_Angle;
	}
	
	public double get_max_Volt(ArrayList<Point> data) {
		double max_volt=0;
		
		for(Point p : data) {
			for(Object o : p.data_array) {
				if(max_volt < Math.abs(o.voltage)) {
					max_volt = Math.abs(o.voltage);
				}
			}	
		}
		
		return max_volt;
	}
	
	public double get_max_Angle(ArrayList<Point> data) {
		double max_angle=0;
		
		for(Point p : data) {
			for(Object o : p.data_array) {
				if(max_angle < Math.abs(o.angle)) {
					max_angle = Math.abs(o.angle);
				}
			}	
		}
		
		return max_angle;
	}
	
}
