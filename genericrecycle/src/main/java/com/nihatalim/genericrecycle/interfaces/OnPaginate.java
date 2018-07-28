package com.nihatalim.genericrecycle.interfaces;

import android.os.Bundle;

public interface OnPaginate<TListObject> {
    void paginate(int nextPageNumber, int paginationSize, TListObject firstItem, TListObject lastItem, Bundle bundle);
}
