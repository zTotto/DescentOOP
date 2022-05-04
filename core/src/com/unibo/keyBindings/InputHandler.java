package com.unibo.keyBindings;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.badlogic.gdx.Gdx;

/**
 * Class that handle the input, in order to give a Command object, 
 * referred to the action the player want to execute (based on what he pressed)
 *
 */
public class InputHandler {
	private final Map<String, Command> commands = new HashMap<>();
	
	/**
	 * Constructor of the InputHandler
	 * 
	 * @param attack command that execute the attack action
	 * @param pickUp command that execute the pick up action
	 * @param switchWeapon command that execute the weapon's switch action
	 * @param moveUp command that move up a character
	 * @param moveRight command that move right a character
	 * @param moveDown command that move down a character
	 * @param moveLeft command that move left a character
	 * @param pause command that pause the game
	 */
	public InputHandler(Command attack, Command pickUp, Command switchWeapon, Movement moveUp, Movement moveRight, Movement moveDown, Movement moveLeft, Command pause) {
		this.commands.put(KeyBindings.ATTACK.getName(), attack);
		this.commands.put(KeyBindings.PICK_UP.getName(), pickUp);
		this.commands.put(KeyBindings.SWITCH_WEAPON.getName(), switchWeapon);
		this.commands.put(KeyBindings.MOVE_UP.getName(), moveUp);
		this.commands.put(KeyBindings.MOVE_RIGHT.getName(), moveRight);
		this.commands.put(KeyBindings.MOVE_DOWN.getName(), moveDown);
		this.commands.put(KeyBindings.MOVE_LEFT.getName(), moveLeft);
		this.commands.put(KeyBindings.PAUSE.getName(), pause);
	}
	
	/**
	 * @param key the key that the user pressed (or better, the action the user want to perform)
	 * @return an optional of the command related to the action the user want do perform
	 */
	public Optional<Command> handleInput(KeyBindings key) {
		
		if(KeyBindings.MOVE_DOWN == key 
		   || KeyBindings.MOVE_UP == key 
		   || KeyBindings.MOVE_RIGHT == key 
		   || KeyBindings.MOVE_LEFT == key) {
			return Gdx.input.isKeyPressed(key.getKey()) ? Optional.ofNullable(this.commands.get(key.getName())) : Optional.empty();
		}
		
		return Gdx.input.isKeyJustPressed(key.getKey()) ? Optional.ofNullable(this.commands.get(key.getName())) : Optional.empty();
	}
}
