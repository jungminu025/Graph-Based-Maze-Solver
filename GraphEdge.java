
public class GraphEdge {

//	I need an origin and a destination
//	I need an int type variable
//	and I need a string label
	GraphNode origin;
	GraphNode destination;
	int type;
	String label;
	
	public GraphEdge(GraphNode u, GraphNode v, int type, String label) {
//		I should probably initialize everything
		this.origin = u;
		this.destination = v;
		this.type = type;
		this.label = label;
	}
	
//	I should probably fill in the bodies of those setters and getters
	//getter
	public GraphNode firstEndpoint() {
		return origin;
	}
	public GraphNode secondEndpoint() {
		return destination;
	}
	public int getType() {
		return type;
	}
	public String getLabel() {
		return label;
	}
	//setter
	public void setype(int type) {
		this.type = type;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
}
