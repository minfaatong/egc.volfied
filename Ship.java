import java.awt.*;
import java.util.*;

public class Ship {
	int WIDTH = 76, HEIGHT=76;
	int x = 50;
	int y = 50;
	
	int keep_direction = 0;
	int direction = NORTH;
	
	static final int NORTH = 1;
	static final int EAST  = 2;
	static final int SOUTH = 3;
	static final int WEST  = 4;
	static final int NEAST = 5;
	static final int NWEST = 6;
	static final int SEAST = 7;
	static final int SWEST = 8;
	
	Random rand = new Random();
	
	boolean growing = true;
	int pase = 5;
	
	Polygon poli = new Polygon();
	
	public Ship(int init_x, int init_y) {
		this.x = init_x;
		this.y = init_y;
		poli.addPoint(0, 0);
		poli.addPoint(0, WIDTH);
		poli.addPoint(WIDTH, HEIGHT);
		poli.addPoint(HEIGHT, 0);
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.RED);
		g.fillPolygon(this.getPaintable());
	}
	
	
	public void draw(Graphics g_main){
		animate();
		this.paint(g_main);
	}
	
	public String toString() {
		String ret = "SHIP position=" + x + ", " + y + "\n";
		for (int i = 0; i < poli.npoints; i++)
			ret += "P[" + i + "]=[" + poli.xpoints[i] + ", " + poli.ypoints[i] + "] ";
		ret += "\n";
		return ret;
	}
	
	public Polygon getPaintable() {
		Polygon ret = new Polygon(poli.xpoints, poli.ypoints, poli.npoints);
		ret.translate(x + Volfied.GRID_X, y + Volfied.GRID_Y);
		return ret;
	}
	
	public Polygon getTranslatedPolygon() {
		Polygon cp_poly = new Polygon(poli.xpoints, poli.ypoints, poli.npoints);
		cp_poly.translate(x, y);
		return cp_poly;
	}
	
	public int getPosition(BrokenLine[] polys) {
		
		if (polys[0].toPolygon().contains(this.x, this.y))
			return 0;
		if (polys[1].toPolygon().contains(this.x, this.y))
			return 1;
		return 1;
	}
	
	
	public void generateDirection() {
		direction = rand.nextInt(SWEST - NORTH + 1) + NORTH;
	}
	
	
	public boolean canGrow() {
		if (WIDTH == 120 || HEIGHT == 120) {
			growing = false;
		}
		
		if (WIDTH == 75 || HEIGHT == 75) {
			growing = true;
		}
		return false;
	}
	
	public boolean isOuter(Point nex_ship_pos) {
		int n = this.poli.npoints;
		for (int i = 0; i < n; i++)
			if (Volfied.terain.isOuter(new Point(poli.xpoints[i] + nex_ship_pos.x, poli.ypoints[i] + nex_ship_pos.y)))
				return true;
		return false;
	}
	
	public void animate() {
		if (keep_direction == 400) {
			generateDirection();
			keep_direction = 0;
		}
		Point curr_ship_pos = new Point(this.x, this.y);
		Point nex_shipp_pos = curr_ship_pos.getShipNewPosition(direction, pase);
		
		if(isOuter(nex_shipp_pos))
			generateDirection();
		else {
			this.x = nex_shipp_pos.x;
			this.y = nex_shipp_pos.y;
		}
			
		keep_direction++;
		
	}
}
