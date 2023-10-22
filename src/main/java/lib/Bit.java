package lib;

public class Bit {
    private byte value;

    public Bit(Bit bit) {
        this.value = bit.value;
    }

    public Bit(boolean bit) {
        this.value = (byte) (bit ? 1 : 0);
    }

    public void set(Bit bit){
        this.value = bit.value;
    }

    public void set(boolean bit){
        this.value = (byte) (bit ? 1 : 0);
    }

    public byte get() {
        return value;
    }

    public boolean getB() {
        return value == 1;
    }
}
