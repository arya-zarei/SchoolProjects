
/** 
 * 
 * @author Arya Zarei
 *         2210B Assignment 5
 */

import java.io.*; //import all classes from java.io
import java.util.*; //import all classes from java.util

public class Maze {
	private Graph graph; // graph object representing the maze
	private GraphNode startNode, endNode; // nodes representing that start and end points of maze
	private int coins; // tracker for number of coins collected

	/**
	 * This Maze constructor reads the text file of the maze to get the
	 * dimensions and the number of coins, as well as the set up for all the
	 * corridors, paths, and, doors (coins to pass) to set up the maze to complete.
	 * 
	 * @param inputFile file for Maze constructor to read
	 * @throws MazeException if input is not valid or can not be
	 *                       read throw the MazeException
	 */
	public Maze(String inputFile) throws MazeException {
		// try: reads maze configuration from input file
		try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
			reader.readLine(); // skip first line
			int width = Integer.parseInt(reader.readLine().trim()); // read maze width
			int length = Integer.parseInt(reader.readLine().trim()); // read maze length
			this.coins = Integer.parseInt(reader.readLine().trim()); // initial coins to start

			int w = width * 2 - 1; // updates width to account for doors and walls
			int l = length * 2 - 1; // updates leght to account for doors and walls
			char[][] mazeMap = new char[l][w]; // creates map with dimensions of maze

			String lineInput = reader.readLine(); // read every character of each line in the maze text
			int row = 0; // track which row of the maze is being filled

			while (lineInput != null) { // while the end of file has not been reached
				for (int i = 0; i < lineInput.length(); i++) {// iterate through entire line
					mazeMap[row][i] = lineInput.charAt(i); // assign character at col i of each row in the maze
				}

				lineInput = reader.readLine(); // move to the next line
				row++; // increment to the next row
			}

			graph = new Graph(width * length); // map a new graph of the length and width dimensions
			int counter = 0; // keep track of the nodes to help maze layout

			// nest loop of maze dimensons to iterate through maze
			for (int i = 0; i < l; i++) { // lenght of maze
				for (int j = 0; j < w; j++) { // width of maze
					if (i % 2 == 0) {
						if (j % 2 == 0) {
							// check if col and row are even, indicating a room or space
							counter++; // increment counter by 1

							if (mazeMap[i][j] == 's') // if character at index is the start
							{
								// set start node, adjust counter to align with node indexing of graph
								startNode = graph.getNode(counter - 1);
							}

							if (mazeMap[i][j] == 'x') // if character at index is the exit
							{
								// set exit node, adjust counter to align with node indexing of graph
								endNode = graph.getNode(counter - 1);
							}
						}

						else if (mazeMap[i][j] != 'w') // if character is a corridor or door but not a wall
						{
							// insert corridor or door edge, adjust counter to align with node indexing of
							// graph, get the next node to find the connection between nodes
							// type (coins to enter) and label to identify edge
							graph.insertEdge(graph.getNode(counter - 1), graph.getNode(counter), getType(mazeMap[i][j]),
									getLabel(mazeMap[i][j]));
						}
					}

					else // for doors or corridors that are not in an even space
					{
						if (j % 2 == 0 && mazeMap[i][j] != 'w') {
							// insert corridor or door edge, adjust counter to align with node indexing of
							// graph of uneven position, get the next node to find the connection between
							// nodes
							// get type (coins to enter) and label to identify edge
							graph.insertEdge(graph.getNode(counter - width + j / 2), graph.getNode(counter + j / 2),
									getType(mazeMap[i][j]), getLabel(mazeMap[i][j]));
						}
					}
				}
			}
			// catch all exception types for reading the input file
		} catch (FileNotFoundException e) {
			throw new MazeException("File not found: " + inputFile);
		} catch (IOException e) {
			throw new MazeException("Error reading file: " + inputFile);
		} catch (NumberFormatException | GraphException e) {
			throw new MazeException("Error processing file: " + inputFile);
		}
	}

	/**
	 * @param c character to return type of
	 * @return helper method to return the type of edge
	 */
	private int getType(char c) {
		if (c == 'c') {
			return 0;
		} else {
			return Character.getNumericValue(c); // get integer value of edge type
		}
	}

	/**
	 * @param c character to return label of
	 * @return helper method to identity if an edge is a door or coridor
	 */
	private String getLabel(char c) {
		if (c == 'c') {
			return "corridor"; // if character c then corridor
		} else {
			return "door"; // if not a corridor than label must be a door
		}
	}

	/**
	 * @return reference to graph object of maze
	 * @throws MazeException throws an exception if the graph is null
	 */
	public Graph getGraph() throws MazeException {
		if (this.graph == null) { // check if graph is null
			throw new MazeException("Graph is null");
		}
		return this.graph; // return graph of maze
	}

	/**
	 * Attempts to find a path from the current node to the end node of
	 * the maze recursively. Uses a depth-first search traversal,
	 * using the number of coins left as a constraint for passing through doors.
	 * 
	 * @param currentNode the current node being checks
	 * @param visited     the list of unique visited nodes
	 * @param path        the lists of nodes from the entrance to exit
	 * @param coinsLeft   the number of coins remaining to get to exit
	 * @return return true or false if path is found or not
	 * @throws GraphException throws graph exception if error in graph occurs
	 */
	private boolean solveHelper(GraphNode currentNode, HashSet<GraphNode> visited, List<GraphNode> path,
			int coinsLeft) throws GraphException {
		// if the current node is the exit node than a valid path has been found
		if (currentNode.equals(endNode)) {
			path.add(endNode); // add the end node to the list of the path
			return true; // return true to indicate path found
		}

		// mark current node as visited preventing revisits in this path
		visited.add(currentNode);

		@SuppressWarnings("unchecked")
		// iterator through all edges from the current node, traversing paths forward
		Iterator<GraphEdge> edges = graph.incidentEdges(currentNode);
		while (edges != null && edges.hasNext()) { // depth first search from current node
			GraphEdge edge = edges.next();
			GraphNode nextNode = edge.secondEndpoint();

			if (visited.contains(nextNode))
				continue; // skip over nodes that have already been visited

			// Determine the cost to traverse the current edge.
			// If it's a door, get the cost (type) of the door, if not door it's free (0)
			int cost = edge.getLabel().equals("door") ? edge.getType() : 0;

			// continue with search if there are enough coins to pass through the current
			// edge
			if (coinsLeft >= cost) {
				// recursive call to continue the search from the next node with updated coins
				// left
				if (solveHelper(nextNode, new HashSet<>(visited), path, coinsLeft - cost)) {
					// Add the current node at the start of the path list, ensuring
					// the path is constructed in correct sequence as we backtrack.
					path.add(0, currentNode);
					// Indicate a successful path to the end node has been found
					// triggering backtracking through the recursion stack.
					return true;
				}
			}
		}
		// If no valid path is found from the current node,
		// return false to backtrack and explore other possibilities.
		return false;
	}

	/**
	 * 
	 * @return an iterator containing the ndoes of the path from the entrace to exit
	 *         of the maze, if exists
	 * @throws GraphException //throws Graph Exception for any error in the graph
	 *                        object of the maze
	 */
	public Iterator<GraphNode> solve() throws GraphException {

		List<GraphNode> path = new ArrayList<>(); // Array list of the path from entrance to exit
		HashSet<GraphNode> visited = new HashSet<>(); // HashSet collection used to store unique visited nodes

		// helper method to find path from start to exit with the given number of coins
		boolean found = solveHelper(startNode, visited, path, this.coins);

		if (found) {
			return path.iterator(); // if valid path found return the path list iterator
		} else {
			return null; // else no path exists return null
		}
	}
}