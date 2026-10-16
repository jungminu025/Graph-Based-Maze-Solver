
public class GraphNode {

//	I need 2 variables, name and mark
	int name;
	boolean mark;
	
	public GraphNode(int name) {
//		It's probably worth it to do something with that name argument
		this.name = name;
	}
	
//	setters and getters, should be fun
	//setter
	public void mark(boolean mark) {
		this.mark = mark;
	}
	
	//getter
	public boolean isMarked() {
		return mark;
	}
	public int getName() {
		return name;
	}
	
}
