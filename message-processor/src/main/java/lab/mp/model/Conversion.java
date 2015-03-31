package lab.mp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents conversion.
 */
@Document(collection = "conversions")
public class Conversion {
    @Id
    private String id;

    private String from;
    private String to;
    private int count;

    /**
     * Conversion constructor.
     * @param from from which currency
     * @param to to which currency
     * @param count how many transactions have been executed
     */
    public Conversion(final String from, final String to, final int count)
    {
        this.from = from;
        this.to = to;
        this.count = count;
    }

    @Override
    public String toString() {
        return String.format("%s > %s : %d", this.from, this.to, this.count);
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
