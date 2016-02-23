import java.util.List;

public class MyGreep extends Greep
{
    // Remember: you cannot extend the Greep's memory. So:
    // no additional fields (other than final fields) allowed in this class!
    
    private static final int TOMATO_LOCATION_KNOWN = 1;
    
    /**
     * Default constructor. Do not remove.
     */
    public MyGreep(Ship ship)
    {
        super(ship);
    }
    
    public void act()
    {
        super.act();
        // Before moving, lets check for food.
        checkFood();
            
        if (carryingTomato()) {
            bringTomatoHome();
        }
        else if(getTomatoes() != null) {            
            TomatoPile tomatoes = getTomatoes(); 
            if(!blockPile(tomatoes)) {
                // Not blocking so lets go towards the centre of the pile
                turnTowards(tomatoes.getX(), tomatoes.getY());
                move();
            }
        }
        else if (getMemory(0) == TOMATO_LOCATION_KNOWN) {
            // Hmm. We know where there are some tomatoes...
            turnTowards(getMemory(1), getMemory(2));
            move();
        }
        else if (numberOfOpponents(false) > 1) {
            // Can we see four or more opponents?
            kablam();            
        } 
        else {
            randomWalk();
        }        
        
        // Avoid obstacles
        if (atWater() || moveWasBlocked()) {
            // If we were blocked, try to move somewhere else
            int r = getRotation();
            setRotation (r + Greenfoot.getRandomNumber(2) * 180 - 90);
            move();
        }        
    }

    
    public void checkFood()
    {
        TomatoPile tomatoes = getTomatoes();
        if(tomatoes != null) {
            loadTomato();
            // Note: this attempts to load a tomato onto *another* Greep. It won't
            // do anything if we are alone here.
            
            setMemory(0, TOMATO_LOCATION_KNOWN);
            setMemory(1, tomatoes.getX());
            setMemory(2, tomatoes.getY());
        }
    }
    
    private void randomWalk()
    {
        // there's a 3% chance that we randomly turn a little off course
        if (randomChance(3)) {
            turn((Greenfoot.getRandomNumber(3) - 1) * 100);
        }
        
        move();
    }
   
    private void bringTomato() 
    {
        if(atShip()) {
            dropTomato();
        }
        else {
            turnHome();
            move();
        }
    }
    
    
    private boolean blockPile(TomatoPile tomatoes) 
    {
        // Are we at the centre of the pile of tomatoes?  
        boolean atPile = tomatoes != null && distanceTo(tomatoes.getX(), tomatoes.getY()) < 4;
        if(atPile && getFriend() == null ) {
            // No friends at this pile, so we might as well block
            block(); 
            return true;
        }
        else {
            return false;
        }
    }

    
    private int distanceTo(int x, int y)
    {
        int deltaX = getX() - x;
        int deltaY = getY() - y;
        return (int) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }
    
    public String getName()
    {
        return "Kostas";
    }    
}
 
