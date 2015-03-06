/*
 * This code uses the CSP License.
 */

package Tetris3D;

/** Tetris3D
 *  @author cuzit
 *  Description: This is a file to launch Tetris3D. Tetris3D is a Tetris clone
 * I made it JME3.  This game exists to practice development with JME3 and to
 * utilize the skills I hopefully learned from following the tutorial. It's not
 * impossible that this will be included in the final game as a minigame or
 * easter egg.
 */

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

public class Tetris3D extends SimpleApplication {
     /** Variables **/
     //Blocks
     Spatial lBlock;
     Spatial squareBlock;
     Spatial tBlock;
     Spatial lineBlock;
     Material blockMat;
     
     /** Initialize **/
     @Override
     public void simpleInitApp() {
          /** This code is to test loading the LBlock model, remove it or move it later **/
          lBlock = assetManager.loadModel("Models/Tetris3D/tetris3D_Lblock.obj");
          blockMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
          lBlock.setMaterial(blockMat);
          lBlock.scale(1f);
          rootNode.attachChild(lBlock);
          DirectionalLight sun = new DirectionalLight();
          sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f));
          rootNode.addLight(sun);
          viewPort.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));
          flyCam.setMoveSpeed(25);
          squareBlock = assetManager.loadModel("Models/Tetris3D/tetris3D_squareBlock.obj");
          squareBlock.setMaterial(blockMat);
          lBlock.scale(1f);
          lBlock.setLocalTranslation(new Vector3f(0.0f, -5.0f, -2.0f));
          rootNode.attachChild(squareBlock);
          tBlock = assetManager.loadModel("Models/Tetris3D/tetris3D_Tblock.obj");
          lineBlock = assetManager.loadModel("Models/Tetris3D/tetris3D_lineBlock.obj");
          tBlock.setMaterial(blockMat);
          lineBlock.setMaterial(blockMat);
          tBlock.setLocalTranslation(new Vector3f(0.0f, -5.0f, -5.0f));
          lineBlock.setLocalTranslation(new Vector3f(0.0f, -5.0f, 5.0f));
          rootNode.attachChild(tBlock);
          rootNode.attachChild(lineBlock);
          
          
          
          
          //This is for reference
          //Load a model from test_data (OgreXML + material + texture)
//          Spatial ninja = assetManager.loadModel("Models/Ninja/Ninja.mesh.xml");
//          ninja.scale(0.05f, 0.05f, 0.05f);
//          ninja.rotate(0.0f, -3.0f, 0.0f);
//          ninja.setLocalTranslation(0.0f, -5.0f, -2.0f);
//          rootNode.attachChild(ninja);
//          //You must add a light to make the model visible
//          DirectionalLight sun = new DirectionalLight();
//          sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f));
//          rootNode.addLight(sun);
//          Material mat_default = new Material(
//                  assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
//          teapot.setMaterial(mat_default);
     }
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     /** Main - Launch Game **/
     public static void main(String[] args) {
          Tetris3D tetris = new Tetris3D();
          tetris.start();
     }
}
