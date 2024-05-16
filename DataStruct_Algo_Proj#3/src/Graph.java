
/** 
 * The Graph class uses ArrayLists to implement an adjacency list and a list of
 * graph node objects to create an undirected graph to insert edges, get nodes, return adjacency
 * lists for specific nodes, return edges and check adjacency. Since the graph
 * implementation is undirected, nodes can from u (first) to v (second) and
 * v to u. Exceptions are thrown for invalid nodes and edges to prevent any classes from
 * crashing while using the Graph class.
 * 
 * @author Arya Zarei
 *         2210B Assignment 5
 */

import java.util.*; //import all classes from java.util

@SuppressWarnings("unchecked") // Suppress unchecked warning for this specific instance
public class Graph implements GraphADT {

	private ArrayList<GraphNode> node; // private arrayList of graph nodes objects
	private ArrayList<ArrayList<GraphEdge>> edge; // private adjacency list representation of graph

	/**
	 * @param n The constructor creates an empty graph with n nodes and no edges
	 */
	public Graph(int n) {
		this.node = new ArrayList<GraphNode>(n); // node list with n capacity
		this.edge = new ArrayList<ArrayList<GraphEdge>>(n); // adjacency list representation for n edges

		for (int i = 0; i < n; i++) {
			this.node.add(new GraphNode(i)); // Add new GraphNode to ArrayList
			this.edge.add(new ArrayList<GraphEdge>()); // Add new ArrayList for edges of this node
		}
	}

	/**
	 * Inserts an edge to the graph connecting the two nodes u and v
	 * 
	 * @param u        the first node to add to edge
	 * @param v        the second node to add to edge
	 * @param edgeType the type for the edge
	 * @param label    the label fo the edge
	 */
	public void insertEdge(GraphNode u, GraphNode v, int edgeType, String label) throws GraphException {

		// checks if the both edges of nodes are in bounds and are not already an edge
		if (u.getName() < 0 || u.getName() >= this.node.size() || v.getName() < 0 || v.getName() >= this.node.size()) {
			// throw exception if invalid edge
			throw new GraphException("Node does not exist or there is already an edge connecting the given nodes.");
		}

		// create a GraphEdge object for both edges
		GraphEdge firstEdge = new GraphEdge(u, v, edgeType, label);
		GraphEdge secondEdge = new GraphEdge(v, u, edgeType, label);

		// add both edges to the adjacency list
		this.edge.get(u.getName()).add(firstEdge);
		this.edge.get(v.getName()).add(secondEdge);
	}

	/**
	 * @param u Returns the node with the specific name n
	 */
	public GraphNode getNode(int u) throws GraphException {
		if (u < 0 || u >= this.node.size()) {
			// throws exception if no node with this name exists
			throw new GraphException("Node with this name does not exist.");
		}
		return this.node.get(u); // returns the name of the GraphNode
	}

	/**
	 * @param u Returns an Iterator over the edges that contain GraphNode u
	 */
	public Iterator incidentEdges(GraphNode u) throws GraphException {

		if (u.getName() < 0 || u.getName() >= this.node.size()) {
			// throws exception if node is not valid
			throw new GraphException("Nodes are invalid.");
		}

		// gets the adjacency list of all edges containing node u
		ArrayList<GraphEdge> edges = this.edge.get(u.getName());

		if (edges.isEmpty()) {
			return null; // returns null if u has no edges on it
		}

		return edges.iterator(); // returns the iterator storing all the edges of GraphNode u
	}

	/**
	 * Returns edge of nodes u and v or throws an exception if there is no edge
	 * 
	 * @param u first node of edge
	 * @param v second node of edge
	 */
	public GraphEdge getEdge(GraphNode u, GraphNode v) throws GraphException {
		if (u.getName() < 0 || u.getName() >= this.node.size() || v.getName() < 0 || v.getName() >= this.node.size()) {
			// throws exception if a node is invalid
			throw new GraphException("Nodes are invalid.");
		}
		// gets the adjacency list for the first node
		ArrayList<GraphEdge> edges = this.edge.get(u.getName());

		// iterates through adjancey list for the first node to find the second node
		for (GraphEdge e : edges) {
			if (e.secondEndpoint() == v) {
				return e; // return edge with first and second node
			}
		}

		// If no edge is found after the loop, throw an exception
		throw new GraphException("No edge exists between the given nodes.");
	}

	/**
	 * Returns true if nodes u and v are adjacent and false if not
	 * 
	 * @param u first node to check for edge adjancency
	 * @param v second node to check for edge adjancency
	 */
	public boolean areAdjacent(GraphNode u, GraphNode v) throws GraphException {
		if (u.getName() < 0 || u.getName() >= this.node.size() || v.getName() < 0 || v.getName() >= this.node.size()) {
			// throws exception if atleast one node is not a valid node of the graph
			throw new GraphException("Nodes are invalid.");
		}

		// gets the adjacency list of the first node
		ArrayList<GraphEdge> edges = this.edge.get(u.getName());

		// iterates through the adjacency list to match the edge with the second node
		for (GraphEdge e : edges) {
			if (e.secondEndpoint() == v) {
				return true; // returns true if edge from first to second node is found
			}
		}

		// gets the adjacency list of the second node
		edges = this.edge.get(v.getName());

		// iterates through the adjacency list to match the edge with the first node
		for (GraphEdge e : edges) {
			if (e.secondEndpoint() == u) {
				return true; // returns true if edge from second to first node is found
			}
		}

		return false; // return false if no adjacent edge is found
	}
}