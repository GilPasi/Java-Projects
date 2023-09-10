/*Authors : Yulia Moshan 319565610
 * 			Gil Pasi 	 206500936
 * Algorithm planning - assignment 3 
 * */
package utilities;

public class Coordinate {
	public static final int NULL = -1;
	private double x = NULL  , y = NULL;
	private int index;
	private String city = "" ;
	public static final Coordinate  GEOGRAFIC_CENTER = initCenter();

	
	
	//----Constructors----
	public Coordinate (double x , double y , int index) {
		this.x = x ; this.y = y;this.index = index;
		}
	
	public Coordinate (double x , double y , int index , String city) {
		this.x = x ;
		this.y = y;
		this.index = index;
		this.city = city;
		}
	
	public Coordinate (int index) {this.index = index;}
	
	public Coordinate (Coordinate other) {
		this.x = other.x;
		this.y = other.y;
		this.index = other.index;
		this.city = other.city;

	}
	
	//----Accessors----
	public double getX() {return x;}
	public double getY() {return y;}
	public int getIndex() {return index;}
	public void setX (int x) {if (this.x != NULL)this.x = x;}
	public void setY (int y) {if (this.y != NULL)this.y = y;}
	public void setCity (String city) {if (this.city != null)this.city = city;}

	public double time (Coordinate other) {
		double xFactor = 0 , yFactor = 0;//Zero is neutral
		
		//Ignore coordinate if does not exists
		if(this.x != NULL && other.x != NULL)
			xFactor = (this.x - other.x) / 2 ;
		
		if(this.y != NULL && other.y != NULL)
			yFactor = (this.y - other.y) / 2 ;
		
		double distance = Math.sqrt(yFactor*yFactor + xFactor*xFactor);
		
		
		final double  EXTRA_SPEED = 70 , INTRA_SPEED  = 30 ,//kph
				FILL_TIME = 5,CITIES_TRANSFER_TIME = 6 ;//mins
		
		double time = 0;
		
		
		if(city.equals(other.city))
			time = distance / INTRA_SPEED + FILL_TIME;
		else 
			time = distance / EXTRA_SPEED + FILL_TIME + CITIES_TRANSFER_TIME;
		
		return time;
		
	}
	
	public String getCity () {return city;}
	public boolean equals(Coordinate other){return index == other.index;}
	public boolean sameCity (Coordinate other) {return city.equals(other.city);}
	
	public String toString() {
		String ret = ""+index +"|" + city ;

		return ret;
	}
	
	//----Class Methods----
	public static Coordinate initCenter () {
		
		//Taken from google-maps
		final double SOUTHEST = 29.490584850191187, NORTHEST = 33.315720555693524,
				 	 WESTEST  = 34.26738628055353 , EASTEST = 35.89624321597831;
		final int DEFAULT_INDEX = 0;
				 	 

		return new Coordinate((SOUTHEST + NORTHEST) / 2 , (WESTEST + EASTEST) / 2 ,
								DEFAULT_INDEX,"מרכז גיאוגרפי" );
		
	}

	
}
