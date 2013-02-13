package org.kubek2k.tools;

import org.mockito.internal.util.MockUtil;

public class TestUtil {

    private static final MockUtil mockUtil = new MockUtil();

    public static boolean isMock(Object mock) {
        return mockUtil.isMock(mock);
    }
}
