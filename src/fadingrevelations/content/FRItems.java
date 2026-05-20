package fadingrevelations.content;

import arc.graphics.Color;
import mindustry.type.Item;

public class FRItems {
    public static Item livingSteel, livingSteelHard, steelAlloy, fuelRod,
            cryogenicGel, igneousAlloy, cryogenicAlloy, nuke,
            optiCrystal, energyCell, nanoFabric, bioMatter;

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

        optiCrystal = new Item("opti-crystal", Color.valueOf("8a2be2")) {{
            cost = 3f; hardness = 5; flammability = 0f; explosiveness = 0.1f; radioactivity = 0.3f; charge = 0.7f;
            localizedName = "Optical Crystal";
            description = "A highly pure crystalline structure grown from silicon and thorium. Used in advanced optical systems and energy weaponry.";
        }};

        energyCell = new Item("energy-cell", Color.valueOf("f0d000")) {{
            cost = 4f; hardness = 2; flammability = 0.6f; explosiveness = 0.8f; radioactivity = 0f; charge = 1.5f;
            localizedName = "Energy Cell";
            description = "A compact energy storage cell made from surge alloy and silicon. Holds a massive electrical charge and is used in high-power equipment.";
        }};

        nanoFabric = new Item("nano-fabric", Color.valueOf("20b2aa")) {{
            cost = 5f; hardness = 7; flammability = 0f; explosiveness = 0f; radioactivity = 0f; charge = 0.3f;
            localizedName = "Nano Fabric";
            description = "An advanced woven material made from living steel infused with phase fabric. Self-repairing and nearly indestructible.";
        }};

        bioMatter = new Item("bio-matter", Color.valueOf("66cc00")) {{
            cost = 1.5f; hardness = 0; flammability = 0.8f; explosiveness = 0.1f; radioactivity = 0f; charge = 0f;
            localizedName = "Biological Matter";
            description = "A concentrated organic compound extracted from spores. Rich in energy and useful as a biological fuel source.";
        }};

        nuke = new Item("nuke", Color.valueOf("22b400")) {{
            hardness = 5; flammability = 0.5f; explosiveness = 2f; radioactivity = 2f;
            localizedName = "Nuke";
            description = "A nuclear warhead made using graphite and fuel rods.";
        }};
    }
}
