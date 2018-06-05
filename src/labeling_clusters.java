import java.util.ArrayList;
import java.util.Objects;

public class labeling_clusters {
	
	String[] labels;
	
	public labeling_clusters (ArrayList<Point> testset, ArrayList<Point> data,Point [] cluster1,Point [] cluster2,Point [] cluster3,Point [] cluster4, int[] index_cluster) {
		int indexes = 0;
		String[] labels = {"","","",""};	
		
		//check voltage angle differences at lines connected to generators to check if there 
		//negligible active power flow (proportional to the angle difference)
		indexes = generator_disconnect(cluster1,cluster2,cluster3,cluster4,index_cluster,data);
		labels[indexes] = "Shut down of generator for maintenance";
		//check if there is a line disconnected by calculating which cluster's line has the highest
		//voltage phasor difference (magnitude of complex voltage difference)
		indexes = line_disconnect(cluster1,cluster2,cluster3,cluster4,index_cluster,data);
		labels[indexes] = "Disconnection of a line for maintenance";
		//assign the remaining cluster based on their capacitive or inductive nature
		labels = high_and_low_load(cluster1,cluster2,cluster3,cluster4,index_cluster, labels);

		//PRINT TO BOARD THE STATE OF EACH CLUSTER
		System.out.println("");
		for(int i = 0;i<4;i++) {
			System.out.println("Cluster " + (i+1) + "has state: " +labels[i]);
		}
		
	}
	
	
	public int line_disconnect(Point [] cluster1,Point [] cluster2,Point [] cluster3,Point [] cluster4, int[] index_cluster,ArrayList<Point> data) {
		//Check if any line at the points connected to a certain cluster is disconnected
		//by comparing the phasor voltage difference between two buses connected through a line
		int check = 0;
		for(Point p: cluster1) {
			if(check_voltdif(p) == 0 && p.data_array.get(0).time != 0) {
				check = 1;
				break;
			}
		}
		
		if(check == 0) {
			return 0;
		}
		

		check = 0;
		for(Point p: cluster2) {
			if(check_voltdif(p) == 0&& p.data_array.get(0).time != 0) {
				check = 1;
				break;
			}
		}
		
		if(check == 0) {
			return 1;
		}
		

		check = 0;
		for(Point p: cluster3) {
			if(check_voltdif(p) == 0 && p.data_array.get(0).time != 0) {
				check = 1;
				break;
			}
		}
		
		if(check == 0) {
			return 2;
		}
		

		check = 0;
		for(Point p: cluster4) {
			if(check_voltdif(p) == 0 && p.data_array.get(0).time != 0) {
				check = 1;
				break;
			}
		}
		
		if(check == 0) {
			return 3;
		}
		
		
		return 4;
	}
	
	public int check_voltdif(Point p) {
		double calc[]= new double[9];
		//CALCULATE PHASOR VOLTAGE DIFFERENCE FOR EACH LINE IN THE GRID PROVIDED
		calc[0] = getmagnitude( buildComplex( p.data_array.get(0).voltage , p.data_array.get(0).angle ).subtraction( buildComplex( p.data_array.get(3).voltage , p.data_array.get(3).angle ) )  );
		calc[1] = getmagnitude( buildComplex( p.data_array.get(1).voltage , p.data_array.get(1).angle ).subtraction( buildComplex( p.data_array.get(7).voltage , p.data_array.get(7).angle ) )  );
		calc[2] = getmagnitude( buildComplex( p.data_array.get(2).voltage , p.data_array.get(2).angle ).subtraction( buildComplex( p.data_array.get(5).voltage , p.data_array.get(5).angle ) )  );
		calc[3] = getmagnitude( buildComplex( p.data_array.get(3).voltage , p.data_array.get(3).angle ).subtraction( buildComplex( p.data_array.get(4).voltage , p.data_array.get(4).angle ) )  );
		calc[4] = getmagnitude( buildComplex( p.data_array.get(3).voltage , p.data_array.get(3).angle ).subtraction( buildComplex( p.data_array.get(8).voltage , p.data_array.get(8).angle ) )  );
		calc[5] = getmagnitude( buildComplex( p.data_array.get(4).voltage , p.data_array.get(4).angle ).subtraction( buildComplex( p.data_array.get(5).voltage , p.data_array.get(5).angle ) )  );
		calc[6] = getmagnitude( buildComplex( p.data_array.get(7).voltage , p.data_array.get(7).angle ).subtraction( buildComplex( p.data_array.get(6).voltage , p.data_array.get(6).angle ) )  );
		calc[7] = getmagnitude( buildComplex( p.data_array.get(7).voltage , p.data_array.get(7).angle ).subtraction( buildComplex( p.data_array.get(8).voltage , p.data_array.get(8).angle ) )  );
		calc[8] = getmagnitude( buildComplex( p.data_array.get(6).voltage , p.data_array.get(6).angle ).subtraction( buildComplex( p.data_array.get(5).voltage , p.data_array.get(5).angle ) )  );

		for(int i = 0; i < 9;i++) {
			if(calc[i]>0.2) {
				return 1;
			}
		}
		
		return 0;
	}
		
	public Complex buildComplex(double mag, double angle) {
		Complex complex ;
		double re, im;
		//CHANGE FROM EXPONENCIAL TO POLAR FORMAT of complex numbers
		re = mag*Math.cos((angle*Math.PI)/180);
		im = mag*Math.sin((angle*Math.PI)/180);
		
		complex = new Complex(re,im);
		
		return complex;
	}
		
	public double getmagnitude(Complex x) {
		double mag=0;
		
		mag = Math.sqrt( Math.pow(x.im,2) + Math.pow(x.re,2) );

		return mag;
	}
	
	public int generator_disconnect(Point [] cluster1,Point [] cluster2,Point [] cluster3,Point [] cluster4, int[] index_cluster,ArrayList<Point> data) {
		int counter[] = {0,0,0};
		double calc[]= {0.0,0.0,0.0};
		
		//for all CLUSTERS
		//since the power flow is proporcional to difference in voltage
		//the branchs connecting the generators are analysed and the one with the lowest power flow has disconnected generator
		//difference threeshold < 0.005
		for(Point p: cluster1) {
			calc[0] = Math.abs((p.data_array.get(0).angle-p.data_array.get(3).angle));
			calc[1] = Math.abs((p.data_array.get(1).angle-p.data_array.get(7).angle));
			calc[2] = Math.abs((p.data_array.get(2).angle-p.data_array.get(5).angle));
			
			if(calc[0] < 0.0005 ) {
				counter[0]++;
			}else if(calc[1] < 0.0005){
				counter[1]++;
			}else if(calc[2] < 0.0005) {
				counter[2]++;
			}else {
				break;
			}
		}
		
		if(counter[0] == data.size() || counter[1] == data.size() || counter[2] == data.size()) {
			return 0;
		}
		
		counter[0] = 0;
		counter[1] = 0;
		counter[2] = 0;
		
		//for all CLUSTERS
		//check if the average of cluster1's voltage at the 5,7,9 buses is lower than 0.9
		for(Point p: cluster2) {
			calc[0] = Math.abs((p.data_array.get(0).angle-p.data_array.get(3).angle));
			calc[1] = Math.abs((p.data_array.get(1).angle-p.data_array.get(7).angle));
			calc[2] = Math.abs((p.data_array.get(2).angle-p.data_array.get(5).angle));
			if(calc[0] < 0.0005 ) {
				counter[0]++;
			}
			if(calc[1] < 0.0005){
				counter[1]++;
			}
			if(calc[2] < 0.0005) {
				counter[2]++;
			}
		}
		
		if(counter[0] == data.size() || counter[1] == data.size() || counter[2] == data.size()) {
			return 1;
		}
		
		counter[0] = 0;
		counter[1] = 0;
		counter[2] = 0;
		//for all CLUSTERS
		//check if the average of cluster1's voltage at the 5,7,9 buses is lower than 0.9
		for(Point p: cluster3) {
			calc[0] = Math.abs((p.data_array.get(0).angle-p.data_array.get(3).angle));
			calc[1] = Math.abs((p.data_array.get(1).angle-p.data_array.get(7).angle));
			calc[2] = Math.abs((p.data_array.get(2).angle-p.data_array.get(5).angle));
			if(calc[0] < 0.0005 ) {
				counter[0]++;
			}
			if(calc[1] < 0.0005){
				counter[1]++;
			}
			if(calc[2] < 0.0005) {
				counter[2]++;
			}
			
		}
		
		if(counter[0] == data.size() || counter[1] == data.size() || counter[2] == data.size()) {
			return 2;
		}
		
		counter[0] = 0;
		counter[1] = 0;
		counter[2] = 0;
		//for all CLUSTERS
		//check if the average of cluster1's voltage at the 5,7,9 buses is lower than 0.9
		for(Point p: cluster4) {
			calc[0] = Math.abs((p.data_array.get(0).angle-p.data_array.get(3).angle));
			calc[1] = Math.abs((p.data_array.get(1).angle-p.data_array.get(7).angle));
			calc[2] = Math.abs((p.data_array.get(2).angle-p.data_array.get(5).angle));
			if(calc[0] < 0.0005 ) {
				counter[0]++;
			}
			if(calc[1] < 0.0005){
				counter[1]++;
			}
			if(calc[2] < 0.0005) {
				counter[2]++;
			}
		}
		
		if(counter[0] == data.size() || counter[1] == data.size() || counter[2] == data.size()) {
			return 3;
		}
		
		
		return 4;
	}
		
	public String[] high_and_low_load(Point [] cluster1,Point [] cluster2,Point [] cluster3,Point [] cluster4, int[] index_cluster, String[] labels) {
		int counter = 0;
		double calc=0.0;
		int[] remaining_clusters = {0,0};
		double[] average_angle = {0,0} ;
		Point[] chosen_cluster = null;
		
		//Check which clusters haven't been identified
		for(int i= 0;i<4;i++) {
			if(Objects.equals(labels[i],"")) {
				remaining_clusters[counter] = i;
				counter++;
			}
			
		}
		
		//Chose the first of the unidentified cluster
		for(int k = 0;k<2;k++) {
			calc =0.0;
			switch(remaining_clusters[k]) {
			case 0:
				chosen_cluster = cluster1;
				break;
			case 1:
				chosen_cluster = cluster2;
				break;
			case 2:
				chosen_cluster = cluster3;
				break;
			case 3:
				chosen_cluster = cluster4;
				break;
			}
		
			//calculate the average angle of the entire grid 
			for(Point p: chosen_cluster) {
				for(int i = 0; i< p.data_array.size();i++) {
					if(p.data_array.get(i).angle != 0) {
						calc += p.data_array.get(i).angle/(p.data_array.size()*chosen_cluster.length);
					}
				}
			}
			
			average_angle[k] = calc;
		}
		
		//the highest angle should be capacitive,which means there is a lot of reactive power in the grid
		// and hence the load don't consume as much reactive power (low load) and vice versa for high load
		if(average_angle[0]<average_angle[1]) {
			labels[remaining_clusters[0]] = "High load rate during peak hours";
			labels[remaining_clusters[1]] = "Low load rate during night";
		}else {
			labels[remaining_clusters[1]] = "High load rate during peak hours";
			labels[remaining_clusters[0]] = "Low load rate during night";
		}
		
		return labels;
		
	}		
	
}
