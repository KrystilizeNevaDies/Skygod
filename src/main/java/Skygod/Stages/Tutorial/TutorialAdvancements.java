package Skygod.Stages.Tutorial;

import Skygod.ColorGradient;
import Skygod.Gradients;
import net.minestom.server.MinecraftServer;
import net.minestom.server.advancements.AdvancementRoot;
import net.minestom.server.advancements.AdvancementTab;
import net.minestom.server.advancements.FrameType;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

public class TutorialAdvancements{
	
	AdvancementTab tutorialTab;
	
	public TutorialAdvancements() {
		tutorialTab = MinecraftServer.getAdvancementManager().createTab("tutorial", 
			new AdvancementRoot(
				ColorGradient.of(Gradients.TUTORIAL, "Tutorial"),
				ColorGradient.of(Gradients.TUTORIAL, "goals"), 
				new ItemStack(Material.STONE, (byte) 1),
				FrameType.TASK,
				0, 0,
				"minecraft:textures/block/stone.png"
				)
			);
	}
}
