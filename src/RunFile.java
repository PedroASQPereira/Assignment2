
import java.util.*;

public class RunFile {

	static public ArrayList<Point> data = new ArrayList<Point>();
	static public ArrayList<Point> testset = new ArrayList<Point>();
	
	static public Point [] centroids;
	
	static public Point[] cluster1;
	static public Point[] cluster2;
	static public Point[] cluster3;
	static public Point[] cluster4;
	
	static public double dist1,dist2,dist3,dist4;
	static public int[] index;
	
	public static void main(String[] args) throws Exception {
		
		new Read_SQL(data,"measurements");
		initDataDimension();
		
		//IF DATA is STANDARDIZED the results with be the same, hence although the code is available 
		//and working it is not used for simplification purposes

		
		new Initialise(data,centroids,cluster1,cluster2,cluster3,cluster4, index);
		//k-means clustering algorithm
		new k_means_clustering(data,centroids,cluster1,cluster2,cluster3,cluster4,index);
		
		//PRINT CENTROID POSITIONS
		printcentroidPositions();
		//PRINT TIME STAMPS CONNECTED TO EACH CLUSTER
		printTimeStampsConnectedtoClusters();
		//LABEL CLUSTERS BASED ON THEIR RESPECTIVE ASSOCIATED POINTS
		new labeling_clusters (testset, data,cluster1, cluster2, cluster3, cluster4, index);
		
		System.out.println("\nNow additional point are added with kNN algorithm");
		//READ TEST FILE
		new Read_SQL(testset,"analog_values");
		//kNN algorithm
		new k_nearest_neighbor(testset,data,centroids,cluster1,cluster2,cluster3,cluster4, index);
		//PRINT TIME STAMPS CONNECTED TO EACH CLUSTER
		printTimeStampsConnectedtoClusters();

	
		
	}
	
	public static void initDataDimension() {
		
		centroids = new Point[4];
				
		cluster1 = new Point[data.size()];
		cluster2 = new Point[data.size()];
		cluster3 = new Point[data.size()];
		cluster4 = new Point[data.size()];
		
		index = new int[4];
		for(int i = 0; i < 4; i++) {
			Point p = new Point();
			centroids[i] = p; centroids[i].fillZeros(data);
			index[i] = 0;
		}
		
		for(int i = 0; i < data.size(); i++) {
			Point p = new Point();
			cluster1[i] = p; cluster1[i].fillZeros(data);
			cluster2[i] = p; cluster2[i].fillZeros(data);
			cluster3[i] = p; cluster3[i].fillZeros(data);
			cluster4[i] = p; cluster4[i].fillZeros(data);
		}		
	}
	
	
	public static void printTimeStampsConnectedtoClusters() {
		
		for(int i =0;i <4;i++) {
			System.out.println("Cluster "+i+ " has  "+index[i] +"points");
		}
		System.out.print("\n");
		
		System.out.print("Cluster 1 has the Points with time:\n");
		for(int i = 0; i < data.size();i++) {
			if(cluster1[i].data_array.get(0).time==0) {break;}else {
			System.out.print(" "+cluster1[i].data_array.get(0).time+" ");
			}
		}
		System.out.println("");
		
		
		System.out.print("Cluster 2 has the Points with time:\n");
		for(int i = 0; i < data.size();i++) {
			if(cluster2[i].data_array.get(0).time==0) {break;}else {
			System.out.print(" "+cluster2[i].data_array.get(0).time+" ");}
		}
		System.out.println("");
		
		
		System.out.print("Cluster 3 has the Points with time:\n");
		for(int i = 0; i < data.size();i++) {
			if(cluster3[i].data_array.get(0).time==0) {break;}else {
			System.out.print(" "+cluster3[i].data_array.get(0).time+" ");}
		}
		System.out.println("");
		
		
		System.out.print("Cluster 4 has the Points with time:\n");
		for(int i = 0; i < data.size();i++) {
			if(cluster4[i].data_array.get(0).time==0) {break;}else {
			System.out.print(" "+cluster4[i].data_array.get(0).time+" ");}
		}
		System.out.println("");
		
	}
	
	
	
	public static void printcentroidPositions() {
		
		System.out.print("Centroid 1 has the Points with time:\n");
		for(int i = 0; i < 9;i++) {
			System.out.printf(" Voltage: %.3f Angle: %.3f \n",centroids[0].data_array.get(i).voltage,centroids[0].data_array.get(i).angle);
		}
		System.out.println("");
		
		System.out.print("Centroid 2 has the Points with time:\n");
		for(int i = 0; i < 9;i++) {
			System.out.printf(" Voltage: %.3f Angle: %.3f \n",centroids[1].data_array.get(i).voltage,centroids[1].data_array.get(i).angle);
		}
		System.out.println("");
		
		System.out.print("Centroid 3 has the Points with time:\n");
		for(int i = 0; i < 9;i++) {
			System.out.printf(" Voltage: %.3f Angle: %.3f \n",centroids[2].data_array.get(i).voltage,centroids[2].data_array.get(i).angle);
		}
		System.out.println("");
		
		System.out.print("Centroid 4 has the Points with time:\n");
		for(int i = 0; i < 9;i++) {
			System.out.printf(" Voltage: %.3f Angle: %.3f \n",centroids[3].data_array.get(i).voltage,centroids[3].data_array.get(i).angle);
		}
		System.out.println("");
		
		
	}
	
		
}



