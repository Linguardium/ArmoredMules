package mod.linguardium.armoredmules.mixin;

import mod.linguardium.armoredmules.mixin.parents.AbstractHorseEntityMixin;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.MuleEntity;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MuleEntity.class)
public abstract class MuleMixin extends AbstractHorseEntityMixin {

    protected MuleMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void ArmoredMules$hasHorseArmorSlot(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }

    @Override
    protected void ArmoredMules$isCompatibleHorseArmor(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(!stack.isEmpty() && stack.getItem() instanceof HorseArmorItem);
    }

    @Override
    protected void ArmoredMules$hasHorseArmorInSlot(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(isHorseArmor(this.items.getStack(1)));
    }

    @Override
    protected void ArmoredMules$saveData(NbtCompound nbt, CallbackInfo cir) {
        if (!this.items.getStack(1).isEmpty()) {
            nbt.put("ArmorItem", this.items.getStack(1).writeNbt(new NbtCompound()));
        }
    }

    @Override
    protected void ArmoredMules$loadData(NbtCompound nbt, CallbackInfo cir) {
        if (nbt.contains("ArmorItem", NbtElement.COMPOUND_TYPE)) {
            ItemStack itemStack = ItemStack.fromNbt(nbt.getCompound("ArmorItem"));
            if (!itemStack.isEmpty() && this.isHorseArmor(itemStack)) {
                this.items.setStack(1, itemStack);
            }
        }
        ItemStack armorSlotStack = items.getStack(1);
        if (!armorSlotStack.isEmpty() && isHorseArmor(armorSlotStack)) {
            equipStack(EquipmentSlot.CHEST,armorSlotStack);
        }
    }
}
