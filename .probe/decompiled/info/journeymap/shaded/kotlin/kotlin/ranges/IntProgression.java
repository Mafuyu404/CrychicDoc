package info.journeymap.shaded.kotlin.kotlin.ranges;

import info.journeymap.shaded.kotlin.kotlin.Metadata;
import info.journeymap.shaded.kotlin.kotlin.collections.IntIterator;
import info.journeymap.shaded.kotlin.kotlin.internal.ProgressionUtilKt;
import info.journeymap.shaded.kotlin.kotlin.jvm.internal.markers.KMappedMarker;
import info.journeymap.shaded.org.jetbrains.annotations.NotNull;
import info.journeymap.shaded.org.jetbrains.annotations.Nullable;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0016\u0018\u0000 \u00172\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0017B\u001f\b\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0002¢\u0006\u0002\u0010\u0006J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0096\u0002J\b\u0010\u0011\u001a\u00020\u0002H\u0016J\b\u0010\u0012\u001a\u00020\u000eH\u0016J\t\u0010\u0013\u001a\u00020\u0014H\u0096\u0002J\b\u0010\u0015\u001a\u00020\u0016H\u0016R\u0011\u0010\u0007\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u0018" }, d2 = { "Linfo/journeymap/shaded/kotlin/kotlin/ranges/IntProgression;", "", "", "start", "endInclusive", "step", "(III)V", "first", "getFirst", "()I", "last", "getLast", "getStep", "equals", "", "other", "", "hashCode", "isEmpty", "iterator", "Linfo/journeymap/shaded/kotlin/kotlin/collections/IntIterator;", "toString", "", "Companion", "info.journeymap.shaded.kotlin.kotlin-stdlib" })
public class IntProgression implements Iterable<Integer>, KMappedMarker {

    @NotNull
    public static final IntProgression.Companion Companion = new IntProgression.Companion(null);

    private final int first;

    private final int last;

    private final int step;

    public IntProgression(int start, int endInclusive, int step) {
        if (step == 0) {
            throw new IllegalArgumentException("Step must be non-zero.");
        } else if (step == Integer.MIN_VALUE) {
            throw new IllegalArgumentException("Step must be greater than Int.MIN_VALUE to avoid overflow on negation.");
        } else {
            this.first = start;
            this.last = ProgressionUtilKt.getProgressionLastElement(start, endInclusive, step);
            this.step = step;
        }
    }

    public final int getFirst() {
        return this.first;
    }

    public final int getLast() {
        return this.last;
    }

    public final int getStep() {
        return this.step;
    }

    @NotNull
    public IntIterator iterator() {
        return new IntProgressionIterator(this.first, this.last, this.step);
    }

    public boolean isEmpty() {
        return this.step > 0 ? this.first > this.last : this.first < this.last;
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof IntProgression && (this.isEmpty() && ((IntProgression) other).isEmpty() || this.first == ((IntProgression) other).first && this.last == ((IntProgression) other).last && this.step == ((IntProgression) other).step);
    }

    public int hashCode() {
        return this.isEmpty() ? -1 : 31 * (31 * this.first + this.last) + this.step;
    }

    @NotNull
    public String toString() {
        return this.step > 0 ? this.first + ".." + this.last + " step " + this.step : this.first + " downTo " + this.last + " step " + -this.step;
    }

    @Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006¨\u0006\t" }, d2 = { "Linfo/journeymap/shaded/kotlin/kotlin/ranges/IntProgression$Companion;", "", "()V", "fromClosedRange", "Linfo/journeymap/shaded/kotlin/kotlin/ranges/IntProgression;", "rangeStart", "", "rangeEnd", "step", "info.journeymap.shaded.kotlin.kotlin-stdlib" })
    public static final class Companion {

        private Companion() {
        }

        @NotNull
        public final IntProgression fromClosedRange(int rangeStart, int rangeEnd, int step) {
            return new IntProgression(rangeStart, rangeEnd, step);
        }
    }
}