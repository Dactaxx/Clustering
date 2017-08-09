package clustering;

import java.util.ArrayList;

import clustering.Clustering.Point;

public class Node {
	public ArrayList<Point> points;
	public ArrayList<Node> branches;
	public Point p;
	public boolean inGroup = false;
	public Node group;
	
	public Node(ArrayList<Node> branches, ArrayList<Point> points, Point p) {
		this.branches = branches;
		this.points = points;
		this.p = p;
		
		
	}

}
