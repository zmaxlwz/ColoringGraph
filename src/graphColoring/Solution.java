package graphColoring;

import java.util.*;

public class Solution {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner scan = new Scanner(System.in);
		int numOfVertices = scan.nextInt();
		
		//create graph
		Graph graph = new Graph(numOfVertices);
		
		int v1, v2;
		
		//read edges and add to the graph
		while(scan.hasNextInt()){
			v1 = scan.nextInt();
			v2 = scan.nextInt();
			graph.addEdge(v1, v2);			
		}
		
		scan.close();
		
		
		//graph.BFS(1);
		
		boolean colorable = graph.colorAll();
		
		if(colorable){
			System.out.println("This graph is colorable. The coloring is:");
			graph.printColor();
		}
		else{
			System.out.println("This graph is not colorable. The odd cycle is as above.");
		}
		
		
	}

}
