package answers;

import java.util.LinkedList;

public class GraphToBinaryTree {
	static final int MAX_NUM = 100 ;  
	static final int BITS_LEN =(int)(Math.log(MAX_NUM) / Math.log(2)); //=7
	static Vertex[] graph;
	
	public static void toBinaryTree() {
		dfs(graph[0]);
		
		
	}
	
    public static void dfs(Vertex v) {
        // Mark the current node as visited
        v.visited = true;
        System.out.print(v.getValue() + " ");

        // Recursively visit all unvisited neighbors
        for (Vertex neighbor : v.neighbors) {
            if (!neighbor.visited) {
                dfs(neighbor);
            }
        }
    }
	
	
	
	
	
	
	public static void initGraph() {
		graph = new Vertex [MAX_NUM + 1];
		
		//Initialize with all binary numbers
		for(int i = 0; i < MAX_NUM + 1 ; i++ ) 
			graph[i] = new Vertex(toBinary(i));
			
			//Create neighbors
		
		for(int i = 0 ; i < MAX_NUM + 1 ; i++) {

			for(int j = 0 ; j < BITS_LEN + 1 ; j++) {
				
				Vertex cur = graph[i];
				char changeChar = cur.getValue().charAt(j) == '0' ? '1' : '0';
				String neig = replaceCharAtIndex(cur.getValue(), j, changeChar) ;
				int nIndex = asDecimal(neig);
				if(nIndex <= MAX_NUM)
					cur.neighbors.add(graph[nIndex]);
				
			}
		}
	}
	
	public static void printGraph() {
		for(int i = 0 ; i < graph.length ; i++ )
			System.out.println(graph[i].toString());
	}
	
	private static String replaceCharAtIndex(String str, int index, char replaceChar) {
	    char[] charArray = str.toCharArray();
	    charArray[index] = replaceChar;
	    return new String(charArray);
	}

	
	private static String toBinary (int dec) {
		String bin = Integer.toBinaryString(dec);
			while(bin.length() < BITS_LEN + 1)
				bin = '0' + bin; //add zeros till fills to seven bits
		return bin;
	}
	public static int asDecimal(String val) {return Integer.parseInt(val, 2);}
	
}
class Vertex {
	private String value;
	boolean visited = false;
	LinkedList<Vertex>neighbors = new LinkedList<Vertex>();
	public Vertex (String value ) {this.value = value;}
	public String getValue() {return value;}
	public String toString() {
		String ret ="Vertex "+ value;
		
		if(visited)ret+= "\t[✓] [";else ret += "\t[";
		
		for(int i = 0 ; i < neighbors.size() ; i ++ ) {
			ret += neighbors.get(i).getValue();
			
			if(i != neighbors.size() - 1)
				ret +=  ",";
		}
		ret += "]";
		return ret;
	}
}



