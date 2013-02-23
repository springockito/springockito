package org.kubek2k.springockito.annotations.internal;

import org.mockito.Answers;
import org.mockito.MockSettings;
import org.mockito.internal.creation.MockSettingsImpl;

public class MockitoMockSettings {

    public static final MockitoMockSettings DEFAULT = new MockitoMockSettings();
    private Class<?>[] extraInterfaces;
    private String mockName;
    private Answers defaultAnswer;

    public MockSettings getMockSettings() {
        return createMockSettings();
    }

    private MockSettings createMockSettings() {
        MockSettings mockSettings = new MockSettingsImpl();

        if (extraInterfaces != null && extraInterfaces.length > 0) {
            mockSettings.extraInterfaces(extraInterfaces);
        }

        if (defaultAnswer != null) {
            mockSettings.defaultAnswer(defaultAnswer.get());
        } else {
            mockSettings.defaultAnswer(Answers.RETURNS_DEFAULTS.get());
        }

        if (mockName != null) {
            mockSettings.name(mockName);
        }
        return mockSettings;
    }

    public MockitoMockSettings withExtraInterfaces(Class<?>[] extraInterfaces) {
        this.extraInterfaces = extraInterfaces;
        return this;
    }

    public MockitoMockSettings withMockName(String mockName) {
        this.mockName = mockName;
        return this;
    }

    public MockitoMockSettings withDefaultAnswer(Answers defaultAnswer) {
        this.defaultAnswer = defaultAnswer;
        return this;
    }
}
