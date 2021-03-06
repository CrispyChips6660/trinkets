package dev.emi.trinkets.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import dev.emi.trinkets.api.ITrinket;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

/**
 * Ticks trinkets
 */
@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {
	@Shadow
	@Final
	public PlayerEntity player;

	@Inject(at = @At("TAIL"), method="updateItems")
	public void updateItems(CallbackInfo info) {
		Inventory inv = TrinketsApi.getTrinketsInventory(player);
		for (int i = 0; i < inv.getInvSize(); i++) {
			ItemStack stack = inv.getInvStack(i);
			if (stack.getItem() instanceof ITrinket) {
				ITrinket trinket = (ITrinket) stack.getItem();
				trinket.tick(player, stack);
			}
		}
	}
}