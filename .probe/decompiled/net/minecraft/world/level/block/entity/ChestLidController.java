package net.minecraft.world.level.block.entity;

import net.minecraft.util.Mth;

public class ChestLidController {

    private boolean shouldBeOpen;

    private float openness;

    private float oOpenness;

    public void tickLid() {
        this.oOpenness = this.openness;
        float $$0 = 0.1F;
        if (!this.shouldBeOpen && this.openness > 0.0F) {
            this.openness = Math.max(this.openness - 0.1F, 0.0F);
        } else if (this.shouldBeOpen && this.openness < 1.0F) {
            this.openness = Math.min(this.openness + 0.1F, 1.0F);
        }
    }

    public float getOpenness(float float0) {
        return Mth.lerp(float0, this.oOpenness, this.openness);
    }

    public void shouldBeOpen(boolean boolean0) {
        this.shouldBeOpen = boolean0;
    }
}