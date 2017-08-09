package clustering;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import graphics.DGraphics;
import graphics.GraphicsInterface;
import graphics.GraphicsThread;

public class Clustering {
	public static Clustering c = new Clustering();
	public static DGraphics g;
	public static ArrayList<GraphicsInterface> gis = new ArrayList<GraphicsInterface>();
	public static void main(String[] args) {
		ArrayList<Point> points = new ArrayList<Point>();
		
		for(int i = 0; i < 100; i++) {
			points.add(c.new Point(
					Math.random() * 100,
					Math.random() * 100,
					points.size()
					));
			
		}
		
		for(int i = 0; i < 100; i++) {
			points.add(c.new Point(
					(Math.random() * 20) + 30,
					(Math.random() * 20) + 30,
					points.size()
					));
		}
		
		for(int i = 0; i < 100; i++) {
			points.add(c.new Point(
					(Math.random() * 30) + 70,
					(Math.random() * 30) + 70,
					points.size()
					));
		}
		
		for(int i = 0; i < 100; i++) {
			points.add(c.new Point(
					(Math.random() * 20),
					(Math.random() * 20) + 80,
					points.size()
					));
			
		}
		
		ArrayList<Point> tempPoints = new ArrayList<Point>();
		
		for(int i = 0; i < points.size(); i++) {
			tempPoints.add(points.get(i));
			
		}
		
		ArrayList<Node> nodes = new ArrayList<Node>();
		double sumX0 = 0;
		double sumY0 = 0;
		for(Point q : points) {
			sumX0 += q.x;
			sumY0 += q.y;
			
		}
		
		Point mean = c.new Point(sumX0 / points.size(), sumY0 / points.size());
		
		double sumD = 0;
		
		for(Point q : points) {
			double d = Math.sqrt(Math.pow(q.x - mean.x, 2) + Math.pow(q.y - mean.y, 2));
			sumD += d;
			
		}
		double standardDeviation = Math.sqrt(sumD / points.size());
		System.out.println(standardDeviation);
		for(Point u : tempPoints) {
			double distMin = 200;
			Point closest = c.new Point(0, 0, 0);
			if(!u.inGroup) {
				for(Point t : tempPoints) {
					if(!t.inGroup && t != u) {
						double dist = Math.sqrt(Math.pow(u.x - t.x, 2) + Math.pow(u.y - t.y, 2));
						if(dist < distMin) {
							distMin = dist;
							closest = t;
						}
					}
				}
				if(distMin < standardDeviation) {
					closest.inGroup = true;
					u.inGroup = true;
					ArrayList<Point> pointsTree = new ArrayList<Point>();
					pointsTree.add(u);
					pointsTree.add(closest);
					
					double sumX = 0;
					double sumY = 0;
					for(Point p : pointsTree) {
						sumX += p.x;
						sumY += p.y;
						
					}
					
					Point avg = c.new Point(sumX / pointsTree.size(), sumY / pointsTree.size());
					
					Node node = new Node(new ArrayList<Node>(), pointsTree, avg);
					
					nodes.add(node);
				}
			}
		}
		
		ArrayList<Node> tree = group(nodes, 5);
		ArrayList<Color> colors = new ArrayList<Color>();
		for(int i = 0; i < tree.size(); i++) {
			colors.add(new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255)));
		}
		
		GraphicsThread gt = new GraphicsThread() {
			@Override
			public void render(Graphics2D g2d) {
				g2d.setColor(new Color(0, 0, 0));
				for(Point p : points) {
					g2d.fillOval((int)(p.x * 10), (int)(p.y * 10), 15, 15);
				}
				int iterations = 0;
				int pointsAmt = 0;
				for(Node n : tree) {
					g2d.setColor(colors.get(iterations));
					for(Point p : n.points) {
						g2d.fillOval((int)(p.x * 10), (int)(p.y * 10), 15, 15);
						pointsAmt++;
					}
					iterations++;
				}
				System.out.println(pointsAmt);
			}

			@Override
			public void tick() {
				
				
			}
			
		};
		
		gis.add(gt);
		
		g = new DGraphics(gis);
		gt.setRunState(GraphicsThread.RUNNING);
		
		
		
	}
	
	public class Point {
		double x = 0;
		double y = 0;
		int id = 0;
		public boolean inGroup = false;
		public Node group;
		
		public Point(double x, double y, int id) {
			this.x = x;
			this.y = y;
			this.id = id;
		}
		
		public Point(double x, double y) {
			this.x = x;
			this.y = y;
		}
		
	}
	
	public static ArrayList<Node> group(ArrayList<Node> nodes, int iteration) {
		ArrayList<Node> tempNodes = new ArrayList<Node>();
		for(int i = 0; i < nodes.size(); i++) {
			tempNodes.add(nodes.get(i));
			
		}
		
		ArrayList<Node> nodesNew = new ArrayList<Node>();
		
		for(Node u : tempNodes) {
			double distMin = 200000;
			Node thisNode = u;
			Node closest = new Node(new ArrayList<Node>(), new ArrayList<Point>(), c.new Point(0, 0));
			if(!u.inGroup) {
				for(Node p : tempNodes) {
					if(!p.inGroup && p != u) { 
						Node thatNode = p;
						double dist = Math.sqrt(Math.pow(thisNode.p.x - thatNode.p.x, 2) + Math.pow(thisNode.p.y - thatNode.p.y, 2));
						if(dist < distMin) {
							distMin = dist;
							closest = thatNode;
						}
					}
				}
				u.inGroup = true;
				closest.inGroup = true;
				ArrayList<Node> nodeList = new ArrayList<Node>();
				nodeList.add(thisNode);
				nodeList.add(closest);
				
				double sumX = 0;
				double sumY = 0;
				for(Node n : nodeList) {
					sumX += n.p.x;
					sumY += n.p.y;
					
				}
	
				ArrayList<Point> nodePoints = new ArrayList<Point>();
				for(Point g : thisNode.points) nodePoints.add(g);
				for(Point g : closest.points) nodePoints.add(g);
				
				Point avg = c.new Point(sumX / nodeList.size(), sumY / nodeList.size());
				
				Node node = new Node(nodeList, nodePoints, avg);
				
				nodesNew.add(node);
			}
		}
		
		if(iteration > 0) return group(nodesNew, iteration - 1);
		else return nodesNew;
		
	}
	
}
