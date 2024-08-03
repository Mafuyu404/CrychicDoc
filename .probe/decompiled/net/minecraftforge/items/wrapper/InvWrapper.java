package net.minecraftforge.items.wrapper;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;

public class InvWrapper implements IItemHandlerModifiable {

    private final Container inv;

    public InvWrapper(Container inv) {
        this.inv = inv;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            InvWrapper that = (InvWrapper) o;
            return this.getInv().equals(that.getInv());
        } else {
            return false;
        }
    }

    public int hashCode() {
        return this.getInv().hashCode();
    }

    @Override
    public int getSlots() {
        return this.getInv().getContainerSize();
    }

    @NotNull
    @Override
    public ItemStack getStackInSlot(int slot) {
        return this.getInv().getItem(slot);
    }

    @NotNull
    @Override
    public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            ItemStack stackInSlot = this.getInv().getItem(slot);
            if (!stackInSlot.isEmpty()) {
                if (stackInSlot.getCount() >= Math.min(stackInSlot.getMaxStackSize(), this.getSlotLimit(slot))) {
                    return stack;
                } else if (!ItemHandlerHelper.canItemStacksStack(stack, stackInSlot)) {
                    return stack;
                } else if (!this.getInv().canPlaceItem(slot, stack)) {
                    return stack;
                } else {
                    int m = Math.min(stack.getMaxStackSize(), this.getSlotLimit(slot)) - stackInSlot.getCount();
                    if (stack.getCount() <= m) {
                        if (!simulate) {
                            ItemStack copy = stack.copy();
                            copy.grow(stackInSlot.getCount());
                            this.getInv().setItem(slot, copy);
                            this.getInv().setChanged();
                        }
                        return ItemStack.EMPTY;
                    } else {
                        stack = stack.copy();
                        if (!simulate) {
                            ItemStack copy = stack.split(m);
                            copy.grow(stackInSlot.getCount());
                            this.getInv().setItem(slot, copy);
                            this.getInv().setChanged();
                            return stack;
                        } else {
                            stack.shrink(m);
                            return stack;
                        }
                    }
                }
            } else if (!this.getInv().canPlaceItem(slot, stack)) {
                return stack;
            } else {
                int m = Math.min(stack.getMaxStackSize(), this.getSlotLimit(slot));
                if (m < stack.getCount()) {
                    stack = stack.copy();
                    if (!simulate) {
                        this.getInv().setItem(slot, stack.split(m));
                        this.getInv().setChanged();
                        return stack;
                    } else {
                        stack.shrink(m);
                        return stack;
                    }
                } else {
                    if (!simulate) {
                        this.getInv().setItem(slot, stack);
                        this.getInv().setChanged();
                    }
                    return ItemStack.EMPTY;
                }
            }
        }
    }

    @NotNull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (amount == 0) {
            return ItemStack.EMPTY;
        } else {
            ItemStack stackInSlot = this.getInv().getItem(slot);
            if (stackInSlot.isEmpty()) {
                return ItemStack.EMPTY;
            } else if (simulate) {
                if (stackInSlot.getCount() < amount) {
                    return stackInSlot.copy();
                } else {
                    ItemStack copy = stackInSlot.copy();
                    copy.setCount(amount);
                    return copy;
                }
            } else {
                int m = Math.min(stackInSlot.getCount(), amount);
                ItemStack decrStackSize = this.getInv().removeItem(slot, m);
                this.getInv().setChanged();
                return decrStackSize;
            }
        }
    }

    @Override
    public void setStackInSlot(int slot, @NotNull ItemStack stack) {
        this.getInv().setItem(slot, stack);
    }

    @Override
    public int getSlotLimit(int slot) {
        return this.getInv().getMaxStackSize();
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return this.getInv().canPlaceItem(slot, stack);
    }

    public Container getInv() {
        return this.inv;
    }
}