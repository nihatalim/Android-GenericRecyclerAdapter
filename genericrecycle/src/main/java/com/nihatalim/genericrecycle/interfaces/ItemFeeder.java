package com.nihatalim.genericrecycle.interfaces;

import java.util.List;

/**
 * Created by nihat on 30.03.2018.
 */

public interface ItemFeeder<TListObject> {
    public List<TListObject> feedItems(int paginationSize, int paginationOrder, TListObject lastItem);
}
