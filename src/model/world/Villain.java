package model.world;

import java.util.ArrayList;

public class Villain extends Champion {

	public Villain(String name, int maxHP, int maxMana, int actions, int speed, int attackRange, int attackDamage) {
		super(name, maxHP, maxMana, actions, speed, attackRange, attackDamage);

	}

	@Override
	public void useLeaderAbility(ArrayList<Champion> targets) {
		for (Champion c : targets) {

			c.setCurrentHP(0);

		}

	}
	@Override
	public String toString() {
		return super.toString() + "Type: AntiHero"+ "\n";
	}
	public String getInfo() {
		return super.getInfo() + "Type: Villain";
	}

}
