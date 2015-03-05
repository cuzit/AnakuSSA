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

public class Tetris3D extends SimpleApplication {
     /** Variables **/
     
     /** Initialize **/
     @Override
     public void simpleInitApp() {
          
     }
     
     /** Main - Launch Game **/
     public static void main(String[] args) {
          Tetris3D tetris = new Tetris3D();
          tetris.start();
     }
}
