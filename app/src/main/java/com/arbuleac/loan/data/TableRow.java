package com.arbuleac.loan.data;

/**
 * @since 3/23/16.
 */
public class TableRow {
    private String title;
    private String description;

    private int type;

    public TableRow(String title, String description, int type) {
        this.title = title;
        this.description = description;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getType() {
        return type;
    }

    public interface Type {
        int DATA = 1;
        int HEADER = 2;
    }


}
