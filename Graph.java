import java.util.ArrayList;
import java.util.Iterator;

public class Graph implements GraphADT {
	
//	Create an adjacency list or an adjacency matrix, a list is probably easier
	//instance variable
	ArrayList<GraphNode> nodes;
	ArrayList<GraphEdge> edges;
	
	//constructor
	public Graph(int n) {
		this.nodes = new ArrayList<GraphNode>();
		this.edges = new ArrayList<GraphEdge>();
//		initialize your representation with empty adjacency lists
		for(int i = 0;  i < n; i++){
			nodes.add(new GraphNode(i));
		}
	}
	
	@Override
	public void insertEdge(GraphNode nodeu, GraphNode nodev, int type, String label) throws GraphException {
//		create and insert the edge
//		REMEMBER, an edge is accessible from both endpoints, so make sure you add it as an edge for both end nodes
		//if any of the nodes are not in graph
		if(!nodes.contains(nodeu) || !nodes.contains(nodev)){
			throw new GraphException("Nodes are not contained");
		}
		//create graph edge
		GraphEdge ge = new GraphEdge(nodeu,nodev,type,label);
		//checking if we alreadt have the edge
		//undirected so bothway
		Iterator<GraphEdge> edge_it = edges.iterator();
		while(edge_it.hasNext()){
			GraphEdge copy = edge_it.next();
			if(copy.firstEndpoint().getName() == nodeu.getName() && copy.secondEndpoint().getName() == nodev.getName() ||
			copy.firstEndpoint().getName() == nodev.getName() && copy.secondEndpoint().getName() == nodeu.getName()){
				throw new GraphException("edge already exist");
			}
		}
		//add edge
		edges.add(new GraphEdge(nodeu,nodev,type,label));	
	}

	//getter for node
	@Override
	public GraphNode getNode(int u) throws GraphException {
//		Return the node with the appropriate name
		GraphNode n;
		//iterate through node list
		Iterator<GraphNode> it = nodes.iterator();
		while(it.hasNext()){
			n = it.next();
			if(n.getName() == u){
				return n;
			}
		}
		//if node is not existing in graph
		throw new GraphException("No node was found");
	}

	@Override
	public Iterator<GraphEdge> incidentEdges(GraphNode u) throws GraphException {
//		Select from your adjacency list the appropriate Node and return an iterator over the collection.
//		Usually a call to .iterator() should work, unless you do something really exotic

		//if node is not found
		if(u == null){
			throw new GraphException("Nodes are null");
		}  
	
		//finding all the incident edge and add it into incident iterator
		ArrayList<GraphEdge> incident = new ArrayList<>();
		Iterator<GraphEdge> edge_it = edges.iterator();
		while (edge_it.hasNext()) {
			GraphEdge e = edge_it.next();
			if (e.firstEndpoint().equals(u) || e.secondEndpoint().equals(u)) {
				incident.add(e);
			}
		}

		//if there is no incident edge
		if (incident.isEmpty()) {
			return null;
		}

		//else
		return incident.iterator();
	}

	@Override
	public GraphEdge getEdge(GraphNode u, GraphNode v) throws GraphException {
//		check if those nodes exist, then check if they have edges, then who has the least number of edges.
//		find the appropriate edge and return it, if no such edge exists remember to return null 
//		there are faster ways too ;)

		//Go through the edgelist and find the edge
		GraphEdge ge;
		Iterator<GraphEdge> edge_it = edges.iterator();
		while(edge_it.hasNext()){
			ge = edge_it.next();
			if(ge.firstEndpoint().equals(u) && ge.secondEndpoint().equals(v)||
			ge.firstEndpoint().equals(v) && ge.secondEndpoint().equals(u)){
				return ge;
			}
		}
		//if edge does not exisit in list
		throw new GraphException("No edges found");
	}

	@Override
	public boolean areAdjacent(GraphNode u, GraphNode v) throws GraphException {
//		maybe you could use a previously written method to solve this one quickly...

		//finding if two nodes are adjdacent
		GraphEdge ge;
		Iterator<GraphEdge> edge_it = edges.iterator();
		while(edge_it.hasNext()){
			ge = edge_it.next();
			if(ge.firstEndpoint().equals(u) && ge.secondEndpoint().equals(v)||
			ge.firstEndpoint().equals(v) && ge.secondEndpoint().equals(u)){
				return true;
			}
		}
		//if they are not
		return false;
	}

}
