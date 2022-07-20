package com.backend.config;

public class EndPoints {
    private EndPoints() {
    }
    public final static String DEFAULT_PAGE = "/room/showRooms/{page}";
    public final static String RETURN_PAGE_DEFAULT = "/room/showRooms/1";
    public final static String REDIRECT_PAGE_DEFAULT = "redirect:/room/showRooms/1";
    public final static String NEW_ROOM = "/room/new";
    public final static String SAVE_ROOM = "/room/save";
    public final static String DELETE_ROOM = "/room/delete/{id}";
    public final static String EDIT_ROOM = "/room/edit/{id}";
}
