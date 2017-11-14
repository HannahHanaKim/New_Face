package kr.newface.new_face.new_face;

import android.graphics.drawable.Drawable;

import java.text.Collator;
import java.util.Comparator;

/**
 * Created by User on 2017-11-08.
 */

public class Table_Cell {

    public Drawable image;

    public String name;
    public String num;
    public String text;

    public static final Comparator<Table_Cell> ALPHA_COMPARATOR = new Comparator<Table_Cell>() {
        private final Collator sCollator = Collator.getInstance();

        @Override
        public int compare(Table_Cell mListDate_1, Table_Cell mListDate_2) {
            return sCollator.compare(mListDate_1.name, mListDate_2.name);
        }
    };


}
