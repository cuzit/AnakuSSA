/*
 * This code uses the CSP License.
 */

package AnakuSSA;

/** AnakuSSA
 *  @author cuzit
 *  Description: PLEASE CHANGE
 */

/** Imported Packages */
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

/** Main Class */
public class AnakuSSA extends SimpleApplication {
     /** Variables */
     
     @Override
     public void simpleInitApp() {
          //The following code is temporary so that a blue box will be displayed
          //when this program is ran. It will be edited later.
          Box b = new Box(1, 1, 1);
          Geometry geom = new Geometry("Box", b);
          
          Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
          mat.setColor("Color", ColorRGBA.Blue);
          geom.setMaterial(mat);
          
          rootNode.attachChild(geom);
          
     }
     
     @Override
     public void simpleUpdate(float tpf) {
          //TODO: add update code
     }
     
     @Override
     public void simpleRender(RenderManager rm) {
          //TODO: add render code
     }
     
     /** Main */
     //Start the game.
     public static void main(String[] args) {
          AnakuSSA game = new AnakuSSA();
          game.start();
     }
}