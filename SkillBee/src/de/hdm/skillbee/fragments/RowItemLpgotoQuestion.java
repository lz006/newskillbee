package de.hdm.skillbee.fragments;

import android.widget.Button;

public class RowItemLpgotoQuestion {
	private String title;
    private Button button;

    public RowItemLpgotoQuestion(String title, Button button) {
        this.title = title;
        this.button = button;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Button getButton() {
        return button;
    }

    public void setIcon(Button button) {
        this.button = button;
    }
}
