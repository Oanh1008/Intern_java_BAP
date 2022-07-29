package com.backend.config;

public class EndPoints {

    private EndPoints() {
    }

    public static final String DEFAULT_PAGE = "/room/showRooms";
    public static final String REDIRECT_PAGE_DEFAULT = "redirect:/room/showRooms";
    public static final String NEW_ROOM = "/room/new";
    public static final String SAVE_ROOM = "/room/save";
    public static final String DELETE_ROOM = "/room/delete/{id}";
    public final static String EDIT_ROOM = "/room/edit/{id}";
}
