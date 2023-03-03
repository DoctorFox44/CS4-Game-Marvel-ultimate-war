package model.world;

import java.awt.Point;
import java.util.ArrayList;

import model.abilities.Ability;
import model.effects.Effect;

@SuppressWarnings("rawtypes")
public abstract class Champion implements Damageable, Comparable {
	private String name;
	private int maxHP;
	private int currentHP;
	private int mana;
	private int maxActionPointsPerTurn;
	private int currentActionPoints;
	private int attackRange;
	private int attackDamage;
	private int speed;
	private ArrayList<Ability> abilities;
	private ArrayList<Effect> appliedEffects;
	private Condition condition;
	private Point location;

	public Champion(String name, int maxHP, int mana, int actions, int speed, int attackRange, int attackDamage) {
		this.name = name;
		this.maxHP = maxHP;
		this.mana = mana;
		this.currentHP = this.maxHP;
		this.maxActionPointsPerTurn = actions;
		this.speed = speed;
		this.attackRange = attackRange;
		this.attackDamage = attackDamage;
		this.condition = Condition.ACTIVE;
		this.abilities = new ArrayList<Ability>();
		this.appliedEffects = new ArrayList<Effect>();
		this.currentActionPoints = maxActionPointsPerTurn;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public String getName() {
		return name;
	}

	public void setCurrentHP(int hp) {

		if (hp <= 0) {
			currentHP = 0;
			condition = Condition.KNOCKEDOUT;

		} else if (hp > maxHP)
			currentHP = maxHP;
		else
			currentHP = hp;

	}

	public int getCurrentHP() {

		return currentHP;
	}

	public ArrayList<Effect> getAppliedEffects() {
		return appliedEffects;
	}

	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public int getAttackDamage() {
		return attackDamage;
	}

	public void setAttackDamage(int attackDamage) {
		this.attackDamage = attackDamage;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int currentSpeed) {
		if (currentSpeed < 0)
			this.speed = 0;
		else
			this.speed = currentSpeed;
	}

	public Condition getCondition() {
		return condition;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point currentLocation) {
		this.location = currentLocation;
	}

	public int getAttackRange() {
		return attackRange;
	}

	public ArrayList<Ability> getAbilities() {
		return abilities;
	}

	public int getCurrentActionPoints() {
		return currentActionPoints;
	}

	public void setCurrentActionPoints(int currentActionPoints) {
		if (currentActionPoints > maxActionPointsPerTurn)
			currentActionPoints = maxActionPointsPerTurn;
		else if (currentActionPoints < 0)
			currentActionPoints = 0;
		this.currentActionPoints = currentActionPoints;
	}

	public int getMaxActionPointsPerTurn() {
		return maxActionPointsPerTurn;
	}

	public void setMaxActionPointsPerTurn(int maxActionPointsPerTurn) {
		this.maxActionPointsPerTurn = maxActionPointsPerTurn;
	}

	public int compareTo(Object o) {
		Champion c = (Champion) o;
		if (speed == c.speed)
			return name.compareTo(c.name);
		return -1 * (speed - c.speed);
	}

	@Override
	public String toString() {

		return "Name : " + getName() + "\n" + "HP : " + getCurrentHP() + "/" + maxHP + "\n" + "Mana : " + getMana()
				+ "\n" + "Speed : " + getSpeed() + "\n" + "ActionPoints : " + getCurrentActionPoints() + "\n"
				+ "Atttack Range : " + getAttackRange() + "\n" + " Attack Damage: " + getAttackDamage();
	}

	public String apptoString() {
		return "First Ability : " + getAbilities().get(0).toString() + "Second Ability : "
				+ getAbilities().get(1).toString() + "Third Ability : " + getAbilities().get(2).toString();
	}

	@Override
	public String getInfo() {
		return "Name: " + name + "\n" + "Hp: " + currentHP + "/" + maxHP + "\n" + "Mana: " + mana + "  ," + "Speed: "
				+ speed + "\n" + "Action Points: " + currentActionPoints + "/" + maxActionPointsPerTurn + "\n"
				+ "Attack Damage: " + attackDamage + "  ," + "Attack Range: " + attackRange + "\n" + effectsName();
	}

	public String effectsName() {
		String s = "Effects: ";
		for (int i = 0; i < appliedEffects.size(); i++) {
			if (i >= 2)
				s = s + "\n";
			s = s + appliedEffects.get(i).getName() + " for:" + appliedEffects.get(i).getDuration() + "turns  ";
		}
		s = s + "\n";
		return s;
	}

	public abstract void useLeaderAbility(ArrayList<Champion> targets);
}
