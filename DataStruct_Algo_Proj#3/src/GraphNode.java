/**
 * This GraphNode class creates the the node representations for the graph.
 * Each node is initalized with an integer name to identify the node and a
 * marked boolean to track is the node has been visited.
 * 
 * @author Arya Zarei
 *         2210B Assignment 5
 */
public class GraphNode {

	private int name; // stores the number name of the node
	private boolean mark; // true or false if node is marked or not

	// Constructor that initializes a GraphNode with a given integer name.
	public GraphNode(int name) {
		this.name = name; // initialize name
		this.mark = false; // set mark to default of not marked
	}

	// Marks the node with the specified boolean value.
	public void mark(boolean mark) {
		this.mark = mark; // Set the node's marked status to the given value
	}

	// Returns whether the node is marked
	public boolean isMarked() {
		return this.mark; // Return the marked status of the node
	}

	// Returns the integer name of the node
	public int getName() {
		return this.name; // Return the node's name
	}
}