package skygod.entities;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.ObjectEntity;
import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.utils.Position;

public class Fireball extends ObjectEntity {
    private final Block material;
    private final BiConsumer<Fireball, Long> updateConsumer;
    private final Consumer<Fireball> endConsumer;

    public Fireball(Block material, Position initialPosition, BiConsumer<Fireball, Long> updateConsumer, Consumer<Fireball> endConsumer) {
        super(EntityType.ITEM, initialPosition);
        this.material = material;
		this.updateConsumer = updateConsumer;
		this.endConsumer = endConsumer;
		this.getPosition().copy(initialPosition);
        setGravity(0, 0, 0);
        setBoundingBox(0.2f, 0.2f, 0.2f);
    }

    @Override
    public int getObjectData() {
        return material.getBlockId();
    }

    @Override
    public void spawn() {
    	
    }

    @Override
    public void update(long time) {
    	if (updateConsumer != null) updateConsumer.accept(this, time);
    	
    	BlockPosition position = this.getPosition().clone().add(-0.5, -0.5, -0.5).toBlockPosition();
    	
    	// Check if in unloaded chunk
    	if(this.getInstance().getChunkAt(position) == null) {
    		if (endConsumer != null) endConsumer.accept(this);
        	remove();
        	return;
    	}
    	
    	// Check if in non-air block
    	if(this.getInstance().getBlock(position) != Block.AIR) {
        	if (endConsumer != null) endConsumer.accept(this);
        	remove();
        	return;
        }
    }
}