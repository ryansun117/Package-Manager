/**
 * Graph.java created by Xuxiang Sun on ThinkPad in p4_PackageManager
 *
 * Author: Xuxiang Sun (xsun272@wisc.edu) Date: March 29, 2020
 *
 * Course: CS400 Semester: Spring 2020 Lecture: 001
 *
 * IDE: Eclipse IDE for Java Developers Version: 2019-12 (4.14.0) Build id: 20191212-1212
 *
 * Device: ThinkPad X1 Carbon OS: Windows 10 Pro Version: 1809 OS Build: 17763.973
 *
 * List Collaborators: Name, email@wisc.edu, lecture number
 * 
 * Other Credits: describe other source (web sites or people)
 *
 * Known Bugs: describe known unresolved bugs here
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Filename: Graph.java Project: p4 Authors:
 * 
 * Directed and unweighted graph implementation
 */

/**
 * 
 * Graph - represents a directed and unweighted graph implementation
 * 
 * @author Xuxiang Sun (2020)
 *
 */
public class Graph implements GraphADT {
  // an adjacency list which maps vertex to its dependency list
  private HashMap<String, List<String>> vertexes;
  private int vertexCt; // tracks number of vertexes
  private int edgeCt; // tracks number of edges

  /*
   * Default no-argument constructor for Graph class
   */
  public Graph() {
    this.vertexes = new HashMap<String, List<String>>();
    this.vertexCt = 0;
  }

  /**
   * Add new vertex to the graph.
   *
   * If vertex is null or already exists, method ends without adding a vertex or throwing an
   * exception.
   * 
   * Valid argument conditions: 1. vertex is non-null 2. vertex is not already in the graph
   * 
   * @param vertex the vertex to be added
   */
  @Override
  public void addVertex(String vertex) {
    // checks if vertex is null or already exists
    if (vertex == null || this.vertexes.containsKey(vertex)) {
      return;
    }
    // adds a new vertex key mapping with empty dependency list
    this.vertexes.put(vertex, new LinkedList<String>());
    this.vertexCt++; // increment size
  }

  /**
   * Remove a vertex and all associated edges from the graph.
   * 
   * If vertex is null or does not exist, method ends without removing a vertex, edges, or throwing
   * an exception.
   * 
   * Valid argument conditions: 1. vertex is non-null 2. vertex is not already in the graph
   * 
   * @param vertex the vertex to be removed
   */
  @Override
  public void removeVertex(String vertex) {
    // checks if vertex is null or already exists
    if (vertex == null || !this.vertexes.containsKey(vertex)) {
      return;
    }
    this.vertexes.remove(vertex, this.vertexes.get(vertex)); // removes vertex and edges
    vertexCt--; // decrements vertexCt
  }


  /**
   * Add the edge from vertex1 to vertex2 to this graph. (edge is directed and unweighted)
   * 
   * If either vertex does not exist, VERTEX IS ADDED and then edge is created. No exception is
   * thrown.
   *
   * If the edge exists in the graph, no edge is added and no exception is thrown.
   * 
   * Valid argument conditions: 1. neither vertex is null 2. both vertices are in the graph 3. the
   * edge is not in the graph
   * 
   * @param vertex1 the first vertex (src)
   * @param vertex2 the second vertex (dst)
   */
  @Override
  public void addEdge(String vertex1, String vertex2) {
    // checks if either of vertex is null
    if (vertex1 == null || vertex2 == null) {
      return;
    }
    this.addVertex(vertex1); // checks if vertex1 already exists, adds it if not
    this.addVertex(vertex2); // checks if vertex2 already exists, adds it if not
    this.vertexes.get(vertex1).add(vertex2); // adds dst vertex to dependency list of src vertex
                                             // (vertext1 requires vertex2)
    edgeCt++; // increment edgeCt
  }

  /**
   * Remove the edge from vertex1 to vertex2 from this graph. (edge is directed and unweighted) If
   * either vertex does not exist, or if an edge from vertex1 to vertex2 does not exist, no edge is
   * removed and no exception is thrown.
   * 
   * Valid argument conditions: 1. neither vertex is null 2. both vertices are in the graph 3. the
   * edge from vertex1 to vertex2 is in the graph
   * 
   * @param vertex1 the first vertex (src)
   * @param vertex2 the second vertex (dst)
   */
  @Override
  public void removeEdge(String vertex1, String vertex2) {
    // checks if either of vertex is null
    if (vertex1 == null || vertex2 == null) {
      return;
    }
    // checks if both vertices are in the graph
    if (!(this.vertexes.containsKey(vertex1) && this.vertexes.containsKey(vertex2))) {
      return;
    }
    // checks if an edge from vertex1 to vertex2 does not exist
    if (!this.vertexes.get(vertex1).contains(vertex2)) {
      return;
    }
    this.vertexes.get(vertex1).remove(vertex2); // removes vertex1 (src) from dependency list
    edgeCt--; // decrements edgeCt
  }

  /**
   * Returns a Set that contains all the vertices
   * 
   * @return a Set<String> which contains all the vertices in the graph
   */
  @Override
  public Set<String> getAllVertices() {
    Set<String> set = new HashSet<String>(); // creates a new Set
    // loops through adjacency list, adds every vertex to the set
    for (String vertex : this.vertexes.keySet()) {
      set.add(vertex);
    }
    return set; // returns the set
  }

  /**
   * Get all the neighbor (adjacent-dependencies) of a vertex
   * 
   * For the example graph, A->[B, C], D->[A, B] getAdjacentVerticesOf(A) should return [B, C].
   * 
   * In terms of packages, this list contains the immediate dependencies of A and depending on your
   * graph structure, this could be either the predecessors or successors of A.
   * 
   * @param vertex the specified vertex
   * @return an List<String> of all the adjacent vertices for specified vertex
   */
  @Override
  public List<String> getAdjacentVerticesOf(String vertex) {
    List<String> list = new LinkedList<String>(); // a temp List to store vertexes
    // loops through every vertex in adjacency list
    for (String tempvertex : this.vertexes.keySet()) {
      // if current vertex == specified vertex
      if (tempvertex.compareTo(vertex) == 0) {
        // adds each dependencies to the list
        for (String dependencies : this.vertexes.get(tempvertex)) {
          list.add(dependencies);
        }
      }
    }
    return list;
  }

  /**
   * Returns the number of edges in this graph.
   * 
   * @return number of edges in the graph.
   */
  @Override
  public int size() {
    return this.edgeCt;
  }

  /**
   * Returns the number of vertices in this graph.
   * 
   * @return number of vertices in graph.
   */
  @Override
  public int order() {
    return this.vertexCt;
  }
}
