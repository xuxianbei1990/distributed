package calculation.chapter.ArrayCode;

import java.util.List;

public class NestedIntegerImpl implements NestedInteger {
    @Override
    public boolean isInteger() {
        return true;
    }

    @Override
    public Integer getInteger() {
        return 1;
    }

    @Override
    public List<NestedInteger> getList() {
        return null;
    }
}
