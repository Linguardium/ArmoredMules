package mod.linguardium.armoredmules.mixin.parents;

import mod.linguardium.armoredmules.mixin.HorseEntityAccessor;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.HorseArmorItem;
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

    @Shadow protected abstract void updateSaddle();

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
    @Inject(method="updateSaddle",at=@At("RETURN"))
    protected void ArmoredMules$updateSaddle(CallbackInfo cir) { }

    /* Copied from HorseEntity */
    protected void ArmoredMules$equipArmor(ItemStack stack) {
        this.equipStack(EquipmentSlot.CHEST, stack);
        this.setEquipmentDropChance(EquipmentSlot.CHEST, 0.0f);
    }

    protected void ArmoredMules$setArmorTypeFromStack(ItemStack stack) {
        this.ArmoredMules$equipArmor(stack);
        if (!this.getWorld().isClient) {
            int i;
            this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR).removeModifier(HorseEntityAccessor.getHorseArmorBonusId());
            if (this.isHorseArmor(stack) && (i = ((HorseArmorItem)stack.getItem()).getBonus()) != 0) {
                this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR).addTemporaryModifier(new EntityAttributeModifier(HorseEntityAccessor.getHorseArmorBonusId(), "Horse armor bonus", i, EntityAttributeModifier.Operation.ADDITION));
            }
        }
    }
}
