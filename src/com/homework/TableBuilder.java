package com.homework;

import java.util.ArrayList;

public class TableBuilder {

    private final String[] header;
    private final ArrayList<String[]> rows;

    public TableBuilder(String[] header) {
        this.header = header;
        this.rows = new ArrayList<>();
    }

    public void append(String[] row) {
        assert header.length == row.length;
        rows.add(row);
    }

    @Override
    public String toString() {
        int[] widths = new int[header.length];
        for (int i = 0; i < header.length; i++) widths[i] = header[i].length();

        for (String[] row : rows) {
            for (int i = 0; i < row.length; i++) widths[i] = Integer.max(row[i].length(), widths[i]);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < header.length; i++) {
            sb.append(String.format("| %s%s ", " ".repeat(widths[i] - header[i].length()), header[i]));
        }
        sb.append("|\n");

        for (String[] row : rows) {
            for (int i = 0; i < header.length; i++) sb.append(String.format("*-%s-", "-".repeat(widths[i])));
            sb.append("*\n");
            for (int i = 0; i < row.length; i++) {
                sb.append(String.format("| %s%s ", " ".repeat(widths[i] - row[i].length()), row[i]));
            }
            sb.append("|\n");
        }

        return sb.toString();
    }

}
