package graphColoring;

import java.util.*;

public class Graph {
	//use adjacency list representation
	
	int numOfVertices;
	GraphNode[] vertices;
	
	public Graph(int numOfVertices){
		this.numOfVertices = numOfVertices;
		this.vertices = new GraphNode[numOfVertices];
		for(int i=0;i<numOfVertices;i++){
			this.vertices[i] = new GraphNode(i+1);
		}
		
	}
	
	public void addEdge(int v1, int v2){
		this.vertices[v1-1].addNeighbor(v2);
		this.vertices[v2-1].addNeighbor(v1);
	}
	
	public void BFS(int startNode){
		
		LinkedList<GraphNode> queue = new LinkedList<GraphNode>();
		queue.offer(this.vertices[startNode-1]);
		
		while(!queue.isEmpty()){
			
			GraphNode node = queue.poll();
			if(!node.visited){
				node.visited = true;
				System.out.println(node.nodeIndex);
				for(int i=0;i<node.neighbors.size();i++){
					int neighborNode = node.neighbors.get(i);
					if(!this.vertices[neighborNode-1].visited){
						queue.offer(this.vertices[neighborNode-1]);
					}
					
				}
			}
			
		}
		
	}
	
	public void printOddCycle(GraphNode node, GraphNode neighbor){
		
		//int nodeIndex = node.nodeIndex;
		//int neighborIndex = neighbor.nodeIndex;
		
		boolean meetNeighbor = false;
		
		GraphNode v = node;
		while(v != null){
			System.out.println(v.nodeIndex);
			if(v.nodeIndex == neighbor.nodeIndex){
				meetNeighbor = true;
				break;
			}
			v = v.parent;
		}
		
		if(meetNeighbor){
			return;
		}
		
		v = neighbor;
		while(v.parent != null){
			System.out.println(v.nodeIndex);
			v = v.parent;
		}
		
		return;
	}
	
	public boolean colorTree(int startNode){
		//return if the graph is colorable
		
		LinkedList<GraphNode> queue = new LinkedList<GraphNode>();
		GraphNode root = this.vertices[startNode-1];
		//color it as red
		root.color = 0;
		queue.offer(root);
		
		while(!queue.isEmpty()){
			
			GraphNode node = queue.poll();
			int color = node.color;
			if(!node.visited){
				node.visited = true;
				
				int nextColor = (color+1)%2;
				for(int i=0;i<node.neighbors.size();i++){
					int neighborNode = node.neighbors.get(i);
					
					if(this.vertices[neighborNode-1].color < 0){
						//this node has not been colored before
						this.vertices[neighborNode-1].color = nextColor;
					}
					else if(this.vertices[neighborNode-1].color != nextColor){
						//there is an odd cycle
						this.printOddCycle(node, this.vertices[neighborNode-1]);
						
						return false;
					}
					
					if(!this.vertices[neighborNode-1].visited){
						this.vertices[neighborNode-1].parent = node;
						queue.offer(this.vertices[neighborNode-1]);
					}
					
				}
				
			}
		}
		
		return true;
	}
	
	public boolean colorAll(){
		
		for(int v=1;v<=this.numOfVertices;v++){
			if(!this.vertices[v-1].visited){
				boolean colorable = this.colorTree(v);
				if(!colorable){
					return false;
				}
			}
		}
		
		return true;
	}
	
	public void printColor(){
		
		for(int i=0;i<this.numOfVertices;i++){
			System.out.printf("%d: ", this.vertices[i].nodeIndex);
			if(this.vertices[i].color == 0){
				System.out.println("Red");
			}
			else{
				System.out.println("Blue");
			}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

class GraphNode{
	
	//node index, from 1 to |V|
	int nodeIndex;
	//neighbors of the node
	ArrayList<Integer> neighbors;
	//if this node is visited
	boolean visited;
	//the parent node in the BFS tree
	GraphNode parent;
	//the color of the node, -1: no color, 0: red, 1: blue
	int color;
	
	public GraphNode(int nodeIndex){
		this.nodeIndex = nodeIndex;
		this.neighbors = new ArrayList<Integer>();
		this.visited = false;
		this.parent = null;
		this.color = -1;
	}
	
	public void addNeighbor(int neighborIndex){
		this.neighbors.add(neighborIndex);
	}
	
}