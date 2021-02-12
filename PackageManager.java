/**
 * PackageManager.java created by Xuxiang Sun on ThinkPad in p4_PackageManager
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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Filename: PackageManager.java Project: p4 Authors: Xuxiang Sun
 * 
 * PackageManager is used to process json package dependency files and provide function that make
 * that information available to other users.
 * 
 * Each package that depends upon other packages has its own entry in the json file.
 * 
 * Package dependencies are important when building software, as you must install packages in an
 * order such that each package is installed after all of the packages that it depends on have been
 * installed.
 * 
 * For example: package A depends upon package B, then package B must be installed before package A.
 * 
 * This program will read package information and provide information about the packages that must
 * be installed before any given package can be installed. all of the packages in
 * 
 * You may add a main method, but we will test all methods with our own Test classes.
 */

/**
 * 
 * PackageManager - used to process json package dependency files and provide function that make
 * that information available to other users.
 * 
 * @author Xuxiang Sun (2020)
 *
 */
public class PackageManager {
  private Graph graph; // Graph object to store the packages

  /*
   * Package Manager default no-argument constructor.
   */
  public PackageManager() {
    this.graph = new Graph(); // creates new Graph object
  }

  /**
   * Takes in a file path for a json file and builds the package dependency graph from it.
   * 
   * @param jsonFilepath the name of json data file with package dependency information
   * @throws FileNotFoundException if file path is incorrect
   * @throws IOException           if the give file cannot be read
   * @throws ParseException        if the given json cannot be parsed
   */
  public void constructGraph(String jsonFilepath)
      throws FileNotFoundException, IOException, ParseException {
    try {
      JSONParser jsonParser = new JSONParser(); // creates new JSonParser()
      FileReader jsonFileReader = new FileReader(jsonFilepath); // creates FileReader for json file
      Object obj = jsonParser.parse(jsonFileReader); // parse jsonFileReader into Object
      JSONObject jo = (JSONObject) obj; // cast Object to JSONObject type

      Object packagesObject = jo.get("packages"); // expect a packages array in jo
      JSONArray packagesJSONArray = (JSONArray) packagesObject; // cast to JSONArray type
      Package[] packagesArray = new Package[packagesJSONArray.size()]; // array of packages
      for (int i = 0; i < packagesArray.length; i++) {
        JSONObject jsonPackage = (JSONObject) packagesJSONArray.get(i); // gets package at index i
        String name = (String) jsonPackage.get("name"); // cast to String type
        this.graph.addVertex(name); // adds String name as vertex in graph

        // loops through dependencies packages
        JSONArray dependenciesArray = (JSONArray) jsonPackage.get("dependencies");
        for (int j = 0; j < dependenciesArray.size(); j++) {
          String dependencies = (String) dependenciesArray.get(j);
          this.graph.addEdge(name, dependencies);
        }
      }
      // throws FileNotFoundException if file path is incorrect
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException();

      // throws IOException if the give file cannot be read
    } catch (IOException e) {
      throw new IOException();

      // throws ParseException if the given json cannot be parsed
    } catch (ParseException e) {
      throw new ParseException(0);
    }
  }

  /**
   * Helper method to get all packages in the graph.
   * 
   * @return Set<String> of all the packages
   */
  public Set<String> getAllPackages() {
    return this.graph.getAllVertices(); // calls getAllVertices
  }

  /**
   * Given a package name, returns a list of packages in a valid installation order.
   * 
   * Valid installation order means that each package is listed before any packages that depend upon
   * that package.
   * 
   * @return List<String>, order in which the packages have to be installed
   * 
   * @throws CycleException           if you encounter a cycle in the graph while finding the
   *                                  installation order for a particular package. Tip: Cycles in
   *                                  some other part of the graph that do not affect the
   *                                  installation order for the specified package, should not throw
   *                                  this exception.
   * 
   * @throws PackageNotFoundException if the package passed does not exist in the dependency graph.
   */
  public List<String> getInstallationOrder(String pkg)
      throws CycleException, PackageNotFoundException {
    // throw exception if pkg has no dependencies, meaning not in the graph
    if (this.graph.getAdjacentVerticesOf(pkg) == null) {
      throw new PackageNotFoundException();
    }
    List<String> visited = new LinkedList<String>(); // creates visited List for DFS
    Stack<String> stack = new Stack<String>(); // creates stack for DFS
    List<String> outList = new LinkedList<String>(); // creates output List for DFS
    DFStraversal(pkg, visited, stack, outList); // gets install order
    return outList; // returns output List
  }

  /**
   * Given two packages - one to be installed and the other installed, return a List of the packages
   * that need to be newly installed.
   * 
   * For example, refer to shared_dependecies.json - toInstall("A","B") If package A needs to be
   * installed and packageB is already installed, return the list ["A", "C"] since D will have been
   * installed when B was previously installed.
   * 
   * @return List<String>, packages that need to be newly installed.
   * 
   * @throws CycleException           if you encounter a cycle in the graph while finding the
   *                                  dependencies of the given packages. If there is a cycle in
   *                                  some other part of the graph that doesn't affect the parsing
   *                                  of these dependencies, cycle exception should not be thrown.
   * 
   * @throws PackageNotFoundException if any of the packages passed do not exist in the dependency
   *                                  graph.
   */
  public List<String> toInstall(String newPkg, String installedPkg)
      throws CycleException, PackageNotFoundException {
    // throw exception if pkg has no dependencies, meaning not in the graph
    if (this.graph.getAdjacentVerticesOf(newPkg) == null) {
      throw new PackageNotFoundException();
    }
    List<String> visited = new LinkedList<String>(); // creates visited List for DFS
    Stack<String> stack = new Stack<String>(); // creates stack for DFS
    List<String> outList = new LinkedList<String>(); // creates output List for DFS
    DFStraversal(installedPkg, visited, stack, outList); // traverses on installedPkg
    outList.clear(); // clears outList for installedPkg
    DFStraversal(newPkg, visited, stack, outList); // traverses again on newPkg
    return outList; // returns output List
  }

  /**
   * Return a valid global installation order of all the packages in the dependency graph.
   * 
   * assumes: no package has been installed and you are required to install all the packages
   * 
   * returns a valid installation order that will not violate any dependencies
   * 
   * @return List<String>, order in which all the packages have to be installed
   * @throws CycleException if you encounter a cycle in the graph
   */
  public List<String> getInstallationOrderForAllPackages() throws CycleException {
    List<String> visited = new LinkedList<String>(); // creates visited List for DFS
    Stack<String> stack = new Stack<String>(); // creates stack for DFS
    List<String> outList = new LinkedList<String>(); // creates output List for DFS
    // traverses on every vertex in the graph, adding all vertices in install order to output List
    for (String pkg : this.graph.getAllVertices()) {
      DFStraversal(pkg, visited, stack, outList);
    }
    return outList; // returns output List
  }

  /**
   * Find and return the name of the package with the maximum number of dependencies.
   * 
   * Tip: it's not just the number of dependencies given in the json file. The number of
   * dependencies includes the dependencies of its dependencies. But, if a package is listed in
   * multiple places, it is only counted once.
   * 
   * Example: if A depends on B and C, and B depends on C, and C depends on D. Then, A has 3
   * dependencies - B,C and D.
   * 
   * @return String, name of the package with most dependencies.
   * @throws CycleException if you encounter a cycle in the graph
   */
  public String getPackageWithMaxDependencies() throws CycleException {
    // HashMap that maps packages to their sizes of dependencies list
    HashMap<String, Integer> vertexes = new HashMap<String, Integer>();
    for (String pkg : this.graph.getAllVertices()) {
      List<String> visited = new LinkedList<String>(); // creates visited List for DFS
      Stack<String> stack = new Stack<String>(); // creates stack for DFS
      List<String> outList = new LinkedList<String>(); // creates output List for DFS
      DFStraversal(pkg, visited, stack, outList); // gets install order
      vertexes.put(pkg, outList.size()); // size of output List = num of total dependencies
    }
    Entry<String, Integer> maxVertex = null; // tracks the pkg with max dependencies
    int maxDependencies = 0; // tracks num of dependencies for cur pkg
    // loops through all pkgs and find max dependencies
    for (Entry<String, Integer> pkg : vertexes.entrySet()) {
      // checks if is greater than
      if (pkg.getValue() > maxDependencies) {
        maxDependencies = pkg.getValue();
        maxVertex = pkg;
      }
    }
    return maxVertex.getKey(); // returns the pkg name of the one with largest dependencies
  }

  /**
   * Private helper function, sample Depth First Search algorithm that outputs a List of packages in
   * installation order
   * 
   * @param pkg     - should be the starting vertex represented in String package
   * @param visited - tracks visited vertices
   * @param stack   - represents a stack of visited vertices
   * @param outList - output List of Strings in the order of traversal
   * 
   * @throws CycleException - if detects a cycle, i.e. one vertex is visited twice
   */
  private void DFStraversal(String pkg, List<String> visited, Stack<String> stack,
      List<String> outList) throws CycleException {
    // base case, if this package is visited
    if (visited.contains(pkg)) {
      return;
    }
    visited.add(pkg); // mark package as visited
    stack.add(pkg); // push package to stack
    // loops through successors of pkg
    for (String vertex : graph.getAdjacentVerticesOf(pkg)) {
      if (stack.contains(vertex)) {
        throw new CycleException(); // throws CycleException if vertex is already visited
      } else {
        DFStraversal(vertex, visited, stack, outList); // recurse on successor
      }
    }
    outList.add(pkg); // adds pkg to output List
    stack.remove(pkg); // pops pkg from stack
  }
  
  public static void main(String[] args) {
    System.out.println("PackageManager.main()");
  }
}
