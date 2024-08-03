package yesman.epicfight.skill.guard;

import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.ExtendableEnum;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillDataKeys;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;
import yesman.epicfight.world.entity.eventlistener.HurtEvent;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

public class ParryingSkill extends GuardSkill {

    private static final int PARRY_WINDOW = 8;

    public static GuardSkill.Builder createActiveGuardBuilder() {
        return GuardSkill.createGuardBuilder().addAdvancedGuardMotion(CapabilityItem.WeaponCategories.SWORD, (itemCap, playerpatch) -> itemCap.getStyle(playerpatch) == CapabilityItem.Styles.ONE_HAND ? new StaticAnimation[] { Animations.SWORD_GUARD_ACTIVE_HIT1, Animations.SWORD_GUARD_ACTIVE_HIT2 } : new StaticAnimation[] { Animations.SWORD_GUARD_ACTIVE_HIT2, Animations.SWORD_GUARD_ACTIVE_HIT3 }).addAdvancedGuardMotion(CapabilityItem.WeaponCategories.LONGSWORD, (itemCap, playerpatch) -> new StaticAnimation[] { Animations.LONGSWORD_GUARD_ACTIVE_HIT1, Animations.LONGSWORD_GUARD_ACTIVE_HIT2 }).addAdvancedGuardMotion(CapabilityItem.WeaponCategories.UCHIGATANA, (itemCap, playerpatch) -> new StaticAnimation[] { Animations.SWORD_GUARD_ACTIVE_HIT1, Animations.SWORD_GUARD_ACTIVE_HIT2 }).addAdvancedGuardMotion(CapabilityItem.WeaponCategories.TACHI, (itemCap, playerpatch) -> new StaticAnimation[] { Animations.LONGSWORD_GUARD_ACTIVE_HIT1, Animations.LONGSWORD_GUARD_ACTIVE_HIT2 });
    }

    public ParryingSkill(GuardSkill.Builder builder) {
        super(builder);
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.SERVER_ITEM_USE_EVENT, EVENT_UUID, event -> {
            CapabilityItem itemCapability = ((ServerPlayerPatch) event.getPlayerPatch()).getHoldingItemCapability(InteractionHand.MAIN_HAND);
            if (this.isHoldingWeaponAvailable(event.getPlayerPatch(), itemCapability, GuardSkill.BlockType.GUARD) && this.isExecutableState(event.getPlayerPatch())) {
                ((ServerPlayerPatch) event.getPlayerPatch()).getOriginal().m_6672_(InteractionHand.MAIN_HAND);
            }
            int lastActive = container.getDataManager().getDataValue(SkillDataKeys.LAST_ACTIVE.get());
            if (((ServerPlayerPatch) event.getPlayerPatch()).getOriginal().f_19797_ - lastActive > 16) {
                container.getDataManager().setData(SkillDataKeys.LAST_ACTIVE.get(), ((ServerPlayerPatch) event.getPlayerPatch()).getOriginal().f_19797_);
            }
        });
    }

    @Override
    public void onRemoved(SkillContainer container) {
        super.onRemoved(container);
    }

    @Override
    public void guard(SkillContainer container, CapabilityItem itemCapability, HurtEvent.Pre event, float knockback, float impact, boolean advanced) {
        if (this.isHoldingWeaponAvailable(event.getPlayerPatch(), itemCapability, GuardSkill.BlockType.ADVANCED_GUARD)) {
            DamageSource damageSource = (DamageSource) event.getDamageSource();
            if (this.isBlockableSource(damageSource, true)) {
                ServerPlayer playerentity = ((ServerPlayerPatch) event.getPlayerPatch()).getOriginal();
                boolean successParrying = playerentity.f_19797_ - container.getDataManager().getDataValue(SkillDataKeys.LAST_ACTIVE.get()) < 8;
                float penalty = container.getDataManager().getDataValue(SkillDataKeys.PENALTY.get());
                ((ServerPlayerPatch) event.getPlayerPatch()).playSound(EpicFightSounds.CLASH.get(), -0.05F, 0.1F);
                EpicFightParticles.HIT_BLUNT.get().spawnParticleWithArgument((ServerLevel) playerentity.m_9236_(), HitParticleType.FRONT_OF_EYES, HitParticleType.ZERO, playerentity, damageSource.getDirectEntity());
                if (successParrying) {
                    event.setParried(true);
                    penalty = 0.1F;
                    knockback *= 0.4F;
                } else {
                    penalty += this.getPenalizer(itemCapability);
                    container.getDataManager().setDataSync(SkillDataKeys.PENALTY.get(), penalty, playerentity);
                }
                if (damageSource.getDirectEntity() instanceof LivingEntity livingentity) {
                    knockback += (float) EnchantmentHelper.getKnockbackBonus(livingentity) * 0.1F;
                }
                ((ServerPlayerPatch) event.getPlayerPatch()).knockBackEntity(damageSource.getDirectEntity().position(), knockback);
                float consumeAmount = penalty * impact;
                ((ServerPlayerPatch) event.getPlayerPatch()).consumeStaminaAlways(consumeAmount);
                GuardSkill.BlockType blockType = successParrying ? GuardSkill.BlockType.ADVANCED_GUARD : (((ServerPlayerPatch) event.getPlayerPatch()).hasStamina(0.0F) ? GuardSkill.BlockType.GUARD : GuardSkill.BlockType.GUARD_BREAK);
                StaticAnimation animation = this.getGuardMotion(event.getPlayerPatch(), itemCapability, blockType);
                if (animation != null) {
                    ((ServerPlayerPatch) event.getPlayerPatch()).playAnimationSynchronized(animation, 0.0F);
                }
                if (blockType == GuardSkill.BlockType.GUARD_BREAK) {
                    ((ServerPlayerPatch) event.getPlayerPatch()).playSound(EpicFightSounds.NEUTRALIZE_MOBS.get(), 3.0F, 0.0F, 0.1F);
                }
                this.dealEvent(event.getPlayerPatch(), event, advanced);
                return;
            }
        }
        super.guard(container, itemCapability, event, knockback, impact, false);
    }

    @Override
    protected boolean isBlockableSource(DamageSource damageSource, boolean advanced) {
        return damageSource.is(DamageTypeTags.IS_PROJECTILE) && advanced || super.isBlockableSource(damageSource, false);
    }

    @Nullable
    @Override
    protected StaticAnimation getGuardMotion(PlayerPatch<?> playerpatch, CapabilityItem itemCapability, GuardSkill.BlockType blockType) {
        StaticAnimation animation = itemCapability.getGuardMotion(this, blockType, playerpatch);
        if (animation != null) {
            return animation;
        } else {
            if (blockType == GuardSkill.BlockType.ADVANCED_GUARD) {
                StaticAnimation[] motions = (StaticAnimation[]) ((BiFunction) this.getGuradMotionMap(blockType).getOrDefault(itemCapability.getWeaponCategory(), (BiFunction) (a, b) -> null)).apply(itemCapability, playerpatch);
                if (motions != null) {
                    SkillDataManager dataManager = playerpatch.getSkill(this).getDataManager();
                    int motionCounter = dataManager.getDataValue(SkillDataKeys.PARRY_MOTION_COUNTER.get());
                    dataManager.setDataF(SkillDataKeys.PARRY_MOTION_COUNTER.get(), v -> v + 1);
                    motionCounter %= motions.length;
                    return motions[motionCounter];
                }
            }
            return super.getGuardMotion(playerpatch, itemCapability, blockType);
        }
    }

    @Override
    public Skill getPriorSkill() {
        return EpicFightSkills.GUARD;
    }

    @Override
    protected boolean isAdvancedGuard() {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public List<Object> getTooltipArgsOfScreen(List<Object> list) {
        list.clear();
        StringBuilder sb = new StringBuilder();
        Iterator<WeaponCategory> iter = this.advancedGuardMotions.keySet().iterator();
        while (iter.hasNext()) {
            sb.append(WeaponCategory.ENUM_MANAGER.toTranslated((ExtendableEnum) iter.next()));
            if (iter.hasNext()) {
                sb.append(", ");
            }
        }
        list.add(sb.toString());
        return list;
    }
}