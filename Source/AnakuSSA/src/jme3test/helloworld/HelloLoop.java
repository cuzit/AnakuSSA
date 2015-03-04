package jme3test.helloworld;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

/** Sample 4 - how to trigger repeating action from the main event loop.
 *  In this example, you use the loop to make the player character
 * rotate continously. */

public class HelloLoop extends SimpleApplication {
     public static void main(String[] args) {
          HelloLoop app = new HelloLoop();
          app.start();
     }
     
     protected Geometry player;
     protected Geometry otherBox;
     
     protected Material mat;
     protected Material mat2;
     
     protected float scale = 0;
     protected int tick = 0;
     protected int pulse = 1;
     
     @Override
     public void simpleInitApp() {
          /** this blue box is our player character */
          Box b = new Box(1, 1, 1);
          player = new Geometry("blue cube", b);
          mat = new Material(assetManager,
                  "Common/MatDefs/Misc/Unshaded.j3md");
          mat.setColor("Color", ColorRGBA.Red);
          player.setMaterial(mat);
          rootNode.attachChild(player);
          
          // Second object for fucking around
          Box c = new Box(1, 1, 1);
          otherBox = new Geometry("red cube", c);
          otherBox.setLocalTranslation(new Vector3f(1, 3, 1));
          otherBox.setMaterial(mat);
          rootNode.attachChild(otherBox);
          
          //Define other mat
          mat2 = new Material(assetManager,
                  "Common/MatDefs/Misc/Unshaded.j3md");
          mat2.setColor("Color", ColorRGBA.Green);
     }
     
     /*Use the main event loop to trigger repeating actions. */
     @Override
     public void simpleUpdate(float tpf) {
          //increase tick
          tick++;
          if(tick > 120) {
               tick = 0;
          }
          
          // make the player rotate:
          player.rotate(0, -2*tpf, 0);
          
          // make the other box rotate
          otherBox.rotate(0, tpf, 0);
          
          //Make the first cube pulsate
          if(tick % 5 == 0) {
               player.setLocalScale(FastMath.sin((tick + tpf) * 2) * 0.5f + 1.5f);
          }
          
          //change second cube's color every half second
          if(tick % 30 == 0) {
               if(otherBox.getMaterial() == mat) {
                    otherBox.setMaterial(mat2);
               }
               else {
                    otherBox.setMaterial(mat);
               }
          }
     }
}
