
import java.util.Comparator;

public class DistanceComparator implements Comparator<P_distance> {
	    @Override
	    public int compare(P_distance a, P_distance b) {
	       return a.distance < b.distance ? -1 : a.distance == b.distance ? 0 : 1;
	    }
}