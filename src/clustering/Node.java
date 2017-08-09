package clustering;

import java.util.ArrayList;

import clustering.Clustering.Point;

public class Node {
	public ArrayList<Point> points;
	public ArrayList<?> branches;
	public Point p;
	
	public Node(ArrayList<?> branches, ArrayList<Point> points, Point p) {
		this.branches = branches;
		this.points = points;
		this.p = p;
		
		
	}

}
