package net.whateclipse.tellurite.mixins;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Inject(method = "set", at = @At("RETURN"))
    private <T> void tellurite$onSetComponent(DataComponentType<? super T> component, T value, CallbackInfoReturnable<T> cir) {
        if (component == DataComponents.ENCHANTMENTS && value instanceof ItemEnchantments enchantments) {
            if (!enchantments.isEmpty()) {
                ((ItemStack) (Object) this).set(DataComponents.UNBREAKABLE, new Unbreakable(true));
            }
        }
    }

    @Inject(method = "applyComponents", at = @At("RETURN"))
    private void tellurite$onApplyComponents(net.minecraft.core.component.DataComponentPatch patch, CallbackInfo ci) {
        ItemEnchantments enchantments = ((ItemStack) (Object) this).get(DataComponents.ENCHANTMENTS);
        if (enchantments != null && !enchantments.isEmpty()) {
            ((ItemStack) (Object) this).set(DataComponents.UNBREAKABLE, new Unbreakable(true));
        }
    }
}
