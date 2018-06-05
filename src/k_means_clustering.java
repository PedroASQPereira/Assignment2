import java.util.ArrayList;

public class k_means_clustering {

	public k_means_clustering(ArrayList<Point> data,Point [] centroids,Point [] cluster1,Point [] cluster2,Point [] cluster3,Point [] cluster4, int[] index_cluster) {
		//k-means algorithm
		Point [] old_centroids = new Point [4];
		for(int i = 0; i < 4; i++) {
			Point p = new Point();
			old_centroids[i] = p;
			old_centroids[i].fillZeros(data);}
		
		double dif1=0.0,dif2=0.0,dif3=0.0,dif4=0.0;
		double dist1=0,dist2=0,dist3=0,dist4=0;
		int index1,index2,index3,index4;
		double tol=0.00001;
		int index = 0;

		//compute a new centroid position based on the average positions of the points assigned to the same cluster
		centroids = new_centroid(data,centroids,cluster1,cluster2,cluster3,cluster4, index_cluster);
		
		
		System.out.print("\n");
		// arbitrarily assign centroids from within the dataset
		while(true) {
			
			//calculate distance of each value from the centroids
			index1=0;
		  	index2=0;
		  	index3=0;
		  	index4=0;
		  	
		  	clean_clusters(data,cluster1,cluster2,cluster3,cluster4);
		  	
		  	//calculate distances from points to centroids and re-assign them
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
		  	
		  	//the old_centroid is used to compute if the tolerance was been reached or not
			old_centroids[0] = centroids[0];
			old_centroids[1] = centroids[1]; 
			old_centroids[2] = centroids[2]; 
			old_centroids[3] = centroids[3]; 

		  	dif1=0;
			dif2=0;
			dif3=0;
			dif4=0;
			
			index_cluster[0] = index1;
			index_cluster[1] = index2;
			index_cluster[2] = index3;
			index_cluster[3] = index4;
			
			centroids = new_centroid(data,centroids,cluster1,cluster2,cluster3,cluster4, index_cluster);
		  	
			// calculate difference between new and old centroids (?)
			for(int j = 0;j< centroids[0].data_array.size() ;j++) {

				dif1+=(  ( Math.pow(  old_centroids[0].data_array.get(j).voltage - centroids[0].data_array.get(j).voltage  , 2) +
									Math.pow(  old_centroids[0].data_array.get(j).angle - centroids[0].data_array.get(j).angle    , 2) )  )/index1;
				
				dif2+=(  ( Math.pow(  old_centroids[1].data_array.get(j).voltage - centroids[1].data_array.get(j).voltage  , 2) +
									Math.pow(  old_centroids[1].data_array.get(j).angle - centroids[1].data_array.get(j).angle    , 2) )  )/index2;
				
				dif3+=(  ( Math.pow(  old_centroids[2].data_array.get(j).voltage - centroids[2].data_array.get(j).voltage  , 2) +
									Math.pow(  old_centroids[2].data_array.get(j).angle - centroids[2].data_array.get(j).angle    , 2) )  )/index3;
				
				dif4+=(  ( Math.pow(  old_centroids[3].data_array.get(j).voltage - centroids[3].data_array.get(j).voltage  , 2) +
									Math.pow(  old_centroids[3].data_array.get(j).angle - centroids[3].data_array.get(j).angle    , 2) )  )/index4;
				
			}
			
			
			dif1 = Math.sqrt(dif1);
			dif2 = Math.sqrt(dif2);
			dif3 = Math.sqrt(dif3);
			dif4 = Math.sqrt(dif4);
			
			// if difference is less than a specified tolerance clustering is done
			if(dif1<=tol&&dif2<=tol&&dif3<=tol&&dif4<=tol){
				break;
			}
		  	
		}
		
		
	}
	
	
	
	
	public Point [] new_centroid(ArrayList<Point> data,Point [] centroids,Point [] cluster1,Point [] cluster2,Point [] cluster3,Point [] cluster4, int [] index_cluster) {
		
		
		//compute a new centroid position based on the average positions of the points assigned to the same cluster
		Point[] new_centroid = new Point[4];
		for(int i = 0; i < 4; i++) {
			Point p = new Point();
			new_centroid[i] = p;
			new_centroid[i].fillZeros(data);}
		
		int index1=index_cluster[0];
		int index2=index_cluster[1];
		int index3=index_cluster[2] ;
		int index4=index_cluster[3];
		
		//Compute the new centroid of cluster 1 by looping through the 9 substations (voltage and angle) and through all points assigned to cluster 1
		for(int j=0; j<index1; j++)
		{
			for(int i=0; i< data.get(0).data_array.size(); i++)
			{
				new_centroid[0].data_array.get(i).voltage+= (cluster1[j].data_array.get(i).voltage/(index1));
				new_centroid[0].data_array.get(i).angle+= (cluster1[j].data_array.get(i).angle/(index1));
			}
		}
		
		centroids[0]=new_centroid[0];
		
		for(int j=0; j<index2; j++)
		{
			for(int i=0; i< data.get(0).data_array.size(); i++)
			{
				new_centroid[1].data_array.get(i).voltage+= (cluster2[j].data_array.get(i).voltage/(index2));
				new_centroid[1].data_array.get(i).angle+= (cluster2[j].data_array.get(i).angle/(index2));
			}
		}
		centroids[1]=new_centroid[1];
		
		for(int j=0; j<index3; j++)
		{
			for(int i=0; i< data.get(0).data_array.size(); i++)
			{
				new_centroid[2].data_array.get(i).voltage+= (cluster3[j].data_array.get(i).voltage/(index3));
				new_centroid[2].data_array.get(i).angle+= (cluster3[j].data_array.get(i).angle/(index3));
			}
		}
		centroids[2]=new_centroid[2];
		
		for(int j=0; j<index4; j++)
		{
			for(int i=0; i< data.get(0).data_array.size(); i++)
			{
				new_centroid[3].data_array.get(i).voltage+= (cluster4[j].data_array.get(i).voltage/(index4));
				new_centroid[3].data_array.get(i).angle+= (cluster4[j].data_array.get(i).angle/(index4));
			}
		}
		centroids[3]=new_centroid[3];
		
		return centroids;
	}
	
	
	
	public static int NearestClusterIndex(double dist1, double dist2,double dist3,double dist4) {
		
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
	
	
	public void clean_clusters(ArrayList<Point> data,Point [] cluster1,Point [] cluster2,Point [] cluster3,Point [] cluster4) {
		// remove all points assigned to each cluster 
		for(int i = 0; i < 20; i++) {
			Point p = new Point();
			cluster1[i] = p; cluster1[i].fillZeros(data);
			cluster2[i] = p; cluster2[i].fillZeros(data);
			cluster3[i] = p; cluster3[i].fillZeros(data);
			cluster4[i] = p; cluster4[i].fillZeros(data);
		}	
		
	}
	
	
	public void printcentroids(Point [] centroids) {
		for(int i = 0; i<4 ;i++) {
			System.out.println("Centroid "+i+ " contains the following coordinates");
			for(int j = 0;j<9;j++) {
				System.out.print("   "+ centroids[i].data_array.get(j).voltage + "   "+centroids[i].data_array.get(j).angle );
			}
		}
	}
	
}
