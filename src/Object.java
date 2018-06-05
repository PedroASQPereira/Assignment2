
public class Object {

	double voltage;
	double angle;
	int time;
	String sub_rdf;
	
	public Object( String sub_rdf, int time) {
		this.sub_rdf =sub_rdf;
		this.time =time;
	}
	
	public void addvoltage(double voltage) {
		this.voltage=voltage;
	}
	
	public void addangle(double angle) {
		this.angle=angle;
	}
}
