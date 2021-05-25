package nl.whizzkit.powerfunctions;

public class TransmitInfo {

    public final int frequency;
    public final int[] pattern;

    public TransmitInfo(int frequency, int[] pattern) {
        this.frequency = frequency;
        this.pattern = pattern;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("TransmitInfo [").append(frequency).append("]: ");
        if (pattern != null) {
            stringBuilder.append(" Count:").append(this.pattern.length).append(": ");
            for (int v : pattern) {
                stringBuilder.append(", ").append(v);
            }
        }
        return stringBuilder.toString();
    }


}
