package com.paul.paulk.testdatabase.ui.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by paulk on 12/22/2016.
 */

@Getter
@AllArgsConstructor
public class ExpandableMenuItem {
    private List<MenuItem> menuItems;
    private String groupName;

    public int size() {
        return menuItems.size ();
    }

    public MenuItem get(int position) {
        return menuItems.get (position);
    }
}
