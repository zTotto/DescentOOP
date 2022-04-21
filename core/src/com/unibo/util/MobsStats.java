package com.unibo.util;

public enum MobsStats {
	ORC("Orc", 60, 130), 
	TROLL("Troll", 30, 170);
	
	private final String name;
	private final int hp;
	private final int speed;
	
	MobsStats(final String name, final int hp, final int speed) {
		this.name = name;
		this.hp = hp;
		this.speed = speed;
	}
	
	public String getName() {
		return this.name;
	}

	public int getHp() {
		return this.hp;
	}

	public int getSpeed() {
		return this.speed;
	}
}
