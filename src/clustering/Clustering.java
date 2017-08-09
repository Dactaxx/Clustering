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
		
		for(int i = 0; i < tempPoints.size(); i++) {
			double distMin = 200;
			Point thisPoint = tempPoints.get(i);
			Point closest = thisPoint;
			for(int j = 0; j < tempPoints.size(); j++) {
				if(j == i) j++;
				int index = j;
				if(j == tempPoints.size()) index = 0;
				Point thatPoint = tempPoints.get(index);
				double dist = Math.sqrt(Math.pow(thisPoint.x - thatPoint.x, 2) + Math.pow(thisPoint.y - thatPoint.y, 2));
				if(dist < distMin) {
					distMin = dist;
					closest = thatPoint;
				}
				
			}
			
			tempPoints.remove(thisPoint);
			tempPoints.remove(closest);
			
			ArrayList<Point> pointsTree = new ArrayList<Point>();
			pointsTree.add(thisPoint);
			pointsTree.add(closest);
			
			double sumX = 0;
			double sumY = 0;
			for(Point p : pointsTree) {
				sumX += p.x;
				sumY += p.y;
				
			}
			
			Point avg = c.new Point(sumX / pointsTree.size(), sumY / pointsTree.size());
			
			Node node = new Node(pointsTree, pointsTree, avg);
			
			nodes.add(node);
			
		}
		
		ArrayList<Node> tree = group(nodes, 2);
		ArrayList<Color> colors = new ArrayList<Color>();
		for(int i = 0; i < tree.size(); i++) {
			colors.add(new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255)));
		}
		
		System.out.println(tree.size());
		GraphicsThread gt = new GraphicsThread() {
			@Override
			public void render(Graphics2D g2d) {
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
		
		for(int i = 0; i < tempNodes.size(); i++) {
			double distMin = 200000;
			Node thisNode = tempNodes.get(i);
			Node closest = new Node(new ArrayList<Node>(), new ArrayList<Point>(), c.new Point(0, 0));
			for(int j = 0; j < tempNodes.size(); j++) {
				if(tempNodes.get(j) == tempNodes.get(i)) j++; 
				int index = j;
				if(j == tempNodes.size()) index = 0;
				Node thatNode = tempNodes.get(index);
				double dist = Math.sqrt(Math.pow(thisNode.p.x - thatNode.p.x, 2) + Math.pow(thisNode.p.y - thatNode.p.y, 2));
				if(dist < distMin) {
					distMin = dist;
					closest = thatNode;
				}
				
			}
			tempNodes.remove(thisNode);
			tempNodes.remove(closest);
			
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
			for(Point p : thisNode.points) nodePoints.add(p);
			for(Point p : closest.points) nodePoints.add(p);
			
			Point avg = c.new Point(sumX / nodeList.size(), sumY / nodeList.size());
			
			Node node = new Node(nodeList, nodePoints, avg);
			
			nodesNew.add(node);
			
		}
		
		if(iteration > 0) return group(nodesNew, iteration - 1);
		else return nodesNew;
		
	}
	
}
