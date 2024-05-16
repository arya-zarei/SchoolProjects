/**
 * The GraphEdge class creates a representation of an edge of the graph. An edge
 * is represented by two GraphNode endpoints, a integer type representing the
 * number of coins to open a door and, a label to identify if the edge is a door
 * or a corridor.
 * 
 * @author Arya Zarei
 *         2210B Assignment 5
 */
public class GraphEdge {

	private GraphNode u; // One endpoint of the edge
	private GraphNode v; // The other endpoint of the edge
	private int type; // Represents the number of coins needed to open the door if this edge is a door
	private String label; // The label of the edge, indicating whether it's a "corridor" or a "door"

	/**
	 * Constructs a GraphEdge between two GraphNode objects with
	 * specified type and label.
	 * 
	 * @param u     The first endpoint of the edge
	 * @param v     The second endpoint of the edge
	 * @param type  The type of the edge, representing the number of
	 *              coins needed if it's a door
	 * @param label The label of the edge, indicating whether it's a
	 *              "corridor" or a "door"
	 */
	public GraphEdge(GraphNode u, GraphNode v, int type, String label) {
		this.u = u; // Assign the first endpoint of the edge
		this.v = v; // Assign the second endpoint of the edge
		this.type = type; // Assign the type, representing coins needed if this edge is a door
		this.label = label; // Assign the label, indicating whether this is a corridor or a door
	}

	public GraphNode firstEndpoint() {
		return this.u; // Returns the first endpoint of the edge
	}

	public GraphNode secondEndpoint() {
		return this.v; // Returns the second endpoint of the edge
	}

	public int getType() {
		return this.type; // Returns the type of the edge
	}

	public void setype(int type) {
		this.type = type; // Sets the type of the edge
	}

	public String getLabel() {
		return this.label; // Returns the label of the edge
	}

	public void setLabel(String label) {
		this.label = label; // Sets the label of the edge
	}
}