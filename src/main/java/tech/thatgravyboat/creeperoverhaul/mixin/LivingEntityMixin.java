package tech.thatgravyboat.creeperoverhaul.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "drop", at = @At("TAIL"))
    private void onDrops(DamageSource source, CallbackInfo ci) {
        LivingEntity livingEntity = ((LivingEntity) ((Object) this));
        World level = livingEntity.world;
        BlockPos pos = livingEntity.getBlockPos();
        if (level.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
            Entity killer = source.getAttacker();
            if ((killer instanceof BaseCreeper baseCreeper && baseCreeper.canDropMobsSkull()) || (killer instanceof CreeperEntity creeper && creeper.shouldDropHead())) {
                Item skull = null;
                if (livingEntity instanceof SkeletonEntity) skull = Items.SKELETON_SKULL;
                if (livingEntity instanceof CreeperEntity || livingEntity instanceof BaseCreeper) skull = Items.CREEPER_HEAD;
                if (livingEntity instanceof WitherSkeletonEntity) skull = Items.WITHER_SKELETON_SKULL;
                if (livingEntity instanceof ZombieEntity) skull = Items.ZOMBIE_HEAD;
                if (skull != null) {
                    if (killer instanceof BaseCreeper baseCreeper) baseCreeper.increaseDroppedSkulls();
                    if (killer instanceof CreeperEntity creeper) creeper.onHeadDropped();
                    level.spawnEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(skull)));
                }
            }
        }
    }
}
