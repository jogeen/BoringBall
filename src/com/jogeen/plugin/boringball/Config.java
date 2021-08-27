package com.jogeen.plugin.boringball;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

/**
 * @Author jogeen
 * @Date 12:39 2021/8/25
 * @Version v1.0
 * @Description
 */
@State(
        name = "PersistentState",
        storages = {@Storage(
                value = "ponyo.xml"
        )}
)
public class Config implements PersistentStateComponent<Config.State> {

    @Nullable
    @Override
    public State getState() {
        return this.state;
    }

    @Override
    public void loadState(State state) {
        XmlSerializerUtil.copyBean(state, this.state);
    }

    public State state = new State();

    public Config() {

        defaultInitState();

    }

    public void defaultInitState() {
        state.MAX_CLICK_COMBO = 0;
    }

    public static Config getInstance() {
        return ServiceManager.getService(Config.class);
    }

    public static final class State {

        /**
         * 敲击的最大连击数
         */
        public int MAX_CLICK_COMBO;

    }
}

