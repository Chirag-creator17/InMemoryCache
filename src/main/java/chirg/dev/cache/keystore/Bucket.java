package chirg.dev.cache.keystore;

public class Bucket<KEY, VAL> {
    public KEY key;
    public VAL value;

    public Bucket<KEY, VAL> prev;
    public Bucket<KEY, VAL> next;

    public Bucket(KEY key, VAL value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "{" + this.key + " : " + this.value + "} -->";
    }
}