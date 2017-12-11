package com.tony.brown.ec.icon;

import com.joanzapata.iconify.Icon;

/**
 * Created by Tony on 2017/12/10.
 */

public enum EcIcons implements Icon {
    icon_scan('\ue689'),
    icon_ali_pay('\ue67c');

    private char character;

    EcIcons(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
