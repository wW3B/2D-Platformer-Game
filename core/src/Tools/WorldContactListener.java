package Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import Sprite.InteractiveTilesObject;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        
        // Check for foot collision
        if (fixA.getUserData() == "foot" || fixB.getUserData() == "foot") {
            Fixture foot = fixA.getUserData() == "foot" ? fixA : fixB;
            Fixture object = foot == fixA ? fixB : fixA;
            
            if (object.getUserData() != null && InteractiveTilesObject.class.isAssignableFrom(object.getUserData().getClass())) {
                ((InteractiveTilesObject) object.getUserData()).footStep(null);
                
            }
        }

        // Check for head collision
        if (fixA.getUserData() == "head" || fixB.getUserData() == "head") {
            Fixture head = fixA.getUserData() == "head" ? fixA : fixB;
            Fixture object = head == fixA ? fixB : fixA;
            
            if (object.getUserData() != null && InteractiveTilesObject.class.isAssignableFrom(object.getUserData().getClass())) {
                ((InteractiveTilesObject) object.getUserData()).headHit(null);
            }
        }

        // Check for front collision
        if (fixA.getUserData() == "front" || fixB.getUserData() == "front") {
            Fixture front = fixA.getUserData() == "front" ? fixA : fixB;
            Fixture object = front == fixA ? fixB : fixA;
            
            if (object.getUserData() != null && InteractiveTilesObject.class.isAssignableFrom(object.getUserData().getClass())) {
                ((InteractiveTilesObject) object.getUserData()).frontHit(null);
            }
        }

        // Check for back collision
        if (fixA.getUserData() == "back" || fixB.getUserData() == "back") {
            Fixture back = fixA.getUserData() == "back" ? fixA : fixB;
            Fixture object = back == fixA ? fixB : fixA;
            
            if (object.getUserData() != null && InteractiveTilesObject.class.isAssignableFrom(object.getUserData().getClass())) {
                ((InteractiveTilesObject) object.getUserData()).backHit(null);
                
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        // TODO Auto-generated method stub
        // Gdx.app.log("End Contact", "");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // TODO Auto-generated method stub
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // TODO Auto-generated method stub
    }
}
