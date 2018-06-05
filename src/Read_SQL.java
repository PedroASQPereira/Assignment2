import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

public class Read_SQL {

	public Read_SQL(ArrayList<Point> data,String info) throws Exception {
	
	int time_retainer = 0;
	Point point  = null;
	Object object = null;
	
	Class.forName("com.mysql.jdbc.Driver");
				
	Connection m_Connection = DriverManager.getConnection(
				    "jdbc:mysql://localhost:3306/subtables?autoReconnect=true&useSSL=false", "root", "root");
			
			Statement m_Statement = m_Connection.createStatement();
			
			//String query = "SELECT * FROM analog_values";
		    String query = "SELECT * FROM "+info;

		    ResultSet m_ResultSet;
			
		    //the variable m_ResultSet contains the following info, [rdf_id, time, voltage or angle, sub_rdf_id]
			m_ResultSet = m_Statement.executeQuery(query);
			
		    while (m_ResultSet.next()) {
//		      System.out.println(m_ResultSet.getString(1) + ", " + m_ResultSet.getString(2) 
//		      + ", " + m_ResultSet.getString(3)+ ", " + m_ResultSet.getString(4)
//		      + ", " + m_ResultSet.getString(5));
		      
		      //in case, the point is still the same, don't add new to list just keep working with same point
		      if(time_retainer == Integer.parseInt(m_ResultSet.getString(3))) {
		    	//update the time_retainer and add point to the list creating a new one
		    	  if(Objects.equals(object.sub_rdf, m_ResultSet.getString(5)) && object != null) {
		    		  if(Objects.equals(m_ResultSet.getString(2).substring(m_ResultSet.getString(2).length()-4),"VOLT")) {
		    			  object.addvoltage(Double.parseDouble(m_ResultSet.getString(4)));
		    		  }else {
		    			  object.addangle(Double.parseDouble(m_ResultSet.getString(4)));
		    		  }
		    		  point.data_array.add(object);
		    	  //create an object that has the corresponding substation and either voltage or angle
		    	  }else {
		    		  object = new Object(m_ResultSet.getString(5),time_retainer);
		    		  if(Objects.equals(m_ResultSet.getString(2).substring(m_ResultSet.getString(2).length()-4),"VOLT")) {
		    			  object.addvoltage(Double.parseDouble(m_ResultSet.getString(4)));
		    		  }else {
		    			  object.addangle(Double.parseDouble(m_ResultSet.getString(4)));
		    		  }
		    	  }
		    	  
		    	  
		      //in case the time changed, create a new point and insert the corresponding data
		      }else {
		    	  //update the time_retainer and add point to the list creating a new one
		    	  time_retainer = Integer.parseInt(m_ResultSet.getString(3));
		    	  if(point != null) {
		    		  data.add(point);}
		    	  point = new Point();
		    	  //in case the voltage or angle have already been input in the object add the remaining variable
		    	  if( object != null && Objects.equals(object.sub_rdf, m_ResultSet.getString(5))) {
		    		  if(Objects.equals(m_ResultSet.getString(2).substring(m_ResultSet.getString(2).length()-4),"VOLT")) {
		    			  object.addvoltage(Double.parseDouble(m_ResultSet.getString(4)));
		    		  }else {
		    			  object.addangle(Double.parseDouble(m_ResultSet.getString(4)));
		    		  }
		    		  point.data_array.add(object);
		    	  //create an object that has the corresponding substation and either voltage or angle
		    	  }else {
		    		  object = new Object(m_ResultSet.getString(5),time_retainer);
		    		  if(Objects.equals(m_ResultSet.getString(2).substring(m_ResultSet.getString(2).length()-4),"VOLT")) {
		    			  object.addvoltage(Double.parseDouble(m_ResultSet.getString(4)));
		    		  }else {
		    			  object.addangle(Double.parseDouble(m_ResultSet.getString(4)));
		    		  }
		    	  }  
		      }
		      
		    }
		    //add last point of the database
		    data.add(point);
		    
		    
		    //Check if database was compiled correctly
		    /*System.out.println("The data is stored in a list of points and each point contains a time and 18 values (9voltages and 9angles)\n");
		    for(Point p : data) {
		    	System.out.println("\n Point has the following info:");
		    	for(Object o : p.data_array) {
		    		System.out.println("Substation " + o.sub_rdf + " has "+o.voltage+" [V] and "+o.angle+" [º] with time "+o.time);
		    	}
		    }*/
	
	
	
	}
	
	
}
