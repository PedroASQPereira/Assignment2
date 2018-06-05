import java.util.ArrayList;
import java.util.Collections;

public class k_nearest_neighbor {

	public k_nearest_neighbor(ArrayList<Point> testset, ArrayList<Point> data,Point [] centroids,Point [] cluster1,Point [] cluster2,Point [] cluster3,Point [] cluster4, int[] index_cluster) {
		
		//for every point in the testset
		for(int i = 0; i < testset.size();i++) {
			
			ArrayList<P_distance> distance = new ArrayList<P_distance>();
			//it is gonna be calculated the distance to every point in the existing database
			for(int j = 0; j <data.size();j++) {
				
				double dist = 0.0;
				
				//Calculate the distance between this data point and the current testing set point
				for(int k = 0; k < data.get(0).data_array.size(); k++) {
					dist += Math.pow((testset.get(i).data_array.get(k).voltage-data.get(j).data_array.get(k).voltage),2);
					dist += Math.pow((testset.get(i).data_array.get(k).angle-data.get(j).data_array.get(k).angle),2);
				}
				dist = Math.sqrt(dist);
				
				P_distance p_dist = new P_distance(data.get(j),dist);
				distance.add(p_dist);	
			}
			
			//sort the current array which has all the distances
			// between one testing set point and all the point in data set
			Collections.sort(distance,new DistanceComparator());
			
			
			//Chose the number of neighbours that will be analyzed
			int K = (int) (0.2*data.size());
			int[] which_cluster = {0,0,0,0};
			for(int j = 0; j < K ; j++) {
				//Check which cluster they belong to (based on the time) and use the nearest cluster index method to choose
				//what is the one it should be inserted into
				
				int index = identify_cluster(distance.get(j),data,cluster1,cluster2,cluster3,cluster4,index_cluster);
				//array has the information of the nearest neighbours of testset 
				which_cluster[index-1]++;
			}
			
			switch(return_cluster(which_cluster)) {
			case 0:
				cluster1[index_cluster[0]]= testset.get(i);
				index_cluster[0]++;
				break;
			case 1:
				cluster2[index_cluster[1]]= testset.get(i);
				index_cluster[1]++;
				break;
			case 2:
				cluster3[index_cluster[2]]= testset.get(i);
				index_cluster[2]++;
				break;
			case 3:
				cluster4[index_cluster[3]]= testset.get(i);
				index_cluster[3]++;
				break;
			}
		}
		
	}
	
	public int identify_cluster(P_distance distance,ArrayList<Point> data, Point [] cluster1,Point [] cluster2,Point [] cluster3,Point [] cluster4, int[] index_cluster ) {
		//identify the cluster the point should be assigned to based on the closest distance of the assumed neighbors
		int index = 0;
		int time = distance.point.data_array.get(0).time;
		
		//Search for the cluster that contains the same time as the nearest point to the testset point
		for(int i = 0; i < index_cluster[0];i++) {
			if(cluster1[i].data_array.get(0).time==time) {
				index=1;
				break;
			}
		}

		for(int i = 0; i < index_cluster[1];i++) {
			if(cluster2[i].data_array.get(0).time==time) {
				index=2;
				break;
			}
		}
		
		for(int i = 0; i < index_cluster[2];i++) {
			if(cluster3[i].data_array.get(0).time==time) {
				index=3;
				break;
			}
		}
		
		for(int i = 0; i < index_cluster[3];i++) {
			if(cluster4[i].data_array.get(0).time==time) {
				index=4;
				break;
			}
		}
	
		return index;
	}
	
	public int return_cluster(int[] which_cluster) {
		int max = 0;
		int index = 0;
		
		for(int i = 0; i < 4;i++) {
			if(max < which_cluster[i]) {
				index = i;
				max = which_cluster[i];
			}
		}

		return index;
		
	}
	
}
