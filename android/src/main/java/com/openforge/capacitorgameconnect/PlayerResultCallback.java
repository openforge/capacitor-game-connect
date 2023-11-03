package com.openforge.capacitorgameconnect;

import com.google.android.gms.games.Player;

public interface PlayerResultCallback {
    void success(Player player);
    void error(String message);
}
