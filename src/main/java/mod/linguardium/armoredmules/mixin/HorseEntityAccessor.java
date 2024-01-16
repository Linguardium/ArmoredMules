package mod.linguardium.armoredmules.mixin;

import net.minecraft.entity.passive.HorseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.UUID;

@Mixin(HorseEntity.class)
public interface HorseEntityAccessor {
    @Accessor("HORSE_ARMOR_BONUS_ID")
    static UUID getHorseArmorBonusId() {
        throw new RuntimeException("HorseEntityAccessor Mixin Failed to apply");
    }
}
