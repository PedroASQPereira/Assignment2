
import java.util.ArrayList;

public class Initialise {

	public Initialise(ArrayList<Point> data,Point [] centroids,Point [] cluster1,Point [] cluster2,Point [] cluster3,Point [] cluster4, int [] index_cluster) {
		
		double dist1=0,dist2 = 0,dist3=0,dist4=0;
		int index1,index2,index3,index4;
			//In this method the algorithm is initialized by first 
			//randomly choosing the centroid of the clusters
				
			int x = data.size()/6;
			int index =0;

			//Randomize initial positions of centroids
			centroids[0]=data.get(x-1);
			centroids[1]=data.get(2*x-1);
			centroids[2]=data.get(5*x);
			centroids[3]=data.get(3*x);		
			
			index1=0;
		  	index2=0;
		  	index3=0;
		  	index4=0;
			
			//assign each point to a cluster based on distance from the randomized centroids
			for(int i=0; i <data.size();i++) {
					
				dist1 = 0;
				dist2 = 0;
				dist3 = 0;
				dist4 = 0;
					
					for(int j=0; j< data.get(i).data_array.size();j++) {
						dist1 += Math.pow( centroids[0].data_array.get(j).voltage - data.get(i).data_array.get(j).voltage,2 ) + Math.pow( centroids[0].data_array.get(j).angle - data.get(i).data_array.get(j).angle,2 );
						dist2 += Math.pow( centroids[1].data_array.get(j).voltage - data.get(i).data_array.get(j).voltage,2 ) + Math.pow( centroids[1].data_array.get(j).angle - data.get(i).data_array.get(j).angle,2 );
						dist3 += Math.pow( centroids[2].data_array.get(j).voltage - data.get(i).data_array.get(j).voltage,2 ) + Math.pow( centroids[2].data_array.get(j).angle - data.get(i).data_array.get(j).angle,2 );
						dist4 += Math.pow( centroids[3].data_array.get(j).voltage - data.get(i).data_array.get(j).voltage,2 ) + Math.pow( centroids[3].data_array.get(j).angle - data.get(i).data_array.get(j).angle,2 );
					}
			
				
				dist1=Math.sqrt(dist1);	
				dist2=Math.sqrt(dist2);	
				dist3=Math.sqrt(dist3);	
				dist4=Math.sqrt(dist4);	
				
				//return closest cluster to point and assign it to the respective cluster
				index = NearestClusterIndex(dist1,dist2,dist3,dist4);
				switch(index) {
					case 0:
						cluster1[index1]= data.get(i);
						index1++;
						break;
					case 1:
						cluster2[index2]= data.get(i);
						index2++;
						break;
					case 2:
						cluster3[index3]= data.get(i);
						index3++;
						break;
					case 3:
						cluster4[index4]= data.get(i);
						index4++;
						break;
			}
		}
		
		index_cluster[0] = index1;
		index_cluster[1] = index2;
		index_cluster[2] = index3;
		index_cluster[3] = index4;
	}
	
	
	public static int NearestClusterIndex(double dist1, double dist2,double dist3,double dist4) {
		
		//find the minimum distance of a vector and return the index of its respective value
		double[] buffer = new double[4];
		double min = 0;
		int index = 0;
		
		buffer[0] = dist1;
		buffer[1] = dist2;
		buffer[2] = dist3;
		buffer[3] = dist4;
		min = dist1;
		
		for(int i = 0; i < 4;i++) {
			if(min > buffer[i]) {
				index = i;
				min = buffer[i];
			}
		}
		
		return index;
	}
		
		
}
	
	

