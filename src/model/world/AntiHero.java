package model.world;

import java.util.ArrayList;

import model.effects.Stun;

public class AntiHero extends Champion {

	public AntiHero(String name, int maxHP, int maxMana, int actions, int speed, int attackRange, int attackDamage) {
		super(name, maxHP, maxMana, actions, speed, attackRange, attackDamage);

	}

	@Override
	public void useLeaderAbility(ArrayList<Champion> targets) {
		for (Champion c: targets)
		{
			Stun s = new Stun(2);
			c.getAppliedEffects().add(s);
			s.apply(c);
		}
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString() + "Type: AntiHero"+ "\n";
	}

	@Override
	public String getInfo() {
		return super.getInfo() + "Type: AntiHero";
	}
}
