/*
 * This code uses the CSP License.
 */

package jme3test.helloworld;

/** HelloTerrain
 *  @author cuzit
 *  Description: This is a jme3 tutorial for terrains and stuff
 *  Sample 10 - How to create fast-rendering terrains from heightmaps,
 * and how to use texture splatting to make the terrain look good.
 */

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.geomipmap.lodcalc.DistanceLodCalculator;
import com.jme3.terrain.heightmap.HillHeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import java.util.ArrayList;
import java.util.List;

public class HelloTerrain extends SimpleApplication implements ActionListener {
     private TerrainQuad terrain;
     private Material mat_terrain;
     private BulletAppState bulletAppState;
     private RigidBodyControl landscape;
     private CharacterControl player;
     private Vector3f walkDirection = new Vector3f();
     private boolean left = false, right = false, up = false, down = false, jump = false;
     private Vector3f camDir = new Vector3f();
     private Vector3f camLeft = new Vector3f();
     
     
     public static void main(String[] args) {
          HelloTerrain app = new HelloTerrain();
          app.start();
     }
     
     @Override
     public void simpleInitApp() {
          bulletAppState = new BulletAppState();
          stateManager.attach(bulletAppState);
          
          viewPort.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));
          flyCam.setMoveSpeed(100);
          
          setUpKeys();
          
          /** 1. Create terrain material and load four textures into it. */
          mat_terrain = new Material(assetManager,
                  "Common/MatDefs/Terrain/Terrain.j3md");
          
          /** 1.1. Add ALPHA map (for red-blue-green coded splat textures */
          mat_terrain.setTexture("Alpha", assetManager.loadTexture(
                  "Textures/Terrain/splat/alphamap.png"));
          
          /**1.2. Add GRASS texture into the red layer (Tex1) */
          Texture grass = assetManager.loadTexture(
                  "Textures/Terrain/splat/grass.jpg");
          grass.setWrap(WrapMode.Repeat);
          mat_terrain.setTexture("Tex1", grass);
          mat_terrain.setFloat("Tex1Scale", 64f);
          
          /** 1.3. Add DIRT texture into the grass layer(Tex2) */
          Texture dirt = assetManager.loadTexture(
                  "Textures/Terrain/splat/dirt.jpg");
          dirt.setWrap(WrapMode.Repeat);
          mat_terrain.setTexture("Tex2", dirt);
          mat_terrain.setFloat("Tex2Scale", 32f);
          
          /** 1.4. Add ROAD texture into the blue layer (Tex3) */
          Texture rock = assetManager.loadTexture(
                  "Textures/Terrain/splat/road.jpg");
          rock.setWrap(WrapMode.Repeat);
          mat_terrain.setTexture("Tex3", rock);
          mat_terrain.setFloat("Tex3Scale", 128f);
          
          //Exercise 1 - Comment the two mat_terrain.setTextures for grass and road and uncomment this
          //mat_terrain.setTexture("Tex3", grass);
          //mat_terrain.setTexture("Tex1", rock);
          
          /** 2. Create the height map */
          AbstractHeightMap heightmap = null;
          Texture heightMapImage = assetManager.loadTexture(
                  "Textures/Terrain/splat/mountains512.png");
          heightmap = new ImageBasedHeightMap(heightMapImage.getImage());
          
          //Exercise 2 - Comment the above three lines and uncomment below.
          /*HillHeightMap heightmap = null;
          HillHeightMap.NORMALIZE_RANGE = 100; //optimal
          try {
               heightmap = new HillHeightMap(513, 1000, 50, 100, (byte) 3); // byte 3 is a random seed
          } catch (Exception ex) {
               ex.printStackTrace();
          }*/
          
          heightmap.load();
          
          /** 3. We have prepared material and heightmap.
           * Now we create the actual terrain:
           * 3.1. Create a TerrainQuad and name it "my terrain".
           * 3.2. A good value for terrain tiles is 64x64 -- so we supply 64+1=65.
           * 3.3. We prepared a heightmap of size 512x512 -- so we supply 512+1=513.
           * 3.4. As LOD step scale we supply Vector3f(1, 1, 1).
           * 3.5. We supply the prepared heightmap itself.
           */
          int patchSize = 65;
          terrain = new TerrainQuad("my terrain", patchSize, 513, heightmap.getHeightMap());
          
          /** 4. We give the terrain its material, position, scale, and attach it. */
          terrain.setMaterial(mat_terrain);
          terrain.setLocalTranslation(0, -100, 0);
          terrain.setLocalScale(2f, 1f, 2f);
          rootNode.attachChild(terrain);
          
          /** 5. The LOD (level of detail) depends on where the camera is: */
          List<Camera> cameras = new ArrayList<Camera>();
          cameras.add(getCamera());
          TerrainLodControl control = new TerrainLodControl(terrain, cameras);
          terrain.addControl(control);
          
          /** 6. Set up collision detection */
          CollisionShape sceneShape = CollisionShapeFactory.createMeshShape((Node) terrain);
          landscape = new RigidBodyControl(sceneShape, 0);
          terrain.addControl(landscape);
          
          /** 7. Set up player physics */
          CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.5f, 6f, 1);
          player = new CharacterControl(capsuleShape, 0.05f);
          player.setJumpSpeed(20);
          player.setFallSpeed(30);
          player.setGravity(30);
          player.setPhysicsLocation(new Vector3f(0, 100, 0));
          
          /** 8. Finally, we add physics */
          bulletAppState.getPhysicsSpace().add(landscape);
          bulletAppState.getPhysicsSpace().add(player);
          
     }
     
     private void setUpKeys() {
          inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
          inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
          inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
          inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
          inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
          inputManager.addListener(this, "Left");
          inputManager.addListener(this, "Right");
          inputManager.addListener(this, "Up");
          inputManager.addListener(this, "Down");
          inputManager.addListener(this, "Jump");
     }
     
     public void onAction(String binding, boolean isPressed, float tpf) {
          if(binding.equals("Left")) {
               left = isPressed;
          }
          else if(binding.equals("Right")) {
               right = isPressed;
          }
          else if(binding.equals("Up")) {
               up = isPressed;
          }
          else if(binding.equals("Down")) {
               down = isPressed;
          }
          else if(binding.equals("Jump")) {
               jump = isPressed;
          }
     }
     
     @Override
     public void simpleUpdate(float tpf) {
          camDir.set(cam.getDirection()).multLocal(0.6f);
          camLeft.set(cam.getLeft()).multLocal(0.4f);
          walkDirection.set(0, 0, 0);
          if(left) {
               walkDirection.addLocal(camLeft);
          }
          if(right) {
               walkDirection.addLocal(camLeft.negate());
          }
          if(up) {
               walkDirection.addLocal(camDir);
          }
          if(down) {
               walkDirection.addLocal(camDir.negate());
          }
          player.setWalkDirection(walkDirection);
          cam.setLocation(player.getPhysicsLocation());
     }
}
