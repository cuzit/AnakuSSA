/*
 * This code uses the CSP License.
 */

package jme3test.helloworld;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.math.ColorRGBA;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;

/** HelloInput
 *  @author cuzit
 *  Description: This is test code to learn JME3
 */

public class HelloInput extends SimpleApplication {
     public static void main(String[] args) {
          HelloInput app = new HelloInput();
          app.start();
     }
     
     protected Geometry player;
     Boolean isRunning = true;
     
     @Override
     public void simpleInitApp() {
          Box b = new Box(1, 1, 1);
          player = new Geometry("Player", b);
          Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
          mat.setColor("Color", ColorRGBA.Blue);
          player.setMaterial(mat);
          rootNode.attachChild(player);
          flyCam.setEnabled(false);
          initKeys(); //load my custom keybinding
     }
     
     /** Custom Keybinding: Map named actions to inputs */
     private void initKeys() {
          inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_P));
          inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
          inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
          inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W),
                                             new MouseAxisTrigger(MouseInput.AXIS_WHEEL, true));
          inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S),
                                             new MouseAxisTrigger(MouseInput.AXIS_WHEEL, false));
          inputManager.addMapping("Rotate", new KeyTrigger(KeyInput.KEY_SPACE),
                                             new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
          
          //Add the names to the action listener
          inputManager.addListener(actionListener, "Pause");
          inputManager.addListener(analogListener, "Left", "Right", "Up", "Down", "Rotate");
     }
     
     private ActionListener actionListener = new ActionListener() {
          @Override
          public void onAction(String name, boolean keyPressed, float tpf) {
               if(name.equals("Pause") && !keyPressed) {
                    System.out.println("Key P was pressed");
                    isRunning = !isRunning;
               }
          }
     };
     
     private AnalogListener analogListener = new AnalogListener() {
          @Override
          public void onAnalog(String name, float value, float tpf) {
               if(isRunning) {
                    if(name.equals("Rotate")) {
                         player.rotate(0, value*speed, 0);
                         System.out.println("Rotating");
                    }
                    if(name.equals("Right")) {
                         Vector3f v = player.getLocalTranslation();
                         player.setLocalTranslation(v.x + value*speed, v.y, v.z);
                         System.out.println("Moving right");
                    }
                    if(name.equals("Left")) {
                         Vector3f v = player.getLocalTranslation();
                         player.setLocalTranslation(v.x - value*speed, v.y, v.z);
                         System.out.println("Moving Left");
                    }
                    if(name.equals("Up")) {
                         Vector3f v = player.getLocalTranslation();
                         player.setLocalTranslation(v.x, v.y, v.z - value * speed);
                         System.out.println("Moving up in the world");
                    }
                    if(name.equals("Down")) {
                         Vector3f v = player.getLocalTranslation();
                         player.setLocalTranslation(v.x, v.y, v.z + value * speed);
                         System.out.println("ARE YOU DOWN WITH THIS");
                    }
               }
               else {
                    System.out.println("Press P to unpause.");
               }
          }
     };
}
