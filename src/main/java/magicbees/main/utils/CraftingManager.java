package magicbees.main.utils;

import magicbees.item.ItemCapsule;
import magicbees.item.types.CombType;
import magicbees.item.types.DropType;
import magicbees.item.types.FluidType;
import magicbees.item.types.NuggetType;
import magicbees.item.types.PollenType;
import magicbees.item.types.PropolisType;
import magicbees.item.types.ResourceType;
import magicbees.item.types.WaxType;
import magicbees.main.Config;
import magicbees.main.utils.compat.ArsMagicaHelper;
import magicbees.main.utils.compat.ForestryHelper;
import magicbees.main.utils.compat.ThaumcraftHelper;
import magicbees.main.utils.compat.ThermalExpansionHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;
import forestry.api.recipes.RecipeManagers;

public class CraftingManager
{
	public static void registerLiquidContainers()
	{
		registerLiquidContainer(Config.magicCapsule);
		registerLiquidContainer(Config.voidCapsule);
	}

	public static void setupCrafting()
	{
		// Broken up into seperate sections to make things a bit easier to find.
		setupVanillaCrafting();
		setupCentrifugeRecipes();
		setupCarpenterRecipes();
	}

	private static void setupVanillaCrafting()
	{
		ItemStack input;
		ItemStack output;

		// Magic capsules
		output = new ItemStack(Config.magicCapsule);
		output.stackSize = 4;
		GameRegistry.addRecipe(new ShapedOreRecipe(output, new Object[] {
				"WWW",
				'W', "waxMagical"
		}));

		// Concentrated Fertilizer -> Forestry fertilizer
		input = Config.miscResources.getStackForType(ResourceType.EXTENDED_FERTILIZER);
		output = ItemInterface.getItemStack("Forestry", "fertilizerCompound", 6);
		GameRegistry.addRecipe(output, new Object[] {
				" S ", " F ", " S ",
				'F', input,
				'S', Blocks.sand
		});
		GameRegistry.addRecipe(output, new Object[] {
				"   ", "SFS", "   ",
				'F', input,
				'S', Blocks.sand
		});

		output = output.copy();
		output.stackSize = 12;
		GameRegistry.addRecipe(output, new Object[] {
				"aaa", "aFa", "aaa",
				'F', input,
				'a', ItemInterface.getItemStack("ash")
		});

		// "bottling" Intellect drops
		GameRegistry.addRecipe(new ItemStack(Items.experience_bottle), new Object[] {
				"DDD", "DBD", "DDD",
				'D', Config.drops.getStackForType(DropType.INTELLECT),
				'B', Items.glass_bottle
		});

		GameRegistry.addRecipe(new ItemStack(Blocks.soul_sand, 4), new Object[] {
				"SwS", "wDw", "SwS",
				'S', Blocks.sand,
				'D', Blocks.dirt,
				'w', Config.wax.getStackForType(WaxType.SOUL)
		});
		GameRegistry.addRecipe(new ItemStack(Blocks.soul_sand, 4), new Object[] {
				"wSw", "SDS", "wSw",
				'S', Blocks.sand,
				'D', Blocks.dirt,
				'w', Config.wax.getStackForType(WaxType.SOUL)
		});

		output = new ItemStack(Config.hiveFrameMagic);
		input = ItemInterface.getItemStack("frameUntreated");
		GameRegistry.addRecipe(output, new Object[] {
				"www", "wfw", "www",
				'w', Config.wax.getStackForType(WaxType.MAGIC),
				'f', input
		});

		GameRegistry.addRecipe(new ItemStack(Config.hiveFrameTemporal), new Object[] {
				"sPs", "PfP", "sPs",
				's', Blocks.sand,
				'P', Config.pollen.getStackForType(PollenType.PHASED),
				'f', Config.hiveFrameMagic
		});

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Config.effectJar), new Object[] {
				"GSG", "QPQ", "GGG",
				'G', Blocks.glass,
				'S', "slabWood",
				'P', Config.pollen.getStackForType(PollenType.UNUSUAL),
				'Q', Items.quartz
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Config.moonDial), new Object[] {
				"DqD", "qrq", "DqD",
				'r', Items.redstone,
				'q', Items.quartz,
				'D', "dyeGreen"
		}));

		GameRegistry.addRecipe(Config.miscResources.getStackForType(ResourceType.SKULL_FRAGMENT), new Object[] {
				"xxx", "xxx", "xxx",
				'x', Config.miscResources.getStackForType(ResourceType.SKULL_CHIP)
		});

		GameRegistry.addRecipe(new ItemStack(Items.skull, 1, 1), new Object[] {
				"xxx", "xxx",
				'x', Config.miscResources.getStackForType(ResourceType.SKULL_FRAGMENT)
		});

		GameRegistry.addRecipe(Config.miscResources.getStackForType(ResourceType.DRAGON_CHUNK), new Object[] {
				"xxx", "xxx",
				'x', Config.miscResources.getStackForType(ResourceType.DRAGON_DUST)
		});

		GameRegistry.addRecipe(new ItemStack(Blocks.dragon_egg, 1), new Object[] {
				"ccc", "cec", "ccc",
				'c', Config.miscResources.getStackForType(ResourceType.DRAGON_CHUNK),
				'e', Config.miscResources.getStackForType(ResourceType.ESSENCE_FALSE_LIFE)
		});

		GameRegistry.addRecipe(new ShapedOreRecipe(Config.miscResources.getStackForType(ResourceType.ESSENCE_EVERLASTING_DURABILITY), new Object[] {
				"gwg", "wiw", "gwg",
				'g', Blocks.glass,
				'w', "waxMagical",
				'i', Blocks.iron_block
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(Config.miscResources.getStackForType(ResourceType.ESSENCE_EVERLASTING_DURABILITY), new Object[] {
				"wgw", "gig", "wgw",
				'g', Blocks.glass,
				'w', "waxMagical",
				'i', Blocks.iron_block
		}));

		GameRegistry.addRecipe(Config.miscResources.getStackForType(ResourceType.ESSENCE_FALSE_LIFE), new Object[] {
				"gwg", "wfw", "gwg",
				'g', Blocks.glass,
				'w', Config.wax.getStackForType(WaxType.SOUL),
				'f', Blocks.red_flower
		});
		GameRegistry.addRecipe(Config.miscResources.getStackForType(ResourceType.ESSENCE_FALSE_LIFE), new Object[] {
				"wgw", "gfg", "wgw",
				'g', Blocks.glass,
				'w', Config.wax.getStackForType(WaxType.SOUL),
				'f', Blocks.red_flower
		});

		GameRegistry.addRecipe(Config.miscResources.getStackForType(ResourceType.ESSENCE_SHALLOW_GRAVE), new Object[] {
				"gwg", "wfw", "gwg",
				'g', Blocks.glass,
				'w', Config.wax.getStackForType(WaxType.SOUL),
				'f', Items.rotten_flesh
		});
		GameRegistry.addRecipe(Config.miscResources.getStackForType(ResourceType.ESSENCE_SHALLOW_GRAVE), new Object[] {
				"wgw", "gfg", "wgw",
				'g', Blocks.glass,
				'w', Config.wax.getStackForType(WaxType.SOUL),
				'f', Items.rotten_flesh
		});

		GameRegistry.addRecipe(Config.miscResources.getStackForType(ResourceType.ESSENCE_LOST_TIME), new Object[] {
				"wgw", "gcg", "wgw",
				'g', Blocks.glass,
				'w', Config.wax.getStackForType(WaxType.SOUL),
				'c', Items.clock
		});
		GameRegistry.addRecipe(Config.miscResources.getStackForType(ResourceType.ESSENCE_LOST_TIME), new Object[] {
				"gwg", "wcw", "gwg",
				'g', Blocks.glass,
				'w', Config.wax.getStackForType(WaxType.SOUL),
				'c', Items.clock
		});

		GameRegistry.addRecipe(Config.miscResources.getStackForType(ResourceType.ESSENCE_FICKLE_PERMANENCE), new Object[] {
				"wew", "gcg", "wew",
				'w', Config.wax.getStackForType(WaxType.SOUL),
				'c', Items.magma_cream,
				'e', Items.egg,
				'g', Blocks.glowstone
		});

		GameRegistry.addRecipe(Config.miscResources.getStackForType(ResourceType.ESSENCE_SCORNFUL_OBLIVION), new Object[] {
				"gst", "sEs", "tsg",
				'g', Config.miscResources.getStackForType(ResourceType.ESSENCE_SHALLOW_GRAVE),
				't', Config.miscResources.getStackForType(ResourceType.ESSENCE_LOST_TIME),
				's', new ItemStack(Items.skull, 1, 1),
				'E', Blocks.dragon_egg,
		});

		// IF YOU UPDATE THESE, CHANGE THE RECIPES IN THAUMCRAFT HELPER, YOU IDIOT.
		input = new ItemStack(Config.hiveFrameMagic);
		GameRegistry.addShapelessRecipe(new ItemStack(Config.hiveFrameResilient), new Object[] {
				Config.miscResources.getStackForType(ResourceType.ESSENCE_EVERLASTING_DURABILITY),
				input
		});

		GameRegistry.addShapelessRecipe(new ItemStack(Config.hiveFrameGentle), new Object[] {
				Config.miscResources.getStackForType(ResourceType.ESSENCE_FALSE_LIFE),
				input
		});

		GameRegistry.addShapelessRecipe(new ItemStack(Config.hiveFrameNecrotic), new Object[] {
				Config.miscResources.getStackForType(ResourceType.ESSENCE_SHALLOW_GRAVE),
				input
		});

		GameRegistry.addShapelessRecipe(new ItemStack(Config.hiveFrameMetabolic), new Object[] {
				Config.miscResources.getStackForType(ResourceType.ESSENCE_FICKLE_PERMANENCE),
				input
		});

		GameRegistry.addShapelessRecipe(new ItemStack(Config.hiveFrameTemporal), new Object[] {
				Config.miscResources.getStackForType(ResourceType.ESSENCE_LOST_TIME),
				input
		});

		GameRegistry.addShapelessRecipe(new ItemStack(Config.hiveFrameOblivion), new Object[] {
				Config.miscResources.getStackForType(ResourceType.ESSENCE_SCORNFUL_OBLIVION),
				ItemInterface.getItemStack("frameProven")
		});
		// </idiot>

		if (OreDictionary.getOres("ingotCopper").size() <= 0)
		{
			NuggetType.COPPER.setInactive();
		}
		else
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(OreDictionary.getOres("ingotCopper").get(0), new Object[] {
					"xxx", "xxx", "xxx",
					'x', "nuggetCopper"
			}));
		}
		if (OreDictionary.getOres("ingotTin").size() <= 0)
		{
			NuggetType.TIN.setInactive();
		}
		else
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(OreDictionary.getOres("ingotTin").get(0), new Object[] {
					"xxx", "xxx", "xxx",
					'x', "nuggetTin"
			}));
		}
		if (OreDictionary.getOres("ingotSilver").size() <= 0)
		{
			NuggetType.SILVER.setInactive();
		}
		else
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(OreDictionary.getOres("ingotSilver").get(0), new Object[] {
					"xxx", "xxx", "xxx",
					'x', "nuggetSilver"
			}));
		}
		if (OreDictionary.getOres("ingotLead").size() <= 0)
		{
			NuggetType.LEAD.setInactive();
		}
		else
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(OreDictionary.getOres("ingotLead").get(0), new Object[] {
					"xxx", "xxx", "xxx",
					'x', "nuggetLead"
			}));
		}

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.iron_ingot), new Object[] {
				"xxx", "xxx", "xxx",
				'x', "nuggetIron"
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.diamond), new Object[] {
				"xxx", "xxx", "xxx",
				'x', "shardDiamond"
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.emerald), new Object[] {
				"xxx", "xxx", "xxx",
				'x', "shardEmerald"
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(ItemInterface.getItemStack("apatite"), new Object[] {
				"xxx", "xxx", "xxx",
				'x', Config.nuggets.getStackForType(NuggetType.APATITE)
		}));

		output = Config.miscResources.getStackForType(ResourceType.DIMENSIONAL_SINGULARITY);
		GameRegistry.addRecipe(output, new Object[] {
				" G ", "QEQ", " W ",
				'E', Items.ender_eye,
				'Q', Blocks.quartz_block,
				'W', Blocks.end_stone,
				'G', Blocks.gold_block
		});

		output = Config.voidCapsule.getCapsuleForLiquid(FluidType.EMPTY);
		output.stackSize = 4;
		GameRegistry.addRecipe(output, new Object[] {
				"T T", "GFG", "T T",
				'G', Blocks.glass_pane,
				'F', Config.miscResources.getStackForType(ResourceType.DIMENSIONAL_SINGULARITY),
				'T', Items.gold_nugget
		});

		output = new ItemStack(Config.magnet);
		GameRegistry.addRecipe(output, new Object[] {
				" i ", "cSc", " d ",
				'i', Items.iron_ingot,
				'c', Items.compass,
				'd', Items.diamond,
				'S', Config.miscResources.getStackForType(ResourceType.DIMENSIONAL_SINGULARITY)
		});

		for (int level = 1; level <= 8; level++)
		{
			output = new ItemStack(Config.magnet, 1, level * 2);
			GameRegistry.addRecipe(new ShapedOreRecipe(output, new Object[] {
					" d ", "mSm", " B ",
					'd', Items.diamond,
					'm', "mb.magnet.level" + (level - 1),
					'B', Blocks.redstone_block,
					'S', Config.miscResources.getStackForType(ResourceType.DIMENSIONAL_SINGULARITY)
			}));
		}
		
		// TODO: Finalize recipe.
		output = new ItemStack(Config.magicApiary);
		GameRegistry.addShapelessRecipe(output, new Object[] {
				Config.pollen.getStackForType(PollenType.UNUSUAL, 2),
				Config.drops.getStackForType(DropType.ENCHANTED, 2),
				new ItemStack(Config.fApicultureBlock, 1, ForestryHelper.ApicultureBlock.APIARY.ordinal())
		});

		if (ThaumcraftHelper.isActive())
		{
			input = Config.miscResources.getStackForType(ResourceType.LORE_FRAGMENT);
			output = new ItemStack(Config.tcMiscResource, 1, ThaumcraftHelper.MiscResource.KNOWLEDGE_FRAGMENT.ordinal());
			GameRegistry.addShapelessRecipe(output, new Object[] {
					input, input, input, input
			});

			if (Config.thaumaturgeBackpackActive)
			{
				// T1 Thaumaturge's backpack
				GameRegistry.addRecipe(new ItemStack(Config.thaumaturgeBackpackT1), new Object[] {
						"SWS", "NCN", "SWS",
						'S', Items.string,
						'W', Blocks.wool,
						'N', new ItemStack(Config.tcMiscResource, 1, ThaumcraftHelper.MiscResource.AMBER.ordinal()),
						'C', Blocks.chest
				});
			}
		}

		if (ArsMagicaHelper.isActive())
		{
			input = ItemInterface.getItemStack("apatite");
			output = Config.miscResources.getStackForType(ResourceType.EXTENDED_FERTILIZER, 4);
			GameRegistry.addShapelessRecipe(output, new Object[] {
					new ItemStack(Config.amEssence, 1, ArsMagicaHelper.EssenceType.EARTH.ordinal()),
					input, input
			});
			GameRegistry.addShapelessRecipe(output, new Object[] {
					new ItemStack(Config.amEssence, 1, ArsMagicaHelper.EssenceType.PLANT.ordinal()),
					input, input
			});
		}
	}

	private static void setupCentrifugeRecipes()
	{
		ItemStack beeswax = ItemInterface.getItemStack("beeswax");
		ItemStack propolis = ItemInterface.getItemStack("propolis");
		propolis.setItemDamage(ForestryHelper.Propolis.PULSATING.ordinal());

		// 20 is the 'average' time to centrifuge a comb.
		RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.MUNDANE),
				new ItemStack[] { beeswax, ItemInterface.getItemStack("honeyDrop"),
						Config.wax.getStackForType(WaxType.MAGIC) },
				new int[] { 90, 60, 10 });
		RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.MOLTEN),
				new ItemStack[] { ItemInterface.getItemStack("refractoryWax"),
						ItemInterface.getItemStack("honeyDrop") },
				new int[] { 86, 8 });
		RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.FORGOTTEN),
				new ItemStack[] { Config.wax.getStackForType(WaxType.AMNESIC), propolis,
						ItemInterface.getItemStack("honeyDrop") },
				new int[] { 50, 50, 22 });
		RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.OCCULT),
				new ItemStack[] { Config.wax.getStackForType(WaxType.MAGIC), ItemInterface.getItemStack("honeyDrop") },
				new int[] { 100, 60 });
		RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.OTHERWORLDLY),
				new ItemStack[] { beeswax, Config.wax.getStackForType(WaxType.MAGIC),
						ItemInterface.getItemStack("honeyDrop") },
				new int[] { 50, 20, 100 });
		RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.PAPERY),
				new ItemStack[] { ItemInterface.getItemStack("beeswax"),
						Config.wax.getStackForType(WaxType.MAGIC),
						new ItemStack(Items.paper) },
				new int[] { 80, 20, 5 });
		RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.INTELLECT),
				new ItemStack[] { Config.wax.getStackForType(WaxType.MAGIC), ItemInterface.getItemStack("honeydew"), Config.drops.getStackForType(DropType.INTELLECT) },
				new int[] { 90, 40, 10 });
		propolis.setItemDamage(ForestryHelper.Propolis.NORMAL.ordinal());
		RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.FURTIVE),
				new ItemStack[] { ItemInterface.getItemStack("beeswax"), propolis,
						ItemInterface.getItemStack("honeydew") },
				new int[] { 90, 20, 35 });
		RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.SOUL),
				new ItemStack[] { Config.wax.getStackForType(WaxType.SOUL), ItemInterface.getItemStack("honeydew") },
				new int[] { 95, 26 });
		RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.TEMPORAL),
				new ItemStack[] { Config.wax.getStackForType(WaxType.MAGIC), Config.pollen.getStackForType(PollenType.PHASED), new ItemStack(Config.fHoneydew, 1) },
				new int[] { 100, 5, 60 });
		RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.TRANSMUTED),
				new ItemStack[] { ItemInterface.getItemStack("beeswax"),
						Config.wax.getStackForType(WaxType.MAGIC), Config.propolis.getStackForType(PropolisType.UNSTABLE) },
				new int[] { 80, 80, 15 });

		RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.AIRY),
				new ItemStack[] { Config.wax.getStackForType(WaxType.MAGIC), new ItemStack(Items.feather) },
				new int[] { 100, 60 });
		RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.FIREY),
				new ItemStack[] { Config.wax.getStackForType(WaxType.MAGIC), new ItemStack(Items.blaze_powder) },
				new int[] { 100, 60 });
		RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.WATERY),
				new ItemStack[] { Config.wax.getStackForType(WaxType.MAGIC), new ItemStack(Items.dye) },
				new int[] { 100, 60 });
		RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.EARTHY),
				new ItemStack[] { Config.wax.getStackForType(WaxType.MAGIC), new ItemStack(Items.clay_ball) },
				new int[] { 100, 60 });

		if (ThaumcraftHelper.isActive())
		{
			RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.TC_AIR),
					new ItemStack[] { Config.wax.getStackForType(WaxType.MAGIC), new ItemStack(Items.feather),
							Config.propolis.getStackForType(PropolisType.AIR) },
					new int[] { 100, 60, 80 });
			RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.TC_FIRE),
					new ItemStack[] { Config.wax.getStackForType(WaxType.MAGIC), new ItemStack(Items.blaze_powder),
							Config.propolis.getStackForType(PropolisType.FIRE) },
					new int[] { 100, 60, 80 });
			RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.TC_WATER),
					new ItemStack[] { Config.wax.getStackForType(WaxType.MAGIC), new ItemStack(Items.dye),
							Config.propolis.getStackForType(PropolisType.WATER) },
					new int[] { 100, 60, 80 });
			RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.TC_EARTH),
					new ItemStack[] { Config.wax.getStackForType(WaxType.MAGIC), new ItemStack(Items.clay_ball),
							Config.propolis.getStackForType(PropolisType.EARTH) },
					new int[] { 100, 60, 80 });
			RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.TC_ORDER),
					new ItemStack[] { Config.wax.getStackForType(WaxType.MAGIC), Config.propolis.getStackForType(PropolisType.ORDER) },
					new int[] { 100, 80 });
			RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.TC_CHAOS),
					new ItemStack[] { Config.wax.getStackForType(WaxType.MAGIC), Config.propolis.getStackForType(PropolisType.CHAOS) },
					new int[] { 100, 80 });

			RecipeManagers.centrifugeManager.addRecipe(8, Config.propolis.getStackForType(PropolisType.AIR),
					new ItemStack[] { propolis, Config.miscResources.getStackForType(ResourceType.TC_DUST_AIR) },
					new int[] { 100, 65 });
			RecipeManagers.centrifugeManager.addRecipe(8, Config.propolis.getStackForType(PropolisType.FIRE),
					new ItemStack[] { propolis, Config.miscResources.getStackForType(ResourceType.TC_DUST_FIRE) },
					new int[] { 100, 65 });
			RecipeManagers.centrifugeManager.addRecipe(8, Config.propolis.getStackForType(PropolisType.WATER),
					new ItemStack[] { propolis, Config.miscResources.getStackForType(ResourceType.TC_DUST_WATER) },
					new int[] { 100, 65 });
			RecipeManagers.centrifugeManager.addRecipe(8, Config.propolis.getStackForType(PropolisType.EARTH),
					new ItemStack[] { propolis, Config.miscResources.getStackForType(ResourceType.TC_DUST_EARTH) },
					new int[] { 100, 65 });
			RecipeManagers.centrifugeManager.addRecipe(8, Config.propolis.getStackForType(PropolisType.ORDER),
					new ItemStack[] { propolis, Config.miscResources.getStackForType(ResourceType.TC_DUST_ORDER) },
					new int[] { 100, 65 });
			RecipeManagers.centrifugeManager.addRecipe(8, Config.propolis.getStackForType(PropolisType.CHAOS),
					new ItemStack[] { propolis, Config.miscResources.getStackForType(ResourceType.TC_DUST_CHAOS) },
					new int[] { 100, 65 });
		}

		if (ArsMagicaHelper.isActive())
		{
			RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.AM_ESSENCE),
					new ItemStack[] { Config.wax.getStackForType(WaxType.MAGIC), new ItemStack(Config.amItemResource), new ItemStack(Config.amItemResource) },
					new int[] { 85, 10, 2 });
			RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.AM_POTENT),
					new ItemStack[] { ItemInterface.getItemStack("beeswax"),
							ItemInterface.getItemStack("refractoryWax"),
							ItemInterface.getItemStack("honeydew") },
					new int[] { 50, 50, 65 });
		}
		if (ThermalExpansionHelper.isActive())
		{

			RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.TE_DESTABILIZED),
					new ItemStack[] { Config.wax.getStackForType(WaxType.MAGIC), Config.drops.getStackForType(DropType.DESTABILIZED) },
					new int[] { 50, 22 });
			RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.TE_CARBON),
					new ItemStack[] { Config.wax.getStackForType(WaxType.MAGIC), Config.drops.getStackForType(DropType.CARBON) },
					new int[] { 50, 22 });
			RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.TE_LUX),
					new ItemStack[] { Config.wax.getStackForType(WaxType.MAGIC), Config.drops.getStackForType(DropType.LUX) },
					new int[] { 50, 22 });
			RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.TE_ENDEARING),
					new ItemStack[] { Config.wax.getStackForType(WaxType.MAGIC), Config.drops.getStackForType(DropType.ENDEARING) },
					new int[] { 50, 22 });

		}

	}

	private static void setupCarpenterRecipes()
	{
		ItemStack input;
		ItemStack output;

		output = ItemInterface.getItemStack("Forestry", "candle", 24);
		RecipeManagers.carpenterManager.addRecipe(30, new FluidStack(FluidRegistry.WATER, 600), null, output, new Object[] {
				" S ", "WWW", "WWW",
				'W', Config.wax,
				'S', Items.string
		});

		output = ItemInterface.getItemStack("Forestry", "candle", 6);
		input = ItemInterface.getItemStack("craftingMaterial");
		input.setItemDamage(ForestryHelper.CraftingMaterial.SILK_WISP.ordinal()); // Set to Silk Wisp
		RecipeManagers.carpenterManager.addRecipe(30, new FluidStack(FluidRegistry.WATER, 600), null, output, new Object[] {
				"WSW",
				'W', Config.wax,
				'S', input
		});

		output = Config.miscResources.getStackForType(ResourceType.AROMATIC_LUMP, 2);
		RecipeManagers.carpenterManager.addRecipe(30, FluidRegistry.getFluidStack("for.honey", 1000), null, output, new Object[] {
				" P ", "JDJ", " P ",
				'P', ItemInterface.getItemStack("pollen"),
				'J', ItemInterface.getItemStack("royalJelly"),
				'D', Config.drops.getStackForType(DropType.ENCHANTED)
		});

		RecipeManagers.carpenterManager.addRecipe(30, FluidRegistry.getFluidStack("for.honey", 1000), null, output, new Object[] {
				" J ", "PDP", " J ",
				'P', ItemInterface.getItemStack("pollen"),
				'J', ItemInterface.getItemStack("royalJelly"),
				'D', Config.drops.getStackForType(DropType.ENCHANTED)
		});

		if (ThaumcraftHelper.isActive())
		{
			// Carpenter recipes
			input = ItemInterface.getItemStack("craftingMaterial");
			input.setItemDamage(3); // Set to Silk Mesh
			output = new ItemStack(Config.thaumaturgeBackpackT2);
			RecipeManagers.carpenterManager.addRecipe(200, new FluidStack(FluidRegistry.WATER, 1000), null, output, new Object[] {
					"WXW", "WTW", "WWW",
					'X', Items.diamond,
					'W', input,
					'T', new ItemStack(Config.thaumaturgeBackpackT1)
			});
		}
	}

	private static void registerLiquidContainer(ItemCapsule baseCapsule)
	{
		ItemStack empty = new ItemStack(baseCapsule, 1, 0);
		ItemStack filled;
		FluidStack liquid = null;

		for (FluidType fluidType : FluidType.values())
		{
			switch (fluidType)
			{
				case EMPTY:
					liquid = null;
					break;
				case WATER:
					liquid = new FluidStack(FluidRegistry.WATER, baseCapsule.getType().capacity);
					break;
				case LAVA:
					liquid = new FluidStack(FluidRegistry.LAVA, baseCapsule.getType().capacity);
					break;
				default:
					liquid = FluidRegistry.getFluidStack(fluidType.liquidID, baseCapsule.getType().capacity);
					break;
			}

			if (liquid != null)
			{
				filled = new ItemStack(baseCapsule, 1, fluidType.ordinal());
				FluidContainerRegistry.registerFluidContainer(liquid, filled, empty);

				// Register with Squeezer/Bottler
				//RecipeManagers.bottlerManager.addRecipe(5, liquid, empty, filled);  Outdated?
				RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[] { filled }, liquid,
						Config.wax.getStackForType(WaxType.MAGIC), 20);
				fluidType.available = true;
			}
		}
		// Empty will be set to unavailable. Obviously, it always is.
		FluidType.EMPTY.available = true;
	}
}