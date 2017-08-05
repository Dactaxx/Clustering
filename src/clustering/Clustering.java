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
		
//		Tree<ArrayList<Point>> tree = new Tree<ArrayList<Point>>();
		
		for(int i = 0; i < tempPoints.size(); i++) {
			double distMin = 200;
			int closest = 0;
			Point thisPoint = tempPoints.get(i);
			for(int j = 0; j < tempPoints.size(); j++) {
				int index = j;
				if(j == i) j++;
				if(j == tempPoints.size()) index = 1;
				Point thatPoint = tempPoints.get(index);
				double dist = Math.sqrt(Math.pow(thisPoint.x - thatPoint.x, 2) + Math.pow(thisPoint.y - thatPoint.y, 2));
				if(dist < distMin) {
					distMin = dist;
					closest = thatPoint.id;
				}
				
			}
			
			
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
	
	private class Point {
		public Point(double x, double y, int id) {
			this.x = x;
			this.y = y;
			this.id = id;
		}
		double x = 0;
		double y = 0;
		int id = 0;
	}
	
	
	
}
