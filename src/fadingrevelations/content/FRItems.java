package fadingrevelations.content;

import arc.graphics.Color;
import mindustry.type.Item;

public class FRItems {
    public static Item livingSteel, livingSteelHard, steelAlloy, fuelRod,
            cryogenicGel, igneousAlloy, cryogenicAlloy,
            ammoLevel1, ammoLevel2, ammoLevel3, healAmmo, homingAmmo, nanoAmmo, nuke;

    public static void load() {
        livingSteel = new Item("living-steel", Color.valueOf("88299f")) {{
            hardness = 3; flammability = 0.5f; explosiveness = 0f; cost = 2.5f;
            frames = 24; frameTime = 5;
            localizedName = "Living Steel";
            description = "A living piece of metal. Titanium infused with spores.";
        }};

        livingSteelHard = new Item("living-steel-hard", Color.valueOf("6d59c6")) {{
            hardness = 4; flammability = 0.3f; explosiveness = 0f; radioactivity = 0.2f; cost = 2.5f;
            localizedName = "Hardened Living Steel";
            description = "Living steel that was infused with thorium to make it more durable.";
        }};

        steelAlloy = new Item("steel-alloy", Color.valueOf("ba6a83")) {{
            flammability = 0.6f; explosiveness = 0.2f; charge = 0.9f; radioactivity = 0f;
            localizedName = "Steel Amalgam";
            description = "A compound material made from Copper, Living Steel and Surge Alloy.";
        }};

        fuelRod = new Item("fuel-rod", Color.valueOf("22b400")) {{
            cost = 2.5f; flammability = 0.1f; explosiveness = 0.2f; radioactivity = 2f;
            localizedName = "Fuel Rod";
            description = "A fuel rod constructed from layering thorium on a durable plastanium and lead core.";
        }};

        cryogenicGel = new Item("cryogenic-gel", Color.valueOf("4fa4c0")) {{
            hardness = 0; flammability = 0f; explosiveness = 0f; radioactivity = 0f; charge = 0f;
            localizedName = "Cryogenic Gel";
            description = "A very cold material made from mixing cryofluid and silicon. Used as one of the components to create [cyan]Cryogenic Alloy.";
        }};

        igneousAlloy = new Item("igneous-alloy", Color.valueOf("ba6a83")) {{
            hardness = 3; cost = 1.3f; charge = 0.55f; explosiveness = 0f; radioactivity = 0f;
            localizedName = "Igneous Alloy";
            description = "A very hot material smelted from surge alloy and titanium using slag. Used as a component needed to create [cyan]Cryogenic Alloy.";
        }};

        cryogenicAlloy = new Item("cryogenic-alloy", Color.valueOf("2cbcc9")) {{
            hardness = 5; charge = 0.45f; explosiveness = 0f; flammability = 0f; radioactivity = 0f; cost = 1.4f;
            localizedName = "Cryogenic Alloy";
            description = "A shock-cooled, highly durable metal made from Igneous Alloy, Cryogenic Gel and Neutron Fluid. Useful in advanced electronic components.";
        }};

        ammoLevel1 = new Item("ammo-level-1", Color.valueOf("9d7600")) {{
            cost = 2.5f; hardness = 4; flammability = 0.1f; explosiveness = 0.1f;
            localizedName = "Copper Ammo";
            description = "Simple, boring ammo with absolutely no effects whatsoever.";
        }};

        ammoLevel2 = new Item("ammo-level-2", Color.valueOf("002b92")) {{
            cost = 2.5f; hardness = 6; flammability = 0.1f; explosiveness = 0.3f;
            localizedName = "Titanium Ammo";
            description = "Ammo made from titanium. Decent damage and has pierce.";
        }};

        ammoLevel3 = new Item("ammo-level-3", Color.valueOf("df0505")) {{
            cost = 2.5f; hardness = 3; flammability = 0.3f; explosiveness = 0.6f;
            localizedName = "Explosive Ammo";
            description = "Ammo made from blast compound. Decent damage and has a frag effect in some turrets.";
        }};

        healAmmo = new Item("heal-ammo", Color.valueOf("77df05")) {{
            cost = 2.5f; hardness = 0; flammability = 0.2f; explosiveness = 0.05f;
            localizedName = "Healing Ammo";
            description = "Ammo made from spore pods. No damage but heals hit allied blocks.";
        }};

        homingAmmo = new Item("homing-ammo", Color.valueOf("df21fa")) {{
            cost = 2.5f; hardness = 1; flammability = 0.2f; explosiveness = 1.2f; radioactivity = 0.3f;
            localizedName = "Homing Ammo";
            description = "Ammo made from thorium. Good damage and has a homing effect.";
        }};

        nanoAmmo = new Item("nano-ammo", Color.valueOf("6e0268")) {{
            cost = 2.5f; hardness = 0; flammability = 0.6f; explosiveness = 0.1f;
            localizedName = "Nano Ammo";
            description = "Ammo made from living steel that infects enemies with nano bots which burn them from the inside.";
        }};

        nuke = new Item("nuke", Color.valueOf("22b400")) {{
            hardness = 5; flammability = 0.5f; explosiveness = 2f; radioactivity = 2f;
            localizedName = "Nuke";
            description = "A nuclear warhead made using graphite and fuel rods.";
        }};
    }
}
