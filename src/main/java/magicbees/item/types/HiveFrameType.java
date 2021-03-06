package magicbees.item.types;

import magicbees.main.utils.LocalizationManager;

public enum HiveFrameType
{
	MAGIC("Magic", 240, 1f, 1f, 1f, 2f, 0.6f),
	RESILIENT("Resilient", 800, 1f, 1f, 1f, 2f, 0.5f),
	GENTLE("Gentle", 200, 1f, 0.7f, 1.5f, 1.4f, 0.01f),
	METABOLIC("Metabolic", 130, 1f, 1.8f, 1f, 1.2f, 1f),
	NECROTIC("Necrotic", 280, 1f, 1f, 0.3f, 0.75f, 1.2f),
	TEMPORAL("Temporal", 300, 1f, 1f, 2.5f, 1f, 0.8f),
	OBLIVION("Oblivion", 50, 1f, 1f, 0.0001f, 0f, 1f),
	;
	
	private HiveFrameType(String name, int damage,
						  float territory, float mutation, float lifespan, float production, float geneticDecay)
	{
		this(name, damage, territory, mutation, lifespan, production, 1f, geneticDecay, false, false, false, false);
	}
	
	private HiveFrameType(String name, int damage,
						  float territory, float mutation, float lifespan, float production, float flowering, float geneticDecay,
						  boolean sealed, boolean lit, boolean sunlit, boolean hellish)
	{
		this.frameName = name;
		this.maxDamage = damage;
		
		this.territoryMod = territory;
		this.mutationMod = mutation;
		this.lifespanMod = lifespan;
		this.productionMod = production;
		this.floweringMod = flowering;
		this.geneticDecayMod = geneticDecay;
		this.isSealed = sealed;
		this.isLit = lit;
		this.isSunlit = sunlit;
		this.isHellish = hellish;
	}
	
	private String frameName;
	public int maxDamage;
	
	public float territoryMod;
	public float mutationMod;
	public float lifespanMod;
	public float productionMod;
	public float floweringMod;
	public float geneticDecayMod;
	public boolean isSealed;
	public boolean isLit;
	public boolean isSunlit;
	public boolean isHellish;
	
	public String getName()
	{
		return this.frameName;
	}
	
	public String getLocalizedName()
	{
		return LocalizationManager.getLocalizedString("frame." + this.frameName);
	}
}
