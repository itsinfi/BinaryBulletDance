package Entities;

import org.newdawn.slick.Graphics;

/**
 * Dieses Interface dient dazu, dass die Methode render(Graphics g) innerhalb des GameStateManagers aufgerufen werden kann, um für jeden Frame Entities visuell darstellen zu können.
 * Dieses Interface wird implementiert von Medkit und LivingEntitiy, um alle Rendering-Prozesse von Entities über diese auszuführen und andere Renderprozesse, wie beispielsweise Waffen, die nötigen Parameter zu verschaffen, um diese ebenfalls rendern zu können.
 */
public interface Renderable {

    /**
     * Diese Methode soll implementiert werden, um Entities innerhalb des Levels visuell darstellen zu können
     * 
     * @param g Grafische Darstellung des Spiels durch die Slick2D-Library
     */
    public abstract void render(Graphics g);
    
}
