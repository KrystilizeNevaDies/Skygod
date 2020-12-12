package skygod.stages;

import java.util.Map;

import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.utils.BlockPosition;
import skygod.Schematic;

public interface SchematicGroup {
	abstract Map<Schematic, BlockPosition> getGroup();
	
	default SchematicGroup register(Schematic[] schematics, BlockPosition[] positions) {
		for (int i = 0; i < schematics.length; i++) {
			getGroup().put(schematics[i], positions[i]);
		}
		return this;
	}
	
	public default void load(InstanceContainer instance, BlockPosition position) {
		getGroup().forEach((schematic, schematicPos) -> {
			schematic.load(instance, position.clone().add(schematicPos));
		});
	}
}
