/*
 * This code uses the CSP License.
 */

package jme3test.helloworld;

/** HelloAnimation
 *  @author cuzit
 *  Description: This code is for learning how to animate things in jme3 using
 * the official tutorial.
 * 
 * Sample 7 - how to load an OgreXML model and play an animation,
 * using channels, a controller, and an AnimEventListener.
 */

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.debug.SkeletonDebugger;
import com.jme3.material.Material;

public class HelloAnimation extends SimpleApplication implements AnimEventListener {
     private AnimChannel channel;
     private AnimControl control;
     Node player;
     
     public static void main(String[] args) {
          HelloAnimation app = new HelloAnimation();
          app.start();
     }
     
     @Override
     public void simpleInitApp() {
          viewPort.setBackgroundColor(ColorRGBA.LightGray);
          initKeys();
          DirectionalLight dl = new DirectionalLight();
          dl.setDirection(new Vector3f(-0.1f, -1f, -1).normalizeLocal());
          rootNode.addLight(dl);
          player = (Node) assetManager.loadModel("Models/Oto/Oto.mesh.xml");
          player.setLocalScale(0.5f);
          rootNode.attachChild(player);
          control = player.getControl(AnimControl.class);
          control.addListener(this);
          channel = control.createChannel();
          channel.setAnim("stand");
          
          //get the names of the animations available to this oto thing model
          for (String anim : control.getAnimationNames()) {
               System.out.println(anim);
          }
          
          //Make those sexy bones visible hell yeah
          SkeletonDebugger skeletonDebug = new SkeletonDebugger("skeleton", control.getSkeleton());
          Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
          mat.setColor("Color", ColorRGBA.Green);
          mat.getAdditionalRenderState().setDepthTest(false);
          skeletonDebug.setMaterial(mat);
          player.attachChild(skeletonDebug);
     }
     
     public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
          if(animName.equals("Walk")) {
               channel.setAnim("stand", 0.50f);
               channel.setLoopMode(LoopMode.DontLoop);
               channel.setSpeed(1f);
          }
          if(animName.equals("Dodge")) {
               channel.setAnim("stand", 0.50f);
               channel.setLoopMode(LoopMode.DontLoop);
               channel.setSpeed(1f);
          }
     }
     
     public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
          //unused
     }
     
     /**Custom Keybinding: Map named actins to inputs */
     private void initKeys() {
          inputManager.addMapping("Walk", new KeyTrigger(KeyInput.KEY_SPACE));
          inputManager.addListener(actionListener, "Walk");
          
          inputManager.addMapping("Dodge", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
          inputManager.addListener(actionListener, "Dodge");
     }
     
     private ActionListener actionListener = new ActionListener() {
          public void onAction(String name, boolean keyPressed, float tpf) {
               if(name.equals("Walk") && !keyPressed) {
                    if(!channel.getAnimationName().equals("Walk")) {
                         channel.setAnim("Walk", 0.50f);
                         channel.setLoopMode(LoopMode.Loop);
                    }
               }
               if(name.equals("Dodge") && !keyPressed) {
                    System.out.println("This stupid fucker clicked the mouse");
                    if(!channel.getAnimationName().equals("Dodge")) {
                         channel.setAnim("Dodge", 0.50f);
                         channel.setLoopMode(LoopMode.Loop);
                    }
               }
          }
     };
}
