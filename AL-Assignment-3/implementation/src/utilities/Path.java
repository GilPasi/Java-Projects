/*Authors : Yulia Moshan 319565610
 * 			Gil Pasi 	 206500936
 * Algorithm planning - assignment 3 
 * */
package utilities;

public class Path {
	//Equivalent to an edge on a graph
	
	
	private Coordinate v , u ;
	boolean isVisited = false;
	private double time = 1; //Default value

	
	
	//----Constructors----
	public Path (Coordinate v , Coordinate u ){
		this.v = v ;
		this.u = u;
		this.time = v.time(u);
	}
	
	
	public Path (Path other) {
		this.v = other.v;
		this.u = other.u;
		this.time = other.time;

	}

	//----Accessors----
	public Coordinate getV() {return v;}
	public Coordinate getU() {return u;}
	public double getVX() {return v.getX();}
	public double getVY() {return v.getY();}
	public double getUX() {return u.getX();}
	public double getUY() {return u.getY();}
	
	public double getTime () {return time;}
	
	public boolean equals(Path other) {
		return(
		(this.v == other.v) && (this.u == other.u)
					||
		(this.v == other.u) && (this.u == other.v)
				);
	}
	
	public String toString () {return "(" + v + ", " + u + ")";}


}	
