
package skygod.stages.tutorial;

import java.util.HashMap;
import java.util.Map;

import net.minestom.server.utils.BlockPosition;
import skygod.blocks.Schematic;
import skygod.stages.SchematicGroup;

public class TutorialSchematicGroup implements SchematicGroup {
	
	public static SchematicGroup largeTent = largeTent(); 

	
	public Map<Schematic, BlockPosition> group = new HashMap<Schematic, BlockPosition>();
	
	@Override
	public Map<Schematic, BlockPosition> getGroup() {
		return group;
	}
	
	private static SchematicGroup largeTent() {
		Schematic[] schematics = {
			new Schematic("Schematics/LargeTent1.schem", Schematic.SchematicType.SCHEM),
			new Schematic("Schematics/LargeTent2.schem", Schematic.SchematicType.SCHEM),
			new Schematic("Schematics/LargeTent3.schem", Schematic.SchematicType.SCHEM),
			new Schematic("Schematics/LargeTent4.schem", Schematic.SchematicType.SCHEM)
		};
		BlockPosition[] positions = {
			new BlockPosition(2, -2, 2),
			new BlockPosition(-2, -2, 2),
			new BlockPosition(-2, -2, -2),
			new BlockPosition(2, -2, -2),
		};
		
		return new TutorialSchematicGroup().register(schematics, positions);
	}
	
	public static SchematicGroup debris() {
		Schematic[] schematics = {
			new Schematic("Schematics/RandomDebris" + (int) ((Math.random() * 4) + 1) + ".schem", Schematic.SchematicType.SCHEM)
		};
		BlockPosition[] positions = {
			new BlockPosition(-2, -2, -2),
		};
		
		return new TutorialSchematicGroup().register(schematics, positions);
	}
	
}
