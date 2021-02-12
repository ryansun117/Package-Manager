/**
 * GraphTest.java created by Xuxiang Sun on ThinkPad in p4_PackageManager
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

import static org.junit.jupiter.api.Assertions.fail;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * GraphTest - Test class for Graph class
 * 
 * @author Xuxiang Sun (2020)
 *
 */
public class GraphTest {
  protected GraphADT graph; // graph object to be used multiple times

  /**
   * Instantiates a new graph
   * 
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception {
    graph = new Graph();
  }

  /**
   * Tear down after tests
   * 
   * @throws Exception
   */
  @After
  public void tearDown() throws Exception {

  }

  /**
   * Tests that a graph's size() returns 0 when correctly initialized
   */
  @Test
  public void test000_size() {
    try {
      GraphADT graph = new Graph();
      if (graph.size() != 0) // should return 0 on empty graph
        fail("size returned incorrect value");
    } catch (Exception e) {
      fail("should not have thrown unexpected exception"); // should not throw exception
    }
  }

  /**
   * Tests that a graph's order() returns 0 when correctly initialized
   */
  @Test
  public void test001_order() {
    try {
      GraphADT graph = new Graph();
      if (graph.size() != 0) // should return 0 on empty graph
        fail("size returned incorrect value");
    } catch (Exception e) {
      fail("should not have thrown unexpected exception"); // should not throw exception
    }
  }

  /**
   * Adds one vertex into the graph
   */
  @Test
  public void test002_add_one_vertex() {
    try {
      this.graph.addVertex("A"); // add one vertex, no expected exception
      if(this.graph.order() != 1) {
        fail("order should be 1");
      }
    } catch (Exception e) {
      fail("should not have thrown unexpected exception"); // should not throw exception
    }
  }

  /**
   * Adds null vertex into the graph
   */
  @Test
  public void test003_add_null_vertex() {
    try {
      this.graph.addVertex(null); // add null vertex, no expected exception
      if(this.graph.order() != 0) {
        fail("order should still be 0");
      }
    } catch (Exception e) {
      fail("should not have thrown unexpected exception"); // should not throw exception
    }
  }
  
  /**
   * Adds two duplicate vertex into the graph
   */
  @Test
  public void test004_add_duplicate_vertex() {
    try {
      this.graph.addVertex("A"); 
      this.graph.addVertex("A"); // add duplicate vertex, no expected exception
      if(this.graph.order() != 1) {
        fail("order should still be one after adding duplicate");
      }
    } catch (Exception e) {
      fail("should not have thrown unexpected exception"); // should not throw exception
    }
  }
  
  /**
   * Removes one vertex from the graph
   */
  @Test
  public void test005_remove_vertex() {
    try {
      this.graph.addVertex("A"); // add one vertex, no expected exception
      this.graph.removeVertex("A"); // should not throw exception
      if(this.graph.order() != 0) {
        fail("order should be zero after removing");
      }
    } catch (Exception e) {
      fail("should not have thrown unexpected exception"); // should not throw exception
    }
  }
  
  /**
   * Removes null vertex from the graph
   */
  @Test
  public void test006_remove_null_vertex() {
    try {
      this.graph.addVertex("A"); // add one vertex, no expected exception
      this.graph.removeVertex(null); // should not throw exception
      if(this.graph.order() != 1) {
        fail("order should still be one");
      }
    } catch (Exception e) {
      fail("should not have thrown unexpected exception"); // should not throw exception
    }
  }
  
  /**
   * Removes non-exsistent vertex from the graph
   */
  @Test
  public void test007_remove_invalid_vertex() {
    try {
      this.graph.addVertex("A"); // add one vertex, no expected exception
      this.graph.removeVertex("B"); // should not throw exception
      if(this.graph.order() != 1) {
        fail("order should still be one");
      }
    } catch (Exception e) {
      fail("should not have thrown unexpected exception"); // should not throw exception
    }
  }
  
  /**
   * Adds one edge into the graph
   */
  @Test
  public void test008_add_one_edge() {
    try {
      this.graph.addEdge("A", "B"); // add one edge, no expected exception
      if(this.graph.size() != 1) {
        fail("order should be 1");
      }
    } catch (Exception e) {
      fail("should not have thrown unexpected exception"); // should not throw exception
    }
  }
  
  /**
   * Adds three edges making a cycle into the graph
   */
  @Test
  public void test009_add_three_edges() {
    try {
      this.graph.addEdge("A", "B"); // add one edge, no expected exception
      this.graph.addEdge("A", "C"); // add one edge on same vertex
      this.graph.addEdge("B", "C"); // add another edge on the same vertex
      if(this.graph.size() != 3) {
        fail("order should be 3");
      }
    } catch (Exception e) {
      fail("should not have thrown unexpected exception"); // should not throw exception
    }
  }
  
  /**
   * Removes one edge after adding three edges making a cycle into the graph
   */
  @Test
  public void test010_remove_edges() {
    try {
      this.graph.addEdge("A", "B"); // add one edge, no expected exception
      this.graph.addEdge("A", "C"); // add one edge on same vertex
      this.graph.addEdge("B", "C"); // add another edge on the same vertex
      this.graph.removeEdge("A", "B"); // remove one of the edges
      if(this.graph.size() != 2) {
        fail("order should be 2");
      }
    } catch (Exception e) {
      fail("should not have thrown unexpected exception"); // should not throw exception
    }
  }
}
