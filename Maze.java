import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Maze {

	
//	instance variables you may need
//	a variable storing the graph, a variable storing the id of the starting node, a variable storing the id of the end node
//	a variable storing the read number of coins, maybe even a variable storing the path so far so that you don't perform accidental 
//	(and unnecessary cycles).
//	if you maintain nodes on a path in a list, be careful to make a list of GraphNodes, 
//	otherwise removal from the list is going to behave in a weird way. 
//	REMEMBER your nodes have a field mark.. maybe that field could be useful to avoid cycles...
	//instance variable
	int scale, width, length, coin;	
	ArrayList<GraphEdge> edges;
	ArrayList<GraphNode> nodes;
	GraphNode entrance;
	GraphNode exit;
	Graph graph;

	public Maze(String inputFile) throws MazeException {
		try {
			//open buffered reader
			BufferedReader br = new BufferedReader(new FileReader(inputFile));
			//read the first four line
			scale = Integer.parseInt(br.readLine());
			width = Integer.parseInt(br.readLine());
			length = Integer.parseInt(br.readLine());
			coin = Integer.parseInt(br.readLine());
			//initilze the graph
			this.graph = new Graph(width * length);
			//read the given input
			readInput(br);
			//close bufferedReader
			br.close();
		} catch (Exception e) {
			throw new MazeException("invalid file.");
		}
	}
	//getting the graph representation of maze
	public Graph getGraph() throws MazeException {
	//		return your graph
		if(this.graph == null){
			throw new MazeException("no Graph found");
		}
		return this.graph;
	}

	//solving a maze
	public Iterator<GraphNode> solve() {
		try {
			ArrayList<GraphNode> path = new ArrayList<>();
			//initialize the node as false
			for (int i = 0; i < width * length; i++) {
				graph.getNode(i).mark(false);
			}
			//if there is path return
			if (DFS(coin, entrance, path)) {
				return path.iterator();
			}
			//else
			return null;
		} catch (GraphException e) {
			return null;
		}
    }
	
	//DFS
    private boolean DFS(int remainingCoins, GraphNode current, ArrayList<GraphNode> path) throws GraphException {
		//mark and add the node in path
        current.mark(true);
        path.add(current);

		//if exit is found
        if (current.getName() == exit.getName()) {
            return true;
        }

        Iterator<GraphEdge> it = graph.incidentEdges(current);

		//going through all the incident edges
        if (it != null) {
            while (it.hasNext()) {
                GraphEdge edge = it.next();

                GraphNode next;
				//finding a node to explore
                if (edge.firstEndpoint().getName() == current.getName()) {
                    next = edge.secondEndpoint();
                } else {
                    next = edge.firstEndpoint();
                }

				//if next node is not marked
                if (!next.isMarked()) {
                    int cost = 0;
					//find the cost if the edge is door
                    if (edge.getLabel().equals("door")) {
                        cost = edge.getType();
                    }
					//get into the room if we have enough coin
                    if (cost <= remainingCoins) {
						//recursive DFS with less coin
                        if (DFS(remainingCoins - cost, next, path)) {
                            return true;
                        }
                    }
                }
            }
        }

		//go back
        path.remove(path.size() - 1);
        current.mark(false);
		//exit not found
        return false;
    }

	private void readInput(BufferedReader inputReader) throws IOException, GraphException {
//		Read the values S, A, L, and k
//		pay attention when iterating over the input.. All testing input will be correctly formatted
//		remember to identify the starting and ending rooms
//		The input will have size A + A-1 and L + L-1 because every pair of nodes has its relationship inbetween them in the textual representation!
//		To maintain this method cleaner, you may use the private helper method insertEdge

		for (int i = 0; i < 2 * length - 1; i++) {
			String line = inputReader.readLine();
			//for every even row with room
			//ex. room - edge - room - edge - ...
			if (i % 2 == 0) {
				int roomRow = i / 2;//row actual row number
				for (int j = 0; j < 2 * width - 1; j++) {
					//for each character in row
					char c = line.charAt(j);

					//for each node in the row
					//mainly focues to find the entrance and exit
					if (j % 2 == 0) {
						int roomCol = j / 2; // current colum
						int node = roomRow * width + roomCol;//node name
					
						//if entrance in found
						if (c == 's') {
							this.entrance = graph.getNode(node);
						}
						//if exit is found
						else if (c == 'x') {
							this.exit = graph.getNode(node);
						}
					}
					//for all the edges in the row
					else {
						int left_Node = roomRow * width + (j / 2);//left node name
						int right_Node = left_Node + 1;//right node name

						//if  the edge is corridor
						if (c == 'c') {
							insertEdge(left_Node, right_Node, 0, "corridor");
						}
						//if the edge is door
						else if (Character.isDigit(c)){
							insertEdge(left_Node, right_Node, Character.getNumericValue(c), "door");
						} 
					}
				}
			}
			//this is for edeg row
			//ex. edge - wall - edge - wall - ... 
			else {
				int upperRow = i / 2;

				for (int j = 0; j < 2 * width - 1; j++) {
					//going through string character by character
					char c = line.charAt(j);

					if (j % 2 == 0) {
						int top_Node = upperRow * width + (j / 2);//top node name
						int bottom_Node = top_Node + width;//bottom node name

						//if the edge is corridor
						if (c == 'c'){
							insertEdge(top_Node, bottom_Node, 0, "corridor");
						}
						//if the edge is door
						else if (Character.isDigit(c)){
							insertEdge(top_Node, bottom_Node, Character.getNumericValue(c), "door");							
						} 
					}
				}
			}
    	}
	}

	//inserting a edge into graph
	private void insertEdge(int node1, int node2, int linkType, String label) throws GraphException {
		graph.insertEdge(graph.getNode(node1), graph.getNode(node2), linkType, label);
	}

}
