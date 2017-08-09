package clustering;

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
				int index = j;
				if(j == i) j++;
				if(j == tempPoints.size()) index = 1;
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
		
		GraphicsThread gt = new GraphicsThread() {
			@Override
			public void render(Graphics2D g2d) {
				for(int i = 0; i < points.size(); i++) {
					g2d.fillOval((int)(points.get(i).x * 10), (int)(points.get(i).y * 10), 5, 5);
				}
				
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
	
	public static ArrayList<Node> group(ArrayList<Node> nodes) {
		ArrayList<Node> tempNodes = new ArrayList<Node>();
		for(int i = 0; i < nodes.size(); i++) {
			tempNodes.add(nodes.get(i));
			
		}
		
		ArrayList<Node> nodesNew = new ArrayList<Node>();
		
		for(int i = 0; i < tempNodes.size(); i++) {
			double distMin = 200;
			Node thisNode = tempNodes.get(i);
			Node closest = thisNode;
			for(int j = 0; j < tempNodes.size(); j++) {
				int index = j;
				if(j == i) j++;
				if(j == tempNodes.size()) index = 1;
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
			nodePoints.addAll(thisNode.points);
			nodePoints.addAll(closest.points);
			
			Point avg = c.new Point(sumX / nodeList.size(), sumY / nodeList.size());
			
			Node node = new Node(nodeList, nodePoints, avg);
			
			nodesNew.add(node);
			
		}
		
		return nodesNew;
		
	}
	
}
