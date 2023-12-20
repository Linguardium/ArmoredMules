package mod.linguardium.armoredmules.mixin.parents;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractHorseEntity.class)
public abstract class AbstractHorseEntityMixin extends AnimalEntity {
    @Shadow public abstract boolean isHorseArmor(ItemStack item);

    @Shadow protected SimpleInventory items;

    protected AbstractHorseEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
    @Inject(method="hasArmorSlot",at=@At("HEAD"), cancellable = true)
    protected void ArmoredMules$hasHorseArmorSlot(CallbackInfoReturnable<Boolean> cir) { }
    @Inject(method="isHorseArmor",at=@At("HEAD"), cancellable = true)
    protected void ArmoredMules$isCompatibleHorseArmor(ItemStack stack, CallbackInfoReturnable<Boolean> cir) { }
    @Inject(method="hasArmorInSlot",at=@At("HEAD"), cancellable = true)
    protected void ArmoredMules$hasHorseArmorInSlot(CallbackInfoReturnable<Boolean> cir) { }
    @Inject(method="writeCustomDataToNbt",at=@At("RETURN"))
    protected void ArmoredMules$saveData(NbtCompound nbt, CallbackInfo cir) { }
    @Inject(method="readCustomDataFromNbt",at=@At("RETURN"))
    protected void ArmoredMules$loadData(NbtCompound nbt, CallbackInfo cir) { }

}
