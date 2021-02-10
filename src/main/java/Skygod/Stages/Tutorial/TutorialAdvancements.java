package skygod.stages.tutorial;

import net.minestom.server.MinecraftServer;
import net.minestom.server.advancements.AdvancementRoot;
import net.minestom.server.advancements.AdvancementTab;
import net.minestom.server.advancements.FrameType;
import net.minestom.server.instance.Instance;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import skygod.text.Gradient;
import skygod.text.Gradients;

public class TutorialAdvancements{
	
	AdvancementTab tutorialTab;
	
	public TutorialAdvancements(Instance instance) {
		tutorialTab = MinecraftServer.getAdvancementManager().createTab(instance.getUniqueId().toString() + "tutorial", 
			new AdvancementRoot(
				Gradient.of(Gradients.TUTORIAL, "Tutorial"),
				Gradient.of(Gradients.TUTORIAL, "goals"), 
				new ItemStack(Material.STONE, (byte) 1),
				FrameType.TASK,
				0, 0,
				"minecraft:textures/block/stone.png"
				)
			);
	}
}
