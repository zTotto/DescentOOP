package com.unibo.util;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.maps.MapLayer;
import com.unibo.maps.Map;
import com.unibo.model.Movement;
import com.unibo.view.HeroView;
import com.unibo.view.LevelView;
import com.unibo.view.MobView;

public final class Pathfinding {

	public static void A(MobView mob, LevelView level, Map map) {
		if (map.validMovement(mob, mob.getCharacter().getPos().getxCoord(), mob.getCharacter().getPos().getyCoord()+1)) {
	    	Movement move = new Movement(Direction.DOWN);
	    	move.executeCommand(mob);
		}
		
	}
	
	public static boolean lineOfSight(MobView mob, LevelView level, Map map) {
//		return false;
		final HeroView hero = level.getHeroView();
		final int mobX = mob.getCharacter().getPos().getxCoord();
		final int mobY = mob.getCharacter().getPos().getyCoord();
		final int heroX = hero.getCharacter().getPos().getxCoord();
		final int heroY = hero.getCharacter().getPos().getyCoord();
		float m = 0;
		float b;
		int width;
		
		Pair<Integer, Integer> heroPair = new Pair<>(heroY, heroX);
		Pair<Integer, Integer> mobPair = new Pair<>(mobY, mobX);
		
		final List<Pair<Integer, Integer>> points = new ArrayList<>();
		
		if (heroX != mobX) {
			m = (heroY-mobY)/(heroX-mobX);
		}
		
		b = mobY-m*(mobX);
		
//		lineFromPoints(heroPair, mobPair);
		
		if (mobX >= heroX) {
			width = mobX - heroX;
			for(int i = 0; i < width; i++) {
			    int x = heroX + i;
			    int y = (int) (heroY + (m * i));
			    points.add(new Pair<Integer, Integer>(x,y));
			}
		}
		
		else {
			width = heroX-mobX;
			for(int i = 0; i < width; i++) {
			    int x = mobX + i;
			    int y = (int) (mobY + (m * i));
			    points.add(new Pair<Integer, Integer>(x,y));
			}
		}

		
		for (Pair<Integer, Integer> position : points) {
			if (!map.validMovement(mob, position.getFirst(), position.getSecond())) {
				System.out.println("FALSE m "+m+" heroX "+ heroX+"  heroY "+ heroY+"  mobX "+ mobX+ " mobY" + mobY);
				return false;
			}
		}
		
		System.out.println("m "+m+"  heroX "+ heroX+"  heroY "+ heroY+"  mobX "+ mobX+ " mobY" + mobY);
		return true;
	}
	
	 static void lineFromPoints(Pair<Integer, Integer> P, Pair<Integer, Integer> Q)
	    {
	        int a = Q.getSecond() - P.getSecond();
	        int b = P.getFirst() - Q.getFirst();
	        int c = a * (P.getFirst()) + b * (P.getSecond());
	 
	        if (b < 0) {
	            System.out.println(
	                "The line passing through points P and Q is: "
	                + a + "x - " + b + "y = " + c);                       //posso fare return pair(x,y) e poi faccio un array di punti con quelle x e y
	        }
	        else {
	            System.out.println(
	                "The line passing through points P and Q is: "
	                + a + "x + " + b + "y = " + c);
	        }
	    }
	
}
	