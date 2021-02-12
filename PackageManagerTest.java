/**
 * PackageManagerTest.java created by Xuxiang Sun on ThinkPad in p4_PackageManager
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
 * GraphTest - Test class for PackageManager class, utilizing provided .json files, including
 * valid.json, shared_dependencies.json, cyclic.json
 * 
 * @author Xuxiang Sun (2020)
 *
 */
public class PackageManagerTest {
  protected PackageManager packageManager; // graph object to be used multiple times

  /**
   * Instantiates a new graph
   * 
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception {
    packageManager = new PackageManager();
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
   * Tests getAllPackage() on valid.json, should print out all packages
   */
  @Test
  public void test000_getAllPackage() {
    try {
      this.packageManager.constructGraph("valid.json"); // tests getAllPackage() on valid.json
      // checks if getAllPackage returns correct
      if (!packageManager.getAllPackages().toString().equals("[A, B, C, D, E]")) {
        fail("returned wrong packages");
      }
    } catch (Exception e) {
      fail("should not have thrown unexpected exception"); // should not throw exception
    }
  }

  /**
   * Tests getInstallationOrder() on "A" in valid.json, should print out correct order according to
   * valid.json
   */
  @Test
  public void test001_getInstallationOrder() {
    try {
      this.packageManager.constructGraph("valid.json"); // tests getInstallationOrder() on
                                                        // valid.json
      // checks if getInstallationOrder returns correct
      if (!packageManager.getInstallationOrder("A").toString().equals("[C, D, B, A]")) {
        fail("returned wrong packages");
      }
    } catch (Exception e) {
      fail("should not have thrown unexpected exception"); // should not throw exception
    }
  }

  /**
   * Tests toInstall() on "A", "B" in shared_dependencies.json, should print out correct order
   * according to shared_dependencies.json
   */
  @Test
  public void test002_toInstall() {
    try {
      this.packageManager.constructGraph("shared_dependencies.json"); // tests
                                                                      // getInstallationOrder()
                                                                      // on shared_dependencies.json
      // checks if getInstallationOrder returns correct
      if (!packageManager.toInstall("A", "B").toString().equals("[C, A]")) {
        fail("returned wrong packages");
      }
    } catch (Exception e) {
      fail("should not have thrown unexpected exception"); // should not throw exception
    }
  }

  /**
   * Tests getInstallationOrderForAllPackages() on shared_dependencies.json, should print out
   * correct order according to shared_dependencies.json
   */
  @Test
  public void test003_getInstallationOrderForAllPackages() {
    try {
      // tests getInstallationOrderForAllPackages() on shared_dependencies.json
      this.packageManager.constructGraph("shared_dependencies.json");
      // checks if getInstallationOrder returns correct
      if (!packageManager.getInstallationOrderForAllPackages().toString().equals("[D, B, C, A]")) {
        fail("returned wrong packages");
      }
    } catch (Exception e) {
      fail("should not have thrown unexpected exception"); // should not throw exception
    }
  }

  /**
   * Tests getPackageWithMaxDependencies() on shared_dependencies.json, should print out correct
   * Package according to shared_dependencies.json
   */
  @Test
  public void test004_getPackageWithMaxDependencies() {
    try {
      // tests getPackageWithMaxDependencies() on shared_dependencies.json
      this.packageManager.constructGraph("shared_dependencies.json");
      // checks if getPackageWithMaxDependencies returns correct
      if (!packageManager.getPackageWithMaxDependencies().toString().equals("A")) {
        fail("returned wrong package");
      }
    } catch (Exception e) {
      fail("should not have thrown unexpected exception"); // should not throw exception
    }
  }

  /**
   * Tests if cyclic exception is thrown on cyclic.json
   */
  @Test
  public void test005_cyclic() {
    try {
      // tests getPackageWithMaxDependencies() on cyclic.json
      this.packageManager.constructGraph("cyclic.json");
      try {
        // checks if getPackageWithMaxDependencies returns correct
        if (!packageManager.getPackageWithMaxDependencies().toString().equals("A")) {
          fail("returned wrong package");
        }
      } catch (CycleException e) {
        // expected exception
      }
    } catch (Exception e) {
      fail("should not have thrown unexpected exception"); // should not throw exception
    }
  }
}
