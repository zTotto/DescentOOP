package com.unibo.keyBindings;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.badlogic.gdx.Gdx;

public class InputHandler {
	private final Map<String, Command> commands = new HashMap<>();
	
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
