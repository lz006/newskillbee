package de.hdm.skillbee.fragments;

import android.widget.Button;

/**
 * Klasse, die ein Listenelement mit Button repräsentiert
 * @author Moser, Roth, Sonntag, Zanella, Zimmermann
 *
 */

public class RowItemLpgotoQuestion {
	private String title;
    private Button button;

    /**
     * Konstruktor
     * @param title
     * @param button
     */
    public RowItemLpgotoQuestion(String title, Button button) {
        this.title = title;
        this.button = button;

    }

    /**
     * Titel bzw. Bezeichnung auslesen
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Titel bzw. Bezeichnung setzen
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Button auslesen
     * @return
     */
    public Button getButton() {
        return button;
    }

    /**
     * Button setzen
     * @param button
     */
    public void setIcon(Button button) {
        this.button = button;
    }
}
